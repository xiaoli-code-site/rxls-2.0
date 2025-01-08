import { ReqGet, Result } from '../../request'
import { Permissions } from './type'

/**
 * 根据角色id查询权限
 * @parama data 角色id
 * @returns 角色列表
 */
export const ReqPermissionsAllByRole = (data: number | string) => {
  return ReqGet<Result<Permissions[]>>('/permissions/auth/role/all', {
    roleId: data,
  })
}
