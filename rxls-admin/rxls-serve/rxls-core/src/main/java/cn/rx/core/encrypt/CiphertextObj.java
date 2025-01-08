package cn.rx.core.encrypt;

import lombok.Data;

/**
 * 加密对象
 */
@Data
public class CiphertextObj {
    /**
     * 加密文本
     */
    private String encryptText;
    /**
     * 密钥
     */
    private String apiKey;
}
