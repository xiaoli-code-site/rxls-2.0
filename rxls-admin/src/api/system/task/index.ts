import { ReqGet, Result } from '@/api/request'

/**
 * 变更任务状态
 * @param data 配置id
 */
export const ReqUpdateTaskState = (data: number) => {
  return ReqGet<Result<void>>('/task/auth/update/state', { id: data })
}

/**
 * 获取所有可执行任务
 */
export const ReqTaskAll = () => {
  return ReqGet<Result<string[]>>('/task/auth/all')
}
