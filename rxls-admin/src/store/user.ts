import { ReqLogout } from '@/api/admin'
import { Admin, Menu } from '@/api/admin/type'
import { TOKEN } from '@/config/constant'
import { CommonStateEnum } from '@/config/enum/common'
import { events } from '@/events/eventBus'
import { defineStore } from 'pinia'
import { ref } from 'vue'

// 用户信息状态
export const useUserSotre = defineStore(
  'userStore',
  () => {
    /**
     * rsa秘钥
     */
    const rsa = ref<string>()

    /**
     * 用户信息
     */
    const userInfo = ref<Admin | null>(null)

    /**
     * 菜单列表
     */
    const menus = ref<Menu[]>([])

    /**
     * 当前选中菜单
     */
    const selectedMenu = ref<string>('/home')

    /**
     * 当前展开级联菜单
     */
    const nowMenu = ref<string[]>(['home'])

    /**
     * 当前打开的菜单列表
     */
    const openMenu = ref<{ title: string; path: string; group: string[] }[]>([
      {
        title: 'home',
        path: '/home',
        group: ['home'],
      },
    ])

    /**
     * 退出登录
     */
    const logout = () => {
      ReqLogout().then((res) => {
        if (res.data.code == CommonStateEnum.OK) {
          window.localStorage.removeItem(TOKEN)
          setTimeout(() => {
            userInfo.value = null
          }, 1000)
          menus.value = []
          events.emit('LOGOUT')
        }
      })
    }

    return {
      userInfo,
      logout,
      menus,
      nowMenu,
      openMenu,
      selectedMenu,
      rsa,
    }
  },
  {
    persist: true,
  }
)
