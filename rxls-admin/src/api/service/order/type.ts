/**
 * 订单
 */
export interface Order {
  /**
   * 订单id
   */
  orderId: number;
  /**
   * 订单金额
   */
  orderAmount: number;
  /**
   * 实付金额
   */
  orderPaymentAmount: number;
  /**
   * 纬度
   */
  orderLatitude: string;

  /**
   * 经度
   */
  orderLongitude: string;
  /**
   * 订单备注
   */
  orderRemarks: string;
  /**
   * 地址信息，省、市、县、详细地址使用 - 连接
   */
  orderAddress: string;
  /**
   * 收货地区编码
   */
  orderRegionCode: string;
  /**
   * 收货人姓名
   */
  orderConsignee: string;
  /**
   * 订单编号
   */
  orderSn: string;
  /**
   * 收货电话
   */
  orderPhone: string;
  /**
   * 状态
   */
  state: number;
  /**
   * 创建时间
   */
  createTime: string | Date | number;
  /**
   * 发货编号
   */
  shipSn: string;
  /**
   * 发货类型
   */
  shipType: string;
  /**
   * 发货时间
   */
  shipTime: string | Date | number;
  /**
   * 订单商品
   */
  orderGoods: OrderGoods[];
}

/**
 * 订单商品
 */
export interface OrderGoods {
  /**
   * 主键id
   */
  orderGoodsId: number;

  /**
   * 订单id
   */
  orderId: number;
  /**
   * 商品id
   */
  goodsId: number;
  /**
   * 数量
   */
  quantity: number;

  /**
   * 单价
   */
  unitPrice: number;
  /**
   * 折扣后的单价
   */
  discountedUnitPrice: number;
  /**
   * 规格
   */
  specifications: string;
  /**
   * 商品标题
   */
  goodsTitle: string;
  // /**
  //  * 商品描述
  //  */
  // goodsDescription: string;
  /**
   * 小计
   */
  subtotal: number;
  /**
   * 商品预览图
   */
  goodsCover: string;
}

/**
 * 分页查询订单
 */
export interface PageQuery {
  /**
   * 订单编号
   */
  orderSn: string;
  /**
   * 收货人
   */
  consignee: string;
  /**
   * 手机号
   */
  phone: string;
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
  startTime: number | string | Date;
  /**
   * 截止时间
   */
  endTime: number | string | Date;
  /**
   * 订单状态
   */
  state: number | string;
}

/**
 * 分页查询订单响应数据
 */
export interface PageData {
  /**
   * 商品列表
   */
  dataList: Order[];
  /**
   * 总数
   */
  total: number;
}

/**
 * 分页查询订单
 */
export interface PageAfterSalesQuery {
  /**
   * 商品名称
   */
  goodsTitle: string;
  /**
   * 第几页
   */
  page: number;
  /**
   * 售后状态
   */
  state: number | string;
}

/**
 * 更新订单地址参数
 */
export interface AlterAddressQuery {
  /**
   *订单id
   */
  orderId: string | number;
  /**
   * 收货电话
   */
  phone: string;
  /**
   * 收货人
   */
  consignee: string;
  /**
   * 地址信息
   */
  address: string;
  /**
   * 区域编码
   */
  areaCode: string;
  /**
   * 备注信息
   */
  remarks: string;
  /**
   * 经度
   */
  lon: string;
  /**
   * 纬度
   */
  lan: string;
}

/**
 * 订单发货参数
 */
export interface ShipQuery {
  /**
   *订单id
   */
  orderId: number;
  /**
   * 物流编号
   */
  shipSn: string;
  /**
   * 收货人
   */
  shipType: string;
}

/**
 * 订单退款参数
 */
export interface RefundQuery {
  /**
   *订单id
   */
  orderId: number;

  /**
   * 退款原因描述
   */
  refundDesc: string;

  /**
   * 退款费用
   */
  refundFee: number | string;
}

/**
 * 订单统计响应参数
 */
export interface OrderStatistics {
  /**
   *时间
   */
  times: string;

  /**
   * 成交量
   */
  num: number;
}


/**
 * 订单状态统计响应参数
 */
export interface OrderStateStatistics {
  /**
   *时间
   */
  state: number;

  /**
   * 成交量
   */
  num: number;
}
