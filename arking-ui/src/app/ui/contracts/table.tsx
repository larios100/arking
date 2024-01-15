import { fetchContracts } from "@/app/lib/data-contracts";
import { Fragment } from "react";
import Pagination from "@/app/ui/contracts/pagination";
import { formatCurrency, formatDateToLocal } from "@/app/lib/utils";
import InvoiceStatus from "./status";
import Link from "next/link";
import { ContractItem } from "@/app/models/contract-item";
import { BasicPagination } from "@/app/models/basic-pagination";
export default async function ContractsTable({
  query,
  currentPage,
}: {
  query: string;
  currentPage: number;
}) {
  let contracts: BasicPagination<ContractItem> = {
    registers: [],
    totalPages: 0,
    pageSize: 0,
    pageIndex: 0,
    total: 0,
  };
  try {
    contracts = await fetchContracts(currentPage, 10, query);
  } catch (ex: any) {
    console.log("error", ex);
    return <div>Error: {ex.message}</div>;
  }
  console.log("contratos", contracts);
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
              Presupuesto
            </th>
            <th scope="col" className="px-3 py-5 font-medium">
              Fecha
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
          {contracts.registers.length === 0 && (
            <tr>
              <td colSpan={6}>Sin registros</td>
            </tr>
          )}
          {contracts.registers.map((contract) => (
            <tr
              key={contract.id}
              className="border-t border-gray-200 hover:bg-gray-100"
            >
              <td className="px-4 py-4 sm:pl-6">{contract.name}</td>
              <td className="px-3 py-4 whitespace-nowrap">
                {contract.description}
              </td>
              <td className="px-3 py-4 whitespace-nowrap">
                {formatCurrency(contract.budget)}
              </td>
              <td className="px-3 py-4 whitespace-nowrap">
                {formatDateToLocal(contract.date)}
              </td>
              <td className="px-3 py-4 whitespace-nowrap">
                <InvoiceStatus status={contract.status}></InvoiceStatus>
              </td>
              <td className="px-3 py-4 whitespace-nowrap text-right text-sm font-medium">
                <Link
                  href={"/admin/contracts/" + contract.id + "/edit"}
                  className="text-indigo-600 hover:text-indigo-900"
                >
                  Editar
                </Link>
                <Link
                  href={"/admin/contracts/" + contract.id}
                  className="text-indigo-600 hover:text-indigo-900"
                >
                  Ver
                </Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="mt-5 flex w-full justify-center">
        <Pagination totalPages={contracts.totalPages} />
      </div>
    </Fragment>
  );
}
