import { compose, applyMiddleware, createStore, combineReducers } from 'redux';
import thunk from 'redux-thunk';
// reducers import
import {reducer as organizationStatistic } from './module/organization_statistic';

// combine the reducers
const reducers =  combineReducers({ organizationStatistic });

// middlewares
const middlewares = [thunk];

// add redux debug tool reduxDevTool
const reduxDevTool = window.__REDUX_DEVTOOLS_EXTENSION__?window.__REDUX_DEVTOOLS_EXTENSION__():f=>f;
// make reducers in use, and middleware thunk, reduxDevTool
export default createStore(reducers, compose(applyMiddleware(...middlewares), reduxDevTool));