export interface OtiDetail {
  id: string;
  description: string;
  comments: string;
  total: number;
  date: string;
  startDate: string;
  endDate: string;
  signAuditorId: string | null;
  signResidentId: string | null;
  statusId: number;
  items: Item[];
}

export interface Item {
  id: string;
  concept: string;
  unit: string;
  unitPrice: number;
  quantity: number;
  otiConceptType: string;
  total: number;
  childs: Item[];
}
