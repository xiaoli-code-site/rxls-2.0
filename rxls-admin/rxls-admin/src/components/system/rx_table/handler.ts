import { PageData } from '@/api/type'
import { ReqGet, ReqPost, Result } from '@/api/request'
import { ref } from 'vue'
import { message } from 'ant-design-vue'
import { useI18n } from 'vue-i18n'
import { CommonStateEnum } from '@/config/enum/common'
import { ReqUploadexcel } from '@/api/system/excel'
import { ExportExcel } from '@/api/system/excel/type'

/**
 * 删除
 */
const PATH_DEL = '/auth/del/id'
/**
 * 新增
 */
const PATH_ADD = '/auth/add'
/**
 * 分页
 */
const PATH_PAGE = '/auth/page'
/**
 * 更新
 */
const PATH_UPDATE = '/auth/update'

/**
 * 数据类型
 */
export interface Key {
  key: string
}

/**
 *
 * @param tableName 表名,请求前缀
 * @param keyId 主键id
 * @param pageQuery 查询参数
 */
const tableHandler = <
  T,
  P extends Object | null,
  A extends Object | null,
  U extends Object | null
>(
  tableName: string,
  keyId: keyof T,
  pageQuery: P
) => {
  /**
   * 加载状态
   */
  const loading = ref(false)

  /**
   * 国际化状态
   */
  const { t } = useI18n()

  /**
   * 分页查询参数
   */
  const selectQuery = ref(pageQuery)

  /**
   * 数据总数
   */
  const dataTotal = ref<number>(0)

  /**
   * 数据列表
   */
  const dataLists = ref<any[]>([])

  /**
   * 分页查询
   */
  const pageHandler = () => {
    loading.value = true
    return ReqGet<Result<PageData<T>>>(
      `${tableName}${PATH_PAGE}`,
      selectQuery.value
    )
      .then((res) => {
        dataLists.value = []
        dataTotal.value = res.data.data.total ?? 0
        if (res.data.data.total) {
          res.data.data.dataList.forEach((s: T) => {
            dataLists.value.push({
              key: s[keyId],
              ...s,
            })
          })
        }
      })
      .finally(() => {
        setTimeout(() => {
          loading.value = false
        }, 200)
      })
  }

  /**
   * 删除
   * @param list 需要删除的数据
   * @param fn 处理函数
   * @returns  Promisse
   */
  const delHandler = (list: any[], fn?: Function) => {
    loading.value = true
    //待删除的id列表
    let idList
    //新的数据列表
    let temp: any = []
    if (list.length == 0) {
      idList = dataLists.value.map((s) => s[keyId]) as unknown as number[]
    } else {
      idList = dataLists.value
        .filter((s) => list.some((item) => item === s.key))
        .map((s) => s[keyId]) as unknown as number[]
      temp = dataLists.value.filter((s) => !list.some((item) => item === s.key))
    }
    return ReqPost<Result<number>>(`/${tableName}${PATH_DEL}`, idList)
      .then((res) => {
        if (res.data.code == CommonStateEnum.OK) {
          fn ? fn() : delEndHandler(res.data.data)
          dataLists.value = temp
        }
      })
      .finally(() => {
        loading.value = false
      })
  }

  /**
   * 更新
   * @param query 请求参数
   * @param close 关闭函数
   * @param fn 处理函数
   * @returns  Promisse
   */
  const updateHandler = (query: U, close: Function, fn?: Function) => {
    if (!query) {
      return
    }
    let modal = message.loading(t('message.committing'), 0)
    loading.value = true
    return ReqPost<Result<void>>(`/${tableName}${PATH_UPDATE}`, query, true)
      .then((res) => {
        if (res.data.code == CommonStateEnum.OK) {
          close()
          fn ? fn() : endHandler(t('message.editSuccess'))
        }
      })
      .finally(() => {
        setTimeout(modal, 10)
        loading.value = false
      })
  }

  /**
   * 添加
   * @param data 参数
   * @param close 关闭函数
   * @param fn 处理函数
   * @returns  Promisse
   */
  const addHandler = (query: A, close: Function, fn?: Function) => {
    if (!query) {
      return
    }
    let modal = message.loading(t('message.committing'), 0)
    loading.value = true
    return ReqPost<Result<void>>(`/${tableName}${PATH_ADD}`, query)
      .then((res) => {
        if (res.data.code == CommonStateEnum.OK) {
          close()
          fn ? fn() : endHandler(t('message.addSuccess'))
        }
      })
      .finally(() => {
        setTimeout(modal, 10)
        loading.value = false
      })
  }

  /**
   * 导出
   * @param {type} data 导出对象
   */
  const excelHandler = (data: ExportExcel) => {
    let { page, size, ...temp } = selectQuery.value
    let query: ExportExcel = {
      fileName: data.fileName,
      fieldList: data.fieldList,
      pageQuery: data.pageQuery,
      idList: data.idList,
      query: temp,
    }
    const hide = message.loading(t('message.exportingIrogress'), 0)
    return ReqUploadexcel(query, tableName)
      .then((res) => {
        // 创建一个临时URL
        const url = window.URL.createObjectURL(new Blob([res.data as Blob]))
        const a = document.createElement('a')
        document.body.appendChild(a)
        a.href = url
        a.download = `${query.fileName}.xlsx` // 指定下载的文件名
        a.click()
        window.URL.revokeObjectURL(url) // 清理临时URL
        document.body.removeChild(a)
        message.success(t('message.exportComplete'))
      })
      .finally(() => {
        setTimeout(hide, 10)
      })
  }

  /**
   * 统一请求结束的回调函数
   */
  const endHandler = (msg: string) => {
    message.success(msg)
    pageHandler()
  }

  /**
   * 删除结束后的处理函数
   * @param  num  成功删除个数
   * @returns  Promisse
   */
  const delEndHandler = (num: number) => {
    let msg = `${t('message.quantity')}:${num}`
    if (num) {
      message.success(`${t('message.deleteSuccess')},${msg}`)
    } else {
      message.error(`${t('common.fail')},${msg}`)
    }
    pageHandler()
  }

  /**
   * 点击查询事件
   */
  const searchClick = () => {
    pageHandler()
  }

  return {
    dataLists,
    dataTotal,
    addHandler,
    updateHandler,
    loading,
    delHandler,
    excelHandler,
    pageHandler,
    searchClick,
  }
}

export { tableHandler }
