/**
 * 区域编码处理
 * @param code 区域编码
 * @returns [省份编码,市区编码,区域编码]
 */
const regionCodeHandler = (code: string) => {
  //获取省份编码
  let province = code.substring(0, 2);
  //获取市区编码
  let city = code.substring(0, 4);
  return [province, city, code];
};

/**
 * 根据区域编码获取 省、市、区
 * @param code 区域编码
 * @param option 选项列表
 * @returns [省份编码,市区编码,区域编码]
 */
const regionNameHandler = (code: string[], option: any[]) => {
  let temp: any[] = [];
  option.forEach((item) => {
    if (item.code === code[0]) {
      temp.push(item.name);
      item.children.forEach((i: any) => {
        if (i.code === code[1]) {
          temp.push(i.name);
          i.children.forEach((t: any) => {
            if (t.code === code[2]) {
              temp.push(t.name);
            }
          });
        }
      });
    }
  });
  return temp;
};

export { regionCodeHandler, regionNameHandler };
