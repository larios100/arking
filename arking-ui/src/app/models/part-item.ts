export interface PartItem {
  id: number;
  name: string;
  prototype: string;
  description: string;
  status: "Pending" | "Canceled" | "Open" | "Done" | "Paused";
}
