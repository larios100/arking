export interface BasicPagination<T> {
  pageIndex: number;
  pageSize: number;
  total: number;
  registers: T[];
  totalPages: number;
}
