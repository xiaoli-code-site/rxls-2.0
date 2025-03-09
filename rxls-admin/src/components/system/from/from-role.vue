<template>
  <div class="form_role_box">
    <a-form
      ref="formRef"
      :rules="rules"
      :model="formState"
      :label-col="{ span: 6 }"
      :wrapper-col="{ span: 14 }"
      :disabled="formState.roleId == RoleEnum.SUPER_ADMIN"
    >
      <a-form-item :label="t('role.roleName')" name="roleName">
        <a-input
          v-model:value="formState.roleName"
          show-count
          :placeholder="t('common.pleaseInput')"
          :maxlength="20"
        >
          <template #prefix>
            <user-outlined />
          </template>
        </a-input>
      </a-form-item>

      <a-form-item :label="t('role.roleValue')" name="roleValue">
        <a-input-number
          :disabled="roleValueIsEdit(props.data?.roleId)"
          v-model:value="formState.roleValue"
          show-count
          :placeholder="t('common.pleaseInput')"
          :max="99999"
          :min="1"
        />
      </a-form-item>

      <a-form-item :label="t('role.permission')" required>
        <a-button type="link" @click="showPermission">{{
          t("common.configuration")
        }}</a-button>
      </a-form-item>

      <a-form-item :label="t('role.menu')" required>
        <a-button type="link" @click="showMenu">{{
          t("common.configuration")
        }}</a-button>
      </a-form-item>

      <a-form-item :label="t('common.state')" name="state" required>
        <a-radio-group
          :disabled="formState.roleId == 0"
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

    <!-- 权限设置弹窗 -->
    <a-modal
      :destroyOnClose="true"
      v-model:open="openPermission"
      :title="t('role.permission')"
      :footer="null"
      :cancel="onPermissionCancel"
    >
      <div class="content">
        <a-collapse v-model:activeKey="activeKey">
          <template #expandIcon="{ isActive }">
            <caret-right-outlined :rotate="isActive ? 90 : 0" />
          </template>
          <a-collapse-panel
            v-for="(item, index) in props.auths.permissions"
            :key="index + 1"
            :header="t('menu.' + item.title)"
          >
            <a-checkbox-group :value="showModalPermissions.map((s) => s.url)">
              <a-checkbox
                @change="onPermissionChange(item.permissions[indexs])"
                v-for="(items, indexs) in item.permissions.map(
                  (s) => s.description
                )"
                :key="indexs"
                :value="item.permissions[indexs].url"
                >{{ items }}</a-checkbox
              >
            </a-checkbox-group>
          </a-collapse-panel>
        </a-collapse>
      </div>

      <div class="permission_bth">
        <a-button @click="onPermissionReset">{{ t("common.reset") }}</a-button>
        <a-button @click="onPermissionConfirm" type="primary">{{
          t("common.confirm")
        }}</a-button>
      </div>
    </a-modal>

    <!-- 菜单设置弹窗 -->
    <a-modal
      :destroyOnClose="true"
      v-model:open="openMenu"
      :title="t('role.menu')"
      :footer="null"
      :cancel="onMenuCancel"
    >
      <div class="content">
        <a-collapse v-model:activeKey="activeKey">
          <template #expandIcon="{ isActive }">
            <caret-right-outlined :rotate="isActive ? 90 : 0" />
          </template>
          <!-- <a-collapse-panel
            v-for="(item, index) in props.auths.menus"
            :key="index + 1"
            :header="t('menu.' + item.title)"
          > -->

          <a-checkbox-group :value="showModalMenu.map((s) => s.menuId)">
            <a-row >
              <a-col :span="8" v-for="(item, index) in props.auths.menus" :key="index">
                <a-checkbox :value="item.menuId" @change="onMenuChange(item)">{{
                  t(`menu.${item.title}`)
                }}</a-checkbox>
              </a-col>
            </a-row>
          </a-checkbox-group>
          <!-- </a-collapse-panel> -->
        </a-collapse>
      </div>

      <div class="permission_bth">
        <a-button @click="onMenuReset">{{ t("common.reset") }}</a-button>
        <a-button @click="onMenuConfirm" type="primary">{{
          t("common.confirm")
        }}</a-button>
      </div>
    </a-modal>
  </div>
</template>

<!-- 角色表单 -->
<script setup lang="ts">
import { RoleEnum } from "@/config/enum/roleEnum";
import { numbers, names } from "../../../util/check";
import { UserOutlined, CaretRightOutlined } from "@ant-design/icons-vue";
import dayjs, { Dayjs } from "dayjs";
import { useI18n } from "vue-i18n";
import { ref, watch } from "vue";
import type { PropType } from "vue";
import { CommonStateEnum } from "@/config/enum/common";
import { message } from "ant-design-vue";
import { Rule } from "ant-design-vue/es/form";
import { Role } from "@/api/system/role/type";
import { Menu, MenuPermissions } from "@/api/system/menu/type";
import { ReqPermissionsAllByRole } from "@/api/system/permissions";
import { Permissions } from "@/api/system/permissions/type";
import { ReqRoleMenu } from "@/api/system/menu";

