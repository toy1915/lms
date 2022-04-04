import { AuthPayload, AuthState, AuthUser } from '@type/Auth.type';

const AUTHENTICATED = '[Auth] Authenticated' as const;
const UNAUTHENTICATED = '[Auth] Unauthenticated' as const;

export const authenticated = (authUser: AuthUser) => ({
  type: AUTHENTICATED,
  payload: { authUser },
});

export const unauthenticated = () => ({
  type: UNAUTHENTICATED,
});

type AuthAction = ReturnType<typeof authenticated> | ReturnType<typeof unauthenticated>;

const emptyAuthState: AuthPayload = {
  authUser: {} as AuthUser,
};

const initalState: AuthState = {
  isAuthenticated: false,
  ...emptyAuthState,
};

function AuthReducer(state: AuthState = initalState, action: AuthAction): AuthState {
  switch (action.type) {
    case AUTHENTICATED:
      return { ...state, isAuthenticated: true, ...action.payload };
    case UNAUTHENTICATED:
      return { ...state, isAuthenticated: false, ...emptyAuthState };
    default:
      return state;
  }
}

export default AuthReducer;
