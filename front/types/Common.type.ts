export type ObjectType = {
  [key: string]: string;
};

export type ResponseDataType<T = Object> = {
  DATA: T;
  RESULT: ResultType;
  MESSAGE?: string;
};

enum ResultType {
  SUCCESS = 'SUCCESS',
  FAILURE = 'FAILURE',
}
