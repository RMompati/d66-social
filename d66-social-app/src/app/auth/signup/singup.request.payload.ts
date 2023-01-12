export interface SignupRequestPayload {
  firstName: string;
  lastName: string;
  email: string;
  username: string;
  password: string;
}

export const emptyPayload: SignupRequestPayload = {
  firstName: '',
  lastName: '',
  email: '',
  username: '',
  password: '',
}