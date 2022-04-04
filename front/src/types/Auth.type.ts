import { Role } from '@constants/Role.constants';

export type TokenRequest = {
  accessToken: string;
  refreshToken: string;
};

export type AuthUser = {
  id: string;
  userNm: string;
  roleNm: string;
  auth: Role;
};

export type AuthPayload = {
  authUser: AuthUser;
};

export type AuthState = AuthPayload & {
  isAuthenticated: boolean;
};
