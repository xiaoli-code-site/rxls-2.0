import { ReqGet, Result } from "@/api/request"
import { Category } from "./type"

/**
 * 获取所哟逇分类类别
 * @returns 分类列表
 */
export const ReqAllCategory = ()=>{
    return ReqGet<Result<Category[]>>("/category/auth/all")
}