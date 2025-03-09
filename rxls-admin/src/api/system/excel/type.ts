export interface ExportExcel {
  /**
   * 文件名
   */
  fileName: string

  /**
   * 导出的字段
   */
  fieldList: string[]

  /**
   * 指定导出的数据id列表
   */
  idList?: number[]

  /**
   * 导出条件参数
   */
  pageQuery?:
    | {
        page: number
        size: number
      }
    | ''

  /**
   * 查询条件参数
   */
  query?: any
}
