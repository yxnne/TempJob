import { compose, applyMiddleware, createStore, combineReducers } from 'redux';
import thunk from 'redux-thunk';
// reducers import


// combine the reducers
const reducers =  combineReducers({  });

// add redux debug tool reduxDevTool
const reduxDevTool = window.__REDUX_DEVTOOLS_EXTENSION__?window.__REDUX_DEVTOOLS_EXTENSION__():f=>f;
// make reducers in use, and middleware thunk, reduxDevTool
export default createStore(reducers, compose(applyMiddleware(thunk), reduxDevTool));