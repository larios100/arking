import { routes } from "@/app/lib/routes";
import CreatePrototypeForm from "@/app/ui/prototypes/create-form";
import { Breadcrumb } from "flowbite-react";
import { Metadata } from "next";

export const metadata: Metadata = {
  title: "Crear modelo",
};
export default async function Page() {
  return (
    <div className="w-full">
      <div className="flex w-full items-center justify-between">
        <Breadcrumb>
          <Breadcrumb.Item href="/">Inicio</Breadcrumb.Item>
          <Breadcrumb.Item href={routes.prototypes}>Modelos</Breadcrumb.Item>
          <Breadcrumb.Item>Crear modelo</Breadcrumb.Item>
        </Breadcrumb>
      </div>
      <br></br>
      <div className="bg-white dark:bg-gray-800 relative shadow-md sm:rounded-lg overflow-hidden">
        <CreatePrototypeForm />
      </div>
    </div>
  );
}
