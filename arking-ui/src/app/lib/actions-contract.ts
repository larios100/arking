"use server";
import { revalidatePath } from "next/cache";
import { redirect } from "next/navigation";
import { z } from "zod";
import { routes } from "./routes";
import { fetchCreateContract, fetchUpdateContract } from "./data-contracts";
import { parse } from "path";

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
  status: z.enum(["Pending", "Canceled", "Open", "Done", "Paused"]),
  budget: z.number({
    invalid_type_error: "Formato inválido.",
    required_error: "Presupuesto es requerido.",
  }),
  sku: z
    .string({
      invalid_type_error: "Formato inválido.",
      required_error: "SKU es requerido.",
    })
    .trim()
    .min(1, { message: "SKU es requerido" }),
});

const CreateContract = FormSchema;
//const UpdateInvoice = FormSchema.omit({ date: true, id: true });

// This is temporary
export type State = {
  errors?: {
    name?: string[];
    description?: string[];
    budget?: string[];
    sku?: string[];
    status?: string[];
  };
  message?: string | null;
};

export async function createContract(prevState: State, formData: FormData) {
  // Validate form fields using Zod
  const validatedFields = CreateContract.safeParse({
    name: formData.get("name"),
    description: formData.get("description"),
    budget:
      formData.get("budget") != null
        ? parseFloat(formData.get("budget") as string) * 100
        : 0,
    sku: formData.get("sku"),
    status: formData.get("status"),
    id: formData.get("id"),
  });
  // If form validation fails, return errors early. Otherwise, continue.
  if (!validatedFields.success) {
    console.log("validatedFields", validatedFields.error.flatten().fieldErrors);
    return {
      errors: validatedFields.error.flatten().fieldErrors,
      message: "Campos inválidos. No se pudo crear el contrato.",
    };
  }

  // Prepare data for insertion into the database
  const { name, description, budget, status, sku, id } = validatedFields.data;
  // Insert data into the database
  try {
    if (parseInt(id) > 0) {
      await fetchUpdateContract(parseInt(id), {
        name,
        description,
        budget,
        status,
        sku,
        id: parseInt(id),
        date: new Date().toISOString(),
        tags: "",
      });
    } else {
      await fetchCreateContract({
        name,
        description,
        budget,
        status,
        sku,
        id: 0,
        date: new Date().toISOString(),
        tags: "",
      });
    }
  } catch (error: any) {
    console.log("error", error);
    return {
      message: "Error: " + error.message,
    };
  }

  // Revalidate the cache for the invoices page and redirect the user.
  revalidatePath(routes.contracts);
  redirect(routes.contracts);
}
