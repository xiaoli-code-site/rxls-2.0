package cn.rx.common.enums;

import lombok.AllArgsConstructor;

/**
 * 订单枚举
 */
@AllArgsConstructor
public enum OrderEnum {

    /**
     * 待付款
     */
    PAY(101),

    /**
     * 待发货
     */
    DELIVERY(201),

    /**
     * 待收货
     */
    TAKE_OVER(301),

    /**
     * 已完成
     */
    COMPLETE(401),

    /**
     * 已取消
     */
    CANCEL(501),


    /**
     * 已退款
     */
    REFUND(601);


    /**
     * 状态
     */
    public final Integer code;


}
