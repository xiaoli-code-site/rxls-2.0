import { Permissions } from "../permissions/type";

export interface Menu {
  /**
   * 主键id
   */
  menuId: number;
  /**
   * 描述
   */
  title: string;
  /**
   * 权限值
   */
  path: string;
  /**
   * 父级id
   */
  parentId: number;
  /**
   * 图标icon
   */
  icon: string;
  /**
   * 路径
   */
  src: string;
  /**
   * 排序
   */
  sort: number;
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
  /**
   * 角色id
   */
  roleId: number;
}

/**
 * 菜单权限
 */
export interface MenuPermissions {
  /**
   * 权限菜单
   */
  permissions: Permission[];

  /**
   * 菜单
   */
  menus: Menu[];
}

/**
 * 权限菜单
 */
export interface Permission {
  /**
   * 主键id
   */
  title: number;
  /**
   * 创建时间
   */
  permissions: Permissions[];
}
