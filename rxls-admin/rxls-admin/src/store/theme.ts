import { theme as themeContext } from 'ant-design-vue'
import { defineStore } from 'pinia'
import { computed, ref } from 'vue'

export const useThemeStore = defineStore(
  'useThemeStore',
  () => {
    /**
     * 主题样式
     */
    const themeStyle = ref({
      /**
       * 次要主题
       */
      auxiliary: themeContext.darkAlgorithm,
      /**
       * 主要主题
       */
      main: themeContext.defaultAlgorithm,
    })

    /**
     * 当前主题
     */
    const theme = ref<'auxiliary' | 'main'>('main')

    /**
     * 主题切换
     */
    const themeHandler = () => {
      theme.value = theme.value == 'auxiliary' ? 'main' : 'auxiliary'
    }

    /**
     * 主题获取
     */
    const getTheme = computed(() => {
      return {
        //自定义的相关配置
        token: {
          colorPrimary: 'rgb(51, 137, 249)',
          colorSuccess: '#1dc779',
          colorWarning: '#ffb302',
          colorError: '#cf4444',
          colorInfo: 'rgb(51, 137, 249)',
          wireframe: true,
        },
        //没有自定义配置的则采用当前算法的样式
        algorithm: themeStyle.value[theme.value],
      }
    })

    return {
      theme,
      themeHandler,
      getTheme,
    }
  },
  {
    persist: true,
  }
)
