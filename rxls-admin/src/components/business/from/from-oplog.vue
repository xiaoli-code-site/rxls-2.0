<template>
  <div class="form_oplog_box">
    <div class="list">
      <div class="category">{{ t('oplog.query') }}</div>
      <div class="header">
        <div>{{ t('config.key') }}</div>
        <div>
          {{ t('config.value') }}
        </div>
      </div>
      <div v-for="(item, index) in contentQuery" :key="index" class="item">
        <div class="title">{{ item.key }}</div>
        <div class="value">
          {{ item.value }}
        </div>
      </div>
    </div>

    <div class="list">
      <div class="category">
        {{
          props.data?.state == CommonStateEnum.YES
            ? t('oplog.result')
            : t('oplog.errorInfo')
        }}
      </div>
      <div class="header">
        <div>{{ t('config.key') }}</div>
        <div>
          {{ t('config.value') }}
        </div>
      </div>
      <div v-for="(item, index) in contentResult" :key="index" class="item">
        <div class="title">{{ t(`oplog.${item.key}`) }}</div>
        <div class="value">
          {{ item.value }}
        </div>
      </div>
    </div>
  </div>
</template>

<!-- 日志表单 -->
<script setup lang="ts">
import { useI18n } from 'vue-i18n'
import { ref, watch } from 'vue'
import type { PropType } from 'vue'
import { Oplog } from '@/api/oplog/type'
import { CommonStateEnum } from '@/config/enum/common'

const props = defineProps({
  /**
   * 传递的数据，用于显示编辑
   */
  data: {
    type: Object as PropType<Oplog | null>,
    default: null,
  },
})

/**
 * 国际化状态
 */
const { t } = useI18n()

/**
 * 操作结果详情内容
 */
const contentResult = ref<{ key: string; value: any }[]>([])

/**
 * 操作参数详情内容
 */
const contentQuery = ref<{ key: string; value: any }[]>([])

watch(
  () => props.data,
  (newValue, _oldValue) => {
    if (!newValue) {
      return
    }
    contentResult.value = []
    contentQuery.value = []

    try {
      let tempArr = Object.entries(JSON.parse(newValue.result))
      tempArr.forEach(([k, v]) => {
        contentResult.value.push({
          key: k,
          value: v,
        })
      })
    } catch (e) {
      if (newValue.result) {
        contentResult.value.push({
          key: 'data',
          value: newValue.result,
        })
      }
    }

    try {
      let tempArr = Object.entries(JSON.parse(newValue.query))
      tempArr.forEach(([k, v]) => {
        contentQuery.value.push({
          key: k,
          value: v,
        })
      })
    } catch (e) {
      if (newValue.result) {
        contentResult.value.push({
          key: 'data',
          value: newValue.result,
        })
      }
    }
  },

  {
    immediate: true,
  }
)
</script>

<style scoped lang="scss">
.form_oplog_box {
  width: 100%;
  height: 80vh;
  overflow: auto;

  .list {
    margin-bottom: 100px;

    .category {
      font-weight: bold;
      margin-bottom: 10px;
      font-size: 15px;
    }

    .header {
      display: flex;
      div {
        display: flex;
        height: 50px;
        align-items: center;
        padding-left: 10px;
        width: 150px;
        font-weight: bold;
        font-size: 15px;
      }
      background: rgba(244, 198, 161, 0.098);
    }

    .item {
      display: flex;
      min-height: 40px;
      border-bottom: 1px solid rgba(186, 183, 183, 0.205);

      .title {
        width: 150px;
        font-weight: bold;
        font-size: 14px;
        background: #fafafa;
        display: flex;
        padding: 10px;
      }

      .value {
        padding: 10px;
        width: calc(100% - 170px);
      }
    }
  }
}
</style>
