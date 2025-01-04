<template>
  <div class="monitor_box">
    <a-space class="list_time" size="large">
      <div class="time">
        {{ `${t('sys.startTime')}： ${dataInfo.jvm.startTime}` }}
      </div>
      <div class="time">{{ timeHandler }}</div>
    </a-space>

    <a-space :size="100">
      <div class="item">
        <span>{{ t('sys.cpu') }}</span>
        <a-progress
          type="circle"
          :stroke-color="{
            '0%': '#108ee9',
            '100%': '#87d068',
          }"
          :percent="parseFloat(dataInfo.cpu.used)"
        />
        <span>{{ dataInfo.cpu.core + t('sys.core') }}</span>
      </div>

      <div class="item">
        <span>{{ t('sys.memory') }}</span>
        <a-progress
          type="circle"
          :stroke-color="{
            '0%': '#108ee9',
            '100%': '#87d068',
          }"
          :percent="parseFloat(dataInfo.memory.usageRate)"
        />
        <span>{{ `${dataInfo.memory.used} / ${dataInfo.memory.total}` }} </span>
      </div>

      <div class="item">
        <span>{{ t('sys.switchboard') }}</span>
        <a-progress
          type="circle"
          :stroke-color="{
            '0%': '#108ee9',
            '100%': '#87d068',
          }"
          :percent="parseFloat(dataInfo.swap.usageRate)"
        />
        <span>{{ `${dataInfo.swap.used} / ${dataInfo.swap.total}` }} </span>
      </div>

      <div class="item">
        <span>{{ t('sys.disk') }}</span>
        <a-progress
          type="circle"
          :stroke-color="{
            '0%': '#108ee9',
            '100%': '#87d068',
          }"
          :percent="parseFloat(dataInfo.disk.usageRate)"
        />
        <span>{{ `${dataInfo.disk.used} / ${dataInfo.disk.total}` }} </span>
      </div>

      <div class="item">
        <span>{{ t('sys.jvm') }}</span>
        <a-progress
          type="circle"
          :stroke-color="{
            '0%': '#108ee9',
            '100%': '#87d068',
          }"
          :percent="dataInfo.jvm.usage"
        />
        <span
          >{{
            `${dataInfo.jvm.used} MB / ${dataInfo.jvm.total} MB / ${dataInfo.jvm.max} MB`
          }}
        </span>
      </div>
    </a-space>

    <a-descriptions :title="t('sys.systemInformation')">
      <a-descriptions-item :label="t('sys.ip')">{{
        dataInfo.sys.computerIp
      }}</a-descriptions-item>
      <a-descriptions-item :label="t('sys.operatingSystem')">{{
        dataInfo.sys.osName
      }}</a-descriptions-item>
      <a-descriptions-item :label="t('sys.model')">{{
        dataInfo.cpu.name
      }}</a-descriptions-item>
      <a-descriptions-item :label="t('sys.physicalCPU')">{{
        dataInfo.cpu.package
      }}</a-descriptions-item>
      <a-descriptions-item :label="t('sys.physicalCore')">{{
        dataInfo.cpu.core
      }}</a-descriptions-item>
      <a-descriptions-item :label="t('sys.logicCPU')">{{
        dataInfo.cpu.logic
      }}</a-descriptions-item>
    </a-descriptions>

    <a-descriptions :title="t('sys.otherInformation')">
      <a-descriptions-item :label="t('sys.physicalMemoryCapacity')">{{
        dataInfo.memory.total
      }}</a-descriptions-item>
      <a-descriptions-item :label="t('sys.diskStorageCapacity')">{{
        dataInfo.disk.total
      }}</a-descriptions-item>
      <a-descriptions-item :label="t('sys.exchangeSpaceCapacity')">{{
        dataInfo.swap.total
      }}</a-descriptions-item>
    </a-descriptions>
  </div>
</template>

<!-- 系统监控 -->
<script lang="ts" setup>
import SSEHandler from '@/util/sse'
import { useI18n } from 'vue-i18n'
import { computed, onMounted, ref } from 'vue'
import { SysData } from '@/api/sys/type'
import { SSE_URL, TOKEN } from '@/config/constant'

/**
 * 国际化
 */
const { t } = useI18n()

/**
 * 数据信息
 */
const dataInfo = ref<SysData>({
  cpu: {
    name: '无',
    package: '无',
    core: '无',
    coreNumber: 0,
    logic: '',
    used: '0',
    idle: '0',
  },
  memory: {
    total: '0',
    available: '0',
    used: '0',
    usageRate: '0',
  },
  swap: {
    total: '0',
    used: '0',
    available: '0',
    usageRate: '0',
  },
  disk: {
    total: '0',
    available: '0',
    used: '0',
    usageRate: '0',
  },
  jvm: {
    total: 0,
    max: 0,
    free: 0,
    home: '无',
    runTime: '0-0-0',
    startTime: '无',
    usage: 0,
    used: 0,
    version: '无',
  },
  sys: {
    computerIp: '无',
    computerName: '无',
    osArch: '无',
    osName: '无',
    userDir: '无',
  },
})

/**
 * 系统信息处理
 */
const sysHandler = (data: string) => {
  if (data != null) {
    try {
      dataInfo.value = JSON.parse(data)
    } catch (e) {}
  }
}

/**
 * SSE处理
 */
const SSE = ref()

/**
 * 系统运行时间信息处理
 */
const timeHandler = computed(() => {
  let run = dataInfo.value.jvm.runTime.split('-')
  let endText = `${run[0]} ${t('common.day')} ${run[1]} ${t('common.hour')} ${
    run[2]
  } ${t('common.minute')}`
  return `${t('sys.runningTime')}： ${endText}`
})

onMounted(() => {
  let token = window.localStorage.getItem(TOKEN)
  SSE.value = SSEHandler(SSE_URL + '?token=' + token, {
    msgFn: sysHandler,
  })
})
</script>

<style scoped lang="scss">
.monitor_box {
  padding-left: 30px;
  padding-top: 20px;
  display: flex;
  flex-direction: column;

  .item {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    margin-bottom: 50px;

    span {
      padding: 10px 0;
    }
  }

  .time {
    font-weight: bold;
    padding: 10px 0;
  }
}
</style>
