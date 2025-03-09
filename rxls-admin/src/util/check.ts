/**
 * 检验是不是一个金额（最多包含两位小数）
 * @param value /
 * @returns
 */
const amount = (value: string) => {
  const regex = /^\d+(\.\d{1,2})?$/;
  return regex.test(value);
};

/**
 * 检验是不是一个数字（只允许非负整数）
 * @param value
 * @returns
 */
const numbers = (value: string) => {
  const regex = /^(\d+)$/;
  return regex.test(value);
};

/**
 * 检验是不是身份证号
 * @param value
 * @returns
 */
const idCard = (value: string) => {
  const regex = /^(\d{17})([0-9Xx])$/;
  return regex.test(value);
};

/**
 * 检验是不是手机号
 * @param value
 * @returns
 */
const phone = (value: string) => {
  const regex = /^1[3-9]\d{9}$/;
  return regex.test(value);
};

/**
 * 名称检查，是否只包含 英文、中文、数字或下划线,长度最小为2,容许中间出现一个空格,最大为20个字符
 * @param value
 * @returns
 */
const names = (value: string) => {
  const regex =
    /^[a-zA-Z0-9_\u4e00-\u9fa5][a-zA-Z0-9_\u4e00-\u9fa5]*([ ]?[a-zA-Z0-9_\u4e00-\u9fa5]+)*[a-zA-Z0-9_\u4e00-\u9fa5]$/;
  return regex.test(value) && value.length >= 2 && value.length <= 20;
};

/**
 * 密码检查，是否包含 英文、数字或下划线,长度最小为6,最大为20个字符
 * @param value
 * @returns
 */
const password = (value: string) => {
  const regex = /^[a-zA-Z0-9_.]+$/;
  return regex.test(value) && value.length >= 6 && value.length <= 20;
};

/**
 * 账号检查，是否只包含 英文、数字,长度最小为5,最大为20个字符
 * @param value
 * @returns
 */
const username = (value: string) => {
  const regex = /^[a-zA-Z0-9]+$/;
  return regex.test(value) && value.length >= 5 && value.length < 20;
};

/**
 * 检验是不是合法文件名
 * @param value
 * @returns
 */
const fileName = (value: string) => {
  const regex = /^[a-zA-Z0-9_（）\u4e00-\u9fa5()]+$/;
  return regex.test(value);
};

export { amount, idCard, numbers, phone, names, password, fileName, username };
