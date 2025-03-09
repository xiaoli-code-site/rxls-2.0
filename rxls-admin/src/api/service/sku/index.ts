import { ReqPost, Result } from "@/api/request";
import { AddQuery, UpdateQuery } from "./type";

/**
 * 新增SKU
 * @param data 参数对象
 */
export const ReqAddSkus= (data: AddQuery) => {
  return ReqPost<Result<void>>("/skus/auth/add", data, false);
};

/**
 * 批量删除SKU
 * @param data 参数对象
 */
export const ReqDelSkus = (data: number[]) => {
  return ReqPost<Result<void>>("/skus/auth/del/id", data, false);
};

/**
 * 变更SKU信息
 * @param data 参数对象
 */
export const ReqUpdateSkus = (data: UpdateQuery) => {
  return ReqPost<Result<void>>("/skus/auth/update", data, false);
};
