import axios from '@/axios/index'
import { AxiosRequestConfig, AxiosResponse } from 'axios'
import { CiphertextObj } from './type'
import { createAesKey, encryptWithAES } from '@/encrypt/aesUtil'
import { rsaEncrypt } from '@/encrypt/rsaUtil'
import { AES, ENCRYPTKEY, RSA } from '@/config/constant'

/**
 * 统一封装响应数据
 */
export interface Result<T> {
  /**
   * 状态码
   */
  code: number
  /**
   * 提示信息
   */
  msg: string
  /**
   * 响应数据
   */
  data: T
}

/**
 * get请求
 * @param url 请求url
 * @param data 携带参数
 * @param config 请求配置
 * @returns
 */
export const ReqGet = <T>(
  url: string,
  data: Object = {},
  config?: AxiosRequestConfig
): Promise<AxiosResponse<T, any>> => {
  const queryData = queryHandler(data)
  return axios.get(url + queryData, config)
}

/**
 * post请求
 * @param url 请求url
 * @param data 携带参数
 * @param encrypt 是否加密
 * @param config 请求配置
 * @returns
 */
export const ReqPost = <T>(
  url: string,
  data: Object = {},
  encrypt: boolean = false,
  config: AxiosRequestConfig = { headers: {} }
): Promise<AxiosResponse<T, any>> => {
  if (encrypt && config && config.headers) {
    config.headers[ENCRYPTKEY] = '1'
  }
  let v = encrypt ? encryptHandler(data) : data
  return axios.post(url, v, config)
}

/**
 * 文件上传请求
 * @param url 请求url
 * @param data 携带参数
 * @param encrypt 是否加密
 * @param config 请求配置
 * @returns
 */
export const ReqFile = <T>(
  url: string,
  data: FormData,
  config?: AxiosRequestConfig
): Promise<AxiosResponse<any, T>> => {
  return axios.postForm(url, data, config)
}

/**
 * 参数拼接
 * @param data 参数对象
 * @returns 字符串
 */
export const queryHandler = (data: any = {}) => {
  if (!(typeof data === 'object') || data === null || Array.isArray(data)) {
    return ''
  }
  //所有键名
  let keys: string[] = Object.keys(data)
  let query = '?'
  keys.forEach((s) => {
    query += `${s}=${data[s]}&`
  })
  return query.slice(0, -1)
}

/**
 * 获取RSA公钥
 * @returns
 */
export const ReqEncrypt = (): Promise<AxiosResponse<any, Result<string>>> => {
  return axios.get('/encrypt/rsa')
}

/**
 * 获取RSA公钥
 * @returns
 */
export const encryptHandler = (data: any): CiphertextObj => {
  let result = {
    encryptText: '',
    apiKey: '',
  }
  let rsa = window.localStorage.getItem(RSA)
  if (!rsa || rsa == '') {
    return result
  }
  let aes = window.localStorage.getItem(AES) ?? createAesKey(16)
  result.encryptText = encryptWithAES(aes, JSON.stringify(data))
  result.apiKey = rsaEncrypt(aes + '', rsa)
  return result
}
