package cn.rx.common.enums.oplog;

/**
 * 日志等级枚举
 */
public enum LogLeve {
    /**
     * 核心
     */
    CRITICAL(1),
    /**
     * 重要
     */
    IMPORTANT(2),
    /**
     * 一般
     */
    GENERAL(3);
    /**
     * 权限值
     */
    public final Integer value;


    LogLeve(Integer value) {
        this.value = value;
    }
}
