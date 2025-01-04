import en from './en'
import zh from './zh'
import { createI18n } from 'vue-i18n'

/**
 * 语言类型
 */
export type LanguageType = 'zh' | 'en'

const custom = {
  common: {
    abc: '1',
  },
}

const i18n = createI18n({
  messages: {
    en,
    zh,
    custom,
  },
  legacy: false,
  locale: 'zh', // 设置默认语言
  silentTranslationWarn: true,
  missingWarn:false,
  silentFallbackWarn:true,
  fallbackWarn:false,
  globalInjection: true, // 全局注册$t方法
  fallbackLocale: 'custom', //如果没有找到要显示的语言，则默认显示
  missing(_locale, key, _vm) {
    let str = key.split('.')
    if (str.length == 1) {
      return str[0]
    }
    return  str[1]
  },
})

export default i18n
