"use client";
import { Fragment } from "react";

export function InputForm({
  name,
  label,
  placeholder,
  value,
  errors,
}: {
  name: string;
  label: string;
  placeholder: string;
  value: any;
  errors: string[] | undefined;
}) {
  return (
    <Fragment>
      <div className="mb-4">
        <label htmlFor={name} className="mb-2 block text-sm font-medium">
          {label}
        </label>
        <div className="relative mt-2 rounded-md">
          <div className="relative">
            <input
              id={name}
              name={name}
              type="string"
              placeholder={placeholder}
              className="peer block w-full rounded-md border border-gray-200 py-2 pl-2 text-sm outline-2 placeholder:text-gray-500"
              aria-describedby={name + "-error"}
              defaultValue={value}
            />
          </div>
        </div>
        <div id={name + "-error"} aria-live="polite" aria-atomic="true">
          {errors &&
            errors.map((error: string) => (
              <p className="mt-2 text-sm text-red-500" key={error}>
                {error}
              </p>
            ))}
        </div>
      </div>
    </Fragment>
  );
}
