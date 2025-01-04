import { ReqGet, ReqPost, Result } from '../request'
import { VerificationCode } from './type'

/**
 * 获取普通验证码
 * @returns
 */
export const ReqVerificationCode = () => {
  return ReqGet<Result<VerificationCode>>('/code')
}

/**
 * 获取特殊校验验证码
 * @returns
 */
export const ReqSpecial = () => {
  return ReqGet<Result<VerificationCode>>('/special/code')
}

/**
 * 检查验证码
 * @param VerificationCode 参数对象
 * @returns
 */
export const ReqVerification = (data: VerificationCode) => {
  return ReqPost<Result<boolean>>('/verification', data)
}
