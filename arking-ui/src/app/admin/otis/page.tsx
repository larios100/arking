import { Metadata } from "next";
import Search from "@/app/ui/search";
import { lusitana } from "@/app/ui/fonts";
import { Suspense } from "react";
import { InvoicesTableSkeleton } from "@/app/ui/skeletons";
import OtisTable from "@/app/ui/otis/table";
import { Breadcrumb } from "flowbite-react";
export const metadata: Metadata = {
  title: "Otis",
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
          <Breadcrumb.Item>Otis</Breadcrumb.Item>
        </Breadcrumb>
      </div>
      <br></br>
      <div className="bg-white dark:bg-gray-800 relative shadow-md sm:rounded-lg overflow-hidden">
        <div className="flex flex-col md:flex-row items-center justify-between space-y-3 md:space-y-0 md:space-x-4 p-4">
          <div className="w-full md:w-1/2">
            <Search placeholder="Buscar otis..." />
          </div>
        </div>
        <Suspense fallback={<InvoicesTableSkeleton />}>
          <div className="overflow-x-auto">
            <OtisTable query={query} currentPage={currentPage} />
          </div>
        </Suspense>
      </div>
    </div>
  );
}
