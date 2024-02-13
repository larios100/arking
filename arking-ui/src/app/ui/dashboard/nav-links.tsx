"use client";

import {
  BuildingOfficeIcon,
  HomeIcon,
  DocumentDuplicateIcon,
  UserGroupIcon,
  WrenchScrewdriverIcon,
} from "@heroicons/react/24/outline";
import Link from "next/link";
import { usePathname } from "next/navigation";
import clsx from "clsx";
import { routes } from "@/app/lib/routes";

// Map of links to display in the side navigation.
// Depending on the size of the application, this would be stored in a database.
const links = [
  { name: "Inicio", href: routes.home, icon: HomeIcon },
  {
    name: "Contratos",
    href: routes.contracts,
    icon: DocumentDuplicateIcon,
  },
  { name: "Otis", href: routes.otis, icon: WrenchScrewdriverIcon },
  { name: "Modelos", href: routes.prototypes, icon: BuildingOfficeIcon },
  { name: "Usuarios", href: routes.users, icon: UserGroupIcon },
];

export default function NavLinks() {
  const pathname = usePathname();

  return (
    <>
      {links.map((link) => {
        const LinkIcon = link.icon;
        return (
          <Link
            key={link.name}
            href={link.href}
            prefetch={false}
            className={clsx(
              "flex h-[48px] grow items-center justify-center gap-2 rounded-md bg-gray-50 p-3 text-sm font-medium hover:bg-sky-100 hover:text-blue-600 md:flex-none md:justify-start md:p-2 md:px-3",
              {
                "bg-sky-100 text-blue-600": pathname.startsWith(link.href),
              }
            )}
          >
            <LinkIcon className="w-6" />
            <p className="hidden md:block">{link.name}</p>
          </Link>
        );
      })}
    </>
  );
}
