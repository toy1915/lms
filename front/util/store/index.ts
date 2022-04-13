import {applyMiddleware, combineReducers, createStore} from "redux";
import DialogReducer from "./Dialog.reducer";
import {createWrapper, HYDRATE} from "next-redux-wrapper";

const rootReducer = combineReducers({
    DialogReducer
});

const reducer = (state:any, action:any) => {
    if(action.type == HYDRATE){
        const nextState = {
            ...state, ...action.payload
        };
        return nextState;
    }
    return rootReducer(state, action);
}

export type RootState = ReturnType<typeof rootReducer>;

const bindMiddleware = (middleware: any) => {
    if(process.env.NODE_ENV !== "production"){
        const {composeWithDevTools} = require("redux-devtools-extension");
        return composeWithDevTools(applyMiddleware(...middleware));
    }
    return applyMiddleware(...middleware);
}

const initStore = () => {
    return createStore(reducer, bindMiddleware([]));
}

export const wrapper = createWrapper(initStore);