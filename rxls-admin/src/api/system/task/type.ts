import { User } from '../user/type'

/**
 * 系统定时任务
 */
export interface SysScheduledTask {
  /**
   * id
   */
  schedulerTaskId: number
  /**
   * 表达式
   */
  expression: string
  /**
   * 结束时间
   */
  endTime: string
  /**
   * 模式 1普通模式  0corn模式
   */
  model: number
  /**
   * 时间单位 / 误点模式
   */
  unit: string
  /**
   * 分组名称
   */
  groupName: string
  /**
   * 任务编号
   */
  jobCode: string
  /**
   * 任务名称
   */
  jobName: string
  /**
   * 开始时间 空或时间小于当前时间表示立即执行
   */
  startTime: string
  /**
   * 用户信息
   */
  user?: User
  /**
   * 状态
   */
  state: number
  /**
   * 创建时间
   */
  createTime: string | Date
  /**
   * 更新时间
   */
  updateTime: string | Date
}

/**
 * 新增参数
 */
export interface AddQuery {
  /**
   * 表达式
   */
  expression: string
  /**
   * 结束时间
   */
  endTime: string
  /**
   * 模式 1普通模式  0corn模式
   */
  model: number
  /**
   * 时间单位 / 误点模式
   */
  unit: string
  /**
   * 分组名称
   */
  groupName: string
  /**
   * 任务编号
   */
  jobCode: string
  /**
   * 任务名称
   */
  jobName: string
  /**
   * 开始时间 空或时间小于当前时间表示立即执行
   */
  startTime: string
  /**
   * 状态
   */
  state: number
}

/**
 * 更新参数
 */
export interface UpdateQuery {
  /**
   * id
   */
  schedulerTaskId: number
  /**
   * 表达式
   */
  expression: string
  /**
   * 结束时间
   */
  endTime: string
  /**
   * 模式 1普通模式  0corn模式
   */
  model: number
  /**
   * 时间单位 / 误点模式
   */
  unit: string
  /**
   * 分组名称
   */
  groupName: string
  /**
   * 任务编号
   */
  jobCode: string
  /**
   * 任务名称
   */
  jobName: string
  /**
   * 开始时间 空或时间小于当前时间表示立即执行
   */
  startTime: string
  /**
   * 状态
   */
  state: number
}

/**
 * 分页参数
 */
export interface PageQuery {
  /**
   * 分组名称
   */
  groupName: string
  /**
   * 任务名称
   */
  jobName: string
  /**
   * 用户名称
   */
  username: string
  /**
   * 第几页
   */
  page: number
  /**
   * 查询几个
   */
  size: number
}

/**
 * 分页响应数据
 */
export interface PageData {
  /**
   * 总量
   */
  total: number

  /**
   * 数据列表
   */
  dataList: SysScheduledTask[]
}
