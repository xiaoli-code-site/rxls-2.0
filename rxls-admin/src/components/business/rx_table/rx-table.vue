<template>
  <div class="rx_table_box">
    <table-toolbar
      :total="props.totalData"
      :isDiable="isSelected"
      :methods="props.methods"
      :bth-list="props.toolBth"
      :columns="dataValue"
      :content-height="parseInt(contentHeight)"
      @on-refresh="emit('onRefresh')"
      @on-delete="handlerDeleteAll"
      @on-excel="onExcel"
      @on-add="onAdd"
      @on-bth="onBth"
      @on-column="onColumn"
      @on-height="(v) => (contentHeight = `${v}vh`)"
    />
    <a-table
      :scroll="{
        x: 'max-content',
        y: contentHeight,
        scrollToFirstRowOnChange: true,
      }"
      :columns="tableColumn"
      :data-source="props.dataSource"
      :row-selection="
        props.isRowSelection
          ? { selectedRowKeys: tickList, onChange: onSelectChange }
          : null
      "
      :pagination="pagination"
      @expand="getInnerData"
    >
      <template #bodyCell="{ column, record }">
        <slot :record="record" :column="column"></slot>

        <!-- 操作选项 -->

        <template v-if="column.key === 'option'">
          <slot :item-data="record" name="option">
            <table-operation
              :is-diable="isSelected"
              :item-data="record"
              @on-bth="onOption"
              :bth-data="props.customBth"
            />
          </slot>
        </template>
      </template>

      <template v-if="isNesting" #expandedRowRender="{ record }">
        <slot name="nesting" :data="record"></slot>
      </template>
    </a-table>

    <rx-loading :loading="props.loading" />

    <a-modal
      :centered="true"
      :destroyOnClose="true"
      v-model:open="openModal"
      :title="alterMode ? t(`common.edit`) : t(`common.add`)"
      :confirm-loading="confirmLoading"
      @cancel="fromData = null"
      :footer="null"
    >
      <slot name="alter" @handleBthClick="onAlter" :fromData="fromData"></slot>
    </a-modal>
  </div>
</template>

<!-- 表格操作工具栏 -->
<script lang="ts" setup>
import RxLoading from '@/components/business/loading/RxLoading.vue'
import { useI18n } from 'vue-i18n'
import TableOperation from './table-operation.vue'
import TableToolbar from './table-toolbar.vue'
import { computed, onMounted, PropType, ref, watch } from 'vue'
import { useLocalStore } from '@/store/local'
import { BthType } from '@/config/bth/index'
import { ExportExcel } from '@/api/excel/type'

const props = defineProps({
  /**
   * 表头列表
   */
  columns: {
    type: Array as PropType<any[]>,
    default: [],
  },
  /**
   * 数据列表
   */
  dataSource: {
    type: Array as PropType<any[]>,
    default: [],
  },
  /**
   * 数据总量
   */
  totalData: {
    type: Number,
    default: 0,
  },
  /**
   * 默认方法列表
   */
  methods: {
    type: Array as PropType<String[]>,
    default: ['deleteSelected', 'exportToData', 'add'],
  },
  /**
   * 加载状态
   */
  loading: {
    type: Boolean,
    default: false,
  },
  /**
   * 表名
   */
  tableName: {
    type: String,
    default: '',
  },
  /**
   * 工具类操作按钮
   */
  toolBth: {
    type: Array as PropType<BthType[]>,
  },
  /**
   * 是否开启嵌套
   */
  isNesting: {
    type: Boolean,
    default: false,
  },
  /**
   * 自定义按钮列表
   */
  customBth: {
    type: Array as PropType<BthType[]>,
  },
  /**
   * 是否开启多选
   */
  isRowSelection: {
    type: Boolean,
    default: true,
  },
  /**
   * 长度参数
   */
  y: {
    type: String,
    default: '60vh',
  },
  /**
   * 分页参数
   */
  pagination: {
    type: Number,
    default: 1,
  },
})

const emit = defineEmits<{
  /**
   * 工具栏自定义按钮点击事件
   * @param text 按钮唯一标识
   * @param confirm 是否点击确认
   */
  (e: 'onToolBth', data: { id: string; confirm?: boolean }): void
  /**
   * 表格自定义按钮点击事件
   * @param text 按钮唯一标识
   * @param data 是否点击确认
   */
  (e: 'onTableBth', data: { id: string; confirm?: boolean; data: any }): void
  /**
   * 删除事件
   * @param lit key的集合
   */
  (e: 'onDelete', lit: string[]): void
  /**
   * 分页事件
   * @param page 第几页
   * @param pageSize 查询几个
   */
  (e: 'onPage', page: number, pageSize: number): void
  /**
   * 变更确认事件
   * @param data 携带的数据
   * @param mode true  编辑   false 新增
   * @param close 关闭弹窗方法
   */
  (e: 'onAlter', mode: boolean, data: any, close: Function): void
  /**
   * 表格刷新
   */
  (e: 'onRefresh'): void

  /**
   * 导出excel
   * @param data excel设置
   */
  (e: 'onExcel', data: ExportExcel): void
  /**
   * 嵌套打开
   * @param expanded
   * @param record
   */
  (e: 'onExpand', expanded: any, record: any): void
}>()

/**
 * 新增、编辑表单值
 */
const fromData = ref<any>(null)

/**
 * 分页变化事件
 */
const handlePageChange = (page: number, pageSize: number) => {
  pagination.value.current = page
  pagination.value.pageSize = pageSize
  emit('onPage', page, pageSize)
}

