//导出函数对象
const format = () => {
  /**
   *
   * @param item 数字
   * @returns 数字字符串
   */

  const number = (item: number): string => {
    if (item > 10000) {
      let w = (item / 10000).toFixed(0)
      let k = item % 10000
      if (k > 1000) {
        return w + '.' + (k / 1000).toFixed(0) + 'w'
      }
      return w + 'w'
    } else if (item > 1000) {
      let k = (item / 1000).toFixed(0)
      let h = item % 1000
      if (h > 100) {
        return k + '.' + (h / 1000).toFixed(0) + 'k'
      }
      return k + 'k'
    }
    return item.toString()
  }

  /**
   *
   * @param dated 时间对象
   * @returns 动态时间格式的字符串
   */
  const dynamic = (dated: Date | undefined | null) => {
    if (!dated) {
      return
    }

    // 获取当前时间
    const nowDate = new Date()

    let nowTimestamp = nowDate.getTime() // 当前时间戳

    let date = new Date(dated) //防止时间是字符串

    let timestamp = date.getTime() // 另一个时间点的时间戳

    // 计算两个日期之间的差值（以毫秒为单位）
    let diffInMilliseconds = Math.abs(nowTimestamp - timestamp)

    // 转换为天数（一天按24小时 * 60分钟 * 60秒 * 1000毫秒计算）
    //等于0则在今日以内,大于0则在未来时间,小于0则表明在之前时间,
    let diffInDays = Math.floor(diffInMilliseconds / (1000 * 60 * 60 * 24))

    //是否在今日以内，返回时间
    if (diffInDays == 0) {
      return time(date)
    }

    // 获取今日星期几
    let nowDay = nowDate.getDay()
    // 获取另一时间星期几
    let day = date.getDay()

    // 是否在7日以内,返回星期几
    if (diffInDays <= 6 && nowDay > day) {
      return days(date)
    }

    // //返回年月日
    return year(date)
  }

  /**
   *
   * @param date 日期对象
   * @returns  时:分
   */
  const time = (date: Date): string => {
    let hour = date.getHours() < 10 ? `0${date.getHours()}` : date.getHours()
    let minutes =
      date.getMinutes() < 10 ? `0${date.getMinutes()}` : date.getMinutes()
    return `${hour}:${minutes}`
  }

  /**
   *
   * @param date 日期对象
   * @returns 年-月-日
   */
  const year = (date: Date): string => {
    let year = date.getFullYear() - 2000
    let month =
      date.getMonth() + 1 < 10 ? `0${date.getMonth() + 1}` : date.getMonth() + 1
    let ri =
      date.getDate() + 1 < 10 ? `0${date.getDate() + 1}` : date.getDate() + 1
    return `${year}-${month}-${ri}`
  }

  /**
   *
   * @param date 日期对象
   * @returns 星期几
   */
  const days = (date: Date): string => {
    let str = null
    switch (date.getDay()) {
      case 1:
        str = '一'
        break
      case 2:
        str = '二'
        break
      case 3:
        str = '三'
        break
      case 4:
        str = '四'
        break
      case 5:
        str = '五'
        break
      case 6:
        str = '六'
        break
      default:
        str = '日'
    }
    return `星期${str}`
  }

  /**
   * 金額
   * @param price: 金额
   * @param text: 单位文本
   * @returns 文本
   */
  const priceHandler = (price: string | number = 0, text?: string): string => {
    if (!price || price == 0) {
      return '0.00' + (text ?? '')
    }
    let priceText = (parseInt(price + '') / 100).toFixed(2)
    return priceText + (text ?? '')
  }

  /**
   * 数字0填充
   * @param price: 金额
   * @param text: 单位文本
   * @returns 文本
   */
  const numberZero = (numbers: number): string => {
    return numbers > 10 ? numbers + '' : '0' + numbers
  }

  return {
    days,
    year,
    time,
    number,
    dynamic,
    priceHandler,
    numberZero,
  }
}

export default format()
