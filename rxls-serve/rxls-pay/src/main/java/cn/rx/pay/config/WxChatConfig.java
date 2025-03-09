package cn.rx.pay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wechat")
public class WxChatConfig {
    /**
     * 微信appid
     */
    private String appid;
    /**
     * 秘钥
     */
    private String secret;
    /**
     * pem存储路径
     */
    private String privateKey;
    /**
     * 支付回调网址
     */
    private String payNotify;
    /**
     * 退款回调网址
     */
    private String refundNotify;
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 商户秘钥
     */
    private String mchSerialKey;
    /**
     * 商户证书序列号
     */
    private String mchSerialNo;

    /**
     * 商户回调通知v3秘钥
     */
    private String v3PrivateKey;

    /**
     * 微信服务器地址
     */
    private String domain;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPayNotify() {
        return payNotify;
    }

    public void setPayNotify(String payNotify) {
        this.payNotify = payNotify;
    }

    public String getRefundNotify() {
        return refundNotify;
    }

    public void setRefundNotify(String refundNotify) {
        this.refundNotify = refundNotify;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchSerialNo() {
        return mchSerialNo;
    }

    public void setMchSerialNo(String mchSerialNo) {
        this.mchSerialNo = mchSerialNo;
    }

    public String getV3PrivateKey() {
        return v3PrivateKey;
    }

    public void setV3PrivateKey(String v3PrivateKey) {
        this.v3PrivateKey = v3PrivateKey;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getMchSerialKey() {
        return mchSerialKey;
    }

    public void setMchSerialKey(String mchSerialKey) {
        this.mchSerialKey = mchSerialKey;
    }
}
