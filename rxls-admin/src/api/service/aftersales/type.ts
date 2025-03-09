import { OrderGoods } from "../order/type";

/**
 * 售后内容
 */
export interface Aftersales {
  /**
   * 主键
   */
  afterSalesId: number;

  /**
   * 订单编号
   */
  orderSn: number;
  /**
   * 订单id
   */
  orderId: number;

  /**
   * 订单编号
   */
  afterSalesPhone: string;

  /**
   * 申请服务类型
   */
  afterSalesType: number;
  /**
   * 申请原因类型
   */
  afterSalesReason: string;
  /**
   * 申请原因描述
   */
  afterSalesDescribe: string;
  /**
   * 图片列表
   */
  afterSalesImages: string;
  /**
   * 申请退款金额
   */
  afterSalesAmount: number;

  /**
   * 实际退款金额
   */
  afterSalesPayAmount: number;

  /**
   * 订单商品
   */
  goods: OrderGoods[];

  /**
   * 状态
   */
  state: number;
  /**
   * 创建时间
   */
  createTime: number | Date | string;
  /**
   * 更新时间
   */
  updateTime: number | Date | string;
}

/**
 * 更新请求参数
 */
export interface UpdateQuery {
  /**
   * 主键
   */
  brandId: number;

  /**
   * 品牌名称
   */
  brandName: string;
  /**
   * 品牌logo
   */
  brandIcon: string;
  /**
   * 品牌描述
   */
  brandDescribe: string;
  /**
   * 状态
   */
  state: number;
}

/**
 * 分页查询请求参数
 */
export interface PageQuery {
  /**
   * 订单编号
   */
  orderSn: string;

  /**
   * 收货人
   */
  orderConsignee: string;
  /**
   * 状态
   */
  state: number | string;
  /**
   * 查几个
   */
  size: number;
  /**
   * 第几页
   */
  page: number;
}

/**
 * 分页查询数据
 */
export interface PageData {
  /**
   * 总量
   */
  total: number;

  /**
   * 数据列表
   */
  dataList: Aftersales[];
}
