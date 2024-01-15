export interface ContractItem {
  id: number;
  name: string;
  description: string;
  status: "Pending" | "Canceled" | "Open" | "Done" | "Paused" | "";
  tags: string;
  date: string;
  budget: number;
  sku: string;
}
