<template>
  <div class="user_info_box">
    <a-form ref="formRef" :rules="rules" :model="formState">
      <a-form-item :label="t('user.avatar')" name="avatar">
        <image-upload
          :max-count="1"
          :img-list="imgList"
          @on-upload="onUpload"
          @on-delete="formState.avatar = ''"
        >
          <template #default>
            <a-avatar
              v-if="formState.avatar"
              :src="formState.avatar"
              shape="square"
              :size="64"
            >
              <template #icon><UserOutlined /></template>
            </a-avatar>
          </template>
        </image-upload>
      </a-form-item>

      <a-form-item :label="t('user.username')" name="username">
        <a-input
          disabled
          v-model:value="formState.username"
          show-count
          :placeholder="t('common.pleaseInput')"
          :maxlength="20"
        >
          <template #prefix>
            <user-outlined />
          </template>
        </a-input>
      </a-form-item>

      <a-form-item :label="t('common.password')" name="password">
        <a-input-password
          v-model:value="formState.password"
          show-count
          :placeholder="t('common.pleaseInput')"
          :maxlength="20"
        >
        </a-input-password>
      </a-form-item>

      <a-form-item :label="t('user.nickname')" name="nickname">
        <a-input
          v-model:value="formState.nickname"
          show-count
          :placeholder="t('common.pleaseInput')"
          :maxlength="20"
        >
          <template #prefix>
            <user-outlined />
          </template>
        </a-input>
      </a-form-item>

      <a-form-item :label="t('user.phone')" name="phone">
        <a-input
          v-model:value="formState.phone"
          show-count
          :placeholder="t('common.pleaseInput')"
          :maxlength="20"
        >
          <template #prefix>
            <phone-outlined />
          </template>
        </a-input>
      </a-form-item>

      <a-form-item :label="t('user.role')">
        <a-checkbox-group :disabled="true" :value="formState.roleName">
          <a-checkbox
            :key="index"
            v-for="(item, index) in formState.roleName.split(',')"
            :value="item"
            >{{ t(`common.${item}`) }}</a-checkbox
          >
        </a-checkbox-group>
      </a-form-item>

      <a-form-item :label="t('user.sex')" name="sex">
        <a-radio-group v-model:value="formState.sex">
          <a-radio :value="1">{{ t('user.boy') }}</a-radio>
          <a-radio :value="0">{{ t('user.girl') }}</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item :label="t('common.state')" name="state">
        <a-radio-group :disabled="true" v-model:value="formState.state">
          <a-radio :value="CommonStateEnum.YES">{{
            t('common.normal')
          }}</a-radio>
          <a-radio :value="CommonStateEnum.NO">{{
            t('common.disable')
          }}</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item :label="t('common.createTime')">
        <a-date-picker
          disabled
          :status="!isTime ? 'error' : ''"
          v-model:value="createTimes"
          format="YYYY-MM-DD HH:mm:ss"
          :show-time="{ defaultValue: dayjs('00:00:00', 'HH:mm:ss') }"
        />
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

<!-- 用户表单 -->
<script setup lang="ts">
import { phone, names, password } from '@/util/check'
import imageUpload from '@/components/common/upload/image-upload.vue'
import { UserOutlined, PhoneOutlined } from '@ant-design/icons-vue'
import dayjs, { Dayjs } from 'dayjs'
import { useI18n } from 'vue-i18n'
import { computed, ref, watch } from 'vue'
import type { PropType } from 'vue'
import { CommonStateEnum } from '@/config/enum/common'
import { message } from 'ant-design-vue'
import { Rule } from 'ant-design-vue/es/form'
import { Admin } from '@/api/system/admin/type'

const props = defineProps({
  /**
   * 传递的数据，用于显示编辑
   */
  data: {
    type: Object as PropType<Admin | null>,
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
const formState = ref<Admin>({
  userId: 0,
  phone: '',
  avatar: '',
  nickname: '',
  createTime: createTimes.value.format('YYYY-MM-DD HH:mm:ss'),
  updateTime: createTimes.value.format('YYYY-MM-DD HH:mm:ss'),
  state: CommonStateEnum.YES,
  sex: 0,
  roleName: '',
  username: '',
  password: '',
})

/**
 * 监听传递值
 */
watch(
  () => props.data,
  (newValue, _oldValue) => {
    if (newValue && props.data) {
      formState.value = { ...props.data }
      createTimes.value = dayjs(formState.value.createTime)
    }
  },
  { immediate: true }
)

/**
 * 图片列表
 */
const imgList = computed(() => {
  if (formState.value.avatar) {
    return [formState.value.avatar]
  }
  return []
})

/**
 * 图片上传
 */
const onUpload = (data: string | string[]) => {
  formState.value.avatar = data as unknown as string
}

/**
 * 点击提交按钮
 */
const onSubmit = () => {
  formRef.value
    .validate()
    .then(() => {
      formState.value.createTime = createTimes.value.format(
        'YYYY-MM-DD HH:mm:ss'
      )
      emit('handleBthClick', { ...formState.value })
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
 * 校验名称
 */
const checkNickname = async (_rule: Rule, value: string) => {
  if (value === '') {
    return Promise.reject(t('user.nicknameNoEmpty'))
  } else if (
    !names(formState.value.nickname) ||
    formState.value.nickname.length < 2
  ) {
    return Promise.reject(t('user.nicknameFormatError'))
  } else {
    return Promise.resolve()
  }
}

/**
 * 校验头像
 */
const checkAvatar = async (_rule: Rule, value: string) => {
  if (value === '' || formState.value.avatar == '') {
    return Promise.reject(t(''))
  } else {
    return Promise.resolve()
  }
}

/**
 * 校验手机号码
 */
const checkPhone = async (_rule: Rule, value: string) => {
  if (value == '' || !value) {
    return Promise.reject(t('user.phoneNumberNoEmpty'))
  } else if (!phone(formState.value.phone)) {
    return Promise.reject(t('user.phoneNumberFormatError'))
  } else {
    return Promise.resolve()
  }
}

/**
 * 校验密码
 */
const checkPassword = async (_rule: Rule, _value: string) => {
  if (formState.value.userId == 0 && !formState.value.password) {
    return Promise.reject(t('user.passwordNoEmpty'))
  } else if (
    formState.value.password &&
    (!password(formState.value.password) || formState.value.password.length < 6)
  ) {
    return Promise.reject(
      t('user.Mustleast6CharactersLettersDigitsUnderscores')
    )
  } else {
    return Promise.resolve()
  }
}

/**
 * 校验规则
 */
const rules: Record<string, Rule[]> = {
  nickname: [
    {
      required: true,
      trigger: 'change',
      validator: checkNickname,
    },
  ],
  password: [
    {
      required: true,
      trigger: 'change',
      validator: checkPassword,
    },
  ],
  phone: [
    {
      required: true,
      trigger: 'change',
      validator: checkPhone,
    },
  ],
  avatar: [
    {
      required: true,
      trigger: 'blur',
      validator: checkAvatar,
    },
  ],
}

/**
 * 取消按钮
 */
const onCancel = () => {
  emit('handleBthClick', null)
}

/**
 * 时间是否为空
 */
const isTime = ref(true)
</script>

<style lang="scss">
.user_info_box {
  width: 100%;
  overflow: auto;
}
</style>
