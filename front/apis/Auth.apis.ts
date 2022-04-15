import { LoginType, TokenType } from '../types/Auth.type';
import { customAxios } from './Axios-moules';

export const AuthApi = {
  login: async (login: LoginType) => await customAxios.post('/api/auth/login', login),
  reissue: async (token: TokenType) => await customAxios.post('/api/auth/reissue', token),
};
