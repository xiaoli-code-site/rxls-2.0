/**
 * 新增请求参数
 */
export interface AddQuery {
  /**
   * 商品id
   */
  goodsId: number;
  /**
   * 规格
   */
  specification: string;
  /**
   * 图片
   */
  skusPicture: string;
  /**
   * sku编号
   */
  skusCode: string;
  /**
   * 库存
   */
  inventory: number;
  /**
   * 折扣后单价
   */
  discountedUnitPrice: number | string;
  /**
   * 单价
   */
  unitPrice: number | string;
  /**
   * 状态
   */
  state: number;
}

/**
 * 更新请求参数
 */
export interface UpdateQuery {
  /**
   * 主键id
   */
  skusId: number;

  /**
   * 商品id
   */
  goodsId: number;
  /**
   * 图片
   */
  skusPicture: string;
  /**
   * 规格
   */
  specification: string;
  /**
   * sku编号
   */
  skusCode: string;
  /**
   * 库存
   */
  inventory: number;
  /**
   * 折扣后单价
   */
  discountedUnitPrice: number | string;
  /**
   * 单价
   */
  unitPrice: number | string;
  /**
   * 状态
   */
  state: number;
}

/**
 * 商品skus
 */
export interface Skus {
  /**
   * 主键id
   */
  skusId: number;

  /**
   * 商品id
   */
  goodsId: number;
  /**
   * 图片
   */
  skusPicture: string;
  /**
   * 规格
   */
  specification: string;
  /**
   * sku编号
   */
  skusCode: string;
  /**
   * 库存
   */
  inventory: number;
  /**
   * 折扣后单价
   */
  discountedUnitPrice: number | string;
  /**
   * 单价
   */
  unitPrice: number | string;
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
