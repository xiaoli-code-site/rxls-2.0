<template>
  <a-card style="z-index: 9999; box-shadow: rgba(17, 17, 26, 0.1) 0px 1px 0px">
    <Toolbar
      :editor="editorRef"
      :defaultConfig="toolbarConfig"
      mode="default"
      :style="{ borderBottom: '1px solid #ccc', width: props.width }"
    />
    <Editor
      :style="{ height: props.height, width: props.width }"
      :defaultConfig="editorConfig"
      v-model="valueHtml"
      mode="default"
      @onCreated="handleCreated"
      @onChange="handleChange"
      @onDestroyed="handleDestroyed"
      @onFocus="handleFocus"
      @onBlur="handleBlur"
      @customAlert="customAlert"
      @customPaste="customPaste"
    />
  </a-card>
</template>

<script setup>
import { ReqUploadImg } from "@/api/system/upload";
import "@wangeditor/editor/dist/css/style.css";
import { onBeforeUnmount, ref, shallowRef, onMounted, watch } from "vue";
import { Editor, Toolbar } from "@wangeditor/editor-for-vue";
import { CommonStateEnum } from "@/config/enum/common";
const props = defineProps({
  data: {
    type: String,
    default: "",
  },
  height: {
    type: String,
    default: "400px",
  },
  width: {
    type: String,
    default: "100%",
  },
});

const emit = defineEmits(["change"]);

// 编辑器实例，必须用 shallowRef，重要！
const editorRef = shallowRef();

// 内容 HTML
const valueHtml = ref("<p>hello</p>");

watch(
  () => props.data,
  (newValue, _oldValue) => {
    valueHtml.value = props.data;
  },
  {
    immediate: true,
  }
);

let toolbarConfig = {};

const editorConfig = { MENU_CONF: {} };
editorConfig.MENU_CONF["uploadImage"] = {
  /**
   * 自定义上传
   */
  customUpload(file, insertFn) {
    let formData = new FormData();
    formData.append("image_file", file);
    ReqUploadImg(formData).then((res) => {
      if (res.data.code == CommonStateEnum.OK) {
        insertFn(res.data.data[0], "", res.data.data[0]);
      }
    });
  },
};

// 编辑器回调函数
const handleCreated = (editor) => {
  editorRef.value = editor; // 记录 editor 实例，重要！
};
const handleChange = (editor) => {
  // console.log("change:", editor.getHtml());
  emit("change", editor.getHtml());
};
const handleDestroyed = (editor) => {
  // console.log("destroyed", editor);
};
const handleFocus = (editor) => {
  // console.log("focus", editor);
};
const handleBlur = (editor) => {
  // console.log("blur", editor);
};
const customAlert = (info, type) => {
  alert(`【自定义提示】${type} - ${info}`);
};
const customPaste = (editor, event, callback) => {
  // console.log("ClipboardEvent 粘贴事件对象", event);

  // 自定义插入内容
  // editor.insertText("xxx");

  // 返回值（注意，vue 事件的返回值，不能用 return）
  callback(true); // 返回 false ，阻止默认粘贴行为
  // callback(true) // 返回 true ，继续默认的粘贴行为
};

// 组件销毁时，也及时销毁编辑器，重要！
onBeforeUnmount(() => {
  const editor = editorRef.value;
  if (editor == null) return;
  editor.destroy();
});
</script>
