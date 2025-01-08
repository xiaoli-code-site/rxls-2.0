import { ReqGet, Result } from '../../request'
import { PageQuery, PageData } from './type'

/**
 * 分页查询
 * @param data 参数对象
 * @returns
 */
export const ReqPageUser = (data: PageQuery) => {
  return ReqGet<Result<PageData>>('/oplog/auth/page', data)
}
