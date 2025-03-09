import { ReqPost } from '../../request'
import { ExportExcel } from './type'

/**
 * 导出报表
 * @param fileName 文件名
 * @param fieldList 导出的字段
 * @param lang 是否中文
 * @param tableName 表名
 * @returns 登录信息
 */
export const ReqUploadexcel = (data: ExportExcel, tableName: string) => {
  return ReqPost<Blob>(`/${tableName}/auth/export/excel`, data, false, {
    responseType: 'blob',
    timeout: 0,
  })
}
