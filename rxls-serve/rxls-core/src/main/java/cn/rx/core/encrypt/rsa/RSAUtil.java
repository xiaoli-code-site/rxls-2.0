package cn.rx.core.encrypt.rsa;



import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 非对称加密
 */
@Slf4j
public class RSAUtil {

    public static String MODE_NAME = "RSA";

    /**
     * 公钥
     */
    public static String publicKey;
    /**
     * 私钥
     */
    public static String privateKey;


    //初始化
    static {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(MODE_NAME);
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            publicKey = Base64.encodeBase64String(rsaPublicKey.getEncoded());
            privateKey = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
        } catch (Exception e) {
            log.error("RSA初始化失败", e);
        }
    }


    /**
     * 私钥解密
     *
     * @param text 待解密的文本
     * @return 解密后的文本
     */
    public static String decrypt(String text) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory keyFactory = KeyFactory.getInstance(MODE_NAME);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
            Cipher cipher = Cipher.getInstance(MODE_NAME);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // 将传入的Base64编码字符串解码为字节数组
            byte[] encryptedBytes = Base64.decodeBase64(text);
            return new String(cipher.doFinal(encryptedBytes));
        } catch (Exception e) {
            log.error("RSA私钥解密失败", e);
            return null;
        }
    }


    /**
     * 公钥加密
     *
     * @param text 待加密的文本
     * @return 加密后的文本
     */
    public static String encrypt(String text) {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        PublicKey pubKey = null;
        try {
            pubKey = KeyFactory.getInstance(MODE_NAME).generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance(MODE_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return Base64.encodeBase64String(cipher.doFinal(text.getBytes()));
        } catch (Exception e) {
            log.error("RSA公钥加密失败", e);
            return null;
        }
    }

    /**
     * 获取公钥
     *
     * @return PEM格式公钥
     */
    public static String getPublic() {
        return publicKey;
    }


}
