import { BasicPagination } from "../models/basic-pagination";
import { PartCreate } from "../models/part-create";
import { PartDetail } from "../models/part-detail";
import { PartItem } from "../models/part-item";
import { cookies } from "next/headers";
import {
  fetchGetWithInterceptor,
  fetchPostJsonWithInterceptor,
  fetchPutJsonWithInterceptor,
} from "./fetch-client";
import { PartSimple } from "../models/part-simple";
process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";
export async function fetchParts(
  contractId: number,
  page: number = 1,
  limit: number = 10
): Promise<BasicPagination<PartItem>> {
  var response = await fetchGetWithInterceptor(
    `part?PageIndex=${page}&PageSize=${limit}&ContractId=${contractId}`
  );
  return response.json();
}
export async function fetchCreatePart(contractId: number, part: PartCreate) {
  var response = await fetchPostJsonWithInterceptor(`part/${contractId}`, part);
}
export async function fetchUpatePart(id: number, part: PartCreate) {
  var response = await fetchPutJsonWithInterceptor(`part/${id}`, part);
}

export async function fetchPart(id: number): Promise<PartDetail> {
  var response = await fetchGetWithInterceptor(`part/${id}`);
  return response.json();
}

export async function fetchPartSimple(id: number): Promise<PartSimple> {
  var response = await fetchGetWithInterceptor(`part/${id}/simple`);
  return response.json();
}
