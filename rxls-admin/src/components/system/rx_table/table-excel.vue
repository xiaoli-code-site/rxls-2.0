<template>
  <a-modal
    :footer="null"
    :open="props.open"
    :title="t('common.exportToData')"
    @cancel="emit('onClick', formState.exportSet)"
  >
    <a-form ref="formRef" :model="formState">
      <a-form-item
        has-feedback
        :label="t('common.filename')"
        name="filename"
        :rules="[
          { required: true, validator: checkFilename, trigger: 'change' },
        ]"
      >
        <a-input v-model:value="formState.fileName" />
      </a-form-item>

      <SortDescendingOutlined />{{ t("common.field") }}:
      <a-form-item-rest>
        <a-space wrap :size="10" style="padding: 6px 0 10px; margin-bottom: 0">
          <a-checkbox :checked="checkboxAll" @change="onCheckboxAll">
            {{ t(`common.all`) }}
          </a-checkbox>

          <a-checkbox
            v-for="(item, index) in props.columns"
            :key="index"
            :checked="isCheckbox(item)"
            @change="onCheckbox(item)"
          >
            {{ item.title }}
          </a-checkbox>
        </a-space>
      </a-form-item-rest>

      <a-form-item :label="t('common.language')" name="range">
        <a-radio-group v-model:value="formState.exportSet" button-style="solid">
          <div
            style="
              display: flex;
              justify-content: center;
              flex-direction: column;
            "
          >
            <a-space>
              <a-radio :value="false">{{
                t("common.theCurrentlySelectedRow")
              }}</a-radio>
            </a-space>

            <a-space style="padding: 10px 0 4px">
              <a-radio :value="true">{{ t("common.customize") }}</a-radio>
              <a-select
                :disabled="!formState.exportSet"
                ref="select"
                v-model:value="pageQuery.size"
              >
                <a-select-option
                  :value="item.value"
                  v-for="(item, index) in pageSizeList"
                  :key="index"
                  >{{ `${item.label}/${t("common.pages")}` }}</a-select-option
                >
              </a-select>
            </a-space>
            <a-space>
              <ContainerOutlined />
              {{ `共${number(props.total)}条数据,${pageNumber}页` }}
            </a-space>
          </div>
        </a-radio-group>
      </a-form-item>

      <a-form-item :wrapper-col="{ span: 15, offset: 1 }">
        <a-button @click="emit('onClick', formState.exportSet)">
          {{ t(`bth.cancel`) }}</a-button
        >
        <a-button
          type="primary"
          html-type="submit"
          style="margin-left: 10px"
          @click.prevent="onSubmit"
        >
          <DownloadOutlined />{{ t(`bth.confirm`) }}</a-button
        >
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<!-- 表格操作工具栏 -->
<script lang="ts" setup>
import { useLocalStore } from "@/store/local";
import { fileName } from "../../../util/check";
import format from "../../../util/format";
import { computed, reactive, ref } from "vue";
import type { PropType, UnwrapRef } from "vue";
import { useI18n } from "vue-i18n";
import { Rule } from "ant-design-vue/es/form";
import { message } from "ant-design-vue";
import {
  SortDescendingOutlined,
  DownloadOutlined,
  ContainerOutlined,
} from "@ant-design/icons-vue";
import type { SelectProps } from "ant-design-vue";
import { watch } from "vue";

const emit = defineEmits<{
  /**
   * 取消，确认按钮点击事件
   * @param data 填写的数据，无则点击取消
   * @param exportSet 是否只导出选中
   */
  (e: "onClick", exportSet: boolean, data?: any): void;
}>();

const props = defineProps({
  /**
   * 是否显示
   */
  open: {
    type: Boolean,
    default: false,
  },
  /**
   * 表名
   */
  tableName: {
    type: String,
    default: "",
  },
  /**
   * 表格列
   */
  columns: {
    type: Array as PropType<any[]>,
    default: false,
  },
  /**
   * 数据总数量
   */
  total: {
    type: Number,
    default: 0,
  },
  /**
   * 查询参数
   */
  selectQuery: {
    type: Object as PropType<any>,
    default: {},
  },
});

