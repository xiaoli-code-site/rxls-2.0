<template>
  <a-upload
    :maxCount="1"
    v-model:file-list="fileList"
    name="file"
    :action="BASE_URL + '/upload/auth/files'"
    :headers="headers"
    @change="handleChange"
    @remove="emit('onDelete', [])"
  >
    <a-button>
      <upload-outlined></upload-outlined>
      {{ t("common.upload") }}
    </a-button>
  </a-upload>
</template>
<script lang="ts" setup>
import { BASE_URL } from "@/config/constant";
import { useI18n } from "vue-i18n";
import { TOKEN } from "@/config/constant";
import { PropType, ref, watch } from "vue";
import { message } from "ant-design-vue";
import { UploadOutlined } from "@ant-design/icons-vue";
import type { UploadChangeParam } from "ant-design-vue";
import { CommonStateEnum } from "@/config/enum/common";

const emit = defineEmits<{
  /**
   * 上传触发
   * @param 图片路径
   */
  (e: "onUpload", url: string): void;

  /**
   * 删除时触发
   * @param daat 图片路径
   */
  (e: "onDelete", data: string | string[]): void;
}>();

const props = defineProps({
  /**
   * 文件列表
   * @param url 上传的文件
   */
  list: {
    type: Array as PropType<string[]>,
    default: [],
  },
});

const { t } = useI18n();

const fileList = ref<any[]>([]);

/**
 * 监听文件
 */
watch(
  () => props.list,
  (newValue, _oldValue) => {
    if (newValue.length > 0 && newValue[0]) {
      fileList.value = [
        {
          name: props.list[0],
          status: "done",
          url: props.list[0],
        },
      ];
    } else {
      fileList.value = [];
    }
  },
  { immediate: true }
);

/**
 * 监听上传
 */
const handleChange = (info: UploadChangeParam) => {
  if (info.file.status !== "uploading") {
    console.log(info.file, info.fileList);
  }
  if (info.file.status === "done") {
    console.log(info);
    if (info.file.response.code != CommonStateEnum.OK) {
      message.error(`${info.file.name}-${info.file.response.msg}.`);
    } else {
      message.success(`${info.file.name} ${t("message.success")}`);
    }
    emit("onUpload", info.file.response.data[0]);
  } else if (info.file.status === "error") {
    message.error(`${info.file.name} ${t("message.fail")}.`);
  }
};

const headers = {
  authorization: window.localStorage.getItem(TOKEN),
};
</script>
