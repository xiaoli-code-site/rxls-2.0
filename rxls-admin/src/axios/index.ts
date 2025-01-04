import { BASE_URL, LANGUAGE, LOCAL, TOKEN } from '@/config/constant'
import axios from 'axios'
import { events } from '@/events/eventBus'

// 初始化实例axios
const instance = axios.create({
  baseURL: BASE_URL,
  timeout: 9000,
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
    'X-Language': 'zh',
    'Encrypt-Key': '0',
  },
})

/**
 * 错误状态码处理
 * @param status 状态码
 * @param msg 错误信息
 */
const errorCodeHandler = (status: number | string, msg: string = '') => {
  msg = msg ?? 'NetworkAbnormal'
  let mode = false
  switch (status) {
    case 400:
      msg = 'SemanticsWrong'
      mode = true
      break
    case 401:
      msg = 'ServerAuthenticationFailed'
      mode = true
      break
    case 403:
      msg = 'ServerDeniesAccess'
      mode = true
      break
    case 404:
      msg = 'RequestAddressIncorrect'
      mode = true
      break
    case 500:
      msg = 'ServerNoResponding'
      mode = true
      break
    case 'ERR_NETWORK':
      msg = 'NetworkError'
      mode = true
      break
  }
  events.emit('MESSAGE', msg, -1, mode)
}

/**
 * 发送数据之前
 */
instance.interceptors.request.use(
  (config) => {
    const token = window.localStorage.getItem(TOKEN)
    const langStr = window.localStorage.getItem(LOCAL)
    if (langStr) {
      let lang = JSON.parse(langStr)
      lang?.status && (config.headers[LANGUAGE] = lang.status)
    }
    config.headers[TOKEN] = token
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

/**
 * 接收数据前
 */
instance.interceptors.response.use(
  (response) => {
    if (response.status != 200) {
      errorCodeHandler(response.status, response.statusText)
      return Promise.reject(response)
    } else if (!(response.data instanceof Blob) && response.data.code != 200) {
      errorCodeHandler(response.data.code, response.data.msg)
      return Promise.reject(response)
    }
    return response
  },
  (error) => {
    const { response } = error
    if (response) {
      //错误的处理
      errorCodeHandler(response.status)
    } else {
      errorCodeHandler(error.code)
    }
  }
)

export default instance
