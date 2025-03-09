<template>
  <div class="table_toolbar_box" v-if="bthList.length">
    <a-space warp :size="5">
      <a-button
        v-for="(item, index) in bthList"
        :key="index"
        v-display="item.fn ?? true"
        :disabled="item.disabled ?? props.isDiable"
        :style="{ background: item.backColor }"
        :type="item.type"
        :ghost="item.ghost"
        :shape="item.shape"
        :danger="item.danger"
        :size="item.size ?? props.size"
        @click="onClick(item)"
      >
        <div class="size">
          {{ t(`bth.${item.text}`) }}
        </div>
      </a-button>
    </a-space>
  </div>
</template>

<!-- 表格操作工具栏 -->
<script lang="ts" setup>
import { useI18n } from "vue-i18n";
import { ExclamationCircleOutlined } from "@ant-design/icons-vue";
import { Modal } from "ant-design-vue";
import { computed, createVNode, PropType } from "vue";
import { BthType } from "@/config/bth";

const emit = defineEmits<{
  /**
   * 点击事件
   * @param id 按钮唯一标识
   * @param confirm true确认 false 取消 （a-button 属性modal为true时使用）
   * @param data 传递值
   */
  (e: "onBth", id: string, confirm?: boolean, data?: Object): void;
}>();

/**
 * 国际化状态
 */
const { t } = useI18n();

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
    type: String as PropType<"large" | "middle" | "small">,
    default: "small",
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
        id: "rx-edit",
        type: "primary",
        text: "detail",
        danger: false,
        ghost: false,
        shape: "default",
      },
      {
        id: "rx-delete",
        type: "primary",
        text: "delete",
        danger: true,
        ghost: false,
        shape: "default",
        modal: true,
      },
    ],
  },
});

/**
 * 点击事件
 */
const onClick = (item: BthType) => {
  if (item.modal) {
    Modal.confirm({
      title: t("common.confirm"),
      icon: item.modalIcon ?? createVNode(ExclamationCircleOutlined),
      content: item.modalTitle ?? t("message.youSureProceedThisOperation"),
      okText: item.modalConfirmText ?? t("bth.confirm"),
      cancelText: item.modalConfirmText ?? t("bth.cancel"),
      onOk: () => {
        emit("onBth", item.id, true, props.itemData);
      },
      onCancel: () => {
        emit("onBth", item.id, false);
      },
    });

    return;
  }

  emit("onBth", item.id, true, props.itemData);
};

/**
 * 按钮处理
 */
const bthList = computed(() => {
  let temp = props.bthData.filter((s) => s.fn && s.fn());
  let temps = props.bthData.filter((s) => !s.fn);
  return [...temp, ...temps];
});
</script>

<style lang="scss" scoped>
.table_toolbar_box {
  padding: 10px 0;

  .size {
    font-size: 13px;
  }
}
</style>
