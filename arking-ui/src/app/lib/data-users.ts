import { BasicPagination } from "../models/basic-pagination";
import { UserItem } from "../models/user-item";
import {
  fetchGetWithInterceptor,
  fetchPostJsonWithInterceptor,
} from "./fetch-client";

export async function fetchUsers(
  name: string = "",
  page: number = 1,
  limit: number = 10
): Promise<BasicPagination<UserItem>> {
  var response = await fetchGetWithInterceptor(
    `user?PageIndex=${page}&PageSize=${limit}&Name=${name}`
  );
  return response.json();
}
export async function fetchCreateUser(user: UserItem) {
  var response = await fetchPostJsonWithInterceptor(`user`, user);
}
