<template>
  <div class="error_404_box">
    <a class="topic">请稍后,{{ time }}秒页面会自动跳转...</a>
  </div>
</template>

<!-- 404 -->
<script lang="ts" setup>
import { onBeforeUnmount, onMounted, ref } from 'vue'
import { useUserSotre } from '@/store/user'
import { useRouter } from 'vue-router'
import { Menu } from '@/api/admin/type'

/**
 * 路由操作
 */
const router = useRouter()

/**
 * 用户信息状态
 */
const userStore = useUserSotre()

/**
 * 倒计时
 */
const time = ref(3)

/**
 * 定时器
 */
const timer = ref()

/**
 * 倒计时结束后的操作
 */
const handler = () => {
  if (userStore.menus.length < 1) {
    router.push({ path: '/login' })
    return
  }
  userStore.openMenu = []
  let menu = getPath(userStore.menus[0])
  userStore.openMenu.push({
    title: menu.path.replace(/\//g, ''),
    path: menu.path,
    group: [menu.path.replace(/\//g, '')],
  })
  userStore.selectedMenu = menu.title
  userStore.nowMenu = [menu.title]
  router.push({ path: menu.path })
}

/**
 * 得到最底层的路由
 * @param temp
 * @returns
 */
const getPath = (temp: Menu) => {
  if (temp.menus.length == 0) {
    return temp
  }
  return getPath(temp.menus[0])
}

onMounted(() => {
  timer.value = setInterval(() => {
    time.value -= 1
    if (time.value == 1) {
      clearInterval(timer.value)
      timer.value = null
      handler()
    }
  }, 1000)
})

onBeforeUnmount(() => {
  if (timer.value) {
    clearInterval(timer.value)
  }
})
</script>

<style lang="scss" scoped>
.error_404_box {
  width: 100vw;
  height: 100vh;
  background: url('../../assets/error/404.svg') no-repeat;
  background-size: 100% 100%;
  overflow: hidden;

  .topic {
    height: 50px;
    line-height: 50px;
    font-size: 18px;
    font-weight: bold;
    padding-left: 50px;
  }
}
</style>
