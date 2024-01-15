import { CheckIcon, ClockIcon } from "@heroicons/react/24/outline";
import clsx from "clsx";

export default function InvoiceStatus({ status }: { status: string }) {
  return (
    <span
      className={clsx(
        "inline-flex items-center rounded-full px-2 py-1 text-xs",
        {
          "bg-gray-100 text-gray-500": status === "Pending",
          "bg-green-500 text-white": status === "Done",
          "bg-red-500 text-white": status === "Canceled",
          "bg-blue-500 text-white": status === "Open",
          "bg-gray-100 text-gray-600": status === "Paused",
        }
      )}
    >
      {status === "Pending" ? <>Pendiente</> : null}
      {status === "Canceled" ? <>Cancelado</> : null}
      {status === "Open" ? <>En proceso</> : null}
      {status === "Paused" ? <>Pausado</> : null}
      {status === "Done" ? <>Terminado</> : null}
    </span>
  );
}
