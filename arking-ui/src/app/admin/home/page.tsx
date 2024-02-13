import { Breadcrumb } from "flowbite-react";
import { Metadata } from "next";
import { cookies } from "next/headers";

export const metadata: Metadata = {
  title: "Inicio",
};
export default async function Page() {
  const userName = cookies().get("user")?.value || "";
  return (
    <div className="w-full">
      <div className="flex w-full items-center justify-between">
        <Breadcrumb>
          <Breadcrumb.Item href="/">Inicio</Breadcrumb.Item>
        </Breadcrumb>
      </div>
      <br></br>
      <div className="bg-white dark:bg-gray-800 relative shadow-md sm:rounded-lg overflow-hidden pb-4 h-[80vh]">
        <div className="flex flex-col md:flex-row items-center justify-between space-y-3 md:space-y-0 md:space-x-4 p-4">
          <div className="w-full h-full md:w-1/2">
            <h3>Bienvenido {userName}</h3>
          </div>
        </div>
      </div>
    </div>
  );
}
