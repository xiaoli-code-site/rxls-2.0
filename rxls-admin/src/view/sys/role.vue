<template>
  <div class="role_box">
    <select-from-role
      v-model:values="formState"
      @on-submit="onSearch"
      @on-clear="onClear"
    />

    <rx-table
      table-name="role"
      :data-source="dataLists"
      :columns="columns"
      y="60vh"
      :loading="loading"
      :total-data="dataTotal"
      :pagination="formState.page"
      :methods="['deleteSelected', 'add']"
      @on-excel="(query) => excelHandler(query)"
      @on-refresh="resetHandler"
      @on-delete="(list) => delHandler(list)"
      @on-page="onPage"
      @on-alter="onAlter"
    >
      <template #alter="{ onHandleBthClick, fromData }">
        <from-role
          :pemissions="menuPermissionsData"
          :key="new Date() + 'role-from'"
          @handle-bth-click="onHandleBthClick"
          :data="fromData"
        />
      </template>

      <template #default="{ record, column }">
        <template v-if="column.key === 'roleValue'">
          <a-tag color="processing">
            {{ record.roleValue }}
          </a-tag>
        </template>
        <template v-if="column.key === 'roleName'">
          <a-tag :bordered="false" color="magenta">
            {{ t(`common.${record.roleName}`) }}
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
              column.key === 'createTime'
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
                  record.state == CommonStateEnum.YES ? 'normal' : 'disable'
                }`
              )
            }}
          </a-tag>
        </template>
      </template>
    </rx-table>
  </div>
</template>

<!-- 角色管理 -->
<script lang="ts" setup>
import { useI18n } from 'vue-i18n'
import SelectFromRole from '@/components/business/from/select-from-role.vue'
import FromRole from '@/components/business/from/from-role.vue'
import RxTable from '@/components/business/rx_table/rx-table.vue'
import { CommonStateEnum } from '@/config/enum/common'
import { onMounted, ref } from 'vue'
import { Role, PageQuery, AddQuery, UpdateQuery } from '@/api/role/type'
import { ClockCircleOutlined, PieChartOutlined } from '@ant-design/icons-vue'
import { tableHandler, Key } from '@/components/business/rx_table/handler'
import { ReqMenuPermissions } from '@/api/menu'
import { MenuPermissions } from '@/api/menu/type'

/**
 * 国际化状态
 */
const { t } = useI18n()

/**
 * 查询表单
 */
const formState = ref<PageQuery>({
  state: '',
  roleName: '',
  size: 10,
  page: 1,
})

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
} = tableHandler<Role, PageQuery, AddQuery, UpdateQuery>(
  'role',
  'roleId',
  formState.value
)

/**
 * 列集合
 */
const columns = ref([
  {
    title: 'id',
    dataIndex: 'roleId',
    key: 'roleId',
  },
  {
    title: 'roleName',
    dataIndex: 'roleName',
    key: 'roleName',
  },
  {
    title: 'roleValue',
    dataIndex: 'roleValue',
    key: 'roleValue',
  },
  {
    title: 'state',
    dataIndex: 'state',
    key: 'state',
  },
  {
    title: 'createTime',
    dataIndex: 'createTime',
    key: 'createTime',
    width: 220,
  },
  {
    title: 'updateTime',
    dataIndex: 'updateTime',
    key: 'updateTime',
    width: 220,
  },
  {
    title: 'option',
    dataIndex: 'option',
    key: 'option',
    fixed: 'right',
    width: 150,
  },
])

/**
 * 编辑，提交确认点击事件
 * @param data 数据
 * @param mode true 编辑  false 新增
 * @param close 关闭弹窗方法
 */
const onAlter = (mode: boolean, data: Role & Key, close: Function) => {
  if (!mode) {
    let query: AddQuery = {
      roleName: data.roleName,
      roleValue: data.roleValue,
      permissions: data.permissions ?? [],
    }
    addHandler(query, close)
    return
  }
  let query: UpdateQuery = {
    roleId: data.roleId,
    roleName: data.roleName,
    roleValue: data.roleValue,
    state: data.state,
    permissions: data.permissions ?? [],
  }
  updateHandler(query, close)
}

/**
 * 分页事件
 * @param page 第几页
 * @param pageSize 查询几个
 */
const onPage = (page: number, pageSize: number) => {
  formState.value.page = page
  formState.value.size = pageSize
  pageHandler()
}

/**
 * 点击搜索
 */
const onSearch = () => {
  formState.value.page = 1
  searchClick()
}

/**
 * 清空搜索条件
 */
const onClear = () => {
  formState.value.state = ''
  formState.value.page = 1
  searchClick()
}

/**
 * 所有的权限信息
 */
const menuPermissionsData = ref<MenuPermissions[]>([])

/**
 * 刷新、初始化事件
 */
const resetHandler = () => {
  ReqMenuPermissions().then((res) => {
    if (res.data.code == CommonStateEnum.OK) {
      menuPermissionsData.value = res.data.data
    }
  })
  onClear()
}



/**
 * 页面加载
 */
onMounted(() => {
  resetHandler()
})
</script>

<style lang="scss" scoped>
.role_box {
  position: relative;
  width: 100%;
  padding: 0 5px;
}
</style>
