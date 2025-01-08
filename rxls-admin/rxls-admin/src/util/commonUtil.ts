/**
 * 属性值复制
 * @param newObj 赋值对象
 * @param oldoObj 目标对象
 */
const objectPropertyCopying = <T extends Object>(
  newObj: any,
  oldoObj: T
): T => {
  let newKeys = Object.keys(newObj)
  let oldKeys = Object.keys(oldoObj)
  let obj: any = {}
  newKeys.forEach((s: any) => {
    let index = oldKeys.findIndex((o) => o == s)
    if (index != -1) {
      obj[s] = newObj[s]
    }
  })

  return { ...obj }
}

export {  objectPropertyCopying }
