import { Brand } from "../brand/type";
import { Category } from "../category/type";
import { Skus } from "../sku/type";

/**
 * 商品信息
 */
export interface Goods {
  /**
   * 主键id
   */
  goodsId: number;
  /**
   *  描述
   */
  goodsDescribe: string;
  /**
   *  商品类型描述
   */
  category: Category[];
  /**
   * 已售数量
   */
  goodsSold: number;
  /**
   * 标题
   */
  goodsTitle: string;
  /**
   *  详情
   */
  goodsDetail: string;
  /**
   * 折扣价
   */
  goodsDiscountPrice: number | string;
  /**
   * 价格
   */
  goodsPrice: number | string;
  /**
   * 预览图片
   */
  goodsPictures: string;

  /**
   * 封面
   */
  goodsCover: string;
  /**
   * 视频
   */
  goodsVideo: string;
  /**
   * 拥有规格
   */
  goodsSpecification: string;
  /**
   * 商品sku
   */
  skus: Skus[];
  /**
   * 商品品牌
   */
  brand: Brand;
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
   *  描述
   */
  goodsDescribe: string;
  /**
   *  类型id
   */
  category: number[];
  /**
   * 商品品牌iid
   */
  brandId: number;
  /**
   * 标题
   */
  goodsTitle: string;
  /**
   *  详情
   */
  goodsDetail: string;
  /**
   * 折扣价
   */
  goodsDiscountPrice: number | string;
  /**
   * 价格
   */
  goodsPrice: number | string;
  /**
   * 预览图片
   */
  goodsPictures: string;

  /**
   * 封面
   */
  goodsCover: string;
  /**
   * 视频
   */
  goodsVideo: string;
  /**
   * 拥有规格
   */
  goodsSpecification: string;
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
  goodsId: number;
  /**
   *  类型id
   */
  category: number[];
  /**
   * 商品品牌iid
   */
  brandId: number;
  /**
   *  描述
   */
  goodsDescribe: string;
  /**
   *  详情
   */
  goodsDetail: string;
  /**
   * 标题
   */
  goodsTitle: string;
  /**
   * 折扣价
   */
  goodsDiscountPrice: number | string;
  /**
   * 价格
   */
  goodsPrice: number | string;
  /**
   * 预览图片
   */
  goodsPictures: string;

  /**
   * 封面
   */
  goodsCover: string;
  /**
   * 视频
   */
  goodsVideo: string;
  /**
   * 拥有规格
   */
  goodsSpecification: string;
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
   * 标题
   */
  goodsTitle: string;
  /**
   * SKU编号
   */
  skusCode: string;
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
  dataList: Goods[];
}
