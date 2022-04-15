export type LoginType = {
  id: string;
  password: string;
};

export type AuthType = {
  account: string;
  nameK: string;
  roleName: string;
};

export type TokenType = {
  accessToken: string;
  refreshToken: string;
};
