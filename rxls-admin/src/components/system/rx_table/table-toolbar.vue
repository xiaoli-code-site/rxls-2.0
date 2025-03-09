<template>
  <div class="rx_no_select table_toolbar_box">
    <table-operation
      :is-diable="!props.isDiable"
      size="small"
      :bth-data="bthList"
      @onBth="onBth"
    />

    <a-space :size="15" class="right">
      <!-- 刷新表格按钮 -->
      <a-tooltip>
        <template #title>
          {{ t(`bth.refresh`) }}
        </template>
        <ReloadOutlined class="icons" @click="emit('onRefresh')" />
      </a-tooltip>

      <!-- 表格列选择 -->
      <a-dropdown
        @open-change="onDropdownState"
        v-model:open="visibleColumn"
        :destroy-popup-on-hide="false"
        :trigger="['click']"
      >
        <a-tooltip placement="top">
          <template #title>
            {{ t(`common.theColumnsThatAreDisplayed`) }}
          </template>
          <span @click.prevent>
            <SettingOutlined class="icons" />
          </span>
        </a-tooltip>
        <template #overlay>
          <a-menu @click="handleMenuClick">
            <a-menu-item>
              <a-checkbox :checked="isCheckboxAll" @change="onCheckAllChange">
                {{ t(`bth.all`) }}
              </a-checkbox>
            </a-menu-item>

            <a-menu-item
              v-for="(item, index) in props.columns"
              :key="index"
              @click.prevent="onSelected(item)"
            >
              <a-checkbox :checked="isCheckbox(item.key)" @change.stop="">
                {{ item.title }}</a-checkbox
              >
            </a-menu-item>

            <a-menu-item key="close">
              <a-button type="link"> {{ t(`bth.confirm`) }}</a-button>
            </a-menu-item>
          </a-menu>
        </template>
      </a-dropdown>

      <!-- 内容高度调整 -->
      <a-tooltip placement="bottom">
        <template #title>
          {{ t(`common.contentHeightDisplayAdjustment`) }}
        </template>
        <span @click.prevent>
          <a-space>
            <ColumnHeightOutlined class="icons" />
            <a-slider
              @change="onChange"
              style="width: 100px"
              v-model:value="contentHeight"
              :tip-formatter="(v:string)=>`${v}%`"
            />
          </a-space>
        </span>
      </a-tooltip>
    </a-space>

    <table-excel
      :total="props.total"
      table-name="user"
      :columns="props.columns"
      :open="openExcel"
      @on-click="onEXcel"
      :key="new Date() + 'excel'"
    />
  </div>
</template>

<!-- 表格操作工具栏 -->
<script lang="ts" setup>
import TableExcel from "./table-excel.vue";
import { useI18n } from "vue-i18n";
import { SettingOutlined, ReloadOutlined } from "@ant-design/icons-vue";
import { BthType } from "@/config/bth";
import TableOperation from "./table-operation.vue";
import { computed, PropType, ref, watch } from "vue";
import { type MenuProps } from "ant-design-vue";
import { ExportExcel } from "@/api/system/excel/type";

const emit = defineEmits<{
  /**
   * 点击删除选中
   * @param confirm true确认 false 取消
   * @param data 传递值
   */
  (e: "onDelete", confirm?: boolean): void;

  /**
   * 导出excel
   */
  (e: "onExcel", data: any, exportSet: boolean): void;

  /**
   * 新增按钮确认方法
   */
  (e: "onAdd"): void;
  /**
   * 新增按钮确认方法
   * @param text 按钮描述
   * @param confirm true确认 false 取消
   * @param data 数据
   */
  (e: "onBth", text: string, confirm?: boolean, data?: any): void;

  /**
   * 表格展示的列的数据列选中事件
   * @param data 不展示的表格列
   */
  (e: "onColumn", data: any[]): void;

  /**
   * 表格刷新
   */
  (e: "onRefresh"): void;
  /**
   * 表格高度发生变化
   */
  (e: "onHeight", data: number): void;
}>();

const props = defineProps({
  /**
   * 是否禁用
   */
  isDiable: {
    type: Boolean,
    default: false,
  },
  /**
   * 数据总量
   */
  total: {
    type: Number,
    default: 0,
  },
  /**
   * 表名
   */
  tableName: {
    type: String,
    default: "",
  },
  /**
   * 默认方法列表
   */
  methods: {
    type: Array as PropType<String[]>,
    default: ["deleteSelected", "exportToData", "add"],
  },
  /**
   * 按钮列表
   */
  bthList: {
    type: Array as PropType<BthType[]>,
    default: [],
  },
  /**
   * 字段列表
   */
  columns: {
    type: Array as PropType<any[]>,
    default: [],
  },
  /**
   * 内容高度
   */
  contentHeight: {
    type: Number,
    default: 60,
  },
});

