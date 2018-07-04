import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './store';

// pages imports
import MainPageAdmin from './page/MainPageAdmin'

import registerServiceWorker from './registerServiceWorker';


ReactDOM.render(
  (
    <Provider store={store}>


      <BrowserRouter>
        <div>
          {/* here Auth */}

          <Switch>
            {/* here Routes */}
            <Route path='/home' component={MainPageAdmin}/>


          </Switch>


        </div>
      
      </BrowserRouter>

    </Provider>
  )
  
  
  , document.getElementById('root'));
registerServiceWorker();
