import { ReqGet, Result } from '../request'
import { MenuPermissions } from './type'

/**
 * 获取所有的菜单权限
 * @returns 菜单权限信息
 */
export const ReqMenuPermissions = () => {
  return ReqGet<Result<MenuPermissions[]>>('/menu/all')
}
