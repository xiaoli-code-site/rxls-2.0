import { ReqGet, ReqPost, Result } from '../../request'
import { AddQuery, PageData, PageQuery, UpdateQuery } from './type'

/**
 * 新增
 * @param data 参数对象
 * @returns
 */
export const ReqAddUser = (data: AddQuery) => {
  return ReqPost<Result<void>>('/user/auth/add', data)
}

/**
 * 分页查询
 * @param data 参数对象
 * @returns
 */
export const ReqPageUser = (data: PageQuery) => {
  return ReqGet<Result<PageData>>('/user/auth/page', data)
}

/**
 * 删除
 * @param data 参数对象
 * @returns
 */
export const ReqDeleteInId = (data: number[]) => {
  return ReqPost<Result<number>>('/user/auth/del/id', data)
}

/**
 * 更新
 * @param data 参数对象
 * @returns
 */
export const ReqUpdateUser = (data: UpdateQuery) => {
  return ReqPost<Result<void>>('/user/auth/update', data)
}
