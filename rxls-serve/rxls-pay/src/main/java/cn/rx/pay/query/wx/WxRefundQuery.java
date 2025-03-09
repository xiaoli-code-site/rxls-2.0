package cn.rx.pay.query.wx;


import lombok.Data;

/**
 * 退款参数封装
 */
@Data
public class WxRefundQuery {

    /**
     * 商户退款单号
     */
    private String outRefundNo;

    /**
     * 总金额
     */
    private Long totalFee;

    /**
     * 退款金额
     */
    private Long refundFee;

    /**
     * 随机数
     */
    private String nonceStr;

    /**
     * 微信支付订单号
     */
    private String transactionId;

    /**
     * 退款描述
     */
    private String refundDesc;
}
