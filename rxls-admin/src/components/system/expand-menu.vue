<template>
  <div class="expand_list_box rx_no_select">
    <a-tag
      class="item"
      v-for="(item, index) in getOpenMenu"
      @click="onClick(item.path, item.group)"
      :key="item.title"
      :color="userStore.selectedMenu == item.path ? 'blue' : ''"
      @close="close(index)"
      :closable="index != 0"
      >{{ t(`menu.${titleHandler(item.title)}`) }}
    </a-tag>
  </div>
</template>

<!-- 展开菜单列表 -->
<script setup lang="ts">
import { useI18n } from "vue-i18n";
import { useUserSotre } from "@/store/user";
import { useRouter } from "vue-router";
import { computed } from "vue";

/**
 * 语言状态
 */
const { t } = useI18n();

/**
 * 路由操作
 */
const router = useRouter();

/**
 * 用户信息全局状态
 */
const userStore = useUserSotre();

/**
 * 点击事件
 */
const onClick = (path: string, group: string[]) => {
  userStore.selectedMenu = path;
  userStore.nowMenu = group;
  router.push({ path });
};

/**
 *获取打开的菜单
 */
const getOpenMenu = computed(() => {
  return userStore.openMenu;
});

/**
 * 标题处理
 */
const titleHandler = (title: string) => {
  let temp = title.split("/");
  return temp[temp.length - 1];
};

/**
 * 点击关闭事件
 */
const close = (index: number) => {
  userStore.openMenu.splice(index, 1);
  const menu = userStore.openMenu[userStore.openMenu.length - 1];
  userStore.selectedMenu = menu.path;
  userStore.nowMenu = menu.group;
  router.push({ path: menu.path });
};
</script>

<style lang="scss">
.expand_list_box {
  position: relative;
  z-index: 999;
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  

  .item {
    padding: 2px 14px 3px;
    margin: 4px;
    cursor: pointer;
    &:hover {
      opacity: 0.8;
    }
  }
}
</style>
