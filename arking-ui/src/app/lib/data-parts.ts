import { BasicPagination } from "../models/basic-pagination";
import { PartCreate } from "../models/part-create";
import { PartDetail } from "../models/part-detail";
import { PartItem } from "../models/part-item";
import { cookies } from "next/headers";
import { fetchPostJsonWithInterceptor } from "./fetch-client";
process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";
export async function fetchParts(
  contractId: number,
  page: number = 1,
  limit: number = 10
): Promise<BasicPagination<PartItem>> {
  const cookieStore = cookies();
  const token = cookieStore.get("token");
  console.log("token", token?.value);
  var response = await fetch(
    `https://localhost:7258/api/part?PageIndex=${page}&PageSize=${limit}&ContractId=${contractId}`,
    {
      method: "GET",
      headers: {
        Authorization: "Bearer " + token?.value,
      },
    }
  );
  console.log("response", response);
  return response.json();
}
export async function fetchCreatePart(contractId: number, part: PartCreate) {
  var response = await fetchPostJsonWithInterceptor(`part/${contractId}`, part);
}

export async function fetchPart(id: number): Promise<PartDetail> {
  const cookieStore = cookies();
  const token = cookieStore.get("token");
  console.log("token", token?.value);
  var response = await fetch(`https://localhost:7258/api/part/${id}`, {
    method: "GET",
    headers: {
      Authorization: "Bearer " + token?.value,
    },
  });
  console.log("response", response);
  return response.json();
}
