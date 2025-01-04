import { ReqGet, Result } from '../request'
import { Role } from './type'
/**
 * 查询所有的角色
 * @returns 角色列表
 */
export const ReqAllRole = () => {
  return ReqGet<Result<Role[]>>('/role/auth/all')
}

