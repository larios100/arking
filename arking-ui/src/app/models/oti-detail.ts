export interface OtiDetail {
  id: string;
  description: string;
  comments: string;
  total: number;
  date: string;
  startDate: string;
  endDate: string;
  signAuditorId: string;
  signResidentId: string;
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
  childs: Item[];
}