const { number } = format();

/**
 * 语言状态
 */
const { t } = useI18n();

const localStore = useLocalStore();

/**
 * 每页数量列表
 */
const pageSizeList = ref<SelectProps["options"]>([
  {
    value: 5000,
    label: "5000",
  },
  {
    value: 10000,
    label: "1w",
  },
  {
    value: 50000,
    label: "5w",
  },
]);

/**
 * 分页页码参数
 */
const pageQuery = ref({
  /**
   * 导出几页
   */
  page: 1,
  /**
   * 每页最大信息量
   */
  size: 5000,
});

/**
 * 监听页码
 */
watch(
  () => pageQuery.value.page,
  (newValue, _oldValue) => {
    if (!newValue) {
      pageQuery.value.page = 1;
    }
  }
);

/**
 * 页数
 */
const pageNumber = computed(() => {
  return Math.ceil(props.total / pageQuery.value.size);
});

/**
 * 表单实例
 */
const formRef = ref();

/**
 * 表单类型
 */
interface FormState {
  /**
   * 文件名
   */
  fileName: string;
  /**
   * 字段列表
   */
  fieldList: any[];
  /**
   * true 中文  false 英文
   */
  lang: boolean;
  /**
   * 是否自定义导出
   */
  exportSet: boolean;
}

/**
 * 表单参数
 */
const formState: UnwrapRef<FormState> = reactive({
  fileName: new Date().getTime() + "",
  fieldList: [...props.columns],
  lang: true,
  exportSet: false,
});

/**
 * 文件名校验规则
 */
const checkFilename = async (_rule: Rule, value: string) => {
  if (value === "") {
    return Promise.reject(t(`common.theFileNameIsNotEmpty`));
  } else if (!fileName(value)) {
    return Promise.reject(
      t(
        `common.theFileNameConsistsOfEnglishDigitsChineseCharactersAndUnderscores`
      )
    );
  } else {
    return Promise.resolve();
  }
};

/**
 * 按钮点击事件
 */
const onSubmit = () => {
  formRef.value
    .validate()
    .then(() => {
      let arr = formState.fieldList.map((s) => s.key);
      formState.lang = localStore.status == "zh";
      const { fieldList, ...otherData } = formState;
      let data = {};

      if (!formState.exportSet) {
        data = { ...otherData, fieldList: arr };
      } else {
        pageQuery.value.page = pageNumber.value;
        data = { ...otherData, pageQuery: pageQuery.value, fieldList: arr };
      }

      emit("onClick", formState.exportSet, data);
    })
    .catch((error: any) => {
      console.log("error", error);
    });
};

/**
 * 是否全部选中
 */
const checkboxAll = computed(() => {
  return formState.fieldList.length == props.columns.length;
});

/**
 * 是否选中
 */
const isCheckbox = (item: any) => {
  return formState.fieldList.findIndex((s) => s.key == item.key) != -1;
};

/**
 * 全部选中多选框点击事件
 */
const onCheckboxAll = (e: any) => {
  if (e.target.checked) {
    formState.fieldList = [...props.columns];
    return;
  }

  formState.fieldList = [];
  formState.fieldList.push(props.columns[0]);
};

/**
 * 单个多选框点击事件
 */
const onCheckbox = (item: any) => {
  let index = formState.fieldList.findIndex((s) => s.key == item.key);
  if (index != -1) {
    if (formState.fieldList.length < 2) {
      message.warning(t(`common.theFieldContainsAtLeastOne`));
    } else {
      formState.fieldList.splice(index, 1);
    }

    return;
  }
  formState.fieldList.push(item);
};
</script>

<style lang="scss" scoped>
.table_excel_box {
  padding: 10px 0;
}
</style>
