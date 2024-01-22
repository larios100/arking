"use server";
import { getUser } from "../../../auth";
import { z } from "zod";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";
import { routes } from "./routes";

const FormSchema = z.object({
  email: z.string(),
  password: z.string(),
});
const errorMessage = "Usuario y/o contraseña inválida.";
export async function authenticate(
  prevState: string | undefined,
  formData: FormData
) {
  // Validate form fields using Zod
  const validatedFields = FormSchema.safeParse({
    email: formData.get("email"),
    password: formData.get("password"),
  });

  // If form validation fails, return errors early. Otherwise, continue.
  if (!validatedFields.success) {
    return errorMessage;
  }
  const { email, password } = validatedFields.data;

  try {
    const user = await getUser(email, password);
    if (!user) {
      console.log("user", user);
      return errorMessage;
    }
    const oneDay = 24 * 60 * 60 * 1000 * 365;
    cookies().set("token", user.token, { expires: Date.now() + oneDay });
  } catch (error) {
    console.log("error", error);
    return errorMessage;
  }
  return redirect(routes.contracts);
}

export async function singout(
  prevState: string | undefined,
  formData: FormData
) {
  cookies().delete("token");
  return redirect(routes.login);
}
