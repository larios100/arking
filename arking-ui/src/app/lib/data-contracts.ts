import { BasicPagination } from "../models/basic-pagination";
import { ContractItem } from "../models/contract-item";
import {
  fetchGetWithInterceptor,
  fetchPostJsonWithInterceptor,
  fetchPutJsonWithInterceptor,
} from "./fetch-client";

process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";
export async function fetchContracts(
  page: number = 1,
  limit: number = 10,
  name: string | null = null
): Promise<BasicPagination<ContractItem>> {
  var response = await fetchGetWithInterceptor(
    `contract?PageIndex=${page}&PageSize=${limit}&Name=${name}`
  );
  return response.json();
}
export async function fetchContractById(id: number): Promise<ContractItem> {
  var response = await fetchGetWithInterceptor(`contract/${id}`);
  return response.json();
}

export async function fetchCreateContract(contract: ContractItem) {
  var response = await fetchPostJsonWithInterceptor(`contract`, contract);
}

export async function fetchUpdateContract(id: number, contract: ContractItem) {
  var response = await fetchPutJsonWithInterceptor(`contract/${id}`, contract);
}
