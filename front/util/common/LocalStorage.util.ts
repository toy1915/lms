import { TOKEN } from '../../constants/Token.constants';

export const _storeGet = (key: string) => {
  return localStorage.getItem(key);
};

export const _storeSet = (key: string, value: string) => {
  localStorage.setItem(key, value);
};

export const _storeRemoveItem = (key: string) => {
  localStorage.removeItem(key);
};

export const _storeRemoveToken = () => {
  localStorage.removeItem(TOKEN.ACCESS_TOKEN);
  localStorage.removeItem(TOKEN.ACCESS_TOKEN_EXP);
};
