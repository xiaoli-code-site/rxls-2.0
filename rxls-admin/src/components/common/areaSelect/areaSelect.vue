<template>
  <a-cascader
    :fieldNames="{ label: 'name', value: 'code', children: 'children' }"
    :options="areaData"
    :placeholder="placeholder"
    v-model:value="selectedValues"
    @change="onChange"
  />
</template>
<script setup lang="ts">
import { regionCodeHandler, regionNameHandler } from "@/hook/address";
import { ref, watch } from "vue";
//@ts-ignore
import areaData from "./areadata";

const { defaultValue, placeholder } = defineProps({
  placeholder: {
    type: String,
    default: "请选择省市区",
  },
  /**
   * 区域编码
   */
  defaultValue: {
    type: String,
    default: "",
  },
});

const emit = defineEmits<{
  /**
   * 区域变动完成
   * @param names 省、市、县名称
   * @param  code 省、市、县编码
   */
  (e: "onChange", data: { names: string[]; code: string[] }): void;
}>();

const selectedValues = ref<any[]>([]);

watch(
  () => defaultValue,
  (newValue, _oldValue) => {
    if (!newValue) {
      return;
    }
    selectedValues.value = regionCodeHandler(defaultValue);
  },
  {
    immediate: true,
  }
);

const onChange = (value: string[]) => {
  let temp = regionNameHandler(value, areaData);
  emit("onChange", {
    names: temp,
    code: [...value],
  });
};
</script>
<style lang="scss" scoped></style>
