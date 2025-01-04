package cn.rx.common.util.server;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取IP方法
 */
public class IpUtils {

    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        }
        return "127.0.0.1";
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        }
        return "未知";
    }
}


