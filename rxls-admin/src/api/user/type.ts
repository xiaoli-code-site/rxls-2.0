/**
 * 用戶信息
 */
export interface User {
  /**
   * 主键id
   */
  userId: number | string
  /**
   * 手机号
   */
  phone: string
  /**
   * 用户头像
   */
  avatar: string
  /**
   * 用户名称
   */
  nickname: string
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
  /**
   * 性别
   */
  sex: number
  /**
   * 用户角色
   */
  roleName: string
}

/**
 * 新增请求参数
 */
export interface AddQuery {
  /**
   * 手机号
   */
  phone: string
  /**
   * 用户头像
   */
  avatar: string
  /**
   * 用户名称
   */
  nickname: string
  /**
   * 1男  0女
   */
  sex: number
  /**
   * 角色名称
   */
  roleName: string
}

/**
 * 更新请求参数
 */
export interface UpdateQuery {
  /**
   * 主键id
   */
  userId: number | string

  /**
   * 手机号
   */
  phone: string
  /**
   * 用户头像
   */
  avatar: string
  /**
   * 用户名称
   */
  nickname: string
  /**
   * 状态
   */
  state: number

  /**
   * 1男  0女
   */
  sex: number

  /**
   * 角色名称
   */
  roleName: string
}

/**
 * 分页查询请求参数
 */
export interface PageQuery {
  /**
   * 手机号
   */
  phone: string
  /**
   * 用户名、账号
   */
  username?: string
  /**
   * 密码
   */
  password?: string
  /**
   * 用户名称
   */
  nickname: string
  /**
   * 状态
   */
  state: number | string
  /**
   * 角色
   */
  role: number | string
  /**
   * 1男  0女
   */
  sex: number | string
  /**
   * 查几个
   */
  size: number
  /**
   * 第几页
   */
  page: number
}

/**
 * 分页查询数据
 */
export interface PageData {
  /**
   * 总量
   */
  total: number

  /**
   * 数据列表
   */
  dataList: User[]
}
