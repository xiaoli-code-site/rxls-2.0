import { events } from '@/events/eventBus'
import { message } from 'ant-design-vue'
import { useI18n } from 'vue-i18n'
/**
 * 消息类型 -1 错误 0警告 1提示 2成功
 */
type MessageType = -1 | 2 | 0 | 1

/**
 * 消息事件
 */
export const messageHandler = () => {
  const { t } = useI18n()

  events.on(
    'MESSAGE',
    (msg: string, typeCode: MessageType = 1, auto: boolean = false) => {
      msg = auto ? t(`message.${msg}`) : msg
      if (typeCode == 1) {
        message.info(msg)
      } else if (typeCode == -1) {
        message.error(msg)
      } else if (typeCode == 0) {
        message.warning(msg)
      } else if (typeCode == 2) {
        message.success(msg)
      }
    }
  )

  return () => {
    events.off('MESSAGE')
  }
}
