export type LoginResponse = {
  accessToken: string;
  refreshToken: string;
  accessTokenExpireIn: number;
};

export type LoginRequest = {
  id: string;
  password: string;
};

export type PublicLoginRequest = {
  publicId: string;
};
