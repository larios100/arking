"use server";
import { revalidatePath } from "next/cache";
import { redirect } from "next/navigation";
import { z } from "zod";
import { routes } from "./routes";
import { fetchCreateUser } from "./data-users";

const FormSchema = z.object({
  id: z.string(),
  firstName: z
    .string({
      invalid_type_error: "Formato inválido.",
      required_error: "Nombre es requerido.",
    })
    .trim()
    .min(1, { message: "Nombre es requerido" }),
  lastName: z
    .string({
      invalid_type_error: "Formato inválido.",
      required_error: "Apellido es requerido.",
    })
    .trim()
    .min(1, { message: "Apellido es requerido" }),
  userName: z
    .string({
      invalid_type_error: "Formato inválido.",
      required_error: "Usuario es requerido.",
    })
    .email({
      message: "Usuario debe ser un correo electrónico válido.",
    }),
  role: z.string({
    invalid_type_error: "Formato inválido.",
    required_error: "Role es requerido.",
  }),
});

const CreateUser = FormSchema.omit({ id: true });
//const UpdateInvoice = FormSchema.omit({ date: true, id: true });

// This is temporary
export type State = {
  errors?: {
    firstName?: string[];
    lastName?: string[];
    userName?: string[];
  };
  message?: string | null;
};

export async function createUser(prevState: State, formData: FormData) {
  // Validate form fields using Zod
  const validatedFields = CreateUser.safeParse({
    userName: formData.get("userName"),
    firstName: formData.get("firstName"),
    lastName: formData.get("lastName"),
    role: formData.get("role"),
  });

  // If form validation fails, return errors early. Otherwise, continue.
  if (!validatedFields.success) {
    return {
      errors: validatedFields.error.flatten().fieldErrors,
      message: "Campos inválidos. No se pudo crear el usuario.",
    };
  }

  // Prepare data for insertion into the database
  const { firstName, lastName, userName, role } = validatedFields.data;

  // Insert data into the database
  try {
    await fetchCreateUser({
      firstName,
      lastName,
      userName,
      role,
      id: "",
      active: true,
    });
  } catch (error: any) {
    return {
      message: "Error: " + error.message,
    };
  }

  // Revalidate the cache for the invoices page and redirect the user.
  revalidatePath(routes.users);
  redirect(routes.users);
}
