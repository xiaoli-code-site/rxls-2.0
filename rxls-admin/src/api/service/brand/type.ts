/**
 * 商品品牌
 */
export interface Brand {
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
 * 新增请求参数
 */
export interface AddQuery {

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
    * 品牌名称
    */
   brandName: string;
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
  dataList: Brand[];
}
