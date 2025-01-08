import { TOKEN } from '@/config/constant'
import { events } from '@/events/eventBus'
import { createRouter, createWebHashHistory } from 'vue-router'
import { NProgressStart, NProgressClose } from '@/nprogress/nprogress'

/**
 * 基本的路由列表
 */
const routes = [
  {
    path: '/login',
    component: () => import('@/view/login/login.vue'),
    name: 'login',
    meta: { title: 'login' },
  },
  {
    path: '/404',
    component: () => import('@/view/error/404.vue'),
    name: '404',
    meta: { title: '404' },
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes,
})

/**
 * 路由前置守卫
 */
router.beforeEach((to, _from, next) => {
  NProgressStart()
  const token = window.localStorage.getItem(TOKEN)

  //如果没有登录就访问其他页面，跳转登录
  if ((!token || token == '') && to.name != 'login') {
    next({ path: '/login' })
    return
  }


  //如果访问路径为 '/'，跳转首页
  if (to.path == '/') {
    next({ path: '/index' })
    return
  }

  //访问的不是默认已有页面
  if (routes.findIndex((s) => s.path == to.path) == -1) {
    //在所有路由中寻找
    let index = router.getRoutes().findIndex((s) => s.path == to.path)
    if (index == -1) {
      events.emit('REFRESH', to.path)
      next(false)
      return
    }
  }

  next()
})

/**
 * 路由后置守卫
 */
router.afterEach((_to, _from) => {
  NProgressClose()
})

export default router
