<template>
  <div class="form_task_box">
    <a-form
      ref="formRef"
      :rules="rules"
      :model="formState"
      :label-col="{ span: 6 }"
      :wrapper-col="{ span: 15 }"
    >
      <a-form-item :label="t('task.jobName')" name="jobName">
        <a-input
          v-model:value="formState.jobName"
          show-count
          :placeholder="t('common.pleaseInput')"
          :maxlength="20"
        >
        </a-input>
      </a-form-item>

      <a-form-item :label="t('task.groupName')" name="groupName">
        <a-input
          v-model:value="formState.groupName"
          show-count
          :placeholder="t('common.pleaseInput')"
          :maxlength="20"
        >
        </a-input>
      </a-form-item>

      <a-form-item :label="t('task.jobCode')" name="jobCode">
        <a-select
          ref="select"
          style="width: 220px"
          v-model:value="formState.jobCode"
        >
          <a-select-option
            :value="item"
            v-for="(item, index) in taskList"
            :key="index"
            >{{ t(`task.${item}`) }}</a-select-option
          >
        </a-select>
      </a-form-item>

      <a-form-item :label="t('task.duration')" name="time">
        <a-range-picker
          :placeholder="[
            t('task.pleaseSelectATime'),
            t('task.pleaseSelectATime'),
          ]"
          v-model:value="timerRang"
          style="width: 350px"
          @ok="onTimeRangeOk"
          :show-time="{
            hideDisabledOptions: true,
          }"
          format="YYYY-MM-DD HH:mm:ss"
        />
      </a-form-item>

      <a-form-item :label="t('task.triggerConfig')" name="config">
        <a-button type="link" @click="openConfig = true">{{
          t('common.configuration')
        }}</a-button>
      </a-form-item>

      <a-form-item :label="t('common.state')" name="state">
        <a-radio-group
          :disabled="formState.schedulerTaskId == 0"
          v-model:value="formState.state"
        >
          <a-radio :value="CommonStateEnum.YES">{{
            t('task.taskStart')
          }}</a-radio>
          <a-radio :value="CommonStateEnum.NO">{{
            t('task.taskStop')
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

    <!-- 执行配置设置弹窗 -->
    <a-modal
      style="padding: 0 10px"
      :destroyOnClose="true"
      v-model:open="openConfig"
      :title="t('task.triggerConfig')"
      :footer="null"
    >
      <a-tabs v-model:activeKey="formState.model">
        <a-tab-pane :key="1" :tab="t('task.normalMode')">
          <a-form :model="normalMode" name="normalMode" :rules="normalRules">
            <a-form-item name="frequency" :label="t('task.frequency')">
              <a-space>
                <a-input-number
                  :placeholder="t('common.pleaseInput')"
                  style="width: 150px"
                  id="inputNumber"
                  v-model:value="normalMode.frequency"
                  :min="1"
                  :max="9999999"
                />
                <a-select
                  ref="select"
                  style="width: 120px"
                  v-model:value="normalMode.unit"
                >
                  <a-select-option value="hour">{{   
                    t('task.hour')
                  }}</a-select-option>
                  <a-select-option value="minutes">{{
                    t('task.minutes')
                  }}</a-select-option>
                  <a-select-option value="seconds">{{
                    t('task.seconds')
                  }}</a-select-option>
                </a-select>
              </a-space>
            </a-form-item>
            <a-form-item :label="t('task.cycle')" name="cycle">
              <a-input-number
                style="width: 200px"
                v-model:value="normalMode.count"
                :placeholder="t('common.pleaseInput')"
                :min="1"
                :max="99999"
              >
              </a-input-number>
            </a-form-item>
          </a-form>
        </a-tab-pane>
        <a-tab-pane :key="0" :tab="t('task.corn')" force-render>
          <a-form :model="cornMode" name="cornMode" :rules="cornRules">
            <a-form-item
              :label="t('task.cornExpression')"
              name="cornExpression"
            >
              <a-input
                v-model:value="cornMode.expression"
                show-count
                :placeholder="t('common.pleaseInput')"
                :maxlength="50"
              >
              </a-input>
            </a-form-item>
            <a-form-item :label="t('task.mistakeMode')" name="mistakeMode">
              <a-select
                ref="select"
                style="width: 220px"
                v-model:value="cornMode.unit"
              >
                <a-select-option value="fireNow">{{
                  t('task.fireNow')
                }}</a-select-option>
                <a-select-option value="ignoreMisfires">{{
                  t('task.ignoreMisfires')
                }}</a-select-option>
                <a-select-option value="withNextFireTime">{{
                  t('task.withNextFireTime')
                }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-form>
        </a-tab-pane>
      </a-tabs>

      <div class="permission_bth">
        <a-button @click="onConfigReset">{{ t('common.reset') }}</a-button>
        <a-button @click="onConfigConfirm" type="primary">{{
          t('common.confirm')
        }}</a-button>
      </div>
    </a-modal>
  </div>
</template>

<!-- 任务表单 -->
<script setup lang="ts">
import dayjs, { Dayjs } from 'dayjs'
import { useI18n } from 'vue-i18n'
import { onMounted, ref, watch } from 'vue'
import type { PropType } from 'vue'
import { CommonStateEnum } from '@/config/enum/common'
import { message } from 'ant-design-vue'
import { Rule } from 'ant-design-vue/es/form'
import { SysScheduledTask } from '@/api/system/task/type'
import { MenuPermissions } from '@/api/system/menu/type'
import { ReqTaskAll } from '@/api/system/task/index'

const props = defineProps({
  /**
   * 传递的数据，用于显示编辑
   */
  data: {
    type: Object as PropType<SysScheduledTask | null>,
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
 * 起止时间
 */
const timerRang = ref<any[]>([])

/**
 * 创建时间
 */
const createTimes = ref<Dayjs>(dayjs())

/**
 * corn表达式
 */
const cornMode = ref({
  unit: 'fireNow',
  expression: '0/3600 * * * * ?',
})

/**
 * 普通模式表达式
 */
const normalMode = ref({
  unit: 'hour',
  count: '1',
  frequency: '1',
})

/**
 * 表单内容
 */
const formState = ref<SysScheduledTask>({
  schedulerTaskId: 0,
  expression: '',
  model: CommonStateEnum.YES,
  unit: '',
  endTime: '',
  groupName: '',
  jobCode: '',
  jobName: '',
  startTime: '',
  state: 0,
  createTime: createTimes.value.format('YYYY-MM-DD HH:mm:ss'),
  updateTime: createTimes.value.format('YYYY-MM-DD HH:mm:ss'),
})

/**
 * 触发配置弹窗控制
 */
const openConfig = ref(false)

/**
 * 触发配置确认事件
 */
const onConfigConfirm = () => {
  if (formState.value.model == CommonStateEnum.YES) {
    formState.value.unit = normalMode.value.unit
    formState.value.expression =
      normalMode.value.count + '-' + normalMode.value.frequency
  } else {
    formState.value.unit = cornMode.value.unit
    formState.value.expression = cornMode.value.expression
  }
  openConfig.value = false
}

/**
 * 监听传递值
 */
watch(
  () => props.data,
  (newValue, _oldValue) => {
    if (newValue && props.data) {
      formState.value = { ...props.data }
      if (formState.value.model == CommonStateEnum.YES) {
        let temp = formState.value.expression.split('-')
        normalMode.value.count = temp[0]
        normalMode.value.unit = formState.value.unit
        normalMode.value.frequency = temp[1]
      } else {
        cornMode.value.expression = formState.value.expression
        cornMode.value.unit = formState.value.unit
      }
      createTimes.value = dayjs(formState.value.createTime)
      timerRang.value.push(dayjs(formState.value.startTime))
      timerRang.value.push(dayjs(formState.value.endTime))
    } else {
      timerRang.value = []
      onConfigConfirm()
      formState.value.state = CommonStateEnum.NO
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
  jobName: [
    {
      required: true,
      trigger: 'change',
      validator: async (_rule: Rule, value: string) => {
        if (value === '') {
          return Promise.reject(t('message.nicknameNoEmpty'))
        } else if (formState.value.jobName.length < 2) {
          return Promise.reject(t('message.nicknameFormatError'))
        } else {
          return Promise.resolve()
        }
      },
    },
  ],
  jobCode: [
    {
      required: true,
      trigger: 'change',
      validator: async (_rule: Rule, value: string) => {
        if (value === '') {
          return Promise.reject(t('task.jobCodeNoEmpty'))
        } else {
          return Promise.resolve()
        }
      },
    },
  ],
  groupName: [
    {
      required: true,
      trigger: 'change',
      validator: async (_rule: Rule, value: string) => {
        if (value === '') {
          return Promise.reject(t('message.nicknameNoEmpty'))
        } else if (formState.value.groupName.length < 2) {
          return Promise.reject(t('message.nicknameFormatError'))
        } else {
          return Promise.resolve()
        }
      },
    },
  ],
  time: [
    {
      required: true,
      trigger: 'change',
      validator: async (_rule: Rule, _value: string) => {
        if (!timerRang.value || timerRang.value.length == 0) {
          return Promise.reject(t('task.pleaseSelectATime'))
        } else {
          return Promise.resolve()
        }
      },
    },
  ],
  config: [
    {
      required: true,
      trigger: 'change',
      validator: async (_rule: Rule, _value: string) => {
        if (formState.value.expression == '' || !formState.value.expression) {
          return Promise.reject(t('message.pleaseCheck'))
        } else {
          return Promise.resolve()
        }
      },
    },
  ],
}

/**
 *普通模式下的校验规则
 */
const normalRules: Record<string, Rule[]> = {
  frequency: [
    {
      required: true,
      trigger: 'change',
      validator: async (_rule: Rule, _value: string) => {
        if (!normalMode.value.frequency || normalMode.value.frequency == '') {
          return Promise.reject(t('message.valueNoEmpty'))
        } else {
          return Promise.resolve()
        }
      },
    },
  ],
  cycle: [
    {
      required: true,
      trigger: 'change',
      validator: async (_rule: Rule, _value: string) => {
        if (!normalMode.value.count || normalMode.value.count == '') {
          return Promise.reject(t('message.valueNoEmpty'))
        } else {
          return Promise.resolve()
        }
      },
    },
  ],
}

/**
 * corn表达式下的校验规则
 */
const cornRules: Record<string, Rule[]> = {
  cornExpression: [
    {
      required: true,
      trigger: 'change',
      validator: async (_rule: Rule, _value: string) => {
        if (!cornMode.value.expression || cornMode.value.expression == '') {
          return Promise.reject(t('message.valueNoEmpty'))
        } else {
          return Promise.resolve()
        }
      },
    },
  ],
  mistakeMode: [
    {
      required: true,
      trigger: 'change',
      validator: async (_rule: Rule, _value: string) => {
        if (!cornMode.value.unit || cornMode.value.unit == '') {
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

/**
 * 时间是否为空
 */
const isTime = ref(true)

/**
 * 权限选择点击重置事件
 */
const onConfigReset = () => {}

/**
 * 起止时间选择
 */
const onTimeRangeOk = (value: [Dayjs, Dayjs]) => {
  if (value.length < 2 || !value[0] || !value[1]) {
    return
  }
  formState.value.startTime = dayjs(value[0]).format('YYYY-MM-DD HH:mm:ss')
  formState.value.endTime = dayjs(value[1]).format('YYYY-MM-DD HH:mm:ss')
}

/**
 * 可执行任务列表
 */
const taskList = ref<string[]>([])

onMounted(() => {
  ReqTaskAll().then((res) => {
    if (res.data.code == CommonStateEnum.OK) {
      taskList.value = res.data.data
      if (formState.value.jobCode == '' && res.data.data.length > 0) {
        formState.value.jobCode = res.data.data[0]
      }
    }
  })
})
</script>

<style lang="scss" scoped>
.form_task_box {
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
.ant-modal-body {
  padding: 0 16px 20px !important;
}
</style>
