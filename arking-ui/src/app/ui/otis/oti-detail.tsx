import { fetchOti } from "@/app/lib/data-otis";
import { Item } from "@/app/models/oti-detail";
import { Fragment } from "react";

export default async function OtiDetail({ id }: { id: string }) {
  const oti = await fetchOti(id);
  return (
    <div style={{ padding: "1em" }}>
      <div className="flex flex-col md:flex-row items-center">
        <div className="w-full md:w-1/2">
          <label className="block text-sm font-bold">Descripción</label>
        </div>
        <div className="w-full md:w-1/2 text-right">
          <label className="block text-sm font-bold">Fecha</label>
        </div>
      </div>
      <div className="flex flex-col md:flex-row items-center">
        <div className="w-full md:w-1/2">
          <p className="block text-sm font-medium text-gray-700 dark:text-gray-400">
            {oti.description}
          </p>
        </div>
        <div className="w-full md:w-1/2 text-right">
          <p className="block text-sm font-medium text-gray-700 dark:text-gray-400">
            {oti.date}
          </p>
        </div>
      </div>
      <div className="flex flex-col md:flex-row items-center">
        <div className="w-full md:w-1/2">
          <label className="block text-sm font-bold">Comentarios</label>
        </div>
        <div className="w-full md:w-1/2 text-right">
          <label className="block text-sm font-bold">Fecha termino</label>
        </div>
      </div>
      <div className="flex flex-col md:flex-row items-center">
        <div className="w-full md:w-1/2">
          <p className="block text-sm font-medium text-gray-700 dark:text-gray-400">
            {oti.comments}
          </p>
        </div>
        <div className="w-full md:w-1/2 text-right">
          <p className="block text-sm font-medium text-gray-700 dark:text-gray-400">
            {oti.endDate}
          </p>
        </div>
      </div>
      <br></br>
      <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
        <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
          <tr>
            <th scope="col" className="px-4 py-3 font-medium sm:pl-6">
              Concepto
            </th>
            <th scope="col" className="px-3 py-3 font-medium">
              Unidad
            </th>
            <th scope="col" className="px-3 py-3 font-medium">
              Precio unitario
            </th>
            <th scope="col" className="px-3 py-3 font-medium">
              Cantidad
            </th>
            <th scope="col" className="px-3 py-3 font-medium">
              Total
            </th>
            <th scope="col" className="px-3 py-3 font-medium">
              Tipo
            </th>
          </tr>
        </thead>
        <tbody className="bg-white">
          {oti.items.map((concept) => (
            <ConceptItem key={concept.id} concept={concept}></ConceptItem>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export function ConceptItem({ concept }: { concept: Item }) {
  return (
    <Fragment>
      <tr
        key={concept.id}
        className="border-t border-gray-200 hover:bg-gray-100 bg-gray-200"
      >
        <td className="px-3 py-3 sm:pl-6">{concept.concept}</td>
        <td className="px-3 py-3 whitespace-nowrap">{concept.unit}</td>
        <td className="px-3 py-3 whitespace-nowrap">{concept.unitPrice}</td>
        <td className="px-3 py-3 whitespace-nowrap">{concept.quantity}</td>
        <td className="px-3 py-3 whitespace-nowrap">{concept.total}</td>
        <td className="px-3 py-3 whitespace-nowrap">
          {concept.otiConceptType}
        </td>
      </tr>
      <ConceptTypeItem
        concepts={concept.childs.filter(
          (item) => item.otiConceptType == "Material"
        )}
        title="Materiales"
      ></ConceptTypeItem>
      <ConceptTypeItem
        concepts={concept.childs.filter(
          (item) => item.otiConceptType == "Workforce"
        )}
        title="Mano de obra"
      ></ConceptTypeItem>
      <ConceptTypeItem
        concepts={concept.childs.filter(
          (item) => item.otiConceptType == "Tool"
        )}
        title="Herramienta"
      ></ConceptTypeItem>
    </Fragment>
  );
}

export function ConceptTypeItem({
  concepts,
  title,
}: {
  concepts: Item[];
  title: string;
}) {
  return (
    <Fragment>
      <tr>
        <th colSpan={6}>
          <label className="block text-sm font-bold">{title}</label>
        </th>
      </tr>
      {concepts.map((child) => {
        return (
          <tr
            key={child.id}
            className="border-t border-gray-200 hover:bg-gray-100"
          >
            <td className="px-3 py-3 sm:pl-6">{child.concept}</td>
            <td className="px-3 py-3 whitespace-nowrap">{child.unit}</td>
            <td className="px-3 py-3 whitespace-nowrap">{child.unitPrice}</td>
            <td className="px-3 py-3 whitespace-nowrap">{child.quantity}</td>
            <td className="px-3 py-3 whitespace-nowrap">{child.total}</td>
            <td className="px-3 py-3 whitespace-nowrap">
              {child.otiConceptType}
            </td>
          </tr>
        );
      })}
    </Fragment>
  );
}
