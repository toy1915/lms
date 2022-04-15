import { AuthType } from '../../types/Auth.type';

const AUTHENTICATED = 'auth/authenticated' as const;
const UNAUTHENTICATED = 'auth/unauthenticated' as const;

export const authenticated = (auth: AuthType) => ({
  type: AUTHENTICATED,
  payload: auth,
});

export const unauthenticated = () => ({
  type: UNAUTHENTICATED,
});

type AuthAction = ReturnType<typeof authenticated> | ReturnType<typeof unauthenticated>;

const initialState = {};

const AuthReducer = (state = initialState, action: AuthAction) => {
  switch (action.type) {
    case AUTHENTICATED:
      return { ...state, ...action.payload };
    case UNAUTHENTICATED:
    default:
      return state;
  }
};

export default AuthReducer;
