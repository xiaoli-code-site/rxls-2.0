<template>
  <div class="permission_box">
    <select-from-permission @on-submit="onSearch" @on-clear="onClear" />

    <rx-table
      y="63vh"
      table-name="permission"
      :data-source="dataLists"
      :columns="columns"
      :loading="loading"
      :methods="['deleteSelected', 'add']"
      :total-data="dataTotal"
      :pagination="formState.page"
      @on-excel="(query) => excelHandler(query)"
      @on-refresh="resetHandler"
      @on-delete="(list) => delHandler(list)"
      @on-page="onPage"
      @on-alter="onAlter"
    >
      <template #alter="{ onHandleBthClick, fromData }">
        <from-permission
          :key="new Date() + 'permission-from'"
          @handle-bth-click="onHandleBthClick"
          :data="fromData"
        />
      </template>

      <template #default="{ record, column }">
        <template v-if="column.key === 'url'">
          <a-tag color="pink">
            {{ record.url }}
          </a-tag>
        </template>

        <template
          v-if="column.key === 'createTime' || column.key === 'updateTime'"
        >
          <a-space>
            <component
              style="font-size: 16px"
              :is="
                column.key === 'createTime'
                  ? ClockCircleOutlined
                  : PieChartOutlined
              "
            ></component>
            {{
              column.key === "createTime"
                ? record.createTime
                : record.updateTime
            }}
          </a-space>
        </template>

        <template v-else-if="column.key === 'state'">
          <a-tag
            :color="record.state === CommonStateEnum.YES ? 'green' : 'volcano'"
          >
            {{
              t(
                `common.${
                  record.state == CommonStateEnum.YES ? "normal" : "disable"
                }`
              )
            }}
          </a-tag>
        </template>
      </template>
    </rx-table>
  </div>
</template>

<!-- 管理人员页面 -->
<script lang="ts" setup>
import { useI18n } from "vue-i18n";
import SelectFromPermission from "@/components/system/from/select-from-permission.vue";
import FromPermission from "@/components/system/from/from-permission.vue";
import RxTable from "@/components/system/rx_table/rx-table.vue";
import { CommonStateEnum } from "@/config/enum/common";
import { onMounted, ref } from "vue";
import {
  AddQuery,
  Permissions,
  PageQuery,
  UpdateQuery,
} from "@/api/system/permissions/type";
import { ClockCircleOutlined, PieChartOutlined } from "@ant-design/icons-vue";
import { useTableHandler, Key } from "@/components/system/rx_table/handler";

/**
 * 国际化状态
 */
const { t } = useI18n();

/**
 * 查询表单
 */
const formState = ref<PageQuery>({
  description: "",
  url: "",
  size: 10,
  page: 1,
  state: ""
});

/**
 * 列集合
 */
const columns = ref([
  {
    title: "id",
    dataIndex: "permissionsId",
    key: "permissionsId",
    width: 100,
  },
  {
    title: "url",
    dataIndex: "url",
    key: "url",
    width: 100,
  },
  {
    title: "description",
    dataIndex: "description",
    key: "description",
    width: 300,
  },

  {
    title: "state",
    dataIndex: "state",
    key: "state",
    width: 100,
  },
  {
    title: "createTime",
    dataIndex: "createTime",
    key: "createTime",
    width: 200,
  },
  {
    title: "updateTime",
    dataIndex: "updateTime",
    key: "updateTime",
    width: 200,
  },
  {
    title: "option",
    dataIndex: "option",
    key: "option",
    fixed: "right",
    width: 150,
  },
]);

/**
 * 通用处理函数
 */
const {
  searchClick,
  dataLists,
  dataTotal,
  addHandler,
  loading,
  updateHandler,
  excelHandler,
  delHandler,
  pageHandler,
} = useTableHandler<Permissions, PageQuery, AddQuery, UpdateQuery>(
  "permissions",
  "permissionsId",
  formState
);

/**
 * 编辑，提交确认点击事件
 * @param data 数据
 * @param mode true 编辑  false 新增
 * @param close 关闭弹窗方法
 */
const onAlter = (mode: boolean, data: Permissions & Key, close: Function) => {
  if (!mode) {
    let query: AddQuery = {
      description: data.description,
      url: data.url,
    };
    addHandler(query, close);
    return;
  } else {
    let query: UpdateQuery = {
      permissionsId: data.permissionsId,
      description: data.description,
      url: data.url,
      state: data.state,
    };
    updateHandler(query, close);
  }
};

/**
 * 分页事件
 * @param page 第几页
 * @param pageSize 查询几个
 */
const onPage = (page: number, pageSize: number) => {
  formState.value.page = page;
  formState.value.size = pageSize;
  pageHandler();
};

/**
 * 刷新、初始化事件
 */
const resetHandler = () => {
  onClear();
};

/**
 * 点击搜索
 */
const onSearch = (data?: PageQuery) => {
  if (data) {
    let size = formState.value.size;
    formState.value = data;
    formState.value.size = size;
  }
  formState.value.page = 1;
  searchClick();
};

/**
 * 清空搜索条件
 */
const onClear = () => {
  formState.value.description = "";
  formState.value.url = "";
  formState.value.state = "";
  formState.value.page = 1;
  searchClick();
};

/**
 * 页面加载
 */
onMounted(() => {
  resetHandler();
});
</script>

<style lang="scss" scoped>
.permission_box {
  position: relative;
  width: calc(100% - 10px);
  padding: 0 5px;
}
</style>
