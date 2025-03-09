import { ReqGet, ReqPost, Result } from "@/api/request";
import type {
  AlterAddressQuery,
  OrderStateStatistics,
  OrderStatistics,
  PageData,
  PageQuery,
  RefundQuery,
  ShipQuery,
} from "./type";

/**
 * 分页查询订单内容
 * @param data 分页参数
 */
export const ReqPageOrder = (data: PageQuery) => {
  return ReqGet<Result<PageData>>("/order/auth/page", data);
};

/**
 * 更新订单地址信息
 * @param data 更新参数
 */
export const ReqUpdateOrderAddress = (data: AlterAddressQuery) => {
  return ReqPost<Result<void>>("/order/auth/update/address", data);
};

/**
 * 订单发货
 * @param data 发货参数
 */
export const ReqShipOrder = (data: ShipQuery) => {
  return ReqPost<Result<void>>("/order/auth/ship", data);
};

/**
 * 订单退款
 * @param data 退款参数
 */
export const ReqRefundOrder = (data: RefundQuery) => {
  return ReqPost<Result<void>>("/order/auth/refund", data);
};

/**
 * 订单删除
 * @param data 订单id列表
 */
export const ReqDeleteOrder = (data: number[]) => {
  return ReqPost<Result<number>>("/order/auth/del/id", data);
};

/**
 * 统计指定天数的成交订单量
 * @param day 天数
 */
export const ReqStatisticsOrder = (day: number) => {
  return ReqGet<Result<OrderStatistics[]>>("/order/auth/statistics", { day });
};


/**
 * 统计指定天数的成交订单量
 * @param day 天数
 */
export const ReqStateStatisticsOrder = () => {
  return ReqGet<Result<OrderStateStatistics[]>>("/order/auth/state/statistics");
};


