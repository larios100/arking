"use server";
import { revalidatePath } from "next/cache";
import { redirect } from "next/navigation";
import { z } from "zod";
import { routes } from "./routes";
import { fetchCreatePrototype } from "./data-prototypes";
import { fetchCreateFile } from "./data-file";

const FormSchema = z.object({
  id: z.string(),
  name: z
    .string({
      invalid_type_error: "Formato inválido.",
      required_error: "Nombre es requerido.",
    })
    .trim()
    .min(1, { message: "Nombre es requerido" }),
  description: z
    .string({
      invalid_type_error: "Formato inválido.",
      required_error: "Descripción es requerido.",
    })
    .trim()
    .min(1, { message: "Descripción es requerido" }),
  fileId: z.string(),
});

const CreatePrototype = FormSchema.omit({ id: true, fileId: true });
//const UpdateInvoice = FormSchema.omit({ date: true, id: true });

// This is temporary
export type State = {
  errors?: {
    name?: string[];
    description?: string[];
    fileId?: string[];
  };
  message?: string | null;
};

export async function createPrototype(prevState: State, formData: FormData) {
  // Validate form fields using Zod
  const validatedFields = CreatePrototype.safeParse({
    name: formData.get("name"),
    description: formData.get("description"),
  });
  console.log(formData.get("file"));

  // If form validation fails, return errors early. Otherwise, continue.
  if (!validatedFields.success) {
    console.log("validatedFields", validatedFields.error.flatten().fieldErrors);
    return {
      errors: validatedFields.error.flatten().fieldErrors,
      message: "Campos inválidos. No se pudo crear el modelo.",
    };
  }

  // Prepare data for insertion into the database
  const { name, description } = validatedFields.data;
  let fileId = null;
  // Insert data into the database
  try {
    if (formData.get("file") != null) {
      const file = formData.get("file") as File;
      if (file.size > 0) {
        fileId = await fetchCreateFile(file);
      }
    }
    console.log("fileId", fileId);
    await fetchCreatePrototype({
      name,
      description,
      fileId,
      fileName: "",
      id: 0,
    });
  } catch (error: any) {
    return {
      message: "Error: " + error.message,
    };
  }

  // Revalidate the cache for the invoices page and redirect the user.
  revalidatePath(routes.prototypes);
  redirect(routes.prototypes);
}
