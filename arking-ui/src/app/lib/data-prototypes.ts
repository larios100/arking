import { BasicPagination } from "../models/basic-pagination";
import { PrototypeItem } from "../models/prototype-item";
import {
  fetchGetWithInterceptor,
  fetchPostJsonWithInterceptor,
} from "./fetch-client";
export async function fetchPrototype(
  name: string = "",
  page: number = 1,
  limit: number = 10
): Promise<BasicPagination<PrototypeItem>> {
  var response = await fetchGetWithInterceptor(
    `prototype?PageIndex=${page}&PageSize=${limit}&Name=${name}`
  );
  return response.json();
}
export async function fetchCreatePrototype(prototype: PrototypeItem) {
  var response = await fetchPostJsonWithInterceptor(`prototype`, prototype);
}