/**
 * 国际化状态
 */
const { t } = useI18n();

/**
 * 默认操作方法列表
 */
const dataValue = ref<BthType[]>([
  {
    id: "rx-deleteSelected",
    type: "primary",
    text: "deleteSelected",
    danger: true,
    modal: true,
  },
  {
    id: "rx-exportToData",
    type: "primary",
    text: "exportToData",
  },
  {
    id: "rx-add",
    type: "primary",
    text: "add",
    disabled: false,
    backColor: "rgb(33, 140, 247)",
  },
]);

/**
 * 监听传递的自定义按钮列表,默认方法列表
 */
const bthList = computed(() => {
  let temp = dataValue.value.filter((s) =>
    props.methods.some((item) => item === s.text)
  );
  temp.push(...props.bthList);
  return temp;
});

/**
 * 是否打开导出excel设置
 */
const openExcel = ref(false);

/**
 * 监听excel，点击确认 或取消事件
 */
const onEXcel = (exportSet: boolean, data: ExportExcel) => {
  openExcel.value = false;
  emit("onExcel", data, exportSet);
};

/**
 * 按钮点击
 * @param text 描述
 * @param confirm true确认 false 取消
 */
const onBth = (id: string, confirm?: boolean) => {
  if (id == "rx-deleteSelected") {
    emit("onDelete", confirm);
  } else if (id == "rx-exportToData") {
    openExcel.value = true;
  } else if (id == "rx-add") {
    emit("onAdd");
  } else {
    emit("onBth", id, confirm);
  }
};

/**
 * 表格列选择下拉列表显示状态
 */
const visibleColumn = ref(false);

/**
 * 内容显示长度
 */
const contentHeight = ref(props.contentHeight);

/**
 * 内容显示高度变化
 */
const onChange = (value: number) => {
  if (value < 30) {
    contentHeight.value = 30;
  } else {
    emit("onHeight", value);
  }
};

/**
 * 监听菜单确认关闭事件
 */
const handleMenuClick: MenuProps["onClick"] = (e) => {
  if (e.key === "close") {
    let data = props.columns.filter(
      (s) => !checkboxState.value.some((item) => item.key == s.key)
    );
    emit("onColumn", data);
    visibleColumn.value = false;
  }
};

/**
 * 下拉框隐藏时触发，点击关闭按钮不触发
 * @param opne 显示状态
 */
const onDropdownState = (open: any) => {
  if (open) {
    return;
  }
  checkboxState.value = [...props.columns];
};

/**
 * 是否全部选中
 */
const checkAll = ref(true);

/**
 * 临时存储选中状态
 */
const checkboxState = ref<any[]>([...props.columns]);

watch(
  () => props.columns,
  (newValue, _oldValue) => {
    if (newValue) {
      checkboxState.value = [...newValue];
    }
  }
);

/**
 * 监听多选框单个点击事件
 * @param key key标识
 */
const onSelected = (item: any) => {
  let index = checkboxState.value.findIndex((s) => s.key == item.key);
  if (index != -1) {
    checkboxState.value.splice(index, 1);
  } else {
    checkboxState.value.push(item);
  }
};

/**
 * 是否选中
 * @param key key标识
 */
const isCheckbox = (key: string) => {
  return checkboxState.value.findIndex((s) => s.key == key) != -1;
};

/**
 * 是否选中
 * @param key key标识
 */
const isCheckboxAll = computed(() => {
  return (checkAll.value = checkboxState.value.length == props.columns.length);
});

/**
 * 监听全部选中点击事件
 * @param e 选中元素
 */
const onCheckAllChange = (e: any) => {
  if (e.target.checked) {
    checkboxState.value = [...props.columns];
    return;
  }
  checkboxState.value = [props.columns[props.columns.length - 1]];
};
</script>

<style lang="scss" scoped>
.table_toolbar_box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-right: 10px;

  .right {
    margin-left: auto;
  }

  .checkbox {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: end;
  }

  .icons {
    font-size: 16px;
    cursor: pointer;
  }
}
</style>
