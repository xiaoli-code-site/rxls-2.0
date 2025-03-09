package cn.rx.pay.query.wx;

import lombok.Data;


/**
 * 付款订单信息参数封装
 */
@Data
public class WxPrepayQuery {

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 用户openid
     */
    private String openid;
    /**
     * 总金额
     */
    private Integer total;
    /**
     * 附加数据
     */
    private String attach;
    /**
     * 商品描述
     */
    private String description;
}
