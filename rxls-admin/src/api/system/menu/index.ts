import { ReqGet, Result } from '../../request'
import { Menu, MenuPermissions } from './type'

/**
 * 获取所有的菜单权限
 * @returns 菜单权限信息
 */
export const ReqMenuPermissions = () => {
  return ReqGet<Result<MenuPermissions>>('/menu/all')
}


/**
 * 获取所属角色的菜单
 * @param roleId 角色id
 * @returns 菜单信息
 */
export const ReqRoleMenu = (roleId:number | string) => {
  return ReqGet<Result<Menu[]>>('/menu/role',{roleId})
}
