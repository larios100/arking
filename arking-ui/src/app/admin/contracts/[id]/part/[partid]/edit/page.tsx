import { fetchContractById } from "@/app/lib/data-contracts";
import { fetchPartSimple } from "@/app/lib/data-parts";
import { fetchPrototype } from "@/app/lib/data-prototypes";
import { routes } from "@/app/lib/routes";
import CreatePartForm from "@/app/ui/parts/create-form";
import { Breadcrumb } from "flowbite-react";
import { Metadata } from "next";

export const metadata: Metadata = {
  title: "Editar lote",
};
export default async function Page({ params }: { params: any }) {
  const contractId = Number(params.id) || 0;
  const contract = await fetchContractById(contractId);
  const prototypes = await fetchPrototype("", 1, 1000);
  const part = await fetchPartSimple(params.partid);
  return (
    <div className="w-full">
      <div className="flex w-full items-center justify-between">
        <Breadcrumb>
          <Breadcrumb.Item href="/">Inicio</Breadcrumb.Item>
          <Breadcrumb.Item href={routes.contracts}>
            {contract.name}
          </Breadcrumb.Item>
          <Breadcrumb.Item href={routes.contracts + "/" + contractId}>
            Lotes
          </Breadcrumb.Item>
          <Breadcrumb.Item>Editar lote</Breadcrumb.Item>
        </Breadcrumb>
      </div>
      <br></br>
      <div className="bg-white dark:bg-gray-800 relative shadow-md sm:rounded-lg overflow-hidden">
        <CreatePartForm
          part={{
            id: part.id,
            name: part.name,
            description: part.description,
            status: part.status,
            prototypeId: part.prototypeId,
          }}
          contractId={contractId}
          prototypes={prototypes.registers}
        />
      </div>
    </div>
  );
}
