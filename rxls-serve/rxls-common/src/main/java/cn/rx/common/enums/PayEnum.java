package cn.rx.common.enums;

import lombok.AllArgsConstructor;

/**
 * 账单主题枚举
 */
@AllArgsConstructor
public enum PayEnum {

    /**
     * 售后退款
     */
    AFTER_SALES("AFTER_SALES"),


    /**
     * 付款
     */
    PAYMENT("PAYMENT"),


    /**
     * 取消退款
     */
    CANCEL_REFUND("CANCEL_REFUND");

    /**
     * 状态
     */
    public final String value;
}
