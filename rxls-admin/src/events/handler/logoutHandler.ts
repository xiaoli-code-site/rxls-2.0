import { events } from '@/events/eventBus'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
/**
 * 退出登录事件
 */
export const logoutHandler = () => {
  const router = useRouter()
  const { t } = useI18n()

  events.on('LOGOUT', () => {
    router.push({ path: '/login' })
    events.emit('MESSAGE', t('header.exited'))
    
  })

  return () => {
    events.off('LOGOUT')
  }
}
