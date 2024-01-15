import { Metadata } from "next";
import { lusitana } from "@/app/ui/fonts";
import { Suspense } from "react";
import { InvoicesTableSkeleton, ListSkeleton } from "@/app/ui/skeletons";
import PartDetail from "@/app/ui/parts/part-detail";
import { Breadcrumb } from "flowbite-react";
export const metadata: Metadata = {
  title: "Lote",
};
export default async function Page({
  searchParams,
  params,
}: {
  searchParams?: {
    query?: string;
    tab?: number;
    contract?: string;
    part?: string;
  };
  params: any;
}) {
  const partId = Number(params.partid) || 0;
  const contractId = Number(params.id) || 0;
  const contractName = searchParams?.contract || "";
  const partName = searchParams?.part || "";
  const tab = Number(searchParams?.tab) || 1;
  return (
    <div className="w-full">
      <div className="flex w-full items-center justify-between">
        <Breadcrumb>
          <Breadcrumb.Item href="/">Inicio</Breadcrumb.Item>
          <Breadcrumb.Item href={"/admin/contracts/" + contractId}>
            {partName}
          </Breadcrumb.Item>
          <Breadcrumb.Item>Tareas</Breadcrumb.Item>
        </Breadcrumb>
      </div>
      <br></br>
      <Suspense fallback={<ListSkeleton />}>
        <PartDetail id={partId} tab={tab}></PartDetail>
      </Suspense>
    </div>
  );
}
