<template>
  <div>
    <a-form layout="inline" style="padding-top: 5px">
      <a-form-item :label="t('user.nickname')">
        <a-input
          v-model:value="formState.nickname"
          :placeholder="t('common.pleaseInput')"
          show-count
          :maxlength="20"
        />
      </a-form-item>
      <a-form-item :label="t('user.phone')">
        <a-input
          v-model:value="formState.phone"
          show-count
          :maxlength="20"
          :placeholder="t('common.pleaseInput')"
        />
      </a-form-item>
    </a-form>
    <a-form layout="inline" style="padding-top: 5px">
      <a-form-item :label="t('user.role')" name="role">
        <a-radio-group v-model:value="formState.role">
          <a-radio value="">{{ t("common.all") }}</a-radio>
          <a-radio
            v-for="(item, index) in props.roleList"
            v-show="item.roleValue < RoleEnum.ADMIN_VALUE"
            :key="index"
            :value="item.roleId"
            >{{ t(`common.${item.roleName}`) }}</a-radio
          >
        </a-radio-group>
      </a-form-item>

      <a-form-item :label="t('user.sex')">
        <a-radio-group v-model:value="formState.sex">
          <a-radio value="">{{ t("common.all") }}</a-radio>
          <a-radio :value="1">{{ t("user.boy") }}</a-radio>
          <a-radio :value="0">{{ t("user.girl") }}</a-radio>
        </a-radio-group>
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

<!-- 用户查询参数表单 -->
<script lang="ts" setup>
import { CommonStateEnum } from "@/config/enum/common";
import { useI18n } from "vue-i18n";
import { ref, type PropType } from "vue";
import { PageQuery } from "@/api/system/user/type";
import { Role } from "@/api/system/role/type";
import { RoleEnum } from "@/config/enum/roleEnum";

const props = defineProps({
  /**
   * 角色列表
   */
  roleList: {
    type: Array as PropType<Role[]>,
    default: [],
  },
});

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
 * 查询表单
 */
const formState = ref<PageQuery>({
  nickname: "",
  phone: "",
  sex: "",
  state: "",
  page: 1,
  size: 10,
  role: "",
});

/**
 * 国际化状态
 */
const { t } = useI18n();

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
    nickname: "",
    phone: "",
    sex: "",
    state: "",
    page: 1,
    size: 10,
    role: "",
  };
  emit("onClear");
};
</script>
