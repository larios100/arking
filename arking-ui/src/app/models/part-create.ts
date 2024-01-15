export interface PartCreate {
  id: number;
  name: string;
  prototypeId: number;
  description: string;
  status: "Pending" | "Canceled" | "Open" | "Done" | "Paused";
}
