/**
 * 系统配置
 */
export interface SysConfig {
  /**
   * 主键
   */
  id: number
  /**
   * 键名
   */
  sysKey: string
  /**
   * 值
   */
  sysValue: string
  /**
   * 状态
   */
  state: number
  /**
   * 描述
   */
  description: string
  /**
   * 更新时间
   */
  updateTime: Date | string | number
}

/**
 * 新增请求参数
 */
export interface AddQuery {
  /**
   * 键名
   */
  sysKey: string
  /**
   * 值
   */
  sysValue: string
  /**
   * 描述
   */
  description: string
}

/**
 * 更新请求参数
 */
export interface UpdateQuery {
  /**
   * 主键
   */
  id: number
  /**
   * 键名
   */
  sysKey: string
  /**
   * 值
   */
  sysValue: string
  /**
   * 状态
   */
  state: number
  /**
   * 描述
   */
  description: string
}

/**
 * 分页查询请求参数
 */
export interface PageQuery {
  /**
   * 描述
   */
  description: string
  /**
   * 状态
   */
  state: number | string
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
  dataList: SysConfig[]
}
