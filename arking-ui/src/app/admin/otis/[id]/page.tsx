import { Metadata } from "next";
import Search from "@/app/ui/search";
import { lusitana } from "@/app/ui/fonts";
import { Suspense } from "react";
import { InvoicesTableSkeleton } from "@/app/ui/skeletons";
import OtisTable from "@/app/ui/otis/table";
import { Breadcrumb } from "flowbite-react";
import OtiDetail from "@/app/ui/otis/oti-detail";
import { routes } from "@/app/lib/routes";
export const metadata: Metadata = {
  title: "Otis",
};
export default async function Page({ params }: { params: any }) {
  const id = params.id || "";

  return (
    <div className="w-full">
      <div className="flex w-full items-center justify-between">
        <Breadcrumb>
          <Breadcrumb.Item href="/">Inicio</Breadcrumb.Item>
          <Breadcrumb.Item href={routes.otis}>Otis</Breadcrumb.Item>
        </Breadcrumb>
      </div>
      <br></br>
      <div className="bg-white dark:bg-gray-800 relative shadow-md sm:rounded-lg overflow-hidden">
        <Suspense fallback={<InvoicesTableSkeleton />}>
          <div className="overflow-x-auto">
            <OtiDetail id={id} />
          </div>
        </Suspense>
      </div>
    </div>
  );
}
