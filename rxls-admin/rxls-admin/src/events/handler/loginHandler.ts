import { LoginData } from '@/api/system/admin/type'
import { events } from '../eventBus'
import { useRouter } from 'vue-router'
import { useUserSotre } from '@/store/user'
import { TOKEN } from '@/config/constant'
import { useI18n } from 'vue-i18n'
import { initRouter } from '@/router/util'

/**
 * 登录事件
 */
export const loginHandler = () => {
  const router = useRouter()
  const userStore = useUserSotre()

  const { t } = useI18n()

  events.on('LOGIN', (data: LoginData) => {
    initRouter(data.menus)
    userStore.menus = data.menus
    userStore.userInfo = data.user
    userStore.userInfo.username = data.username
    window.localStorage.setItem(TOKEN, data.token)
    setTimeout(() => {
      events.emit('MESSAGE', t('p_login.success'), 2)
      if (userStore.selectedMenu != '') {
        router.push({ path: userStore.selectedMenu })
      } else {
        router.push({ path: '/' })
      }
    }, 400)
  })

  return () => {}
}
