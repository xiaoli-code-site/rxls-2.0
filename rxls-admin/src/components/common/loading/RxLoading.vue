<template>
  <div class="rx_loading_box" v-if="props.loading">
    <a-spin
      size="large"
      :tip="props.title"
      :spinning="props.loading"
      :delay="500"
    ></a-spin>
  </div>
</template>

<!-- 加载中 -->
<script lang="ts" setup>
const props = defineProps({
  /**
   * 标题
   */
  title: {
    type: String,
    default: 'Loading...',
  },
  /**
   * 加载状态
   */
  loading: {
    type: Boolean,
    default: false,
  },
})
</script>

<style lang="scss" scoped>
.rx_loading_box {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 999;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
