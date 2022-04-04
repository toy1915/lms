import { LoginRequest, LoginResponse } from '@type/Login.type';
import { customAxios } from '@apis/Axios-module';
import { TokenRequest } from '@type/Auth.type';

export const authApis = {
  login: async (loginRequest: LoginRequest) => await customAxios.post<LoginResponse>('/api/login', loginRequest),
  reissue: async (tokenRequest: TokenRequest) => await customAxios.post<LoginResponse>('/api/reissue', tokenRequest),
};
