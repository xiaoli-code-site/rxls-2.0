import { ReqGet, Result } from '../../request'

/**
 * 变更配置状态
 * @param data 配置id
 */
export const ReqUpdateConfigState = (data: number) => {
  return ReqGet<Result<void>>('/config/auth/update/state', { id: data })
}
