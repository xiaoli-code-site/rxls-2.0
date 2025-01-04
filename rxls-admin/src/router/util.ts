import { Menu } from '@/api/admin/type'
import router from '.'

/**
 * 获取所有页面路径对象
 */
const modules = import.meta.glob('../view/**/*.vue')

/**
 * 初始化路由路径
 */
const initRouter = (menus: Menu[]) => {
  if (menus.length == 0) return
  const list = secondaryRouting(menus)
  router.addRoute({
    path: '/index',
    component: () => import('@/view/index/index.vue'),
    name: 'index',
    meta: { title: 'index' },
    redirect: menus[0].path,
    children: [...addRouterAll(list)],
  })
}

/**
 * 添加
 */
const addRouterAll = (menu: Menu[]) => {
  let temp: any[] = []
  menu.forEach((s) => {
    let filePath = modules[`../view${s.src}.vue`]
    let obj = {
      path: s.path,
      component: filePath,
      name: s.path.replace('/', ''),
      meta: { title: s.title },
    }
    temp.push(obj)
  })

  return temp
}

/**
 * 全部作为二级路由处理
 */
const secondaryRouting = (list: Menu[]) => {
  let result: any[] = []
  function recurse(node: Menu) {
    if (node !== null) {
      // 将当前节点添加到结果数组
      result.push(node)

      // 对每个子节点递归调用此函数
      if (node.menus && node.menus.length > 0) {
        for (let child of node.menus) {
          recurse(child)
        }
      }
    }
  }

  // 遍历根节点开始递归
  for (let root of list) {
    recurse(root)
  }

  return result.filter((s) => s.src && s.src != '')
}

export { addRouterAll, initRouter, secondaryRouting }
