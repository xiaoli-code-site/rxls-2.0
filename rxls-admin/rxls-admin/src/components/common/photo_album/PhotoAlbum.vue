<template>
  <div>
    <a-image
      :preview="{ visible: false }"
      :width="props.width"
      :height="props.height"
      :src="props.imgList[0]"
      @click="visible = true"
    />

    <div style="display: none">
      <a-image-preview-group
        :preview="{ visible, onVisibleChange: (vis: boolean) => (visible = vis) }"
      >
        <a-image
          v-for="(item, index) in props.imgList"
          :key="index"
          :src="item"
        />
      </a-image-preview-group>
    </div>
  </div>
</template>

<!-- 相册组件 -->
<script lang="ts" setup>
import { PropType, ref } from 'vue'

const props = defineProps({
  /**
   * 图片列表
   */
  imgList: {
    type: Array as PropType<string[]>,
    default: [],
  },
  /**
   * 宽
   */
  width: {
    type: Number,
    default: 100,
  },
  /**
   * 高
   */
  height: {
    type: Number,
    default: 100,
  },
})

/**
 * 是否显示
 */
const visible = ref(false)
</script>

<style lang="scss" scoped></style>
