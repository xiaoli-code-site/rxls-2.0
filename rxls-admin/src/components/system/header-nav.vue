<template>
  <div
    class="header_box rx_no_select"
    :style="{
      backgroundColor: token.colorBgContainer,
    }"
  >
    <!-- 当前菜单 -->
    <a-space class="menus">
      <strong> {{ t("common.currentLocation") }}</strong>
      <a-breadcrumb class="now_menu">
        <a-breadcrumb-item
          v-for="(item, index) in userStore.nowMenu"
          :key="index"
        >
          <span class="now_menu_item">
            {{ t(`menu.${titleHandler(item)}`) }}</span
          >
        </a-breadcrumb-item>
      </a-breadcrumb>
    </a-space>
    <div class="content">
      <!-- 主题切换 -->
      <a-tooltip placement="leftTop">
        <template #title>
          <span>{{ t("header.theme") }}</span>
        </template>
        <icons :distance="12">
          <a-switch v-model:checked="isThem" size="small" />
        </icons>
      </a-tooltip>

      <!-- 全屏 -->
      <a-tooltip @click="toFullScreen">
        <template #title>
          <span>{{ t("header.enterFullScreenMode") }}</span>
        </template>
        <icons :distance="12">
          <CompressOutlined :style="{ fontSize: iconSize }" />
        </icons>
      </a-tooltip>

      <!-- 语言切换 -->
      <a-tooltip placement="rightTop">
        <template #title>
          <span>{{ t(`common.languageSwitching`) }}</span>
        </template>

        <a-dropdown placement="bottom" :trigger="['click']">
          <icons :distance="12">
            <TranslationOutlined :style="{ fontSize: iconSize }" />
          </icons>
          <template #overlay>
            <a-menu>
              <a-menu-item @click="toLanguage('zh')">
                <span
                  :style="{
                    color: localStore.status == 'zh' ? 'rgb(51, 137, 249)' : '',
                  }"
                  >{{ t("common.zh") }}</span
                >
              </a-menu-item>
              <a-menu-item @click="toLanguage('en')">
                <span
                  :style="{
                    color: localStore.status == 'en' ? 'rgb(51, 137, 249)' : '',
                  }"
                  >{{ t("common.en") }}</span
                >
              </a-menu-item>
            </a-menu>
          </template>
        </a-dropdown>
      </a-tooltip>

      <!-- 用戶信息 -->
      <a-tooltip>
        <template #title>
          <span>个人详情</span>
        </template>
        <div class="user_info" @click="userInfoShow = true">
          <img class="avatar" :src="userStore.userInfo?.avatar" alt="" />
          <div>
            <p class="msg">{{ userStore.userInfo?.nickname }}</p>
            <p class="role">
              {{
                userStore.userInfo?.roles &&
                userStore.userInfo?.roles.length > 0
                  ? t(`common.${userStore.userInfo?.roles[0].roleName}`)
                  : ""
              }}
            </p>
          </div>
        </div>
      </a-tooltip>

      <a-tooltip>
        <template #title>
          <span>{{ t("header.logout") }}</span>
        </template>
        <icons
          :msg="t('header.logout')"
          @on-click="userStore.logout"
          :distance="12"
        >
          <LogoutOutlined :style="{ fontSize: iconSize }" />
        </icons>
      </a-tooltip>
    </div>
  </div>
  <a-drawer
    :closable="false"
    :width="350"
    :open="userInfoShow"
    @close="onClose"
  >
    <user-info
      :key="new Date() + 'admin-info'"
      :data="userStore.userInfo"
      @handle-bth-click="onHandleBthClick"
    />
  </a-drawer>
</template>

<!-- 顶部导航栏 -->
<script setup lang="ts">
import userInfo from "./user-info.vue";
import {
  LogoutOutlined,
  TranslationOutlined,
  CompressOutlined,
} from "@ant-design/icons-vue";
import { useI18n } from "vue-i18n";
import { useThemeStore } from "@/store/theme";
import { ref, watch } from "vue";
import Icons from "../common/Icons.vue";
import { useUserSotre } from "@/store/user";
import { useLocalStore } from "@/store/local";
import { Admin, UpdateQuery } from "@/api/system/admin/type";
import { ReqUpdateAdmin } from "@/api/system/admin";
import { message, theme } from "ant-design-vue";

/**
 * 全局用户信息状态
 */
const userStore = useUserSotre();

/**
 * 主题
 */
const { useToken } = theme;

/**
 * 当前主题算法
 */
const { token } = useToken();

/**
 * 语言状态
 */
const localStore = useLocalStore();

/**
 * 图标大小
 */
const iconSize = ref("18px");

/**
 * 语言状态
 */
const { t } = useI18n();

/**
 * 全局主题信息状态
 */
const themeStore = useThemeStore();

/**
 * 是否展示主主题
 */
const isThem = ref(themeStore.theme == "auxiliary");

/**
 * 标题处理
 */
const titleHandler = (title: string) => {
  let temp = title.split("/");
  return temp[temp.length - 1];
};

watch(
  () => isThem.value,
  (_new, _old) => {
    themeStore.themeHandler();
  }
);

/**
 *  用户信息提交按钮点击
 */
const onHandleBthClick = (data?: Admin) => {
  if (!data) {
    userInfoShow.value = false;
    return;
  }
  let query: UpdateQuery = {
    phone: data?.phone ?? "",
    nickname: data?.nickname ?? "",
    sex: data?.sex ?? "",
    password: data?.password ?? "",
    userId: data?.userId ?? "",
    avatar: data?.avatar ?? "",
    roleIds: [],
    born: data.born
  };
  ReqUpdateAdmin(query)
    .then((res) => {
      if (res.data.code == 200) {
        message.success(t("message.editSuccess"));
        if (userStore.userInfo && data) {
          userStore.userInfo = data;
          userStore.userInfo.password = "";
        }
        userInfoShow.value = false;
      }
    })
    .finally(() => {});
};

/**
 * 是否显示个人信息
 */
const userInfoShow = ref(false);

/**
 * 全屏按钮点击
 */
const toFullScreen = () => {
  // DOM对象的一个属性：判断当前是不是全屏模式 全屏:true,不是全屏:false
  let full = document.fullscreenElement;
  //  切换为全屏模式
  if (!full) {
    document.documentElement.requestFullscreen();
  } else {
    document.exitFullscreen();
  }
};

/**
 * 语言切换
 */
const toLanguage = (type: string) => {
  setTimeout(() => {
    if (type != localStore.status) {
      localStore.localeHanlder();
    }
  }, 200);
};

const onClose = () => {
  userInfoShow.value = false;
};
</script>

<style scoped lang="scss">
.header_box {
  display: flex;
  align-items: center;
  padding: 0 5px 0 5px;
  height: 44px;
  letter-spacing: 0;
  box-shadow: rgba(17, 17, 26, 0.1) 0px 1px 0px;

  .menus {
    margin: 0 auto 0 10px;
  }

  .content {
    display: flex;
    align-items: center;
    height: fit-content;

    .user_info {
      display: flex;
      align-items: center;
      cursor: pointer;
      padding-right: 20px;
      padding-left: 10px;

      .avatar {
        width: 30px;
        height: 30px;
        border-radius: 6px;
        border-radius: 50%;
        margin-right: 5px;
      }

      .msg {
        max-width: 100px;
        font-weight: 550;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        font-size: 13px;
      }

      .role {
        max-width: 100px;
        padding-top: 2px;
        color: rgb(9, 140, 254);
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        font-weight: 550;
        font-size: 12px;
      }
    }
  }
}
</style>
