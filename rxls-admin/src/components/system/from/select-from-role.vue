<template>
  <div>
    <a-form layout="inline" style="padding-top: 5px">
      <a-form-item :label="t('role.roleName')">
        <a-input
          v-model:value="formState.roleName"
          :placeholder="t('common.pleaseInput')"
          show-count
          :maxlength="20"
        />
      </a-form-item>
      <a-form-item :label="t('common.state')">
        <a-radio-group v-model:value="formState.state">
          <a-radio value="">{{ t("common.all") }}</a-radio>
          <a-radio :value="CommonStateEnum.YES">{{
            t("common.normal")
          }}</a-radio>
          <a-radio :value="CommonStateEnum.NO">{{
            t("common.disable")
          }}</a-radio>
        </a-radio-group>
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

<!-- 角色查询参数表单 -->
<script lang="ts" setup>
import { CommonStateEnum } from "@/config/enum/common";
import { useI18n } from "vue-i18n";
import { ref } from "vue";
import { PageQuery } from "@/api/system/role/type";

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
  state: "",
  roleName: "",
  size: 10,
  page: 1,
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
    state: "",
    roleName: "",
    size: 10,
    page: 1,
  };
  emit("onClear");
};
</script>
