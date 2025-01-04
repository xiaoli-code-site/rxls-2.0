<template>
  <div class="table_toolbar_box">
    <a-space warp :size="15">
      <a-button
        v-for="(item, index) in props.bthData"
        :key="index"
        :disabled="item.disabled ?? props.isDiable"
        :style="{ background: backHandler(item) }"
        :type="item.type"
        :ghost="item.ghost"
        :shape="item.shape"
        :danger="item.danger"
        :size="item.size ?? props.size"
        @click="onClick(item)"
      >
        {{ t(`common.${msgHandler(item.text)}`) }}
      </a-button>
    </a-space>
  </div>
</template>

<!-- 表格操作工具栏 -->
<script lang="ts" setup>
import { useI18n } from 'vue-i18n'
import { ExclamationCircleOutlined } from '@ant-design/icons-vue'
import { Modal } from 'ant-design-vue'
import { createVNode, PropType } from 'vue'
import { BthType } from '@/config/bth'

const emit = defineEmits<{
  /**
   * 点击导出excel
   * @param id 按钮唯一标识
   * @param confirm true确认 false 取消 （a-button 属性modal为true时使用）
   */
  (e: 'onBth', id: string, confirm?: boolean, data?: Object): void
}>()

/**
 * 国际化状态
 */
const { t } = useI18n()

const props = defineProps({
  /**
   * 是否禁用
   */
  isDiable: {
    type: Boolean,
    default: false,
  },
  /**
   * 按钮大小
   */
  size: {
    type: String as PropType<'large' | 'middle' | 'small'>,
    default: 'small',
  },
  /**
   * 传递值
   */
  itemData: {
    type: Object,
    default: null,
  },
  /**
   * 自定义按钮列表
   */
  bthData: {
    type: Array as PropType<BthType[]>,
    default: [
      {
        id: 'rx-edit',
        type: 'primary',
        text: 'edit',
        danger: false,
        ghost: false,
        shape: 'default',
      },
      {
        id: 'rx-delete',
        type: 'primary',
        text: 'delete',
        danger: true,
        ghost: false,
        shape: 'default',
        modal: true,
      },
    ],
  },
  /**
   * 自定义按钮列表
   */
  bthStyle: {
    type: Array as PropType<BthType[]>,
    default: [],
  },
})

/**
 * 显示文本处理
 */
const msgHandler = (text: string) => {
  let msg = text
  props.bthStyle.forEach((s) => {
    if (text == s.text && s.uText) {
      msg = s.uText
    }
  })
  return msg
}

/**
 * 背景颜色
 */
const backHandler = (item: BthType) => {
  let color = item.backColor
  props.bthStyle.forEach((s) => {
    if (item.text == s.text && s.backColor) {
      color = s.backColor
    }
  })
  return color
}

/**
 * 点击事件
 */
const onClick = (item: BthType) => {
  if (item.modal) {
    Modal.confirm({
      title: t('common.confirm'),
      icon: item.modalIcon ?? createVNode(ExclamationCircleOutlined),
      content: item.modalTitle ?? t('message.youSureProceedThisOperation'),
      okText: item.modalConfirmText ?? t('common.confirm'),
      cancelText: item.modalConfirmText ?? t('common.cancel'),
      onOk: () => {
        emit('onBth', item.id, true, props.itemData)
      },
      onCancel: () => {
        emit('onBth', item.id, false)
      },
    })

    return
  }

  emit('onBth', item.id, true, props.itemData)
}
</script>

<style lang="scss" scoped>
.table_toolbar_box {
  padding: 10px 0;
}
</style>
