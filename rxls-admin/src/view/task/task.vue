<template>
  <div>
    <select-from-task @on-submit="onSearch" @on-clear="onClear" />
    <rx-table
      table-name="task"
      :data-source="dataLists"
      :columns="columns"
      y="60vh"
      :loading="loading"
      :methods="['deleteSelected', 'add']"
      :total-data="dataTotal"
      :pagination="formState.page"
      @on-refresh="resetHandler"
      @on-delete="(list) => delHandler(list)"
      @on-page="onPage"
      @on-alter="onAlter"
    >
      <template #alter="{ onHandleBthClick, fromData }">
        <from-task
          :key="new Date() + 'task-from'"
          @handle-bth-click="onHandleBthClick"
          :data="fromData"
        />
      </template>

      <template #default="{ record, column }">
        <template v-if="column.key === 'username'">
          {{ record.user.nickname }}
        </template>

        <template v-if="column.key === 'jobCode'">
          <a-tag size="small" color="green">{{
            t(`task.${record.jobCode}`)
          }}</a-tag>
        </template>

        <template v-if="column.key === 'result'">
          <a-button size="small" type="primary" color="green">{{
            t(`common.detail`)
          }}</a-button>
        </template>

        <template v-if="column.key === 'model'">
          <a-tag color="red">
            {{
              t(
                `task.${
                  record.model == CommonStateEnum.YES
                    ? "normalMode"
                    : "cornMode"
                }`
              )
            }}
          </a-tag>
        </template>

        <template v-else-if="column.key === 'category'">
          <a-tag color="orange">
            {{
              t(
                `oplog.${
                  record.category == OplogEnum.CRITICAL
                    ? "critical"
                    : record.category == OplogEnum.IMPORTANT
                    ? "important"
                    : "general"
                }`
              )
            }}
          </a-tag>
        </template>

        <template v-else-if="column.key === 'address'">
          <a-tag :bordered="false" color="processing">{{
            record.address
          }}</a-tag>
        </template>

        <template v-if="column.key === 'createTime'">
          <a-space>
            <component
              style="font-size: 16px"
              :is="ClockCircleOutlined"
            ></component>
            {{ record.createTime }}
          </a-space>
        </template>

        <template v-else-if="column.key === 'state'">
          <a-switch
            :checked="record.state == CommonStateEnum.YES"
            @click="stateUpdate(record.schedulerTaskId)"
          >
            <template #checkedChildren><check-outlined /></template>
            <template #unCheckedChildren><close-outlined /></template>
          </a-switch>
        </template>
      </template>
    </rx-table>
  </div>
</template>

<!-- 任务管理 -->
<script lang="ts" setup>
import selectFromTask from "@/components/system/from/select-from-task.vue";
import { CheckOutlined, CloseOutlined } from "@ant-design/icons-vue";
import fromTask from "@/components/system/from/from-task.vue";
import { useI18n } from "vue-i18n";
import RxTable from "@/components/system/rx_table/rx-table.vue";
import { CommonStateEnum } from "@/config/enum/common";
import { onMounted, ref } from "vue";
import {
  SysScheduledTask,
  PageQuery,
  AddQuery,
  UpdateQuery,
} from "@/api/system/task/type";
import { ClockCircleOutlined } from "@ant-design/icons-vue";
import { useTableHandler, Key } from "@/components/system/rx_table/handler";
import { OplogEnum } from "@/config/enum/oplogEnum";
import { ReqUpdateTaskState } from "@/api/system/task";
import { message } from "ant-design-vue";

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
 * 通用处理函数
 */
const {
  searchClick,
  addHandler,
  updateHandler,
  dataLists,
  dataTotal,
  loading,
  delHandler,
  pageHandler,
} = useTableHandler<SysScheduledTask, PageQuery, AddQuery, UpdateQuery>(
  "task",
  "schedulerTaskId",
  formState
);

/**
 * 列集合
 */
const columns = ref([
  {
    title: "id",
    dataIndex: "schedulerTaskId",
    key: "schedulerTaskId",
    width: 100,
  },
  {
    title: "username",
    dataIndex: "username",
    key: "username",
    width: 120,
  },
  {
    title: "expression",
    dataIndex: "expression",
    key: "expression",
    width: 150,
  },
  {
    title: "model",
    dataIndex: "model",
    key: "model",
    width: 100,
  },
  {
    title: "groupName",
    dataIndex: "groupName",
    key: "groupName",
    width: 120,
  },
  {
    title: "jobCode",
    dataIndex: "jobCode",
    key: "jobCode",
    width: 130,
  },
  {
    title: "jobName",
    dataIndex: "jobName",
    key: "jobName",
    width: 150,
  },
  {
    title: "startTime",
    dataIndex: "startTime",
    key: "startTime",
    width: 180,
  },
  {
    title: "endTime",
    dataIndex: "endTime",
    key: "endTime",
    width: 180,
  },
  {
    title: "state",
    dataIndex: "state",
    key: "state",
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
 * 编辑，提交确认点击事件
 * @param data 数据
 * @param mode true 编辑  false 新增
 * @param close 关闭弹窗方法
 */
const onAlter = (
  mode: boolean,
  data: SysScheduledTask & Key,
  close: Function
) => {
  console.log(data);
  if (!mode) {
    let query: AddQuery = {
      expression: data.expression,
      endTime: data.endTime,
      unit: data.unit,
      groupName: data.groupName,
      jobCode: data.jobCode,
      jobName: data.jobName,
      startTime: data.startTime,
      state: data.state,
      model: data.model,
    };
    addHandler(query, close);
    return;
  }
  let query: UpdateQuery = {
    schedulerTaskId: data.schedulerTaskId,
    expression: data.expression,
    endTime: data.endTime,
    unit: data.unit,
    groupName: data.groupName,
    jobCode: data.jobCode,
    jobName: data.jobName,
    startTime: data.startTime,
    state: data.state,
    model: data.model,
  };
  updateHandler(query, close);
};

/**
 * 状态变化
 * @param 配置id
 */
const stateUpdate = (id: number) => {
  loading.value = true;
  ReqUpdateTaskState(id)
    .then((res) => {
      if (res.data.code == CommonStateEnum.OK) {
        let index = dataLists.value.findIndex((s) => s.schedulerTaskId == id);
        if (index != -1) {
          dataLists.value[index].state =
            dataLists.value[index].state == CommonStateEnum.YES
              ? CommonStateEnum.NO
              : CommonStateEnum.YES;
          if (dataLists.value[index].state) {
            message.success(t("task.taskStart"));
          } else {
            message.warning(t("task.taskStop"));
          }
        }
      }
    })
    .finally(() => {
      setTimeout(() => {
        loading.value = false;
      }, 200);
    });
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
  formState.value.page = 1;
  formState.value.username = "";
  formState.value.groupName = "";
  formState.value.jobName = "";
  searchClick();
};

/**
 * 刷新、初始化事件
 */
const resetHandler = () => {
  onClear();
};

/**
 * 页面加载
 */
onMounted(() => {
  resetHandler();
});
</script>

<style lang="scss" scoped>
.tasks_box {
  position: relative;
  width: 100%;
  height: 100vh;
  padding: 0 5px;
}
</style>
