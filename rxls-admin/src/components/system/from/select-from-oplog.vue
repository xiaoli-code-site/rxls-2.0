<template>
  <div>
    <a-form layout="inline" style="padding-top: 5px">
      <a-form-item :label="t('oplog.category')">
        <a-radio-group v-model:value="formState.leve">
          <a-radio value="">
            <a-tag>
              {{ t("common.all") }}
            </a-tag>
          </a-radio>
          <a-radio :value="OplogEnum.CRITICAL">
            <a-tag color="red">
              {{ t("oplog.critical") }}
            </a-tag>
          </a-radio>
          <a-radio :value="OplogEnum.IMPORTANT"
            ><a-tag color="orange">
              {{ t("oplog.important") }}
            </a-tag></a-radio
          >
          <a-radio :value="OplogEnum.GENERAL">
            <a-tag color="blue">
              {{ t("oplog.general") }}
            </a-tag>
          </a-radio>
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

<!-- 操作日志查询参数表单 -->
<script lang="ts" setup>
import { OplogEnum } from "@/config/enum/oplogEnum";
import { useI18n } from "vue-i18n";
import { ref } from "vue";
import { PageQuery } from "@/api/system/oplog/type";

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
  leve: "",
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
    leve: "",
  };
  emit("onClear");
};
</script>
