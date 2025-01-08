import { defineStore } from 'pinia'
import { onMounted, ref } from 'vue'
import { useI18n } from 'vue-i18n'
import { LanguageType } from '@/local'
import { LOCAL } from '@/config/constant'

/**
 * 国际化状态
 */
export const useLocalStore = defineStore(
  'localStore',
  () => {
    /**
     * 默认语言状态
     */
    const status = ref<LanguageType>('zh')

    // 国际化对象
    const { locale } = useI18n()

    /**
     * 语言切换
     */
    const localeHanlder = () => {
      status.value = status.value == 'zh' ? 'en' : 'zh'
      locale.value = status.value
      let vStr: any = window.localStorage.getItem(LOCAL)
      if (!vStr || vStr == '') {
        return
      }
      let info = JSON.parse(vStr)
      info.status = status.value
      window.localStorage.setItem(LOCAL, JSON.stringify(info))
    }

    onMounted(() => {
      locale.value = status.value
    })

    return {
      localeHanlder,
      status,
    }
  },
  {
    persist: true,
  }
)
