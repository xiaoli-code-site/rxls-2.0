package cn.rx.common.util;

import java.util.Objects;

/**
 * 字符串工具类
 */
public class StrUtil {

    public static String intercept(String str,char targetChar){
        // 查找字符c在字符串str中的位置
        int index = str.lastIndexOf(targetChar);
        // 如果找到了字符c，则返回它之后的所有字符；否则，返回空字符串
        return index != -1 ? str.substring(index+1) : "";
    }

    public static String interceptBefore(String str,char targetChar){
        // 查找字符c在字符串str中的位置
        int index = str.lastIndexOf(targetChar);
        // 如果找到了字符c，则返回它之前的所有字符；否则，返回空字符串
        return index != -1 ? str.substring(0,index) : "";
    }

    /**
     * 判断字符串不为空，且不为 ''
     * @param str 字符串
     * @return 是否不为空，且不为 ''
     */
    public static boolean validate(String str){
        return Objects.nonNull(str) && !str.equals("");
    }


    /**
     * 值为 ""  则返回null
     * @param value 字符串值
     * @return null 或 字符串
     */
    public static String validateValue(String value) {
        // 检查是否为空字符串
        if (value != null && value.trim().isEmpty()) {
            return null;
        }
        return value;
    }
}
