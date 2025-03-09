/**
 * 角色
 */
export interface Role {
  /**
   * 角色id
   */
  roleId: number;
  /**
   * 角色名称
   */
  roleName: string;
  /**
   * 角色权限值
   */
  roleValue: number;
  /**
   * 状态
   */
  state: number;
  /**
   * 创建时间
   */
  createTime: Date | string | number;
  /**
   * 更新时间
   */
  updateTime: Date | string | number;
  /**
   * 权限id列表
   */
  permissions?: number[];
  /**
   * 菜单id列表
   */
  menus?: number[];
}

/**
 * 新增请求参数
 */
export interface AddQuery {
  /**
   * 角色名称
   */
  roleName: string;
  /**
   * 角色权限值
   */
  roleValue: number;
  /**
   * 权限id列表
   */
  permissions: number[];
  /**
   * 菜单id列表
   */
  menus?: number[];
}

/**
 * 更新请求参数
 */
export interface UpdateQuery {
  /**
   * 角色id
   */
  roleId: number | string;
  /**
   * 角色名称
   */
  roleName: string;
  /**
   * 角色权限值
   */
  roleValue: number;
  /**
   * 状态
   */
  state: number;
  /**
   * 权限id列表
   */
  permissions: number[];
  /**
   * 菜单id列表
   */
  menus?: number[];
}

/**
 * 分页查询请求参数
 */
export interface PageQuery {
  /**
   * 状态
   */
  state: number | string;
  /**
   * 角色名称
   */
  roleName: string;
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
  dataList: Role[];
}
