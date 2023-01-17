export interface D66Response {
  timestamp: Date;
  statusCode: number;
  status: string;
  username?: string;
  message: string;
  data?: {
    auth?: LoginResponse
  }
}

export interface D66ErrorResponse {
  error?: D66Response
}

export interface LoginResponse {
  authenticationToken: string;
  refreshToken: string;
  expiresAt: Date;
}

export const emptyResponse: D66Response = {
  timestamp: new Date(),
  statusCode: -1,
  status: '',
  message: ''
}