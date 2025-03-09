<template>
  <div>
    <a-form layout="inline" style="padding-top: 5px">
      <a-form-item :label="t('task.username')">
        <a-input
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

      <a-form-item :label="t('task.groupName')">
        <a-input
          v-model:value="formState.groupName"
          show-count
          :placeholder="t('common.pleaseInput')"
          :maxlength="20"
        >
        </a-input>
      </a-form-item>
      <a-form-item :label="t('task.jobName')">
        <a-input
          v-model:value="formState.jobName"
          show-count
          :placeholder="t('common.pleaseInput')"
          :maxlength="20"
        >
        </a-input>
      </a-form-item>

      <a-form-item>
        <a-space :size="20">
          <a-button size="small" type="primary" danger @click="onClear">{{
            t("bth.reset")
          }}</a-button>
          <a-button size="small" type="primary" @click="onSubmit">{{
            t("bth.search")
          }}</a-button>
        </a-space>
      </a-form-item>
    </a-form>
  </div>
</template>

<!-- 任务查询参数表单 -->
<script lang="ts" setup>
import { useI18n } from "vue-i18n";
import { ref } from "vue";
import { PageQuery } from "@/api/system/task/type";

const emit = defineEmits<{
  /**
   * 提交数据
   */
  (e: "onSubmit", data: PageQuery): void;
  /**
   * 清空数据
   */
  (e: "onClear"): void;
}>();

/**
 * 国际化状态
 */
const { t } = useI18n();

/**
 * 查询表单
 */
const formState = ref<PageQuery>({
  size: 10,
  page: 1,
  groupName: "",
  jobName: "",
  username: "",
});

/**
 * 点击查询事件
 * @param data 表单数据
 */
const onSubmit = () => {
  emit("onSubmit", { ...formState.value });
};

/**
 * 重置
 */
const onClear = () => {
  formState.value = {
    size: 10,
    page: 1,
    groupName: "",
    jobName: "",
    username: "",
  };
  emit("onClear");
};
</script>
