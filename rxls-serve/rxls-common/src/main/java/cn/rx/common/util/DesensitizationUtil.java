package cn.rx.common.util;

/**
 *
 * 脱敏处理
 */
public class DesensitizationUtil {

    //电话脱敏处理
    public static String phone(String phone){
        if(phone==null || "".equals(phone)){
            return "";
        }
        StringBuffer string = new StringBuffer(phone);
        for (int i=3 ;i<phone.length()-4;i++) {
            string.replace(i, i+1, "*");
        }
        return string.toString();
    }
}
