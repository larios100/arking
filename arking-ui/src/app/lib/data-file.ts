import { randomUUID } from "crypto";
import { fetchPostFormDataWithInterceptor } from "./fetch-client";
export async function fetchCreateFile(file: File): Promise<string> {
  const id = randomUUID();
  var formData = new FormData();
  formData.append("file", file, file.name);
  var response = await fetchPostFormDataWithInterceptor(`file/${id}`, formData);
  return id;
}
