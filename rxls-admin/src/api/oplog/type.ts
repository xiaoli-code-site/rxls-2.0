export interface Oplog {
  /**
   * 主键
   */
  id: number
  /**
   * 用户id
   */
  userId: number
  /**
   * 结果1 成功   0失败
   */
  state: number
  /**
   * 请求参数
   */
  query: string
  /**
   * 耗时（ms）
   */
  times: number
  /**
   * 操作类型
   */
  category: number
  /**
   * 描述
   */
  description: string
  /**
   * 模块
   */
  model: string
  /**
   * ip地址
   */
  ip: string
  /**
   * 操作结果
   */
  result: string
  /**
   * 创建时间
   */
  createTime: number | Date | string
}

/**
 * 分页查询请求参数
 */
export interface PageQuery {
  /**
   * 查几个
   */
  size: number
  /**
   * 第几页
   */
  page: number
  /**
   * 等级
   */
  leve: number | string
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
  dataList: Oplog[]
}
