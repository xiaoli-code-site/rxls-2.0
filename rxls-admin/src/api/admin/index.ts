import { ReqGet, ReqPost, Result } from '../request'
import {
  AddQuery,
  LoginData,
  LoginQuery,
  PageData,
  PageQuery,
  UpdateQuery,
} from './type'

/**
 * 账号登录请求
 * @param data 参数对象
 * @returns
 */
export const ReqLogin = (data: LoginQuery) => {
  return ReqPost<Result<LoginData>>('/account/login', data, true)
}

/**
 * 退出登录
 * @returns
 */
export const ReqLogout = () => {
  return ReqGet<Result<void>>('/account/logout')
}

/**
 * 新增
 * @param data 参数对象
 * @returns
 */
export const ReqAddAdmin = (data: AddQuery) => {
  return ReqPost<Result<void>>('/admin/auth/add', data, true)
}

/**
 * 分页查询
 * @param data 参数对象
 * @returns
 */
export const ReqPageAdmin = (data: PageQuery) => {
  return ReqGet<Result<PageData>>('/admin/auth/page', data)
}

/**
 * 删除
 * @param data 参数对象
 * @returns
 */
export const ReqDeleteInId = (data: number[]) => {
  return ReqPost<Result<number>>('/admin/auth/del/id', data)
}

/**
 * 更新
 * @param data 参数对象
 * @returns
 */
export const ReqUpdateAdmin = (data: UpdateQuery) => {
  return ReqPost<Result<void>>('/admin/auth/update', data, true)
}
