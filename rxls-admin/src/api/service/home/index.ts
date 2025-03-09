import { ReqGet,  Result } from "@/api/request";
import { HomeStatistics } from "./type";
/**
 * 统计订单，商品，用户信息
 */
export const ReqStatisticsHome = () => {
  return ReqGet<Result<HomeStatistics>>("/home/auth/statistics");
};
