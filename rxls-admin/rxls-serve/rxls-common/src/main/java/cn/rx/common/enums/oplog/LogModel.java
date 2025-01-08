package cn.rx.common.enums.oplog;

/**
 * 日志模块枚举
 */
public enum LogModel {

    /**
     * 系统模块
     */
    SYS("sys"),
    /**
     * 安全模块
     */
    SECURE("secure"),

    /**
     * 登录模块
     */
    LOGIN("login");
    /**
     * 权限值
     */
    public final String value;


    LogModel(String value) {
        this.value = value;
    }

}
