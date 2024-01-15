import { RedirectType, redirect } from "next/navigation";
import { useRouter } from "next/navigation";
import { cookies } from "next/headers";
process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";
export async function fetchWithInterceptor(
  url: string,
  options: RequestInit
): Promise<Response> {
  console.log("url", url);
  console.log("options", options);
  const response = await fetch(url, options);
  console.log("response", response);
  // Aquí puedes validar el código HTTP de la respuesta
  if (response.ok) {
    return response;
  }
  if (response.status === httpStatus.unauthorized) {
    return redirect("/login", RedirectType.replace);
  }
  throw new Error(`HTTP error! Status: ${response.status}`);
}

export async function fetchGetWithInterceptor(url: string): Promise<Response> {
  const cookieStore = cookies();
  const token = cookieStore.get("token")?.value;
  console.log("token", token);
  var response = await fetchWithInterceptor(
    `https://localhost:7258/api/${url}`,
    {
      method: "GET",
      headers: {
        Authorization: "Bearer " + token,
      },
    }
  );
  return response;
}
export async function fetchPostJsonWithInterceptor(
  url: string,
  body: any
): Promise<Response> {
  const cookieStore = cookies();
  const token = cookieStore.get("token")?.value;
  var response = await fetchWithInterceptor(
    `https://localhost:7258/api/${url}`,
    {
      method: "POST",
      headers: {
        Authorization: "Bearer " + token,
        "content-type": "application/json",
      },
      body: JSON.stringify(body),
    }
  );
  return response;
}
export async function fetchPutJsonWithInterceptor(
  url: string,
  body: any
): Promise<Response> {
  const cookieStore = cookies();
  const token = cookieStore.get("token")?.value;
  var response = await fetchWithInterceptor(
    `https://localhost:7258/api/${url}`,
    {
      method: "PUT",
      headers: {
        Authorization: "Bearer " + token,
        "content-type": "application/json",
      },
      body: JSON.stringify(body),
    }
  );
  return response;
}
export async function fetchPostFormDataWithInterceptor(
  url: string,
  body: FormData
): Promise<Response> {
  const cookieStore = cookies();
  const token = cookieStore.get("token")?.value;
  var response = await fetchWithInterceptor(
    `https://localhost:7258/api/${url}`,
    {
      method: "POST",
      headers: {
        Authorization: "Bearer " + token,
      },
      body: body,
    }
  );
  return response;
}

export const httpStatus = {
  ok: 200,
  created: 201,
  accepted: 202,
  noContent: 204,
  badRequest: 400,
  unauthorized: 401,
  forbidden: 403,
  notFound: 404,
  internalServerError: 500,
};
