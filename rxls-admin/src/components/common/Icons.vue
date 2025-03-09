<template>
  <div
    class="icons_box"
    @click="emit('onClick')"
    :style="{
      '--size': props.size + 'px',
      margin: '0 ' + props.distance + 'px',
      color: props.color,
      flexDirection: props.flexDirection,
    }"
  >
    <slot>
      <icon>
        <template #component>
          <svg class="icon" aria-hidden="true">
            <use :xlink:href="`#${iconsType[props.icon]}`"></use>
          </svg>
        </template>
      </icon>
    </slot>

    <p
      class="msg"
      v-if="props.msg != ''"
      :style="{
        fontSize: `${props.fontSize}px`,
        paddingTop: props.flexDirection == 'row' ? '0' : '3px',
        paddingLeft: props.flexDirection == 'row' ? '5px' : '0',
      }"
    >
      {{ props.msg }}
    </p>
    <span
      class="dot"
      v-show="props.isDot"
      :style="{ '--y': props.size / 2 + 'px' }"
    ></span>
  </div>
</template>

<!-- icons 图标组件 -->
<script lang="ts" setup>
import Icon from '@ant-design/icons-vue'
import { iconsType, Typed } from '@/config/icon/iconType'
import '@/assets/icons/iconfont.js'
import { PropType } from 'vue'
const props = defineProps({
  /**
   * 图标大小、图片大小
   */
  size: {
    type: Number,
    default: 23,
  },
  /**
   * 图标路径
   */
  icon: {
    type: String as PropType<Typed | string>,
    default: '',
  },
  /**
   * 描述
   */
  msg: {
    type: String,
    default: '',
  },
  /**
   * 是否显示指示点
   */
  isDot: {
    type: Boolean,
    default: false,
  },
  /**
   * 左右距离
   */
  distance: {
    type: Number,
    default: 0,
  },
  /**
   * 颜色，会覆盖原本的颜色
   */
  color: {
    type: String,
    default: '',
  },
  /**
   * 排列模式
   */
  flexDirection: {
    type: String as PropType<'column' | 'row'>,
    default: 'column',
  },
  /**
   * 文本的字体大小,默认12
   */
  fontSize: {
    type: String || Number,
    default: '12',
  },
})

const emit = defineEmits<{
  /**
   *点击事件
   */
  (e: 'onClick'): void
}>()
</script>

<style lang="scss">
.icons_box {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0s !important;
  font-size: var(--size);
  width: fit-content;
  height: fit-content;

  .icon {
    width: var(--size);
    height: var(--size);
    fill: currentColor;
    overflow: hidden;
  }

  &:hover {
    cursor: pointer;
    opacity: 0.9;
  }

  img {
    width: var(--size);
    height: var(--size);
    box-shadow: rgba(50, 50, 105, 0.15) 0px 2px 5px 0px,
      rgba(0, 0, 0, 0.05) 0px 1px 1px 0px;
    border-radius: 4px;
  }

  .msg {
    font-size: 12px;
    margin: 0;
  }

  .dot {
    position: absolute;
    right: 50%;
    top: 0;
    width: 7px;
    height: 7px;
    border-radius: 50%;
    background: rgb(0, 136, 255);
    transform: translateX(var(--y));
  }
}
</style>
