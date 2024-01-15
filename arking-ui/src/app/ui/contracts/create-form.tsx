"use client";

import Link from "next/link";
import { Button } from "@/app/ui/button";
import { useFormState, useFormStatus } from "react-dom";
import { routes } from "@/app/lib/routes";
import { InputForm } from "../components/input-form";
import { createContract } from "@/app/lib/actions-contract";
import { ContractItem } from "@/app/models/contract-item";

export default function CreateContractForm({
  contract,
}: {
  contract: ContractItem;
}) {
  const initialState = {
    message: "",
    errors: {},
  };
  const [state, dispatch] = useFormState(createContract, initialState);
  return (
    <form action={dispatch}>
      <input type="hidden" name="id" id="id" value={contract.id} />
      <div className="rounded-md bg-gray-50 p-4 md:p-6">
        {/* Nombre */}
        <InputForm
          name="name"
          label="Nombre"
          placeholder="Nombre"
          value={contract.name}
          errors={state.errors?.name}
        />
        {/* Description */}
        <InputForm
          name="description"
          label="Descripción"
          placeholder="Descripción"
          value={contract.description}
          errors={state.errors?.description}
        />
        {/* SKU */}
        <InputForm
          name="sku"
          label="SKU"
          placeholder="SKU"
          value={contract.sku}
          errors={state.errors?.sku}
        />
        {/* SKU */}
        <InputForm
          name="budget"
          label="Presupuesto"
          placeholder="Presupuesto"
          value={contract.budget}
          errors={state.errors?.budget}
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
              defaultValue={contract.status}
              aria-describedby="status-error"
            >
              <option value="" disabled>
                Seleccionar estatus
              </option>
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
      </div>
      <div className="mt-6 flex justify-end gap-4 py-2 px-2">
        <Link
          href={routes.contracts}
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
