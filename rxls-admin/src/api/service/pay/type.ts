export interface Payment {
  /**
   * 主键
   */
  payId: number;

  /**
   * 支付主题描述
   */
  paySubject: string;
  /**
   * 买家实际支付时间
   */
  payPaymentTime: string | Date | number;
  /**
   * 交易支付凭证
   */
  payTradeNo: string;
  /**
   * 交易金额
   */
  payTotalAmount: string;
  /**
   * 支付类型
   */
  payKind: number;
  /**
   * 实际支付金额
   */
  payBuyerPayAmount: string;
  /**
   * 所属订单id
   */
  orderSn: number;
  /**
   * 状态
   */
  state: number;
  /**
   * 创建时间
   */
  createTime: string | Date | number;
  /**
   * 更新时间
   */
  updateTime: string | Date | number;
}

/**
 * 分页查询账单
 */
export interface PageQuery {
  /**
   * 订单编号
   */
  orderSn: string;
  /**
   * 交易凭证
   */
  payTradeNo: string;

  /**
   * 第几页
   */
  page: number;
  /**
   * 查多少
   */
  size: number;
  /**
   * 起始时间
   */
  startTime: number | string;
  /**
   * 起始时间
   */
  endTime: number | string;
  /**
   * 支付类型
   */
  payKind: number | string;
}

/**
 * 分页查询订单响应数据
 */
export interface PageData {
  /**
   * 数据列表
   */
  dataList: Payment[];
  /**
   * 总数
   */
  total: number;
}
