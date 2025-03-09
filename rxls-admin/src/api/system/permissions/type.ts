export interface Permissions {
  /**
   * 主键id
   */
  permissionsId: number;
  /**
   * 描述
   */
  description: string;
  /**
   * 权限值
   */
  url: string;
  /**
   * 创建时间
   */
  createTime: Date | string | number;
  /**
   * 更新时间
   */
  updateTime: Date | string | number;
  /**
   * 状态
   */
  state: number;
}

/**
 * 新增请求参数
 */
export interface AddQuery {
  /**
   * 描述
   */
  description: string;
  /**
   * 权限值
   */
  url: string;
}

/**
 * 更新请求参数
 */
export interface UpdateQuery {
  /**
   * 主键id
   */
  permissionsId: number;
  /**
   * 描述
   */
  description: string;
  /**
   * 权限值
   */
  url: string;
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
   * 描述
   */
  description: string;
  /**
   * 权限值
   */
  url: string;
  /**
   * 状态
   */
  state: string | number;
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
  dataList: Permissions[];
}
