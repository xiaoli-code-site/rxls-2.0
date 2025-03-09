package cn.rx.common.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AfterSalesStateEnum {

    /**
     * 退款
     */
    REFUND(1),
    /**
     * 换货
     */
    RETURN(2),

    /**
     * 处理中
     */
    PROCESSING(100),
    /**
     * 已通过
     */
    APPROVE(101),
    /**
     * 退款中
     */
    REFUNDING(201),
    /**
     * 已完成
     */
    COMPLETED(301),
    /**
     * 已拒绝
     */
    REFUSE(401),
    /**
     * 已取消
     */
    CANCEL(501);

    /**
     * 状态
     */
    public final Integer code;
}
