export interface Admin {
  /**
   * 用户id
   */
  userId: number
  /**
   * 用户名、账号
   */
  username: string
  /**
   * 密码
   */
  password: string
  /**
   * 手机号
   */
  phone: string
  /**
   * 用户头像
   */
  avatar: string
  /**
   * 用户角色
   */
  roleName: string
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
}

/**
 * 菜单
 */
export interface Menu {
  /**
   * 主键id
   */
  menId: number
  //描述
  title: string
  //权限值
  path: string
  //父级id
  parentId: number
  //图标icon
  icon: string
  //路径
  src: string
  //排序
  sort: number
  //状态
  state: number
  //更新时间
  updateTime: Date | string | number
  //创建时间
  createTime: Date | string | number

  menus: Menu[]
}

/**
 * 登录请求参数
 */
export interface LoginQuery {
  /**
   * 用户名、账号
   */
  username: string
  /**
   * 密码
   */
  password: string
  /**
   * 验证码key
   */
  verifyCodeKey: string
  /**
   * 验证码值
   */
  verifyCode: string
}

/**
 * 登录后响应数据
 */
export interface LoginData {
  /**
   * 权限列表
   */
  menus: Menu[]
  /**
   * 账号
   */
  username: string
  /**
   * 用户信息
   */
  user: Admin
  /**
   * 令牌
   */
  token: string
}

/**
 * 新增请求参数
 */
export interface AddQuery {
  /**
   * 密码
   */
  password: string
  /**
   * 手机号
   */
  phone: string
  /**
   * 角色
   */
  role: number | string
  /**
   * 账号
   */
  username: string
  /**
   * 用户头像
   */
  avatar: string
  /**
   * 用户名称
   */
  nickname: string
  /**
   * 性别
   */
  sex: number
}

/**
 * 新增请求参数
 */
export interface UpdateQuery {
  /**
   * 主键id
   */
  userId: number | string

  /**
   * 头像
   */
  avatar?: string
  /**
   * 角色
   */
  roleName: string

  /**
   * 手机号
   */
  phone?: string

  /**
   * 密码
   */
  password?: string

  /**
   * 用户名称
   */
  nickname?: string
  /**
   * 状态
   */
  state?: number | string
  /**
   * 性别
   */
  sex?: number | string
}

/**
 * 分页查询请求参数
 */
export interface PageQuery {
  /**
   * 用户名、账号
   */
  username?: string

  /**
   * 角色id
   */
  roleId: number | string

  /**
   * 手机号
   */
  phone?: string

  /**
   * 用户名称
   */
  nickname?: string

  /**
   * 状态
   */
  state?: number | string
  /**
   * 性别
   */
  sex?: number | string

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
  dataList: Admin[]
}
