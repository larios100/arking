"use client";

import Link from "next/link";
import {
  CheckIcon,
  ClockIcon,
  CurrencyDollarIcon,
  UserCircleIcon,
} from "@heroicons/react/24/outline";
import { Button } from "@/app/ui/button";
import { useFormState, useFormStatus } from "react-dom";
import { createUser } from "@/app/lib/actions-user";
import { routes } from "@/app/lib/routes";
import { InputForm } from "../components/input-form";

export default function CreateUserForm() {
  const initialState = {
    message: "",
    errors: {
      firstName: [],
      lastName: [],
      userName: [],
      role: [],
    },
  };
  const [state, dispatch] = useFormState(createUser, initialState);
  const { pending } = useFormStatus();
  return (
    <form action={dispatch}>
      <div className="rounded-md bg-gray-50 p-4 md:p-6">
        {/* Nombre */}
        <InputForm
          name="firstName"
          label="Nombre"
          placeholder="Nombre"
          value={""}
          errors={state.errors?.firstName}
        />
        {/* Apellido */}
        <InputForm
          name="lastName"
          label="Apellido"
          placeholder="Apellido"
          value={""}
          errors={state.errors?.lastName}
        />
        {/* Usuario */}
        <InputForm
          name="userName"
          label="Email"
          placeholder="Email"
          value={""}
          errors={state.errors?.userName}
        />
        {/* Rol */}
        <div className="mb-4">
          <label htmlFor="role" className="mb-2 block text-sm font-medium">
            Rol
          </label>
          <div className="relative">
            <select
              id="role"
              name="role"
              className="peer block w-full cursor-pointer rounded-md border border-gray-200 py-2 pl-10 text-sm outline-2 placeholder:text-gray-500"
              defaultValue=""
              aria-describedby="role-error"
            >
              <option value="" disabled>
                Select a customer
              </option>
              <option key="Admin" value="Admin">
                Admin
              </option>
              <option key="Operador" value="Admin">
                Operador
              </option>
            </select>
            <UserCircleIcon className="pointer-events-none absolute left-3 top-1/2 h-[18px] w-[18px] -translate-y-1/2 text-gray-500" />
          </div>

          <div id="role-error" aria-live="polite" aria-atomic="true">
            {state.errors?.role &&
              state.errors.role.map((error: string) => (
                <p className="mt-2 text-sm text-red-500" key={error}>
                  {error}
                </p>
              ))}
          </div>
        </div>
        <div aria-live="polite" aria-atomic="true">
          {state.message ? (
            <p className="mt-2 text-sm text-red-500">{state.message}</p>
          ) : null}
        </div>
      </div>
      <div className="mt-6 flex justify-end gap-4 py-2 px-2">
        <Link
          href={routes.users}
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
