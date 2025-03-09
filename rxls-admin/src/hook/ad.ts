/**
 * 广告展示形式
 */
const displayForm: any = {
  0: "carousel", //轮播图
  1: "nine_spaces", //九空格图
};

const position = [
  "index_carousel", //首页轮播图
  "index_nine_spaces", //首页九空格图
];

/**
 * 广告展示形式
 * @param index 形式标识
 */
const getDisplayForm = (index: number) => {
  if (index in displayForm) {
    return displayForm[index];
  }
  return "";
};

/**
 * 获取广告展示形式
 */
const getDisplayFormList = () => {
  return Object.values(displayForm);
};

/**
 * 获取广告可用展示位置
 */
const getPosition = () => {
    return position;
  };

export { getDisplayFormList, getDisplayForm,getPosition };
