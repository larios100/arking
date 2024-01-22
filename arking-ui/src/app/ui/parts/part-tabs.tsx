"use client";
import { Accordion, Tabs } from "flowbite-react";
import { Header } from "@/app/models/part-detail";
import { usePathname, useRouter, useSearchParams } from "next/navigation";
import { Fragment, useState } from "react";
import Image from "next/image";
import { Button } from "../button";
import ArrowDownTrayIcon from "@heroicons/react/24/outline/ArrowDownTrayIcon";

export default function PartTabs({ tabs }: { tabs: Header[] }) {
  const [photos, setPhotos] = useState<string[]>([]);
  const [showGallery, setShowGallery] = useState(false);
  const searchParams = useSearchParams();
  const { replace } = useRouter();
  const pathname = usePathname();
  const handleClick = (id: number) => {
    const params = new URLSearchParams(searchParams);
    params.set("tab", id.toString());
    replace(`${pathname}?${params.toString()}`);
  };
  if (showGallery) {
    return (
      <Fragment>
        <button
          className="bg-blue-500 p-2 rounded-sm text-white/75 backdrop-blur-lg transition hover:bg-sky-900 hover:text-white"
          onClick={() => setShowGallery(false)}
        >
          Cerrar
        </button>
        <div className="grid grid-cols-2 md:grid-cols-3 gap-4">
          {photos.map((photo) => {
            return (
              <div
                key={photo}
                className="relative z-50 flex aspect-[3/2] w-full max-w-7xl items-center wide:h-full xl:taller-than-854:h-auto"
              >
                <Image
                  className="transform rounded-lg brightness-90 transition will-change-auto group-hover:brightness-110"
                  src={"https://localhost:7258/api/file/" + photo}
                  style={{ transform: "translate3d(0, 0, 0)" }}
                  alt=""
                  width={720}
                  height={480}
                  sizes="(max-width: 640px) 100vw,
                  (max-width: 1280px) 50vw,
                  (max-width: 1536px) 33vw,
                  25vw"
                />
                <div className="absolute top-5 right-0 mx-auto flex max-w-7xl items-center justify-center">
                  <button
                    className="rounded-full bg-blue-500 p-2 text-white/75 backdrop-blur-lg transition hover:bg-sky-900 hover:text-white"
                    title="Download fullsize version"
                  >
                    <a
                      href={"https://localhost:7258/api/file/" + photo}
                      download
                    >
                      <ArrowDownTrayIcon className="h-5 w-5" />
                    </a>
                  </button>
                </div>
              </div>
            );
          })}
        </div>
      </Fragment>
    );
  }
  return (
    <Tabs color="blue">
      {tabs.map((tab) => {
        return (
          <Tabs.Item key={tab.id} id={tab.id.toString()} title={tab.name}>
            {tab.categories.map((category) => {
              return (
                <div
                  key={category.id}
                  className="bg-white dark:bg-gray-800 relative shadow-md sm:rounded-lg overflow-hidden"
                  style={{ marginBottom: "1rem", padding: "1rem" }}
                >
                  <h2>{category.name}</h2>
                  {category.tasks.map((task) => {
                    return (
                      <div key={task.id}>
                        <input
                          type="checkbox"
                          id={task.id.toString()}
                          name={task.name}
                          value={task.name}
                          checked={task.isCompleted}
                          className="mr-2"
                        />
                        <label
                          htmlFor={task.id.toString()}
                          className="mr-2 text-sm"
                        >
                          {task.name}
                        </label>
                        {task.photos.length > 0 && (
                          <a
                            href="#"
                            className="text-blue-500 hover:text-blue-700 text-sm"
                            onClick={() => {
                              setPhotos(task.photos);
                              setShowGallery(true);
                            }}
                          >
                            Fotos
                          </a>
                        )}
                      </div>
                    );
                  })}
                </div>
              );
            })}
            {tab.tests.map((test) => {
              return (
                <div
                  key={test.id}
                  className="bg-white dark:bg-gray-800 relative shadow-md sm:rounded-lg overflow-hidden"
                  style={{ marginBottom: "1rem", padding: "1rem" }}
                >
                  <h2 className="mb-2">{test.name}</h2>
                  {test.items.map((item) => {
                    return (
                      <div key={item.id} className="mt-2 border-2 p-2">
                        <p>
                          {item.description}
                          {item.photos.length > 0 && (
                            <a
                              href="#"
                              className="text-blue-500 hover:text-blue-700 text-sm ml-2"
                              onClick={() => {
                                setPhotos(item.photos);
                                setShowGallery(true);
                              }}
                            >
                              Fotos
                            </a>
                          )}
                        </p>
                        <div className="flex flex-col md:flex-row items-center">
                          <div className="w-full md:w-1/2">
                            <label className="block text-sm font-bold">
                              Fecha
                            </label>
                          </div>
                          <div className="w-full md:w-1/2 text-right">
                            <label className="block text-sm font-bold">
                              Fecha corrección
                            </label>
                          </div>
                        </div>
                        <div className="flex flex-col md:flex-row items-center">
                          <div className="w-full md:w-1/2">
                            <p className="block text-sm font-medium text-gray-700 dark:text-gray-400">
                              {item.testDate}
                            </p>
                          </div>
                          <div className="w-full md:w-1/2 text-right">
                            <p className="block text-sm font-medium text-gray-700 dark:text-gray-400">
                              {item.fixDate}
                            </p>
                          </div>
                        </div>
                        <div className="flex flex-col md:flex-row items-center">
                          <div className="w-full md:w-1/2">
                            <label className="block text-sm font-bold">
                              Resultado
                            </label>
                          </div>
                          <div className="w-full md:w-1/2 text-right">
                            <label className="block text-sm font-bold">
                              Validación
                            </label>
                          </div>
                        </div>
                        <div className="flex flex-col md:flex-row items-center">
                          <div className="w-full md:w-1/2">
                            <p className="block text-sm font-medium text-gray-700 dark:text-gray-400">
                              {item.result}
                            </p>
                          </div>
                          <div className="w-full md:w-1/2 text-right">
                            <p className="block text-sm font-medium text-gray-700 dark:text-gray-400">
                              <input
                                type="checkbox"
                                checked={item.validation}
                              />
                            </p>
                          </div>
                        </div>
                      </div>
                    );
                  })}
                  <label className="block text-sm font-bold mt-3">
                    Comentarios
                  </label>
                  <label>{test.comments}</label>
                </div>
              );
            })}
          </Tabs.Item>
        );
      })}
    </Tabs>
  );
}
