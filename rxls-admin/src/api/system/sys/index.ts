import { ReqGet, Result } from '../../request'
import { SysData } from './type'

/**
 * 获取系统信息
 * @returns
 */
export const ReqSys = () => {
  return ReqGet<Result<SysData>>('/sys/auth/info')
}
