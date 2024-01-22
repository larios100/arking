"use client";
import Link from "next/link";
import NavLinks from "@/app/ui/dashboard/nav-links";
import { PowerIcon } from "@heroicons/react/24/outline";
import { singout } from "@/app/lib/actions";
import { useFormState } from "react-dom";
import Image from "next/image";

export default function SideNav() {
  const [errorMessage, dispatch] = useFormState(singout, undefined);
  return (
    <div className="flex h-full flex-col px-3 py-4 md:px-2">
      <Link
        className="mb-2 flex h-20 items-end justify-start rounded-md bg-sky-100 p-4 md:h-40"
        href="/"
      >
        <Image
          alt="Arking"
          src="/logo-simple.png"
          width={150}
          height={50}
          style={{ margin: "0 auto" }}
          className="relative dark:drop-shadow-[0_0_0.3rem_#ffffff70] dark:invert"
        ></Image>
      </Link>
      <div className="flex grow flex-row justify-between space-x-2 md:flex-col md:space-x-0 md:space-y-2">
        <NavLinks />
        <div className="hidden h-auto w-full grow rounded-md bg-gray-50 md:block"></div>
        <form action={dispatch}>
          <button className="flex h-[48px] w-full grow items-center justify-center gap-2 rounded-md bg-gray-50 p-3 text-sm font-medium hover:bg-sky-100 hover:text-blue-600 md:flex-none md:justify-start md:p-2 md:px-3">
            <PowerIcon className="w-6 text-red-600" />
            <div className="hidden md:block text-red-600">Salir</div>
          </button>
        </form>
      </div>
    </div>
  );
}
