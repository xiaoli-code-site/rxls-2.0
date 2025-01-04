package cn.rx.common.util;


import org.mindrot.jbcrypt.BCrypt;

/**
 * 密码加密、解密
 */
public class BcryptUtil {

    /**
     * 密码加密
     *
     * @param password 密码
     * @return 加密后的文本
     */
    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * 密码校对
     *
     * @param plaintext  明文密码
     * @param ciphertext 密文密码
     * @return 是否正确
     */
    public static Boolean proofread(String plaintext, String ciphertext) {
        return BCrypt.checkpw(plaintext, ciphertext);
    }
}
