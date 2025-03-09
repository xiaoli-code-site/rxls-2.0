import { ReqGet, Result } from "@/api/request";
import { Order } from "../order/type";

/**
 * 售后退款
 * @param 售后订单id
 */
export const ReqAftersalesRefund = (id: number) => {
  return ReqGet<Result<void>>("/afterSales/auth/refund", { id });
};

/**
 * 售后通过
 * @param 售后订单id
 */
export const ReqAftersalesAdopt = (id: number) => {
  return ReqGet<Result<void>>("/afterSales/auth/adopt", { id });
};

/**
 * 售后拒绝
 * @param 售后订单id
 */
export const ReqAftersalesRefuse = (id: number) => {
  return ReqGet<Result<void>>("/afterSales/auth/refuse", { id });
};


/**
 * 售后换货
 * @param 售后订单id
 */
export const ReqAftersalesExchangeGoods = (id: number) => {
    return ReqGet<Result<void>>("/afterSales/auth/exchangeGoods", { id });
  };


  /**
 * 售后订单详情信息
 * @param 售后订单id
 */
export const ReqAftersalesOrderDetail = (id: number) => {
  return ReqGet<Result<Order>>("/afterSales/auth/order", { id });
};