/**
 * 内容的默认高度
 */
const contentHeight = ref<string>(props.y)

/**
 * 是否打开新增窗口
 */
const openModal = ref<boolean>(false)

/**
 * 新增按钮加载状态
 */
const confirmLoading = ref<boolean>(false)

/**
 * @param id 按钮唯一标识
 * @param confirm 是否点击确认
 * @param data 数据对象
 */
const onBth = (id: string, confirm?: boolean) => {
  emit('onToolBth', { id, confirm })
}

/**
 * 新增确认、编辑确认提交事件
 * @param data 元素数据
 */
const onAlter = (data?: any) => {
  if (data) {
    emit('onAlter', alterMode.value, data, () => {
      openModal.value = false
      setTimeout(() => {
        fromData.value = null
      }, 100)
    })
    return
  }
  openModal.value = false
  setTimeout(() => {
    fromData.value = null
  }, 100)
}

/**
 * 工具栏新增按钮点击事件
 */
const onAdd = () => {
  alterMode.value = false
  openModal.value = true
}

/**
 * 嵌套打开
 */
const getInnerData = (expanded: any, record: any) => {
  emit('onExpand', expanded, record)
}

/**
 * 变更模式,true 编辑  false 新增
 */
const alterMode = ref<boolean>(false)

/**
 * 分页属性
 */
const pagination = ref({
  // 分页配置
  pageSize: 10, // 每页显示的条数
  showSizeChanger: true, // 是否可以改变每页显示的条数
  pageSizeOptions: ['5', '10', '20', '40'], // 可选的每页显示条数
  showQuickJumper: true, // 是否可以快速跳转到指定页
  showTotal: (total: number) => `共 ${total} 条`, // 显示总条数和当前数据范围
  current: 1, // 当前页数
  total: props.totalData, // 总条数
  onChange: handlePageChange, // 页码改变时的回调函数
})

watch(
  () => props.dataSource,
  (newValue, _old) => {
    if (newValue) {
      tickList.value = []
      isSelected.value = false
    }
  },
  {
    deep: true,
  }
)

watch(
  () => props.pagination,
  (newValue, _old) => {
    pagination.value.current = newValue
  },
  {
    immediate: true,
  }
)

watch(
  () => props.totalData,
  (newValue, _olValue) => {
    if (newValue != null) {
      pagination.value.total = newValue
    }
  }
)

/**
 * 是否进入选中模式
 */
const isSelected = ref(false)

/**
 * 国际化对象
 */
const { t } = useI18n()

/**
 * @param id 按钮唯一标识
 * @param confirm 是否点击确认
 * @param data 数据对象
 */
const onOption = (id: string, confirm?: boolean, data?: any) => {
  if (id == 'rx-delete' && confirm) {
    handlerDelete(data)
    return
  }

  if (id == 'rx-edit') {
    openModal.value = true
    alterMode.value = true
    fromData.value = data
    return
  }

  emit('onTableBth', { id, confirm, data })
}

/**
 * 导出excel事件
 * @param data: 导出参数
 * @param exportSet 是否自定义导出
 */
const onExcel = (data: ExportExcel, exportSet: boolean) => {
  if (!data) {
    //表示表单不会提交
    return false
  }
  if (!exportSet) {
    data.idList = tickList.value
  }
  emit('onExcel', data)
}

/**
 * 单个元素点击删除事件
 * @param item 删除的元素对象
 */
const handlerDelete = (item: any = null) => {
  if (item) {
    emit('onDelete', [item.key])
  }
  isSelected.value = false
}

/**
 * 表格工具栏删除选中点击确认事件
 */
const handlerDeleteAll = (confirm?: boolean) => {
  if (confirm) {
    emit('onDelete', [...tickList.value])
    tickList.value = []
    isSelected.value = false
  }
}

/**
 * 表格勾选框点击事件
 */
const onSelectChange = (selectedRowKeys: string[] | number[]) => {
  tickList.value = selectedRowKeys
  isSelected.value = selectedRowKeys && selectedRowKeys.length > 0
}

/**
 * 国际化状态处理
 */
const localHandler = (): any[] => {
  dataValue.value = []
  const list = [...props.columns]
  list.forEach((s) => {
    let temp = { ...s }
    if (
      s.title == 'state' ||
      s.title == 'createTime' ||
      s.title == 'updateTime' ||
      s.title == 'option' ||
      s.title == 'id'
    ) {
      temp.title = t(`common.${s.title}`)
    } else {
      temp.title = t(`${props.tableName}.${s.title}`)
    }

    dataValue.value.push(temp)
  })

  return dataValue.value
}

/**
 * 表格列数据列表
 */
const dataValue = ref<any[]>([])

/**
 * 不展示的表格列数据列表
 */
const displayDataValue = ref<any[]>([])

/*列
 * 监听不展示的行
 */
const tableColumn = computed(() => {
  return dataValue.value.filter(
    (s) => !displayDataValue.value.some((item) => item.key == s.key)
  )
})

/**
 * 勾选中的列表
 */
const tickList = ref<any[]>([])

/**
 * 监听国际化状态
 */
watch(
  () => useLocalStore().status,
  (_newValue, _oldValue) => {
    localHandler()
  }
)

/**
 * 监听展示的表格列选择
 */
const onColumn = (data: any[]) => {
  displayDataValue.value = data
}

/**
 * 页面加载
 */
onMounted(() => {
  localHandler()
  onAlter //避免打包报错未使用
})
</script>

<style lang="scss" scoped>
.rx_table_box {
  width: 100%;
  overflow: hidden;
}
</style>
