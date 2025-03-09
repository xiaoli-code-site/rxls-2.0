<template>
  <div class="from_permission_box">
    <a-form
      ref="formRef"
      :rules="rules"
      :model="formState"
      :label-col="{ span: 6 }"
      :wrapper-col="{ span: 14 }"
    >
      <a-form-item :label="t('common.id')" name="id" v-if="formState.permissionsId != 0">
        <a-input :disabled="true" v-model:value="formState.permissionsId">
        </a-input>
      </a-form-item>

      <a-form-item :label="t('permission.url')" name="url">
        <a-input
          v-model:value="formState.url"
          show-count
          :placeholder="t('common.pleaseInput')"
          :maxlength="20"
        >
        </a-input>
      </a-form-item>

      <a-form-item :label="t('permission.description')" name="description">
        <a-textarea
          style="height: 100px"
          v-model:value="formState.description"
          show-count
          :placeholder="t('common.pleaseInput')"
          :maxlength="2000"
        >
        </a-textarea>
      </a-form-item>

      <a-form-item :label="t('common.state')" name="state" required>
        <a-radio-group
          :disabled="formState.permissionsId == 0"
          v-model:value="formState.state"
        >
          <a-radio :value="CommonStateEnum.YES">{{
            t("common.normal")
          }}</a-radio>
          <a-radio :value="CommonStateEnum.NO">{{
            t("common.disable")
          }}</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
        <a-space :size="30">
          <a-button type="primary" @click="onSubmit">
            {{ t("bth.confirm") }}</a-button
          >
          <a-button @click="onCancel">{{ t("bth.cancel") }}</a-button>
        </a-space>
      </a-form-item>
    </a-form>
  </div>
</template>

<!-- 角色表单 -->
<script setup lang="ts">
import dayjs, { Dayjs } from "dayjs";
import { useI18n } from "vue-i18n";
import { ref, watch } from "vue";
import type { PropType } from "vue";
import { CommonStateEnum } from "@/config/enum/common";
import { message } from "ant-design-vue";
import { Rule } from "ant-design-vue/es/form";
import { Permissions } from "@/api/system/permissions/type";

const props = defineProps({
  /**
   * 传递的数据，用于显示编辑
   */
  data: {
    type: Object as PropType<Permissions | null>,
    default: null,
  },
});

const emit = defineEmits<{
  /**
   * 提交确认方法,固定写法
   * @param data 提交的数据，取消则传递null
   */
  (e: "handleBthClick", data: any): void;
}>();

/**
 * 国际化状态
 */
const { t } = useI18n();

/**
 * 创建时间
 */
const createTimes = ref<Dayjs>(dayjs());

/**
 * 表单内容
 */
const formState = ref<Permissions>({
  updateTime: createTimes.value.format("YYYY-MM-DD HH:mm:ss"),
  permissionsId: 0,
  description: "",
  url: "",
  createTime: "",
  state: 0,
});

/**
 * 监听传递值
 */
watch(
  () => props.data,
  (newValue, _oldValue) => {
    if (newValue && props.data) {
      formState.value = { ...props.data };
    } else {
      formState.value.permissionsId = 0;
      formState.value.description = "";
      formState.value.url = "";
      formState.value.createTime = createTimes.value.format(
        "YYYY-MM-DD HH:mm:ss"
      );
      formState.value.state = CommonStateEnum.YES;
    }
  },
  { immediate: true }
);

/**
 * 点击提交按钮
 */
const onSubmit = () => {
  formRef.value
    .validate()
    .then(() => {
      emit("handleBthClick", {
        ...formState.value,
      });
    })
    .catch((_error: any) => {
      message.error(t("message.pleaseCheck"));
    });
};

/**
 * ant表单实例
 */
const formRef = ref();

/**
 * 校验规则
 */
const rules: Record<string, Rule[]> = {
  url: [
    {
      required: true,
      trigger: "change",
      validator: async (_rule: Rule, value: string) => {
        if (value === "") {
          return Promise.reject(t("message.valueNoEmpty"));
        } else {
          return Promise.resolve();
        }
      },
    },
  ],
  description: [
    {
      required: true,
      trigger: "change",
      validator: async (_rule: Rule, value: string) => {
        if (value === "") {
          return Promise.reject(t("message.valueNoEmpty"));
        } else {
          return Promise.resolve();
        }
      },
    },
  ],
};

/**
 * 取消按钮
 */
const onCancel = () => {
  emit("handleBthClick", null);
};
</script>

<style scoped lang="scss">
.from_permission_box {
  width: 100%;
  max-height: 70vh;
  overflow: auto;
}
</style>
