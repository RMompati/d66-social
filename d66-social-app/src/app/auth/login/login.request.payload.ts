export interface LoginRequestPayload {
  username: string;
  password: string;
}

export const emptyPayload: LoginRequestPayload = {
  username: '',
  password: ''
}