import { logoutHandler } from './handler/logoutHandler'
import { messageHandler } from './handler/messageHandler'
import { loginHandler } from './handler/loginHandler'
import { refreshHandler } from './handler/refreshHandler'

/**
 * 事件清理函数
 */
const clearEvent = () => {
  let clearList = []
  clearList.push(logoutHandler())
  clearList.push(messageHandler())
  clearList.push(loginHandler())
  clearList.push(refreshHandler())
  return () => {
    clearList.forEach((fn) => fn())
  }
}

/**
 * 事件入口
 */
export default clearEvent
