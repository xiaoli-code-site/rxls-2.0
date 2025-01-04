/**
 * 验证码
 */
export interface VerificationCode {
  /**
   * 验证码类型
   */
  types: string

  /**
   * 验证码key
   */
  codeKey: string

  /**
   * 验证码图片
   */
  codeImage: string

  /**
   * 验证码值
   */
  text: string
}
