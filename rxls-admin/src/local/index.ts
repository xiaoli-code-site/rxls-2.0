import en from './en'
import zh from './zh'
import { createI18n } from 'vue-i18n'

/**
 * 语言类型
 */
export type LanguageType = 'zh' | 'en'

const i18n = createI18n({
  messages: {
    en,
    zh
  },
  legacy: false,
  locale: 'zh', // 设置默认语言
  silentTranslationWarn: true,
  missingWarn:false,
  silentFallbackWarn:true,
  fallbackWarn:false,
  globalInjection: true, 
  missing(_locale, key, _vm) {
    let str = key.split('.')
    if (str.length == 1) {
      return str[0]
    }
    return  str[1]
  },
})

export default i18n
