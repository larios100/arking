"use client";

import Link from "next/link";
import { Button } from "@/app/ui/button";
import { useFormState, useFormStatus } from "react-dom";
import { routes } from "@/app/lib/routes";
import { InputForm } from "../components/input-form";
import { PartCreate } from "@/app/models/part-create";
import { createPart } from "@/app/lib/actions-part";
import { PrototypeItem } from "@/app/models/prototype-item";

export default function CreatePartForm({
  part,
  contractId,
  prototypes,
}: {
  part: PartCreate;
  contractId: number;
  prototypes: PrototypeItem[];
}) {
  const initialState = {
    message: "",
    errors: {},
  };
  const [state, dispatch] = useFormState(createPart, initialState);
  const prorotypeOptions = prototypes.map((prototype) => (
    <option key={prototype.id} value={prototype.id}>
      {prototype.name}
    </option>
  ));
  return (
    <form action={dispatch}>
      <input
        type="hidden"
        name="contractId"
        id="contractId"
        value={contractId}
      />
      <input type="hidden" name="id" id="id" value={part.id} />
      <div className="rounded-md bg-gray-50 p-4 md:p-6">
        {/* Nombre */}
        <InputForm
          name="name"
          label="Nombre"
          placeholder="Nombre"
          value={part.name}
          errors={state.errors?.name}
        />
        {/* Description */}
        <InputForm
          name="description"
          label="Descripción"
          placeholder="Descripción"
          value={part.description}
          errors={state.errors?.description}
        />
        {/* Estatus */}
        <div className="mb-4">
          <label htmlFor="role" className="mb-2 block text-sm font-medium">
            Estatus
          </label>
          <div className="relative">
            <select
              id="status"
              name="status"
              className="peer block w-full cursor-pointer rounded-md border border-gray-200 py-2 pl-2 text-sm outline-2 placeholder:text-gray-500"
              defaultValue={part.status}
              aria-describedby="status-error"
            >
              <option value="">Seleccionar estatus</option>
              <option key="Pending" value="Pending">
                Pendiente
              </option>
              <option key="Canceled" value="Canceled">
                Cancelado
              </option>
              <option key="Open" value="Open">
                En proceso
              </option>
              <option key="Done" value="Done">
                Terminado
              </option>
              <option key="Paused" value="Paused">
                Pausado
              </option>
            </select>
          </div>
        </div>
        {/* Modelo */}
        <div className="mb-4">
          <label htmlFor="role" className="mb-2 block text-sm font-medium">
            Modelo
          </label>
          <div className="relative">
            <select
              id="prototypeId"
              name="prototypeId"
              className="peer block w-full cursor-pointer rounded-md border border-gray-200 py-2 pl-2 text-sm outline-2 placeholder:text-gray-500"
              defaultValue={part.prototypeId}
              aria-describedby="status-error"
            >
              <option value="">Seleccionar modelo</option>
              {prorotypeOptions}
            </select>
          </div>
        </div>
      </div>

      <div className="mt-6 flex justify-end gap-4 py-2 px-2">
        <Link
          href={"/admin/contracts/" + contractId}
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
