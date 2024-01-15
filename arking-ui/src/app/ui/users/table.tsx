import { fetchUsers } from "@/app/lib/data-users";
import Link from "next/link";
import { Fragment } from "react";
import Pagination from "../contracts/pagination";
import { BasicPagination } from "@/app/models/basic-pagination";
import { UserItem } from "@/app/models/user-item";

export default async function UsersTable({
  query,
  currentPage,
}: {
  query: string;
  currentPage: number;
}) {
  let users: BasicPagination<UserItem> = {
    registers: [],
    totalPages: 0,
    pageSize: 0,
    pageIndex: 0,
    total: 0,
  };
  try {
    users = await fetchUsers(query, currentPage);
  } catch (ex: any) {
    console.log("error", ex);
    return <div>Error: {ex.message}</div>;
  }

  return (
    <Fragment>
      <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
        <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
          <tr>
            <th scope="col" className="px-4 py-4 font-medium sm:pl-6">
              Nombre
            </th>
            <th scope="col" className="px-3 py-4 font-medium">
              Apellido
            </th>
            <th scope="col" className="px-3 py-4 font-medium">
              Usuario
            </th>
            <th scope="col" className="px-3 py-4 font-medium">
              Role
            </th>
            <th scope="col" className="px-3 py-4 font-medium">
              Activo
            </th>
            <th scope="col" className="relative py-3 pl-6 pr-3">
              <span className="sr-only">Editar</span>
            </th>
          </tr>
        </thead>
        <tbody className="bg-white">
          {users.registers.map((user) => (
            <tr
              key={user.id}
              className="border-t border-gray-200 hover:bg-gray-100"
            >
              <td className="px-4 py-3 sm:pl-6">{user.firstName}</td>
              <td className="px-3 py-3 whitespace-nowrap">{user.lastName}</td>
              <td className="px-3 py-3 whitespace-nowrap">{user.userName}</td>
              <td className="px-3 py-3 whitespace-nowrap">{user.role}</td>
              <td className="px-3 py-3 whitespace-nowrap">
                {user.active ? <span>Activo</span> : <span>Inactivo</span>}
              </td>
              <td className="px-3 ppy-3 whitespace-nowrap text-right text-sm font-medium">
                <Link
                  href={"/admin/users/" + user.id}
                  className="text-indigo-600 hover:text-indigo-900"
                >
                  Editar
                </Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="mt-5 flex w-full justify-center">
        <Pagination totalPages={users.totalPages} />
      </div>
    </Fragment>
  );
}
