<script setup lang="ts">
import { onMounted, onUnmounted } from 'vue'
import { useThemeStore } from './store/theme'
import cleanupEventHandlers from '@/events/index'
import { ReqEncrypt } from './api/request'
import { message } from 'ant-design-vue'
import { useI18n } from 'vue-i18n'
import { AES, RSA } from './config/constant'
import { createAesKey } from './encrypt/aesUtil'

/**
 * 国际化状态
 */
const { t } = useI18n()

/**
 * 初始化
 */
const init = () => {
  let modal = message.loading(t('message.preparingPleaseWait'), 0)
  ReqEncrypt()
    .then((res) => {
      if ((res.data.code = 200)) {
        window.localStorage.setItem(RSA, res.data.data)
        window.localStorage.setItem(AES, createAesKey(16))
      }
    })
    .finally(() => {
      setTimeout(modal, 500)
    })
}

/**
 * 当前主题
 */
const themeStore = useThemeStore()

/**
 * 清理事件处理器
 */
const cleanup = cleanupEventHandlers()

onMounted(() => {
  init()
})

/**
 * 页面卸载时
 */
onUnmounted(() => {
  cleanup()
})
</script>

<template>
  <a-config-provider :theme="themeStore.getTheme">
    <router-view> </router-view>
  </a-config-provider>
</template>
