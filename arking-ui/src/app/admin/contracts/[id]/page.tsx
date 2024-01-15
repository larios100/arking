import { Metadata } from "next";
import Search from "@/app/ui/search";
import { lusitana } from "@/app/ui/fonts";
import PartsTable from "@/app/ui/parts/table";
import { Suspense } from "react";
import { InvoicesTableSkeleton } from "@/app/ui/skeletons";
import { Breadcrumb, Button } from "flowbite-react";
import { routes } from "@/app/lib/routes";
import { fetchContractById } from "@/app/lib/data-contracts";
import Link from "next/link";
export const metadata: Metadata = {
  title: "Lotes",
};
export default async function Page({
  searchParams,
  params,
}: {
  searchParams?: {
    query?: string;
    page?: string;
    contract?: string;
  };
  params: any;
}) {
  const query = searchParams?.query || "";
  const currentPage = Number(searchParams?.page) || 1;
  const contractId = Number(params.id) || 0;
  const contract = await fetchContractById(contractId);
  return (
    <div className="w-full">
      <div className="flex w-full items-center justify-between">
        <Breadcrumb>
          <Breadcrumb.Item href="/">Inicio</Breadcrumb.Item>
          <Breadcrumb.Item href={routes.contracts}>
            {contract.name}
          </Breadcrumb.Item>
          <Breadcrumb.Item>Lotes</Breadcrumb.Item>
        </Breadcrumb>
      </div>
      <br></br>
      <div className="bg-white dark:bg-gray-800 relative shadow-md sm:rounded-lg overflow-hidden">
        <div className="flex flex-col md:flex-row items-center justify-between space-y-3 md:space-y-0 md:space-x-4 p-4">
          <div className="w-full md:w-1/2">
            <Search placeholder="Buscar lotes..." />
          </div>
          <div className="w-full md:w-auto flex flex-col md:flex-row space-y-2 md:space-y-0 items-stretch md:items-center justify-end md:space-x-3 flex-shrink-0">
            <Link
              href={routes.contracts + "/" + contractId + "/part/create"}
              className="flex h-10 items-center rounded-lg bg-blue-500 px-4 text-sm font-medium text-white transition-colors hover:bg-blue-400 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue-500 active:bg-blue-600 aria-disabled:cursor-not-allowed aria-disabled:opacity-50'"
            >
              Agregar
            </Link>
          </div>
        </div>
        <Suspense fallback={<InvoicesTableSkeleton />}>
          <div className="overflow-x-auto">
            <PartsTable
              query={query}
              currentPage={currentPage}
              contractId={contractId}
            />
          </div>
        </Suspense>
      </div>
    </div>
  );
}
