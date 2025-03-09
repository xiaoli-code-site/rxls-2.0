package cn.rx.pay.result.wx;

import lombok.Data;

/**
 * 退款响应信息封装
 */
@Data
public class WxRefundResult {
    /**
     * 退款金额
     */
    private Long refundFee;

    /**
     * 退款描述
     */
    private String refundDesc;
}
