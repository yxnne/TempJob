import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './store';
import './index.css';
// pages imports
import AdminMainHospitalPage from './page/AdminMainHospitalPage';
import AdminMainTabBar from './page/AdminMainTabBar'

import registerServiceWorker from './registerServiceWorker';


ReactDOM.render(
  (
    <Provider store={store}>


      <BrowserRouter>
        <div>
          {/* here Auth */}

          <Switch>
            {/* here Routes */}
            <Route path='/home' component={AdminMainHospitalPage}/>

            
            {/* to Tabs Navigation */}
            <Route component={AdminMainTabBar}/>
          </Switch>


        </div>
      
      </BrowserRouter>

    </Provider>
  )
  
  
  , document.getElementById('root'));
registerServiceWorker();
