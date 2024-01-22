export interface PartDetail {
  id: number;
  name: string;
  description: string;
  contractId: number;
  contract: string;
  prototypeId: number;
  prototype: string;
  headers: Header[];
}

export interface Header {
  id: number;
  name: string;
  categories: Category[];
  tests: Test[];
}

export interface Category {
  id: number;
  name: string;
  tasks: Task[];
}

export interface Task {
  id: number;
  name: string;
  isCompleted: boolean;
  photos: string[];
}

export interface Test {
  id: number;
  name: string;
  comments: string;
  items: Item[];
}

export interface Item {
  id: number;
  name: string;
  description: string;
  photos: string[];
  testDate: string;
  fixDate: string;
  result: string;
  validation: boolean;
}
