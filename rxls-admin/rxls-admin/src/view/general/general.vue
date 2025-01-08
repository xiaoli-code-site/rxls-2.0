<template>
  <div class="general_config_box">
    <select-from-config
      v-model:values="formState"
      @on-submit="onSearch"
      @on-clear="onClear"
    />

    <rx-table
      table-name="config"
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
        <from-config
          :key="new Date() + 'config-from'"
          @handle-bth-click="onHandleBthClick"
          :data="fromData"
        />
      </template>

      <template #default="{ record, column }">
        <template v-if="column.key === 'description'">
          <div class="description">
            {{ record.description }}
          </div>
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
          <a-switch
            :checked="record.state == CommonStateEnum.YES"
            @click="stateUpdate(record.id)"
          >
            <template #checkedChildren><check-outlined /></template>
            <template #unCheckedChildren><close-outlined /></template>
          </a-switch>
        </template>
      </template>
    </rx-table>
  </div>
</template>

<!-- 通用配置 -->
<script lang="ts" setup>
import { CheckOutlined, CloseOutlined } from '@ant-design/icons-vue'
import { useI18n } from 'vue-i18n'
import SelectFromConfig from '@/components/system/from/select-from-config.vue'
import FromConfig from '@/components/system/from/from-config.vue'
import RxTable from '@/components/system/rx_table/rx-table.vue'
import { CommonStateEnum } from '@/config/enum/common'
import { onMounted, ref } from 'vue'
import {
  SysConfig,
  PageQuery,
  AddQuery,
  UpdateQuery,
} from '@/api/system/config/type'
import { ClockCircleOutlined, PieChartOutlined } from '@ant-design/icons-vue'
import { tableHandler, Key } from '@/components/system/rx_table/handler'
import { ReqUpdateConfigState } from '@/api/system/config'
import { message } from 'ant-design-vue'

/**
 * 国际化状态
 */
const { t } = useI18n()

/**
 * 查询表单
 */
const formState = ref<PageQuery>({
  description: '',
  size: 10,
  page: 1,
  state: '',
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
} = tableHandler<SysConfig, PageQuery, AddQuery, UpdateQuery>(
  'config',
  'id',
  formState.value
)

/**
 * 列集合
 */
const columns = ref([
  {
    title: 'id',
    dataIndex: 'id',
    key: 'id',
  },
  {
    title: 'key',
    dataIndex: 'sysKey',
    key: 'sysKey',
  },
  {
    title: 'value',
    dataIndex: 'sysValue',
    key: 'sysValue',
  },
  {
    title: 'description',
    dataIndex: 'description',
    key: 'description',
    width: 400,
  },
  {
    title: 'state',
    dataIndex: 'state',
    key: 'state',
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
const onAlter = (mode: boolean, data: SysConfig & Key, close: Function) => {
  if (!mode) {
    let query: AddQuery = {
      sysKey: data.sysKey,
      sysValue: data.sysValue,
      description: data.description,
    }
    addHandler(query, close)
    return
  }
  let query: UpdateQuery = {
    id: data.id,
    sysKey: data.sysKey,
    sysValue: data.sysValue,
    description: data.description,
    state: data.state,
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
  formState.value.description = ''
  formState.value.state = ''
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
 * 状态变化
 * @param 配置id
 */
const stateUpdate = (id: number) => {
  loading.value = true
  ReqUpdateConfigState(id)
    .then((res) => {
      if (res.data.code == CommonStateEnum.OK) {
        let index = dataLists.value.findIndex((s) => s.id == id)
        if (index != -1) {
          dataLists.value[index].state =
            dataLists.value[index].state == CommonStateEnum.YES
              ? CommonStateEnum.NO
              : CommonStateEnum.YES
          if (dataLists.value[index].state) {
            message.success(t('common.normal'))
          } else {
            message.warning(t('common.close'))
          }
        }
      }
    })
    .finally(() => {
      setTimeout(() => {
        loading.value = false
      }, 200)
    })
}

/**
 * 页面加载
 */
onMounted(() => {
  pageHandler()
})
</script>

<style lang="scss" scoped>
.general_config_box {
  position: relative;
  width: 100%;
  padding: 0 5px;

  .description {
    max-width: 400px;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    text-overflow: ellipsis;
    overflow: hidden;
  }
}
</style>
