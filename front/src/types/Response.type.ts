export type ResponseType<T = Object> = {
  DATA: T;
  PAGE: PageType;
  List: Array<GridType>;
  RESULT: ResultType;
};

export enum ResultType {
  FAILURE = 'FAILURE',
  SUCCESS = 'SUCCESS',
}

type PageType = {
  DATACOUNT: number;
  PAGENUM: number;
  PAGESIZE: number;
};

export type GridType = {
  [key: string]: string;
};
