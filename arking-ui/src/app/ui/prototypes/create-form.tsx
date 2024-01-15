"use client";

import Link from "next/link";
import { Button } from "@/app/ui/button";
import { useFormState, useFormStatus } from "react-dom";
import { routes } from "@/app/lib/routes";
import { InputForm } from "../components/input-form";
import { createPrototype } from "@/app/lib/actions-prototypes";
import FileForm from "../components/file-form";
import { FileInput } from "flowbite-react";

export default function CreatePrototypeForm() {
  const initialState = {
    message: "",
    errors: {},
  };
  const [state, dispatch] = useFormState(createPrototype, initialState);
  return (
    <form action={dispatch}>
      <div className="rounded-md bg-gray-50 p-4 md:p-6">
        {/* Nombre */}
        <InputForm
          name="name"
          label="Nombre"
          placeholder="Nombre"
          value={""}
          errors={state.errors?.name}
        />
        {/* Apellido */}
        <InputForm
          name="description"
          label="Descripción"
          placeholder="Descripción"
          value={""}
          errors={state.errors?.description}
        />
        <FileInput color={"blue"} name="file" />
      </div>
      <div className="mt-6 flex justify-end gap-4 py-2 px-2">
        <Link
          href={routes.prototypes}
          className="flex h-10 items-center rounded-lg bg-gray-100 px-4 text-sm font-medium text-gray-600 transition-colors hover:bg-gray-200"
        >
          Cancelar
        </Link>
        <Submit />
      </div>
    </form>
  );
}
function Submit() {
  const { pending } = useFormStatus();
  return (
    <Button type="submit" disabled={pending} aria-disabled={pending}>
      {pending ? "Enviando..." : "Guardar"}
    </Button>
  );
}
