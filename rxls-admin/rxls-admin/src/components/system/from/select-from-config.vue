<template>
  <div>
    <a-form layout="inline" style="padding-top: 5px">
      <a-form-item :label="t('common.description')">
        <a-input
          v-model:value="props.values.description"
          show-count
          :placeholder="t('common.pleaseInput')"
          :maxlength="20"
        >
        </a-input>
      </a-form-item>

      <a-form-item :label="t('common.state')" name="state">
        <a-radio-group v-model:value="props.values.state">
          <a-radio value="">{{
            t('common.all')
          }}</a-radio>
          <a-radio :value="CommonStateEnum.YES">{{
            t('common.normal')
          }}</a-radio>
          <a-radio :value="CommonStateEnum.NO">{{
            t('common.disable')
          }}</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item>
        <a-space :size="20">
          <a-button
            size="small"
            type="primary"
            danger
            @click="emit('onClear')"
            >{{ t('common.reset') }}</a-button
          >
          <a-button size="small" type="primary" @click="onSubmit">{{
            t('common.search')
          }}</a-button>
        </a-space>
      </a-form-item>
    </a-form>
  </div>
</template>

<!-- 操作日志查询参数表单 -->
<script lang="ts" setup>
import { useI18n } from 'vue-i18n'
import type { PropType } from 'vue'
import { PageQuery } from '@/api/system/config/type'
import { CommonStateEnum } from '@/config/enum/common'

const props = defineProps({
  values: {
    type: Object as PropType<PageQuery>,
    default: {},
  },
})

const emit = defineEmits<{
  /**
   * 提交数据
   */
  (e: 'onSubmit'): void
  /**
   * 清空数据
   */
  (e: 'onClear'): void
}>()

/**
 * 国际化状态
 */
const { t } = useI18n()

/**
 * 点击查询事件
 * @param data 表单数据
 */
const onSubmit = () => {
  emit('onSubmit')
}
</script>
