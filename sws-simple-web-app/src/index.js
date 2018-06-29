import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware, compose } from 'redux';
import thunk from 'redux-thunk';
import reducers from './reducers';

import registerServiceWorker from './registerServiceWorker';

// add redux debug tool reduxDevTool
const reduxDevTool = window.__REDUX_DEVTOOLS_EXTENSION__?window.__REDUX_DEVTOOLS_EXTENSION__():f=>f;
// make reducers in use, and middleware thunk, reduxDevTool
const store = createStore(reducers, compose(applyMiddleware(thunk), reduxDevTool));
ReactDOM.render(
  (
    <Provider store={store}>


      <BrowserRouter>
        <div>
          {/* here Auth */}

          <Switch>
            {/* here Routes */}


          </Switch>


        </div>
      
      </BrowserRouter>

    </Provider>
  )
  
  
  , document.getElementById('root'));
registerServiceWorker();
