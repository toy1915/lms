import axios, { AxiosInstance, AxiosResponse } from 'axios';
import { _storeGet, _storeRemoveToken, _storeSet } from '../util/common/LocalStorage.util';
import { TOKEN } from '../constants/Token.constants';
import { ErrorMessageConstants } from '../constants/ErrorMessage.constants';
import { ResponseDataType } from '../types/Common.type';
import { TokenType } from '../types/Auth.type';
import { AuthApi } from './Auth.apis';

export const customAxios: AxiosInstance = axios.create({
  headers: {
    'Content-Type': 'application/json',
    'X-Authorization': 'Bearer ' + _storeGet(TOKEN.ACCESS_TOKEN),
  },
});

customAxios.interceptors.response.use(
  (response: AxiosResponse) => httpSuccessResponse(response),
  (error: any) => httpErrorResponse(error),
);

const httpSuccessResponse = (response: AxiosResponse<ResponseDataType>) => {
  if (response.data.RESULT != 'SUCCESS') {
    alert(response.data.MESSAGE);
  }
  return response;
};

const httpErrorResponse = (error: any) => {
  switch (error.response?.status) {
    case 500:
      alert(ErrorMessageConstants.COMMON_MESSAGE);
      break;
    case 401:
      if (error.config._retry) break;
      error.config._retry = true;

      const accessToken = _storeGet('accessToken');
      const refreshToken = _storeGet('refreshToken');
      if (accessToken && refreshToken) {
        const tokenRequestParam: TokenType = {
          accessToken: accessToken,
          refreshToken: refreshToken,
        };

        AuthApi.reissue(tokenRequestParam)
          .then((response: AxiosResponse<ResponseDataType<TokenType>>) => {
            _storeSet(TOKEN.ACCESS_TOKEN, response.data.DATA.accessToken);
          })
          .catch(() => _storeRemoveToken());
      }
      break;
    default:
      break;
  }

  return error;
};
