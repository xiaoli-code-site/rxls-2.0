package cn.rx.wx.service;

import cn.rx.common.util.HttpUtil;
import cn.rx.wx.util.SignatureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 微信公众号业务实现
 */
@Slf4j
@Service
public class WeChatService {

    /**
     * 不加密,仅与微信服务器联调测试
     */
    public String testInfo(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("UTF-8");
        // 签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr
        if (SignatureUtil.checkSignature(signature, timestamp, nonce)) {
            // 随机字符串
            String echoStr = request.getParameter("echostr");
//            log.info("校验信息是否是从微信服务器发出,接入成功");
            return echoStr;
        }
//        log.error("校验信息是否是从微信服务器发出,接入失败");
        return "";
    }


    /**
     * 接受微信消息,不加密
     */
    public void processPost(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("UTF-8");
        // 从请求体中读取内容
        Map map = HttpUtil.bodyXmlHandler(request);
        // 现在xmlMessage包含了完整的XML数据包
//        log.info("接受微信消息: " + map);

    }
}
