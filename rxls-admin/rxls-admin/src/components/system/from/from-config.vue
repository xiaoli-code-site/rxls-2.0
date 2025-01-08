<template>
  <div class="form_config_box">
    <a-form
      ref="formRef"
      :rules="rules"
      :model="formState"
      :label-col="{ span: 6 }"
      :wrapper-col="{ span: 14 }"
    >
      <a-form-item :label="t('config.key')" name="sysKey">
        <a-input
          :disabled="formState.id != 0"
          v-model:value="formState.sysKey"
          show-count
          :placeholder="t('common.pleaseInput')"
          :maxlength="20"
        >
        </a-input>
      </a-form-item>

      <a-form-item :label="t('config.value')" name="sysValue">
        <a-input
          v-model:value="formState.sysValue"
          show-count
          :placeholder="t('common.pleaseInput')"
          :maxlength="20"
        >
        </a-input>
      </a-form-item>

      <a-form-item :label="t('config.description')" name="description">
        <a-textarea
          style="height: 100px"
          v-model:value="formState.description"
          show-count
          :placeholder="t('common.pleaseInput')"
          :maxlength="2000"
        >
        </a-textarea>
      </a-form-item>

      <a-form-item :label="t('common.state')" name="state">
        <a-radio-group
          :disabled="formState.id == 0"
          v-model:value="formState.state"
        >
          <a-radio :value="CommonStateEnum.YES">{{
            t('common.normal')
          }}</a-radio>
          <a-radio :value="CommonStateEnum.NO">{{
            t('common.disable')
          }}</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
        <a-space :size="30">
          <a-button type="primary" @click="onSubmit">
            {{ t('common.confirm') }}</a-button
          >
          <a-button @click="onCancel">{{ t('common.cancel') }}</a-button>
        </a-space>
      </a-form-item>
    </a-form>
  </div>
</template>

<!-- 角色表单 -->
<script setup lang="ts">
import dayjs, { Dayjs } from 'dayjs'
import { useI18n } from 'vue-i18n'
import { ref, watch } from 'vue'
import type { PropType } from 'vue'
import { CommonStateEnum } from '@/config/enum/common'
import { message } from 'ant-design-vue'
import { Rule } from 'ant-design-vue/es/form'
import { SysConfig } from '@/api/system/config/type'

const props = defineProps({
  /**
   * 传递的数据，用于显示编辑
   */
  data: {
    type: Object as PropType<SysConfig | null>,
    default: null,
  },
})

const emit = defineEmits<{
  /**
   * 提交确认方法,固定写法
   * @param data 提交的数据，取消则传递null
   */
  (e: 'handleBthClick', data: any): void
}>()

/**
 * 国际化状态
 */
const { t } = useI18n()

/**
 * 创建时间
 */
const createTimes = ref<Dayjs>(dayjs())

/**
 * 表单内容
 */
const formState = ref<SysConfig>({
  id: 0,
  sysKey: '',
  sysValue: '',
  state: 0,
  description: '',
  updateTime: createTimes.value.format('YYYY-MM-DD HH:mm:ss'),
})

/**
 * 监听传递值
 */
watch(
  () => props.data,
  (newValue, _oldValue) => {
    if (newValue && props.data) {
      formState.value = { ...props.data }
    } else {
      formState.value.id = 0
      formState.value.sysKey = ''
      formState.value.sysValue = ''
      formState.value.state = CommonStateEnum.YES
      formState.value.updateTime = createTimes.value.format(
        'YYYY-MM-DD HH:mm:ss'
      )
    }
  },
  { immediate: true }
)

/**
 * 点击提交按钮
 */
const onSubmit = () => {
  formRef.value
    .validate()
    .then(() => {
      emit('handleBthClick', {
        ...formState.value,
      })
    })
    .catch((_error: any) => {
      message.error(t('message.pleaseCheck'))
    })
}

/**
 * ant表单实例
 */
const formRef = ref()

/**
 * 校验规则
 */
const rules: Record<string, Rule[]> = {
  sysKey: [
    {
      required: true,
      trigger: 'change',
      validator: async (_rule: Rule, value: string) => {
        if (value === '') {
          return Promise.reject(t('message.valueNoEmpty'))
        } else {
          return Promise.resolve()
        }
      },
    },
  ],
  sysValue: [
    {
      required: true,
      trigger: 'change',
      validator: async (_rule: Rule, value: string) => {
        if (value === '') {
          return Promise.reject(t('message.valueNoEmpty'))
        } else {
          return Promise.resolve()
        }
      },
    },
  ],
  description: [
    {
      required: true,
      trigger: 'change',
      validator: async (_rule: Rule, value: string) => {
        if (value === '') {
          return Promise.reject(t('message.valueNoEmpty'))
        } else {
          return Promise.resolve()
        }
      },
    },
  ],
}

/**
 * 取消按钮
 */
const onCancel = () => {
  emit('handleBthClick', null)
}
</script>

<style scoped lang="scss">
.form_config_box {
  width: 100%;
  max-height: 70vh;
  overflow: auto;
}

.content {
  max-height: 50vh;
  overflow: auto;
}

.permission_bth {
  padding-top: 20px;
  text-align: end;

  button {
    margin-left: 20px;
  }
}
</style>
