import { App } from "vue";

/**
 * 指定指令名称
 */
type Types = "permission" | "display";

/**
 * 全局实例
 */
let app: App | null = null;

/**
 * 自定义元素显示，隐藏控制指令注册器
 * @param app 注册名称
 *
 */
const setupDisplay = (types: Types) => {
  if (!app) {
    return;
  }
  app.directive(types, {
    //el:元素  binding:指令绑定的属性
    mounted(el, binding) {
      const { value } = binding;
      //如果回调函数存在，执行回调函数，结束处理
      if (value) {
        if (typeof value === "function" && !value()) {
          el.parentNode && el.parentNode.removeChild(el);
        }
        return;
      }
      //执行默认逻辑，移除元素
      el.parentNode && el.parentNode.removeChild(el);
    },
  });
};

/**
 * 自定义权限控制指令注册器
 * @param app 全局实例
 * @param app 注册名称
 *
 */
const setupPermission = (types: Types) => {
  if (!app) {
    return;
  }
  app.directive(types, {
    //el:元素  binding:指令绑定的属性
    mounted(el, binding) {
      //value 指令的值
      const { value } = binding;
      console.log("el", el);
      console.log("value", value);

      if (types) {
        el.parentNode && el.parentNode.removeChild(el);
        el.disabled = true;
      }
    },
  });
};

/**
 * 初始化全局实例对象
 * @param apps 实例对象
 */
const init = (apps: App) => {
  if (!app) {
    app = apps;
  }
};

/**
 * 初始化全局实例对象
 * @param apps 实例对象
 */
const initSetup = (app: App) => {
  init(app);
  setupPermission("permission");
  setupDisplay("display");
};

export { initSetup };
