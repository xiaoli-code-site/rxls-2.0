import { ref } from 'vue'

/**
 * SSE创建函数
 * @param url 请求路径
 * @param opFn  打开时的回调
 * @param msgFn 收到消息的回调
 * @param errFn 发生错误的回调
 * @returns 关闭函数
 */
const SSEHandler = (
  url: string,
  fn?: {
    opFn?: Function
    msgFn?: Function
    errFn?: Function
  }
) => {
  // 浏览器是否支持 SSE
  if (typeof EventSource == 'undefined') {
    throw new Error('SSE error!')
  }
  /*
   * 跨域时，可以指定第二个参数，withCredentials: true
   */
  let source = new EventSource(url, { withCredentials: true })

  // 连接一旦建立，就会触发 open 事件
  source.onopen = function (event) {
    fn && fn.opFn && fn.opFn(event)
    if (timer.value) {
      clearInterval(timer.value)
      timer.value = null
      reconnect.value = 0
    }
  }

  // 客户端收到服务器发来的数据
  source.onmessage = function (event) {
    // data 就是服务器传回的数据（文本格式）
    fn && fn.msgFn && fn.msgFn(event.data)
  }

  // 如果发生通信错误（比如连接中断）
  source.onerror = function (event) {
    fn && fn.errFn && fn.errFn(event)
    if (!timer.value && reconnect.value < MAX_RRCONNECT) {
      reconnectHandler()
    }
  }

  /**
   * 最大重连次数
   */
  const MAX_RRCONNECT = 10

  const reconnect = ref(0)

  /**
   * 重连定时器
   */
  const timer = ref()

  /**
   * 网络重连
   */
  const reconnectHandler = () => {
    timer.value = setInterval(() => {
      reconnect.value++
      source = new EventSource(url, { withCredentials: true })
      if (reconnect.value > MAX_RRCONNECT) {
        clearInterval(timer.value)
        timer.value = null
      }
    }, 3000)
  }

  /**
   * 网页关闭
   */
  window.addEventListener('unload', () => {
    source.close()
  })

  return source.close
}

export default SSEHandler
