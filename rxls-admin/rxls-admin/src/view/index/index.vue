<template>
  <div class="index_box">
    <a-layout>
      <!--顶部导航栏 -->
      <header-nav></header-nav>

      <a-layout>
        <!--  侧边导航栏 -->
        <menu-nav-bar />

        <!-- 展示内容 -->
        <a-layout-content class="content">
          <!-- 展开菜单 -->
          <expand-menu />
          <!-- 路由页面 -->
          <router-view v-slot="{ Component }">
            <transition name="fade" mode="out-in" appear>
              <keep-alive>
                <component :is="Component" />
              </keep-alive>
            </transition>
          </router-view>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </div>
</template>

<!-- 首页 -->
<script setup lang="ts">
import expandMenu from '@/components/system/expand-menu.vue'
import menuNavBar from '@/components/system/menu-nav-bar.vue'
import headerNav from '@/components/system/header-nav.vue'
</script>

<style scoped lang="scss">
.index_box {
  min-width: 800px;
  width: 100%;
  overflow: hidden;

  .content {
    padding: 0 6px;
    width: calc(100% - 212px);
    height: calc(100vh - 56px);
    overflow: auto;
  }
}

/* 路由切换动画 */
/* fade-transform */
.fade-leave-active,
.fade-enter-active {
  transition: all 0.4s;
}

/* 可能为enter失效，拆分为 enter-from和enter-to */
.fade-enter-from {
  opacity: 0;
  transform: translateY(-30px);
}
.fade-enter-to {
  opacity: 1;
  transform: translateY(0px);
}

.fade-leave-to {
  opacity: 0;
  transform: translateY(30px);
}
</style>
