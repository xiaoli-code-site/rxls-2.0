import { ReqFile, ReqPost, Result } from '../../request'

/**
 * 上传图片
 * @param data 文件对象
 * @returns 路径列表
 */
export const ReqUploadImg = (data: FormData) => {
  return ReqFile<Result<string[]>>('/upload/auth/images', data)
}

/**
 * 上传文件
 * @param data 文件对象
 * @returns 路径列表
 */
export const ReqUploadFile = (data: FormData) => {
  return ReqFile<Result<string[]>>('/upload/auth/files', data)
}

/**
 * 删除文件
 * @param data 文件路径
 * @returns 成功数
 */
export const ReqDelFile = (data: string[]) => {
  return ReqPost<Result<number>>('/upload/auth/deleteFile', data)
}
