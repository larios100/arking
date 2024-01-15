import { Fragment } from "react";

export default function FileForm({ name }: { name: string }) {
  return (
    <Fragment>
      <label className="block">
        <span className="sr-only">Seleccionar archivo</span>
        <input
          type="file"
          name={name}
          className="block w-full text-sm text-slate-500
      file:mr-4 file:py-2 file:px-4
      file:rounded-full file:border-0
      file:text-sm file:font-semibold
      file:bg-blue-500 file:text-white
      hover:file:bg-blue-700 hover:file:text-white
    "
        />
      </label>
    </Fragment>
  );
}
