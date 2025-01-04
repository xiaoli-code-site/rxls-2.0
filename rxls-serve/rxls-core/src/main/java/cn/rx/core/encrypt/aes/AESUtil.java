package cn.rx.core.encrypt.aes;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES
 */
public class AESUtil {

    // 算法模式
    private static final String ALGORITHM_RES = "AES/ECB/PKCS5Padding";

    // 加密模式
    private static final String ENCRYPT_NAME = "AES";

    /**
     * 生成指定长度的AES密钥（Base64编码）
     *
     * @return Base64编码的AES密钥
     */
    public static String generateBase64Key() throws NoSuchAlgorithmException {
        // 生成随机密钥
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128, new SecureRandom());
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] key = secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(key);
    }


    /**
     * 加密
     * @param content 需要加密的内容
     * @param encryptKey 密钥
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(ENCRYPT_NAME);
        kg.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHM_RES);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), ENCRYPT_NAME));
        byte[] bytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        return  Base64.getEncoder().encodeToString(bytes);
    }


    /**
     * 解密
     * @param encryptStr 加密文本
     * @param decryptKey 密钥
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(ENCRYPT_NAME);
        kg.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHM_RES);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), ENCRYPT_NAME));
        byte[] decryptBytes = cipher.doFinal(Base64.getDecoder().decode(encryptStr));
        return new String(decryptBytes);
    }

}


