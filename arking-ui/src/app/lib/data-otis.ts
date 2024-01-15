import { BasicPagination } from "../models/basic-pagination";
import { OtiDetail } from "../models/oti-detail";
import { OtiItem } from "../models/oti-item";
import { fetchGetWithInterceptor } from "./fetch-client";

process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";
export async function fetchOtis(
  page: number = 1,
  limit: number = 10
): Promise<BasicPagination<OtiItem>> {
  var response = await fetchGetWithInterceptor(
    `oti?PageIndex=${page}&PageSize=${limit}`
  );
  return response.json();
}
export async function fetchOti(id: string): Promise<OtiDetail> {
  var response = await fetchGetWithInterceptor(`oti/${id}`);
  return response.json();
}
