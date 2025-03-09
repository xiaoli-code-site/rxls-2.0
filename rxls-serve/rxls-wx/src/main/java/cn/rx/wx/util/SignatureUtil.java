package cn.rx.wx.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;

/**
 * 微信公众号校验签名工具类
 */
public class SignatureUtil {

    // 此处的token即为微信公众号接口配置填写的签名
    private static final String TOKEN = "aB3dEfgHjKlMn0pQrStUvWxYz1C2e4";

    /**
     * 验证签名
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @return 是否有效
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {

        // 1.将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = new String[] { TOKEN, timestamp, nonce };
        Arrays.sort(arr);

        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder content = new StringBuilder();
        for (String s : arr) {
            content.append(s);
        }
        MessageDigest md = null;
        String tmpStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 3.将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return Objects.nonNull(tmpStr) && tmpStr.equals(signature.toUpperCase());

    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray 字节数组
     * @return 十六进制字符串
     */
    private static String byteToStr(byte[] byteArray) {
        StringBuilder strDigest = new StringBuilder();
        for (byte b : byteArray) {
            strDigest.append(byteToHexStr(b));
        }
        return strDigest.toString();
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte 字节
     * @return 十六进制字符串
     */
    private static String byteToHexStr(byte mByte) {
        char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = digit[mByte & 0X0F];
        return new String(tempArr);
    }

}
