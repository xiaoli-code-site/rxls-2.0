
/**
 * 商品信息
 */
export interface Category {
  /**
   * 主键id
   */
  categoryId: number;

  /**
   * 标题
   */
  categoryTitle: string;

  /**
   * 图标
   */
  icon:string
  /**
   * 排序
   */
  sort: number;

  /**
   * 父级id
   */
  parentId: number;
  /**
   * 子分类
   */
  category?: Category[];
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
   * 图标
   */
   icon:string
  /**
   * 标题
   */
  categoryTitle: string;
  /**
   * 排序
   */
  sort: number;
  /**
   * 父级id
   */
  parentId: number;
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
   * 图标
   */
   icon:string
  /**
   * 标题
   */
  categoryTitle: string;
  /**
   * 排序
   */
  sort: number;
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
  categoryTitle: string;
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
  dataList: Category[];
}
