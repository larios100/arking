import { fetchPart } from "@/app/lib/data-parts";
import { Tabs } from "flowbite-react";
import { usePathname, useRouter, useSearchParams } from "next/navigation";
import PartTabs from "./part-tabs";

export default async function PartDetail({
  id,
  tab,
}: {
  id: number;
  tab: number;
}) {
  const part = await fetchPart(id);
  return (
    <div>
      <PartTabs tabs={part.headers}></PartTabs>
    </div>
  );
}
