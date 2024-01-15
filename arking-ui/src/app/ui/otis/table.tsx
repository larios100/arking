import { Fragment } from "react";
import Pagination from "@/app/ui/otis/pagination";
import { formatCurrency, formatDateToLocal } from "@/app/lib/utils";
import InvoiceStatus from "./status";
import { fetchOtis } from "@/app/lib/data-otis";
import Link from "next/link";
export default async function OtisTable({
  query,
  currentPage,
}: {
  query: string;
  currentPage: number;
}) {
  let otis = await fetchOtis(currentPage);
  console.log("otis", otis);
  return (
    <Fragment>
      <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
        <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
          <tr>
            <th scope="col" className="px-4 py-5 font-medium sm:pl-6">
              Descripción
            </th>
            <th scope="col" className="px-3 py-5 font-medium">
              Comentarios
            </th>
            <th scope="col" className="px-3 py-5 font-medium">
              Fecha
            </th>
            <th scope="col" className="px-3 py-5 font-medium">
              Fecha inicio
            </th>
            <th scope="col" className="px-3 py-5 font-medium">
              Fecha termino
            </th>
            <th scope="col" className="relative py-3 pl-6 pr-3">
              <span className="sr-only">Edit</span>
            </th>
          </tr>
        </thead>
        <tbody className="bg-white">
          {otis.registers.map((oti) => (
            <tr
              key={oti.id}
              className="border-t border-gray-200 hover:bg-gray-100"
            >
              <td className="px-4 py-4 sm:pl-6">{oti.description}</td>
              <td className="px-3 py-4 whitespace-nowrap">{oti.comments}</td>
              <td className="px-3 py-4 whitespace-nowrap">{oti.date}</td>
              <td className="px-3 py-4 whitespace-nowrap">{oti.starDate}</td>
              <td className="px-3 py-4 whitespace-nowrap">{oti.endDate}</td>
              <td className="px-3 py-4 whitespace-nowrap text-right text-sm font-medium">
                <Link
                  href={"/admin/otis/" + oti.id}
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
        <Pagination totalPages={otis.totalPages} />
      </div>
    </Fragment>
  );
}
