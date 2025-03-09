import { ReqGet, ReqPost, Result } from '../../request'
import { VerificationCode } from './type'

/**
 * 获取普通验证码
 * @returns
 */
export const ReqVerificationCode = () => {
  return ReqGet<Result<VerificationCode>>('/code/transit/ordinary')
}

/**
 * 发送校验验证码
 * @returns
 */
export const ReqSpecial = () => {
  return ReqGet<Result<VerificationCode>>('.code/transit/verification')
}

/**
 * 检查验证码
 * @param VerificationCode 参数对象
 * @returns
 */
export const ReqVerification = (data: VerificationCode) => {
  return ReqPost<Result<boolean>>('/code/auth/check', data)
}
