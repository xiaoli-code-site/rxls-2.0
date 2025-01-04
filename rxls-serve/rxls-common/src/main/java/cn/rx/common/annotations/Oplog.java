package cn.rx.common.annotations;

import cn.rx.common.enums.oplog.LogLeve;
import cn.rx.common.enums.oplog.LogModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志记录注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Oplog {
    /**
     * 操作等级
     */
    LogLeve leve() default LogLeve.GENERAL;

    /**
     * 操作模块
     */
    LogModel model() default LogModel.SYS;

    /**
     * 操作描述
     */
    String description();
}
