"use client";
import { Accordion, Tabs } from "flowbite-react";
import { Header } from "@/app/models/part-detail";
import { usePathname, useRouter, useSearchParams } from "next/navigation";
import { Fragment, useState } from "react";
import Image from "next/image";
import { Button } from "../button";

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
        <button onClick={() => setShowGallery(false)}>Cerrar</button>
        <div className="grid grid-cols-2 md:grid-cols-3 gap-4">
          {photos.map((photo) => {
            return (
              <div key={photo}>
                <Image
                  className="h-auto max-w-full rounded-lg"
                  src={"https://localhost:7258/api/file/" + photo}
                  alt=""
                  width={500}
                  height={300}
                />
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
                  <h2>{test.name}</h2>
                  {test.items.map((item) => {
                    return (
                      <div key={item.id}>
                        <p>{item.description}</p>
                        <label>
                          Fecha <strong>{item.testDate}</strong>
                        </label>
                        <label>
                          Fecha corrección <strong>{item.fixDate}</strong>
                        </label>
                        <label>
                          Resultado <strong>{item.result}</strong>
                        </label>
                        <label>
                          Validación{" "}
                          <input type="checkbox" checked={item.validation} />
                        </label>
                      </div>
                    );
                  })}
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
