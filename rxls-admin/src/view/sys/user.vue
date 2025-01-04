<template>
  <div class="user_box">
    <select-from-user
      :role-list="roleList"
      v-model:values="formState"
      @on-submit="onSearch"
      @on-clear="onClear"
    />

    <rx-table
      table-name="user"
      :data-source="dataLists"
      :columns="columns"
      y="60vh"
      :loading="loading"
      :total-data="dataTotal"
      :pagination="formState.page"
      @on-excel="(query) => excelHandler(query)"
      @on-refresh="resetHandler"
      @on-delete="(list) => delHandler(list)"
      @on-page="onPage"
      @on-alter="onAlter"
    >
      <template #alter="{ onHandleBthClick, fromData }">
        <from-user
          :role-list="roleList"
          :key="new Date() + 'user-from'"
          @handle-bth-click="onHandleBthClick"
          :data="fromData"
        />
      </template>

      <template #default="{ record, column }">
        <template v-if="column.key === 'sex'">
          <a-tag :color="record.sex === CommonStateEnum.YES ? 'pink' : 'blue'">
            {{
              t(`user.${record.sex == CommonStateEnum.YES ? 'boy' : 'girl'}`)
            }}
          </a-tag>
        </template>

        <template v-if="column.key === 'role'">
          <div
            v-for="(item, index) in record.roleName.split(',')"
            :key="index"
            style="padding: 2px 0"
          >
            <a-tag :color="item == 'member' ? 'red' : 'blue'">
              {{ t(`common.${item}`) }}
            </a-tag>
          </div>
        </template>

        <template v-if="column.key === 'avatar'">
          <a-image
            style="border-radius: 5px"
            :width="50"
            :height="50"
            :src="record.avatar"
          />
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

<!-- 用户管理 -->
<script lang="ts" setup>
import { useI18n } from 'vue-i18n'
import SelectFromUser from '@/components/business/from/select-from-user.vue'
import FromUser from '@/components/business/from/from-user.vue'
import RxTable from '@/components/business/rx_table/rx-table.vue'
import { CommonStateEnum } from '@/config/enum/common'
import { onMounted, ref } from 'vue'
import { User, PageQuery, AddQuery, UpdateQuery } from '@/api/user/type'
import { ClockCircleOutlined, PieChartOutlined } from '@ant-design/icons-vue'
import { ReqAllRole } from '@/api/role'
import { Role } from '@/api/role/type'
import { tableHandler, Key } from '@/components/business/rx_table/handler'

/**
 * 国际化状态
 */
const { t } = useI18n()

/**
 * 查询表单
 */
const formState = ref<PageQuery>({
  nickname: '',
  phone: '',
  sex: '',
  state: '',
  page: 1,
  size: 10,
  role: '',
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
} = tableHandler<User, PageQuery, AddQuery, UpdateQuery>(
  'user',
  'userId',
  formState.value
)

/**
 * 列集合
 */
const columns = ref([
  {
    title: 'id',
    dataIndex: 'userId',
    key: 'userId',
    width: 100,
  },
  {
    title: 'role',
    dataIndex: 'role',
    key: 'role',
    width: 120,
  },
  {
    title: 'avatar',
    dataIndex: 'avatar',
    key: 'avatar',
    width: 100,
  },
  {
    title: 'sex',
    dataIndex: 'Sex',
    key: 'sex',
    width: 100,
  },
  {
    title: 'nickname',
    dataIndex: 'nickname',
    key: 'nickname',
    width: 120,
  },
  {
    title: 'phone',
    dataIndex: 'phone',
    key: 'phone',
    width: 130,
  },

  {
    title: 'state',
    dataIndex: 'state',
    key: 'state',
    width: 100,
  },
  {
    title: 'createTime',
    dataIndex: 'createTime',
    key: 'createTime',
    width: 200,
  },
  {
    title: 'updateTime',
    dataIndex: 'updateTime',
    key: 'updateTime',
    width: 200,
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
const onAlter = (mode: boolean, data: User & Key, close: Function) => {
  if (!mode) {
    let query: AddQuery = {
      phone: data.phone,
      nickname: data.nickname,
      sex: data.sex,
      avatar: data.avatar,
      roleName: data.roleName,
    }
    addHandler(query, close)
    return
  }
  let query: UpdateQuery = {
    userId: data.userId,
    phone: data.phone,
    avatar: data.avatar,
    nickname: data.nickname,
    state: data.state,
    sex: data.sex,
    roleName: data.roleName,
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
  formState.value.nickname = ''
  formState.value.phone = ''
  formState.value.role = ''
  formState.value.sex = ''
  formState.value.state = ''
  formState.value.page = 1
  searchClick()
}

/**
 * 角色列表
 */
const roleList = ref<Role[]>([])

/**
 * 刷新、初始化事件
 */
const resetHandler = () => {
  ReqAllRole().then((res) => {
    if (res.data.code == CommonStateEnum.OK && res.data.data) {
      roleList.value = res.data.data
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
.user_box {
  position: relative;
  width: 100%;
  padding: 0 5px;
}
</style>
