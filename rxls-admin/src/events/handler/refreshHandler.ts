import { events } from '../eventBus'
import { initRouter } from '@/router/util'
import { useUserSotre } from '@/store/user'
import { useRouter } from 'vue-router'
/**
 * 页面刷新事件
 */
export const refreshHandler = () => {
  const menusStore = useUserSotre()
  const router = useRouter()
  events.on('REFRESH', (path: string = '/index') => {
    if (router.getRoutes.length == 0) {
      initRouter(menusStore.menus)
      let index = router.getRoutes().findIndex((s) => s.path == path)
      index == -1 ? router.push({ path: '/404' }) : router.push({ path })
    }
  })

  return () => {
    events.off('REFRESH')
  }
}
