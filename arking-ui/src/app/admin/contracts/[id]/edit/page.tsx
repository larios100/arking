import { fetchContractById } from "@/app/lib/data-contracts";
import { routes } from "@/app/lib/routes";
import CreateContractForm from "@/app/ui/contracts/create-form";
import { Breadcrumb } from "flowbite-react";
import { Metadata } from "next";

export const metadata: Metadata = {
  title: "Editar contrato",
};
export default async function Page({ params }: { params: { id: number } }) {
  const id = params.id;
  const contract = await fetchContractById(id);
  if (contract === null) {
    return <div>Contrato no encontrado</div>;
  }
  return (
    <div className="w-full">
      <div className="flex w-full items-center justify-between">
        <Breadcrumb>
          <Breadcrumb.Item href="/">Inicio</Breadcrumb.Item>
          <Breadcrumb.Item href={routes.contracts}>Contratos</Breadcrumb.Item>
          <Breadcrumb.Item>Editar contrato</Breadcrumb.Item>
        </Breadcrumb>
      </div>
      <br></br>
      <div className="bg-white dark:bg-gray-800 relative shadow-md sm:rounded-lg overflow-hidden">
        <CreateContractForm contract={contract} />
      </div>
    </div>
  );
}
