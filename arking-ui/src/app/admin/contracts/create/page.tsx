import { routes } from "@/app/lib/routes";
import CreateContractForm from "@/app/ui/contracts/create-form";
import { Breadcrumb } from "flowbite-react";
import { Metadata } from "next";

export const metadata: Metadata = {
  title: "Crear contrato",
};
export default async function Page() {
  return (
    <div className="w-full">
      <div className="flex w-full items-center justify-between">
        <Breadcrumb>
          <Breadcrumb.Item href="/">Inicio</Breadcrumb.Item>
          <Breadcrumb.Item href={routes.contracts}>Contratos</Breadcrumb.Item>
          <Breadcrumb.Item>Crear contrato</Breadcrumb.Item>
        </Breadcrumb>
      </div>
      <br></br>
      <div className="bg-white dark:bg-gray-800 relative shadow-md sm:rounded-lg overflow-hidden">
        <CreateContractForm
          contract={{
            id: 0,
            name: "",
            description: "",
            sku: "",
            budget: 0,
            status: "Pending",
            date: "",
            tags: "",
          }}
        />
      </div>
    </div>
  );
}