const props = defineProps({
  /**
   * 传递的数据，用于显示编辑
   */
  data: {
    type: Object as PropType<Role | null>,
    default: null,
  },
  auths: {
    type: Object as PropType<MenuPermissions>,
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
const formState = ref<Role>({
  roleId: 0,
  roleName: "",
  roleValue: 1,
  state: CommonStateEnum.YES,
  createTime: createTimes.value.format("YYYY-MM-DD HH:mm:ss"),
  updateTime: createTimes.value.format("YYYY-MM-DD HH:mm:ss"),
});

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
      emit("handleBthClick", {
        ...formState.value,
        permissions: usePermissions.value.map((s) => s.permissionsId),
        menus: useMenu.value.map((s) => s.menuId),
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
 * 打开权限的折叠
 */
const activeKey = ref([]);

/**
 * 校验规则
 */
const rules: Record<string, Rule[]> = {
  roleName: [
    {
      required: true,
      trigger: "change",
      validator: async (_rule: Rule, value: string) => {
        if (value === "") {
          return Promise.reject(t("role.roleNameNoEmpty"));
        } else if (
          !names(formState.value.roleName) ||
          formState.value.roleName.length < 2
        ) {
          return Promise.reject(t("user.nicknameFormatError"));
        } else {
          return Promise.resolve();
        }
      },
    },
  ],
  roleValue: [
    {
      required: true,
      trigger: "change",
      validator: async (_rule: Rule, value: string) => {
        if (value === "") {
          return Promise.reject(t("role.roleValueNoEmpty"));
        } else if (
          !numbers(formState.value.roleValue + "") ||
          formState.value.roleValue < 1
        ) {
          return Promise.reject(t("role.roleValueMin"));
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

/**
 * 当前拥有的权限信息
 */
const usePermissions = ref<Permissions[]>([]);

/**
 * 当前弹窗显示的权限信息
 */
const showModalPermissions = ref<Permissions[]>([]);

/**
 * 权限弹窗控制
 */
const openPermission = ref(false);

/**
 * 权限弹窗显示事件
 */
const showPermission = () => {
  openPermission.value = true;
  showModalPermissions.value = [...usePermissions.value];
};

/**
 * 权限选择事件
 */
const onPermissionChange = (e: Permissions) => {
  let index = showModalPermissions.value.findIndex((s) => s.url == e.url);
  if (index == -1) {
    showModalPermissions.value.push(e);
    return;
  } else {
    showModalPermissions.value.splice(index, 1);
  }
};

/**
 * 权限选择点击确认事件
 */
const onPermissionConfirm = () => {
  usePermissions.value = [...showModalPermissions.value];
  openPermission.value = false;
};

/**
 * 权限选择点击重置事件
 */
const onPermissionReset = () => {
  showModalPermissions.value = [];
};

/**
 * 权限选择点击重置事件
 */
const onPermissionCancel = () => {
  showModalPermissions.value = [...usePermissions.value];
};

/**
 * 权限值是否可编辑
 */
const roleValueIsEdit = (id: number | string | null | undefined) => {
  if (!id) {
    return true;
  }
  return (
    id == RoleEnum.ADMIN || id == RoleEnum.USER || id == RoleEnum.SUPER_ADMIN
  );
};

/**
 * 当前拥有的菜单信息
 */
const useMenu = ref<Menu[]>([]);

/**
 * 当前弹窗显示的菜单信息
 */
const showModalMenu = ref<Menu[]>([]);

/**
 * 菜单弹窗控制
 */
const openMenu = ref(false);

/**
 * 菜单窗显示事件
 */
const showMenu = () => {
  openMenu.value = true;
  showModalPermissions.value = [...usePermissions.value];
};

/**
 * 菜单选择事件
 */
const onMenuChange = (e: Menu) => {
  let index = showModalMenu.value.findIndex((s) => s.menuId == e.menuId);
  if (index == -1) {
    showModalMenu.value.push(e);
    return;
  } else {
    showModalMenu.value.splice(index, 1);
  }
};

/**
 * 权限选择点击确认事件
 */
const onMenuConfirm = () => {
  useMenu.value = [...showModalMenu.value];
  openMenu.value = false;
};

/**
 * 权限选择点击重置事件
 */
const onMenuReset = () => {
  showModalMenu.value = [];
};

/**
 * 权限选择点击重置事件
 */
const onMenuCancel = () => {
  showModalMenu.value = [...useMenu.value];
};

/**
 * 监听传递值
 */
watch(
  () => props.data,
  (newValue, _oldValue) => {
    if (newValue && props.data) {
      formState.value = { ...props.data };
      createTimes.value = dayjs(formState.value.createTime);
      let hide = message.loading(t("message.preparingPleaseWait"), 0);
      let index = 0; //请求结束标识
      ReqPermissionsAllByRole(formState.value.roleId)
        .then((res) => {
          if (res.data.code == CommonStateEnum.OK) {
            usePermissions.value = res.data.data;
            showModalPermissions.value = usePermissions.value;
          }
        })
        .finally(() => {
          index += 1;
          if (index == 2) {
            setTimeout(hide, 500);
          }
        });
      ReqRoleMenu(newValue.roleId)
        .then((res) => {
          if (res.data.code == CommonStateEnum.OK) {
            useMenu.value = res.data.data;
            showModalMenu.value = useMenu.value;
          }
        })
        .finally(() => {
          index += 1;
          if (index == 2) {
            setTimeout(hide, 500);
          }
        });
    } else {
      formState.value.roleId = 0;
      formState.value.roleName = "";
      formState.value.roleValue = 1;
      formState.value.state = CommonStateEnum.YES;
      formState.value.createTime = createTimes.value.format(
        "YYYY-MM-DD HH:mm:ss"
      );
    }
  },
  { immediate: true }
);
</script>

<style scoped lang="scss">
.form_role_box {
  width: 100%;
  max-height: 70vh;
  overflow: auto;
}

.content {
  max-height: 50vh;
  overflow: auto;
}

.permission_bth {
  padding-top: 20px;
  text-align: end;

  button {
    margin-left: 20px;
  }
}
</style>
