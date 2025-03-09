/**
 * 商品品牌
 */
export interface Ad {
  /**
   * 广告id
   */
  adId: number;
  /**
   * 广告标题
   */
  adTitle: string;
  /**
   * 展示位置
   */
  adPosition: string;
  /**
   * 目标id或内容
   */
  adTargetId: string;
  /**
   * 展示形式
   */
  adCategory: number;
  /**
   * 广告内容
   */
  adContent: string;
  /**
   * 广告图片
   */
  adPictures: string;
  /**
   * 广告描述
   */
  adDescribe: string;
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
   * 展示位置
   */
   adPosition: string;
  /**
   * 广告标题
   */
  adTitle: string;
  /**
   * 目标id或内容
   */
  adTargetId: string;
  /**
   * 展示形式
   */
  adCategory: number;
  /**
   * 广告内容
   */
  adContent: string;
  /**
   * 广告图片
   */
  adPictures: string;
  /**
   * 广告描述
   */
  adDescribe: string;
}

/**
 * 更新请求参数
 */
export interface UpdateQuery {
   /**
   * 展示位置
   */
   adPosition: string;
  /**
   * 广告id
   */
  adId: number;
  /**
   * 广告标题
   */
  adTitle: string;
  /**
   * 目标id或内容
   */
  adTargetId: string;
  /**
   * 展示形式
   */
  adCategory: number;
  /**
   * 广告内容
   */
  adContent: string;
  /**
   * 广告图片
   */
  adPictures: string;
  /**
   * 广告描述
   */
  adDescribe: string;
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
   * 广告名称
   */
  adTitle: string;
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
  dataList: Ad[];
}
