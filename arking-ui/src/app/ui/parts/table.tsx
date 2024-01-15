import { Fragment } from "react";
import Pagination from "@/app/ui/contracts/pagination";
import { formatCurrency, formatDateToLocal } from "@/app/lib/utils";
import InvoiceStatus from "@/app/ui/contracts/status";
import { fetchParts } from "@/app/lib/data-parts";
export default async function PartsTable({
  query,
  currentPage,
  contractId,
}: {
  query: string;
  currentPage: number;
  contractId: number;
}) {
  let parts = await fetchParts(contractId, currentPage);
  console.log("lotes", parts);
  return (
    <Fragment>
      <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
        <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
          <tr>
            <th scope="col" className="px-4 py-5 font-medium sm:pl-6">
              Nombre
            </th>
            <th scope="col" className="px-3 py-5 font-medium">
              Descripción
            </th>
            <th scope="col" className="px-3 py-5 font-medium">
              Estatus
            </th>
            <th scope="col" className="relative py-3 pl-6 pr-3">
              <span className="sr-only">Ver</span>
            </th>
          </tr>
        </thead>
        <tbody className="bg-white">
          {parts.registers.length === 0 && (
            <tr>
              <td colSpan={4}>Sin registros</td>
            </tr>
          )}
          {parts.registers.map((part) => (
            <tr
              key={part.id}
              className="border-t border-gray-200 hover:bg-gray-100"
            >
              <td className="px-4 py-4 sm:pl-6">{part.name}</td>
              <td className="px-3 py-4 whitespace-nowrap">
                {part.description}
              </td>
              <td className="px-3 py-4 whitespace-nowrap">
                <InvoiceStatus status={part.status}></InvoiceStatus>
              </td>
              <td className="px-3 py-4 whitespace-nowrap text-right text-sm font-medium">
                <a
                  href={
                    "/admin/contracts/" +
                    contractId +
                    "/part/" +
                    part.id +
                    "?part=" +
                    part.name
                  }
                  className="text-indigo-600 hover:text-indigo-900"
                >
                  Ver
                </a>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="mt-5 flex w-full justify-center">
        <Pagination totalPages={parts.totalPages} />
      </div>
    </Fragment>
  );
}
