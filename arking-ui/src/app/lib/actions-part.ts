"use server";
import { revalidatePath } from "next/cache";
import { redirect } from "next/navigation";
import { z } from "zod";
import { routes } from "./routes";
import { fetchCreatePart, fetchUpatePart } from "./data-parts";
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
  prototypeId: z.number(),
  status: z.enum(["Pending", "Canceled", "Open", "Done", "Paused"]),
  contractId: z.number(),
});

const CreatePart = FormSchema;
//const UpdateInvoice = FormSchema.omit({ date: true, id: true });

// This is temporary
export type State = {
  errors?: {
    name?: string[];
    description?: string[];
    prototypeId?: string[];
    status?: string[];
  };
  message?: string | null;
};

export async function createPart(prevState: State, formData: FormData) {
  // Validate form fields using Zod
  const validatedFields = CreatePart.safeParse({
    name: formData.get("name"),
    description: formData.get("description"),
    status: formData.get("status"),
    prototypeId: parseInt(formData.get("prototypeId") as string),
    contractId: parseInt(formData.get("contractId") as string),
    id: formData.get("id"),
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
  const { name, description, prototypeId, status, contractId, id } =
    validatedFields.data;
  // Insert data into the database
  try {
    if (parseInt(id) > 0) {
      await fetchUpatePart(parseInt(id), {
        name,
        description,
        prototypeId,
        status,
        id: 0,
      });
    } else {
      await fetchCreatePart(contractId, {
        name,
        description,
        prototypeId,
        status,
        id: 0,
      });
    }
  } catch (error: any) {
    return {
      message: "Error: " + error.message,
    };
  }

  // Revalidate the cache for the invoices page and redirect the user.
  revalidatePath(routes.contracts + "/" + contractId);
  redirect(routes.contracts + "/" + contractId);
}
