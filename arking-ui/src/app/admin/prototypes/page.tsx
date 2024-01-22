import { Metadata } from "next";
import Search from "@/app/ui/search";
import { Suspense } from "react";
import { InvoicesTableSkeleton } from "@/app/ui/skeletons";
import { Breadcrumb } from "flowbite-react";
import PrototypesTable from "@/app/ui/prototypes/table";
import Link from "next/link";
import { routes } from "@/app/lib/routes";
export const metadata: Metadata = {
  title: "Modelos",
};
export default async function Page({
  searchParams,
}: {
  searchParams?: {
    query?: string;
    page?: string;
  };
}) {
  const query = searchParams?.query || "";
  const currentPage = Number(searchParams?.page) || 1;

  return (
    <div className="w-full">
      <div className="flex w-full items-center justify-between">
        <Breadcrumb>
          <Breadcrumb.Item href="/">Inicio</Breadcrumb.Item>
          <Breadcrumb.Item>Modelos</Breadcrumb.Item>
        </Breadcrumb>
      </div>
      <br></br>
      <div className="bg-white dark:bg-gray-800 relative shadow-md sm:rounded-lg overflow-hidden pb-4">
        <div className="flex flex-col md:flex-row items-center justify-between space-y-3 md:space-y-0 md:space-x-4 p-4">
          <div className="w-full md:w-1/2">
            <Search placeholder="Buscar modelo..." />
          </div>
          <div className="w-full md:w-auto flex flex-col md:flex-row space-y-2 md:space-y-0 items-stretch md:items-center justify-end md:space-x-3 flex-shrink-0">
            <Link
              href={routes.prototypeCreate}
              className="flex h-10 items-center rounded-lg bg-blue-500 px-4 text-sm font-medium text-white transition-colors hover:bg-blue-400 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue-500 active:bg-blue-600 aria-disabled:cursor-not-allowed aria-disabled:opacity-50'"
            >
              Agregar
            </Link>
          </div>
        </div>
        <Suspense fallback={<InvoicesTableSkeleton />}>
          <div className="overflow-x-auto">
            <PrototypesTable query={query} currentPage={currentPage} />
          </div>
        </Suspense>
      </div>
    </div>
  );
}
