package cn.rx.common.util;

import java.util.Objects;

/**
 * 参数校验
 */
public class QueryUtil {

    /**
     * 名称检查，是否只包含 英文、中文、数字或下划线,长度最小为2,容许中间出现空格,最大为20个字符
     *
     * @param str 字符串
     */
    public static boolean validateName(String str) {
        // 正则表达式匹配英文、中文、数字或下划线
        String regex = "^[a-zA-Z0-9_\\u4e00-\\u9fa5][a-zA-Z0-9_\\u4e00-\\u9fa5]*([ ]?[a-zA-Z0-9_\\u4e00-\\u9fa5]+)*[a-zA-Z0-9_\\u4e00-\\u9fa5]$";
        if (Objects.isNull(str)) {
            return false;
        }
        return str.matches(regex) && str.length() > 1 && str.length() <= 20;
    }


    /**
     * 账号检查，是否只包含 英文、数字,长度最小为5,最大为20个字符
     *
     * @param str 字符串
     */
    public static boolean validateAccount(String str) {
        // 正则表达式匹配英文、或数字
        String regex = "^[a-zA-Z0-9]+$";
        if (Objects.isNull(str)) {
            return false;
        }
        return str.matches(regex) && str.length() > 4 && str.length() <= 20;
    }


    /**
     * 密码检查，是否包含 英文、数字或下划线,长度最小为6,最大为20个字符
     *
     * @param str 字符串
     */
    public static boolean validatePassword(String str) {
        // 正则表达式匹配英文字符、数字和下划线以及 . 符号
        if (Objects.isNull(str)) {
            return false;
        }

        String regex = "^[a-zA-Z0-9.]+$";
        return str.matches(regex) && str.length() > 5 && str.length() <= 20;

    }


    /**
     * 手机号码检查
     *
     * @param str 字符串
     */
    public static boolean validatePhone(String str) {
        if (Objects.isNull(str)) {
            return false;
        }
        // 正则表达式匹配11位数字
        String regex = "^1[3-9]\\d{9}$";
        return str.matches(regex);
    }


}
