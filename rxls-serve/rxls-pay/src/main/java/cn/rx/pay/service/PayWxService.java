package cn.rx.pay.service;

import cn.rx.common.util.HttpUtil;

import cn.rx.pay.config.WxChatConfig;
import cn.rx.pay.query.wx.WxRefundQuery;
import cn.rx.pay.query.wx.WxPrepayQuery;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.Transaction;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.AmountReq;
import com.wechat.pay.java.service.refund.model.CreateRequest;
import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.RefundNotification;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * jsapi支付工具类
 */
@Component
public class PayWxService {

    /**
     * 微信配置
     */
    @Resource
    private WxChatConfig wxChatConfig;



    /**
     * 回调处理配置
     */
    private NotificationConfig notificationConfig;

    /**
     * jsapi支付对象
     */
    private JsapiServiceExtension jsapiServiceExtension;

    /**
     * 退款服务配置
     */
    public RefundService refundService;


    /**
     * 初始化
     */
    @PostConstruct
    public void intiPayInfo() {

        // 初始化 支付回调配置
        notificationConfig = new RSAAutoCertificateConfig.Builder().merchantId(wxChatConfig.getMchId()).privateKeyFromPath(wxChatConfig.getPrivateKey()).merchantSerialNumber(wxChatConfig.getMchSerialNo()).apiV3Key(wxChatConfig.getV3PrivateKey()).build();

        // 初始化商户配置
        Config config = new RSAAutoCertificateConfig.Builder().merchantId(wxChatConfig.getMchId()).privateKeyFromPath(wxChatConfig.getPrivateKey()).merchantSerialNumber(wxChatConfig.getMchSerialNo()).apiV3Key(wxChatConfig.getV3PrivateKey()).build();

        // 初始化服务
        jsapiServiceExtension = new JsapiServiceExtension.Builder().config(config).signType("RSA") // 不填默认为RSA
                .build();
        // 初始化服务
        refundService = new RefundService.Builder().config(config).build();
    }


    /**
     * 支付回调检查
     *
     * @param request 请求内容
     * @return 支付回调内容
     */
    public Transaction payNotifyHandler(HttpServletRequest request) {
        // 构造 RequestParam
        RequestParam requestParam = new RequestParam.Builder().nonce(request.getHeader("Wechatpay-Nonce")).signature(request.getHeader("Wechatpay-Signature")).signType(request.getHeader("Wechatpay-Signature-Type")).timestamp(request.getHeader("Wechatpay-Timestamp")).serialNumber(request.getHeader("Wechatpay-Serial")).body(HttpUtil.bodyHandler(request)).build();

        // 初始化 NotificationParser
        NotificationParser parser = new NotificationParser(notificationConfig);
        try {
            // 以支付通知回调为例，验签、解密并转换成 Transaction
            return parser.parse(requestParam, Transaction.class);
        } catch (ValidationException e) {
            // 签名验证失败，返回 401 UNAUTHORIZED 状态码
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 退款回调检查
     *
     * @param request 请求内容
     * @return 支付回调内容
     */
    public RefundNotification refundNotifyHandler(HttpServletRequest request) {
        // 构造 RequestParam
        RequestParam requestParam = new RequestParam.Builder().nonce(request.getHeader("Wechatpay-Nonce")).signature(request.getHeader("Wechatpay-Signature")).signType(request.getHeader("Wechatpay-Signature-Type")).timestamp(request.getHeader("Wechatpay-Timestamp")).serialNumber(request.getHeader("Wechatpay-Serial")).body(HttpUtil.bodyHandler(request)).build();

        // 初始化 NotificationParser
        NotificationParser parser = new NotificationParser(notificationConfig);
        try {
            // 以支付通知回调为例，验签、解密并转换成 Transaction
            return parser.parse(requestParam, RefundNotification.class);
        } catch (ValidationException e) {
            // 签名验证失败，返回 401 UNAUTHORIZED 状态码
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 支付下单，并返回JSAPI调起支付数据
     *
     * @param prepayInfo 下单信息
     * @return JSAPI调起支付数据
     */
    public PrepayWithRequestPaymentResponse prepayWithRequestPayment(WxPrepayQuery prepayInfo) {
        PrepayRequest request = new PrepayRequest();
        request.setAppid(wxChatConfig.getAppid());
        request.setMchid(wxChatConfig.getMchId());
        request.setNotifyUrl(wxChatConfig.getPayNotify());
        request.setOutTradeNo(prepayInfo.getOrderSn());
        Payer payer = new Payer();
        payer.setOpenid(prepayInfo.getOpenid());
        Amount amount = new Amount();
        amount.setTotal(prepayInfo.getTotal());
        amount.setCurrency("CNY");
        request.setAmount(amount);
        request.setPayer(payer);
        request.setAttach(prepayInfo.getAttach());
        request.setDescription(prepayInfo.getDescription());
        // 调用接口
        return jsapiServiceExtension.prepayWithRequestPayment(request);
    }




    /**
     * 退款
     *
     * @param info 退款信息
     * @return 退款结果
     */
    public Refund refund(WxRefundQuery info) {
        CreateRequest request = new CreateRequest();
        request.setNotifyUrl(wxChatConfig.getRefundNotify());
        request.setTransactionId(info.getTransactionId());
        request.setOutRefundNo(info.getOutRefundNo());
        AmountReq amountReq = new AmountReq();
        amountReq.setRefund(info.getRefundFee());
        amountReq.setCurrency("CNY");
        amountReq.setTotal(info.getTotalFee());
        request.setAmount(amountReq);
        request.setReason(info.getRefundDesc());
        return refundService.create(request);

    }


    /**
     * 关闭订单
     */
    public void closeOrder() {
        CloseOrderRequest request = new CloseOrderRequest();
        // 调用request.setXxx(val)设置所需参数，具体参数可见Request定义
        // 调用接口
        jsapiServiceExtension.closeOrder(request);
    }




}
