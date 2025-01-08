<template>
  <div class="form_role_box">
    <a-form
      ref="formRef"
      :rules="rules"
      :model="formState"
      :label-col="{ span: 6 }"
      :wrapper-col="{ span: 14 }"
      :disabled="formState.roleId == RoleEnum.SUPER_ADMIN"
    >
      <a-form-item :label="t('role.roleName')" name="roleName">
        <a-input
          v-model:value="formState.roleName"
          show-count
          :placeholder="t('common.pleaseInput')"
          :maxlength="20"
        >
          <template #prefix>
            <user-outlined />
          </template>
        </a-input>
      </a-form-item>

      <a-form-item :label="t('role.roleValue')" name="roleValue">
        <a-input-number
          :disabled="roleValueIsEdit(props.data?.roleId)"
          v-model:value="formState.roleValue"
          show-count
          :placeholder="t('common.pleaseInput')"
          :max="99999"
          :min="1"
        />
      </a-form-item>

      <a-form-item :label="t('role.permission')" name="menu">
        <a-button type="link" @click="showPermission">{{
          t('common.configuration')
        }}</a-button>
      </a-form-item>

      <a-form-item :label="t('common.state')" name="state">
        <a-radio-group
          :disabled="formState.roleId == 0"
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

    <!-- 权限设置弹窗 -->
    <a-modal
      :destroyOnClose="true"
      v-model:open="openPermission"
      :title="t('role.permission')"
      :footer="null"
      :cancel="onPermissionCancel"
    >
      <div class="content">
        <a-collapse v-model:activeKey="activeKey">
          <template #expandIcon="{ isActive }">
            <caret-right-outlined :rotate="isActive ? 90 : 0" />
          </template>
          <a-collapse-panel
            v-for="(item, index) in props.pemissions"
            :key="index + 1"
            :header="t('menu.' + item.title)"
          >
            <a-checkbox-group :value="showdelPermissions.map((s) => s.url)">
              <a-checkbox
                @change="onPermissionChange(item.permissions[indexs])"
                v-for="(items, indexs) in item.permissions.map(
                  (s) => s.description
                )"
                :key="indexs"
                :value="item.permissions[indexs].url"
                >{{ items }}</a-checkbox
              >
            </a-checkbox-group>
          </a-collapse-panel>
        </a-collapse>
      </div>

      <div class="permission_bth">
        <a-button @click="onPermissionReset">{{ t('common.reset') }}</a-button>
        <a-button @click="onPermissionConfirm" type="primary">{{
          t('common.confirm')
        }}</a-button>
      </div>
    </a-modal>
  </div>
</template>

<!-- 角色表单 -->
<script setup lang="ts">
import { RoleEnum } from '@/config/enum/RoleEnum'
import { numbers, names } from '../../../util/check'
import { UserOutlined, CaretRightOutlined } from '@ant-design/icons-vue'
import dayjs, { Dayjs } from 'dayjs'
import { useI18n } from 'vue-i18n'
import { ref, watch } from 'vue'
import type { PropType } from 'vue'
import { CommonStateEnum } from '@/config/enum/common'
import { message } from 'ant-design-vue'
import { Rule } from 'ant-design-vue/es/form'
import { Role } from '@/api/system/role/type'
import { MenuPermissions } from '@/api/system/menu/type'
import { ReqPermissionsAllByRole } from '@/api/system/permissions'
import { Permissions } from '@/api/system/permissions/type'

const props = defineProps({
  /**
   * 传递的数据，用于显示编辑
   */
  data: {
    type: Object as PropType<Role | null>,
    default: null,
  },
  pemissions: {
    type: Array as PropType<MenuPermissions[]>,
    default: [],
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
const formState = ref<Role>({
  roleId: 0,
  roleName: '',
  roleValue: 1,
  state: CommonStateEnum.YES,
  createTime: createTimes.value.format('YYYY-MM-DD HH:mm:ss'),
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
      createTimes.value = dayjs(formState.value.createTime)
      ReqPermissionsAllByRole(formState.value.roleId).then((res) => {
        if (res.data.code == CommonStateEnum.OK) {
          usePermissions.value = res.data.data
          showdelPermissions.value = usePermissions.value
        }
      })
    } else {
      formState.value.roleId = 0
      formState.value.roleName = ''
      formState.value.roleValue = 1
      formState.value.state = CommonStateEnum.YES
      formState.value.createTime = createTimes.value.format(
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
      formState.value.createTime = createTimes.value.format(
        'YYYY-MM-DD HH:mm:ss'
      )
      emit('handleBthClick', {
        ...formState.value,
        permissions: usePermissions.value.map((s) => s.permissionsId),
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
 * 打开权限的折叠
 */
const activeKey = ref([])

/**
 * 校验规则
 */
const rules: Record<string, Rule[]> = {
  roleName: [
    {
      required: true,
      trigger: 'change',
      validator: async (_rule: Rule, value: string) => {
        if (value === '') {
          return Promise.reject(t('role.roleNameNoEmpty'))
        } else if (
          !names(formState.value.roleName) ||
          formState.value.roleName.length < 2
        ) {
          return Promise.reject(t('user.nicknameFormatError'))
        } else {
          return Promise.resolve()
        }
      },
    },
  ],
  roleValue: [
    {
      required: true,
      trigger: 'change',
      validator: async (_rule: Rule, value: string) => {
        if (value === '') {
          return Promise.reject(t('role.roleValueNoEmpty'))
        } else if (
          !numbers(formState.value.roleValue + '') ||
          formState.value.roleValue < 1
        ) {
          return Promise.reject(t('role.roleValueMin'))
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

/**
 * 时间是否为空
 */
const isTime = ref(true)

/**
 * 当前拥有的权限信息
 */
const usePermissions = ref<Permissions[]>([])

/**
 * 当前拥有的权限信息
 */
const showdelPermissions = ref<Permissions[]>([])

/**
 * 权限弹窗控制
 */
const openPermission = ref(false)

/**
 * 权限弹窗显示事件
 */
const showPermission = () => {
  openPermission.value = true
  showdelPermissions.value = [...usePermissions.value]
}

/**
 * 权限选择事件
 */
const onPermissionChange = (e: Permissions) => {
  let index = showdelPermissions.value.findIndex((s) => s.url == e.url)
  if (index == -1) {
    showdelPermissions.value.push(e)
    return
  } else {
    showdelPermissions.value.splice(index, 1)
  }
}

/**
 * 权限选择点击确认事件
 */
const onPermissionConfirm = () => {
  usePermissions.value = [...showdelPermissions.value]
  openPermission.value = false
}

/**
 * 权限选择点击重置事件
 */
const onPermissionReset = () => {
  showdelPermissions.value = []
}

/**
 * 权限值是否可编辑
 */
const roleValueIsEdit = (id: number | string | null | undefined) => {
  if (!id) {
    return true
  }
  return (
    id == RoleEnum.ADMIN || id == RoleEnum.USER || id == RoleEnum.SUPER_ADMIN
  )
}

/**
 * 权限选择点击重置事件
 */
const onPermissionCancel = () => {
  showdelPermissions.value = [...usePermissions.value]
}
</script>

<style scoped lang="scss">
.form_role_box {
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
