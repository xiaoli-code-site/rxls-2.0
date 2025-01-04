<template>
  <div class="oplog_box">
    <select-from-oplog
      :role-list="roleList"
      v-model:values="formState"
      @on-submit="onSearch"
      @on-clear="onClear"
    />
    <rx-table
      table-name="oplog"
      :data-source="dataLists"
      :columns="columns"
      y="60vh"
      :loading="loading"
      :methods="['deleteSelected']"
      :total-data="dataTotal"
      :pagination="formState.page"
      @on-refresh="resetHandler"
      @on-delete="(list) => delHandler(list)"
      @on-page="onPage"
    >
      <template #alter="{ onHandleBthClick, fromData }">
        <from-user
          :role-list="roleList"
          :key="new Date() + 'oplog-from'"
          @handle-bth-click="onHandleBthClick"
          :data="fromData"
        />
      </template>

      <template #default="{ record, column }">
        <template v-if="column.key === 'times'">
          <a-tag :bordered="false" color="purple">{{
            record.times + 'ms'
          }}</a-tag>
        </template>

        <template v-if="column.key === 'result'">
          <a-button
            size="small"
            type="primary"
            color="green"
            @click="handlerDetail(record)"
            >{{ t(`common.detail`) }}</a-button
          >
        </template>

        <template v-if="column.key === 'model'">
          <a-tag color="red">
            {{ t(`oplog.${record.model}`) }}
          </a-tag>
        </template>

        <template v-else-if="column.key === 'category'">
          <a-tag color="orange">
            {{
              t(
                `oplog.${
                  record.category == OplogEnum.CRITICAL
                    ? 'critical'
                    : record.category == OplogEnum.IMPORTANT
                    ? 'important'
                    : 'general'
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
          <a-tag
            :color="record.state === CommonStateEnum.YES ? 'green' : 'volcano'"
          >
            {{
              t(
                `oplog.${
                  record.state == CommonStateEnum.YES ? 'success' : 'error'
                }`
              )
            }}
          </a-tag>
        </template>
      </template>
    </rx-table>

    <a-modal
      :centered="true"
      :destroyOnClose="true"
      v-model:open="open"
      :title="t('common.detail')+'：'+detailContent?.description"
      :footer="null"
      width="100%"
      style="margin-top: 0"
    >
      <from-oplog :data="detailContent" />
    </a-modal>
  </div>
</template>

<!-- 日志管理 -->
<script lang="ts" setup>
import fromOplog from '@/components/business/from/from-oplog.vue'
import { useI18n } from 'vue-i18n'
import FromUser from '@/components/business/from/from-user.vue'
import RxTable from '@/components/business/rx_table/rx-table.vue'
import { CommonStateEnum } from '@/config/enum/common'
import { onMounted, ref } from 'vue'
import { Oplog, PageQuery } from '@/api/oplog/type'
import { ClockCircleOutlined } from '@ant-design/icons-vue'
import { Role } from '@/api/role/type'
import { tableHandler } from '@/components/business/rx_table/handler'
import { OplogEnum } from '@/config/enum/OplogEnum'

/**
 * 国际化状态
 */
const { t } = useI18n()

/**
 * 查询表单
 */
const formState = ref<PageQuery>({
  size: 10,
  page: 1,
  leve: '',
})

const open = ref<boolean>(false)

/**
 * 通用处理函数
 */
const { searchClick, dataLists, dataTotal, loading, delHandler, pageHandler } =
  tableHandler<Oplog, PageQuery, null, null>('oplog', 'id', formState.value)

/**
 * 列集合
 */
const columns = ref([
  {
    title: 'id',
    dataIndex: 'id',
    key: 'id',
    width: 100,
  },
  {
    title: 'nickname',
    dataIndex: 'username',
    key: 'username',
    width: 120,
  },
  {
    title: 'category',
    dataIndex: 'category',
    key: 'category',
    width: 100,
  },
  {
    title: 'ip',
    dataIndex: 'ip',
    key: 'ip',
    width: 120,
  },
  {
    title: 'times',
    dataIndex: 'ip',
    key: 'times',
    width: 50,
  },
  {
    title: 'address',
    dataIndex: 'address',
    key: 'address',
    width: 120,
  },
  {
    title: 'model',
    dataIndex: 'model',
    key: 'model',
    width: 130,
  },
  {
    title: 'description',
    dataIndex: 'description',
    key: 'description',
    width: 150,
  },
  {
    title: 'result',
    dataIndex: 'result',
    key: 'result',
    width: 100,
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
])

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
 * 角色列表
 */
const roleList = ref<Role[]>([])

/**
 * 详情内容
 */
const detailContent = ref<Oplog>()

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
  formState.value.leve = ''
  formState.value.page = 1
  searchClick()
}

/**
 * 刷新、初始化事件
 */
const resetHandler = () => {
  onClear()
}

/**
 * 打开详情
 */
const handlerDetail = (temp: Oplog) => {
  open.value = true

  detailContent.value = temp
}

/**
 * 页面加载
 */
onMounted(() => {
  resetHandler()
})
</script>

<style lang="scss" scoped>
.oplog_box {
  position: relative;
  width: 100%;
  padding: 0 5px;
}
</style>
