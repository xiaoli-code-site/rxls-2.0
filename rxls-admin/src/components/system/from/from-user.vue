<template>
  <div class="form_user_box">
    <a-form
      ref="formRef"
      :rules="rules"
      :model="formState"
      :label-col="{ span: 6 }"
      :wrapper-col="{ span: 14 }"
    >
      <a-form-item :label="t('user.avatar')" name="avatar">
        <image-upload
          :max-count="1"
          :img-list="imgList"
          @on-upload="onUpload"
          @on-delete="formState.avatar = ''"
        >
          <template #default>
            <a-avatar
              v-if="formState.avatar"
              :src="formState.avatar"
              shape="square"
              :size="64"
            >
              <template #icon>
                <UserOutlined />
              </template>
            </a-avatar>
          </template>
        </image-upload>
      </a-form-item>

      <a-form-item :label="t('user.nickname')" name="nickname">
        <a-input
          v-model:value="formState.nickname"
          show-count
          :placeholder="t('common.pleaseInput')"
          :maxlength="20"
        >
          <template #prefix>
            <user-outlined />
          </template>
        </a-input>
      </a-form-item>

      <a-form-item :label="t('user.phone')" name="phone">
        <a-input
          v-model:value="formState.phone"
          show-count
          :placeholder="t('common.pleaseInput')"
          :maxlength="20"
        >
          <template #prefix>
            <phone-outlined />
          </template>
        </a-input>
      </a-form-item>

      <a-form-item :label="t('user.role')" name="role">
        <a-checkbox-group v-model:value="formRoleIds">
          <a-checkbox
            :key="index"
            v-for="(item, index) in props.roleList"
            :value="item.roleId"
            :disabled="item.roleValue >= RoleEnum.ADMIN_VALUE"
            >{{ t(`common.${item.roleName}`) }}</a-checkbox
          >
        </a-checkbox-group>
      </a-form-item>

      <a-form-item :label="t('user.sex')" name="sex" required>
        <a-radio-group v-model:value="formState.sex">
          <a-radio :value="1">{{ t("user.boy") }}</a-radio>
          <a-radio :value="0">{{ t("user.girl") }}</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item :label="t('common.state')" name="state" required>
        <a-radio-group
          :disabled="formState.userId == 0"
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

      <a-form-item :label="t('user.born')" name="born">
        <a-date-picker
          :placeholder="t('message.pleaseSelect')"
          :status="!isTime ? 'error' : ''"
          v-model:value="bornTime"
          format="YYYY-MM-DD"
        />
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
            {{ t("bth.confirm") }}</a-button
          >
          <a-button @click="onCancel">{{ t("bth.cancel") }}</a-button>
        </a-space>
      </a-form-item>
    </a-form>
  </div>
</template>

<!-- 用户表单 -->
<script setup lang="ts">
import { RoleEnum } from "@/config/enum/roleEnum";
import { phone, names } from "../../../util/check";
import ImageUpload from "@/components/common/upload/image-upload.vue";
import { UserOutlined, PhoneOutlined } from "@ant-design/icons-vue";
import dayjs, { Dayjs } from "dayjs";
import { useI18n } from "vue-i18n";
import { computed, ref, watch } from "vue";
import type { PropType } from "vue";
import { User } from "@/api/system/user/type";
import { CommonStateEnum } from "@/config/enum/common";
import { message } from "ant-design-vue";
import { Rule } from "ant-design-vue/es/form";
import { Role } from "@/api/system/role/type";

const props = defineProps({
  /**
   * 传递的数据，用于显示编辑
   */
  data: {
    type: Object as PropType<User | null>,
    default: null,
  },
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
 * 出生时间
 */
const bornTime = ref<Dayjs>();

/**
 * 用户角色列表
 */
const formRoleIds = ref<number[]>([]);

/**
 * 表单内容
 */
const formState = ref<User>({
  userId: 0,
  phone: "",
  avatar: "",
  nickname: "",
  createTime: createTimes.value.format("YYYY-MM-DD HH:mm:ss"),
  updateTime: createTimes.value.format("YYYY-MM-DD HH:mm:ss"),
  state: CommonStateEnum.YES,
  sex: 0,
  roles: [],
  born: "",
});

/**
 * 监听传递值
 */
watch(
  () => props.data,
  (newValue, _oldValue) => {
    if (newValue && props.data) {
      formState.value = { ...props.data };
      createTimes.value = dayjs(formState.value.createTime);
      formRoleIds.value = formState.value.roles.map((s) => s.roleId);
      bornTime.value = dayjs(formState.value.born);
    } else {
      formState.value.avatar = "http://dummyimage.com/100x100";
      formState.value.createTime = "";
      formState.value.nickname = "";
      formState.value.phone = "";
      formState.value.sex = 1;
      formState.value.state = CommonStateEnum.YES;
      formState.value.userId = 0;
      formRoleIds.value = [];
    }
  },
  { immediate: true }
);

/**
 * 图片列表
 */
const imgList = computed(() => {
  if (formState.value.avatar) {
    return [formState.value.avatar];
  }
  return [];
});

/**
 * 图片上传
 */
const onUpload = (data: string | string[]) => {
  formState.value.avatar = data as unknown as string;
};

/**
 * 点击提交按钮
 */
const onSubmit = () => {
  formRef.value
    .validate()
    .then(() => {
      formState.value.createTime = createTimes.value.format(
        "YYYY-MM-DD HH:mm:ss"
      );
      if (bornTime.value) {
        formState.value.born = bornTime.value?.format("YYYY-MM-DD");
      }
      let arr = props.roleList.filter(
        (s) => formRoleIds.value.findIndex((id) => s.roleId === id) != -1
      );
      formState.value.roles = [...arr];
      emit("handleBthClick", { ...formState.value });
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
  born: [
    {
      required: true,
      trigger: "change",
      validator: async (_rule: Rule, _value: string) => {
        if (!bornTime.value) {
          return Promise.reject(t("user.bornNoEmpty"));
        } else {
          return Promise.resolve();
        }
      },
    },
  ],
  nickname: [
    {
      required: true,
      trigger: "change",
      validator: async (_rule: Rule, value: string) => {
        if (value === "") {
          return Promise.reject(t("user.nicknameNoEmpty"));
        } else if (
          !names(formState.value.nickname) ||
          formState.value.nickname.length < 2
        ) {
          return Promise.reject(t("user.nicknameFormatError"));
        } else {
          return Promise.resolve();
        }
      },
    },
  ],
  role: [
    {
      required: true,
      trigger: "change",
      validator: async (_rule: Rule, _value: string) => {
        if (formRoleIds.value.length == 0) {
          return Promise.reject(t("user.roleNoEmpty"));
        } else {
          return Promise.resolve();
        }
      },
    },
  ],
  phone: [
    {
      required: true,
      trigger: "change",
      validator: async (_rule: Rule, value: string) => {
        if (value == "" || !value) {
          return Promise.reject(t("user.phoneNumberNoEmpty"));
        } else if (!phone(formState.value.phone)) {
          return Promise.reject(t("user.phoneNumberFormatError"));
        } else {
          return Promise.resolve();
        }
      },
    },
  ],
  avatar: [
    {
      required: true,
      trigger: "blur",
      validator: async (_rule: Rule, value: string) => {
        if (value === "" || formState.value.avatar == "") {
          return Promise.reject(t("user.avatarNoEmpty"));
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

/**
 * 时间是否为空
 */
const isTime = ref(true);
</script>

<style lang="scss">
.form_user_box {
  width: 100%;
  max-height: 75vh;
  overflow: auto;
}
</style>
