import { ReqGet, Result } from "@/api/request"
import { Brand } from "./type"

/**
 * 获取所哟逇分类类别
 * @returns 分类列表
 */
export const ReqAllBrand = ()=>{
    return ReqGet<Result<Brand[]>>("/brand/auth/all")
}