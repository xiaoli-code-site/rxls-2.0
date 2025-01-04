/**
 * 分页查询数据
 */
export interface PageData<T> {
  /**
   * 总量
   */
  total: number

  /**
   * 数据列表
   */
  dataList: T[]
}

/**
 * 加密
 */
export interface CiphertextObj {
  /**
   * 加密文本
   */
  encryptText: string
  /**
   * 密钥
   */
  apiKey: string
}
