/**
 * 状态码枚举
 */
const RoleEnum = {
  /**
   * 管理员（大于等于此值为管理员）
   */
  ADMIN_VALUE: 100,

  /**
   * 基础角色普通用户ID
   */
  USER: 3,
  /**
   * 基础角色管理员ID
   */
  ADMIN: 2,

  /**
   * 基础角色超级管理员ID
   */
  SUPER_ADMIN: 1,
}

export { RoleEnum }
