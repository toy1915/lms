import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios';
import { _storeGet } from '@utils/LocalStorage.utils';
import { TOKEN } from '@constants/Token';
import { ResponseType, ResultType } from '@type/Response.type';
import { TokenRequest } from '@type/Auth.type';
import { authApis } from '@apis/Auth.apis';

export const checkToken = async (config: AxiosRequestConfig) => {
  let accessToken = _storeGet(TOKEN.ACCESS_TOKEN);

  if (config.headers) config.headers['X-Authorization'] = 'Bearer ' + accessToken;
  return config;
};

export const customAxios: AxiosInstance = axios.create({
  headers: {
    'Content-type': 'application/json',
  },
});

customAxios.interceptors.request.use(checkToken);

const httpSuccessResponse = (response: AxiosResponse) => {
  const result: ResponseType = response.data;
  if (result && result.RESULT === ResultType.FAILURE) {
    // TODO 실패 액션
  }
  return response;
};

const httpErrorResponse = async (error: any) => {
  const originalRequest = error.config;
  if (error.response?.status == 500) {
    alert('서버 오류입니다. \r 관리자에게 문의하세요.');
  } else if (error.response?.status === 401) {
    if (!originalRequest._retry) {
      console.log('토큰 만료');
      originalRequest._retry = true;

      const accessToken = localStorage.getItem('accessToken');
      const refreshToken = localStorage.getItem('refreshToken');

      if (accessToken && refreshToken) {
        const tokenRequestParam: TokenRequest = {
          accessToken: accessToken,
          refreshToken: refreshToken,
        };

        const { data } = await authApis.reissue(tokenRequestParam);

        if (data) {
          originalRequest.headers['X-Authorization'] = 'Bearer ' + data.accessToken;
          localStorage.setItem('accessToken', data.accessToken);
          localStorage.setItem('refreshToken', data.refreshToken);
        }
      }

      return axios(originalRequest);
    } else {
      // TODO
    }
  }
  return Promise.reject(error);
};

customAxios.interceptors.response.use(
  (response: AxiosResponse) => httpSuccessResponse(response),
  (error) => httpErrorResponse(error),
);
