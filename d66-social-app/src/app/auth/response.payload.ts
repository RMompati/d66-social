export interface D66Response {
  timestamp: Date;
  statusCode: number;
  status: string;
  username?: string;
  message: string;
  data?: {}
}

export const emptyResponse: D66Response = {
  timestamp: new Date(),
  statusCode: -1,
  status: '',
  message: ''
}