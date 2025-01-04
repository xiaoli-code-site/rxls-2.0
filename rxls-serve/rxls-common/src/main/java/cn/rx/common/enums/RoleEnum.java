package cn.rx.common.enums;

/**
 * 基础角色权限值、id枚举
 */
public enum RoleEnum {
    /**
     * 基础管理层角色权限值
     */
    ADMIN_VALUE(999),
    /**
     * 超级管理id
     */
    SUPER_ADMIN(1),
    /**
     * 管理层角色id
     */
    ADMIN(2),
    /**
     * 用户角色id
     */
    USER(3);
    /**
     * 权限值
     */
    public final Integer value;


    RoleEnum(Integer value) {
        this.value = value;
    }
}