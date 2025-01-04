/**
 * 事件类型
 */
type EventType = 'MESSAGE' | 'LOGOUT' | 'LOGIN' | 'REFRESH'

interface Event {
  /**
   * 注册事件
   * @param names 事件名
   * @param event 事件
   *
   */
  on: (names: EventType, event: Function) => void
  /**
   * 提交事件
   * @param names 事件名
   * @param args 参数
   */
  emit: (names: EventType, ...args: any) => void

  /**
   * 事件删除
   * @param names 事件名
   * @param fn 回调函数
   */
  off: (names: EventType, fn?: Function) => void

  /**
   * 事件列表
   */
  events: Map<string, Function[]>
}

class EventCenter implements Event {
  on = (names: EventType, event: Function) => {
    if (this.events.has(names)) {
      let data = this.events.get(names)
      data && data.push(event)
    } else {
      this.events.set(names, [event])
    }
  }
  emit = (names: EventType, ...args: any) => {
    if (this.events.has(names)) {
      let data = this.events.get(names)
      data && data.forEach((fn) => fn(...args))
    }
  }
  events: Map<string, Function[]>
  constructor() {
    this.events = new Map()
  }
  off = (names: EventType, fn?: Function) => {
    this.events.delete(names)
    fn && fn()
  }
}

const events = new EventCenter()

export { events, type EventType }
