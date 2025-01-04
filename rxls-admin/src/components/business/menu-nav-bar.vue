<template>
  <div
    class="menu_nav_bar_box rx_no_select"
    :style="{
      backgroundColor: token.colorBgContainer,
    }"
  >
    <a-layout-sider
      width="200"
      :collapsed="state.collapsed"
      :style="{ backgroundColor: token.colorBgBase }"
    >
      <div class="set">
        <a-tooltip placement="topRight">
          <template #title>
            <span>{{ t('menu.onlyOpenCurrentMenu') }}</span>
          </template>
          <a-switch
            v-model:checked="state.onlyNow"
            size="small"
            checked-children="开"
            un-checked-children="关"
          />
        </a-tooltip>
        <!-- 收縮按鈕 -->
        <span
          @click="toggleCollapsed"
          class="shrink"
          :style="{ backgroundColor: token.colorBgBase }"
        >
          <a-tooltip v-if="!state.collapsed">
            <template #title>
              <span>{{ t('common.shrink') }}</span>
            </template>
            <MenuFoldOutlined :style="{ fontSize: '20px' }" />
          </a-tooltip>

          <a-tooltip v-else>
            <template #title>
              <span>{{ t('common.unfold') }}</span>
            </template>
            <MenuUnfoldOutlined :style="{ fontSize: '20px' }" />
          </a-tooltip>
        </span>
      </div>
      <div class="menu_list">
        <a-menu
          :inline-indent="12"
          @click="toPath"
          v-model:selectedKeys="state.selectedKeys"
          v-model:openKeys="state.openKeys"
          mode="inline"
          :items="menuHandler"
          :inline-collapsed="state.collapsed"
          @openChange="onOpenChange"
          :style="{
            borderRight: 0,
            padding: 0,
          }"
        ></a-menu>
      </div>
    </a-layout-sider>
  </div>
</template>

<!-- 菜单导航栏 -->
<script setup lang="ts">
import { useUserSotre } from '@/store/user'
import { useI18n } from 'vue-i18n'
import { theme } from 'ant-design-vue'
import { useRoute } from 'vue-router'
import { useRouter } from 'vue-router'
import { MenuFoldOutlined, MenuUnfoldOutlined } from '@ant-design/icons-vue'
import { computed, h, onMounted, reactive, resolveComponent, watch } from 'vue'
import { Menu } from '@/api/admin/type'

/**
 * 国际化状态
 */
const { t } = useI18n()

/**
 * 路由对象
 */
const route = useRoute()

/**
 * 路由操作
 */
const router = useRouter()

/**
 * 用户信息状态
 */
const userStore = useUserSotre()

/**
 * 主题
 */
const { useToken } = theme

/**
 * 当前主题算法
 */
const { token } = useToken()

/**
 * 菜单栏状态
 */
const state = reactive<{
  collapsed: boolean
  selectedKeys: string[]
  openKeys: string[]
  preOpenKeys: string[]
  onlyNow: boolean
}>({
  /**
   * 是否收缩菜单
   */
  collapsed: false,
  /**
   * 选中的子菜单
   */
  selectedKeys: [],
  /**
   * 展开的菜单列表
   */
  openKeys: [],
  preOpenKeys: [],
  /**
   * 是否只打开当前菜单
   */
  onlyNow: false,
})

/**
 * 当前展示菜单
 */
watch(
  () => userStore.selectedMenu,
  (newValue, _old) => {
    //当菜单选中时发生变化
    if (userStore.nowMenu && userStore.nowMenu.length > 1 && !state.onlyNow) {
      state.openKeys.push(`/${userStore.nowMenu[0]}`)
    } else if (state.onlyNow && !state.collapsed) {
      state.openKeys = [`/${userStore.nowMenu[0]}`]
    }

    state.selectedKeys = [newValue]
    state.preOpenKeys = [newValue]
  }
)

/**
 * 路由切换
 */
const toPath = (query: any) => {
  if (!query.key || query.key == '') {
    return
  }

  //当前级联菜单
  let tempArr: string[] = []
  query.keyPath.forEach((s: string) => {
    tempArr.push(s.replace(/^\//, ''))
  })
  userStore.nowMenu = tempArr

  //当前选中的菜单
  userStore.selectedMenu = query.key

  //菜单窗口是否已打开过
  const menu = userStore.openMenu.find(
    (s) => s.title == tempArr[tempArr.length - 1]
  )

  //添加新的菜单窗口
  !menu &&
    userStore.openMenu.push({
      title: tempArr[tempArr.length - 1],
      path: query.key,
      group: tempArr,
    })

  //跳转对应页面
  router.push({ path: query.key })
}

/**
 * 子菜单处理
 */
const subMenuHandler = (list: Menu[]) => {
  const r: any = []
  list.forEach((s) => {
    let title = t(`menu.${s.title}`)
    r.push({
      key: s.path,
      icon: () => h(resolveComponent(s.icon)),
      label: title,
      title: title,
      children: s.menus.length > 0 ? [...subMenuHandler(s.menus)] : null,
    })
  })

  return r
}

/**
 * 菜单处理
 */
const menuHandler = computed(() => {
  const list = userStore.menus
  const r: any = []
  list.forEach((s) => {
    let title = t(`menu.${s.title}`)
    r.push({
      key: s.path,
      icon: () =>
        h(resolveComponent(s.icon), {
          props: {
            someProp: 'someValue',
          },
        }),
      label: title,
      title: title,
      children: s.menus.length > 0 ? [...subMenuHandler(s.menus)] : null,
    })
  })
  return r
})

watch(
  () => state.openKeys,
  (_val, oldVal) => {
    state.preOpenKeys = oldVal
  }
)

/**
 * 收缩展开
 */
const toggleCollapsed = () => {
  state.collapsed = !state.collapsed
  state.onlyNow = state.collapsed
  state.openKeys = state.collapsed ? [] : state.preOpenKeys
}

/**
 * 监听菜单展开
 */
const onOpenChange = (openKeys: string[]) => {
  if (state.onlyNow && openKeys.length > 0) {
    state.openKeys = [openKeys[openKeys.length - 1]]
  } else {
    state.openKeys = openKeys
  }
}

/**
 * 页面加载
 */
onMounted(() => {
  state.openKeys = [`/${userStore.nowMenu[0]}`]
  state.selectedKeys = [route.path]
  state.preOpenKeys = [route.path]
})
</script>

<style lang="scss">
.menu_nav_bar_box {
  position: relative;
  .menu_list {
    overflow: auto;
  }

  .set {
    position: relative;
    display: flex;
    justify-content: space-between;
    align-content: center;

    .shrink {
      position: absolute;
      right: 0;
      bottom: 0;
      display: flex;
      align-items: center;
      justify-content: center;
      box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px;
      z-index: 1;
    }
  }
}
</style>
