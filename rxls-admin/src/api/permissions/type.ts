export interface Permissions {
  /**
   * 主键id
   */
  permissionsId: number
  /**
   * 描述
   */
  description: string
  /**
   * 权限值
   */
  url: string
  /**
   * 创建时间
   */
  createTime: Date | string | number
  /**
   * 更新时间
   */
  updateTime: Date | string | number
  /**
   * 状态
   */
  state: number
}
