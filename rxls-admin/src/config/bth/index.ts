import { VueNode } from "ant-design-vue/es/_util/type";

/**
 * 按钮类型
 */
export interface BthType {
  /**
   * 唯一标识id
   */
  id: string;
  /**
   * 按钮类型
   */
  type?: "primary" | "ghost" | "dashed" | "link" | "text" | "default" | string;
  /**
   * 按钮文本
   */
  text: string;
  /**
   * 变更按钮文本
   */
  uText?: string;
  /**
   * 危险按钮样式
   */
  danger?: boolean;
  /**
   * 按钮大小，当与传递的props.size同时有值时，使用该值
   */
  size?: "large" | "middle" | "small";
  /**
   * 是否禁用
   */
  disabled?: boolean;
  /**
   * 是否禁用
   */
  ghost?: boolean;
  /**
   * 按钮样式
   */
  shape?: "default" | "circle" | "round" | string;
  /**
   * 按钮背景颜色
   */
  backColor?: string;
  /**
   * 是否需要点击提示对话框
   */
  modal?: boolean;
  /**
   * 提示对话框标题（modal为true时使用）
   */
  modalTitle?: string;
  /**
   * 提示对话框图标，传递ant icon的组件 （modal为true时使用）
   */
  modalIcon?: VueNode | (() => VueNode);
  /**
   * 左侧取消按钮文本 （modal为true时使用）
   */
  modalCancelText?: string;
  /**
   * 是否显示按钮
   */
  fn?: Function;
  /**
   * 右侧确认按钮文本 （modal为true时使用）
   */
  modalConfirmText?: string;
}

/**
 * 按钮创建工具函数
 * @param config 按钮配置
 * @returns 按钮对象
 */
const createButton = (config: BthType): BthType => {
  return {
    id: config.id,
    type: config.type,
    text: config.text,
    uText: config.uText,
    danger: config.danger,
    size: config.size,
    disabled: config.disabled,
    ghost: config.ghost,
    shape: config.shape,
    backColor: config.backColor,
    modal: config.modal,
    modalTitle: config.modalTitle,
    modalIcon: config.modalIcon,
    modalCancelText: config.modalCancelText,
    fn: config.fn,
    modalConfirmText: config.modalConfirmText,
  };
};

export { createButton };
