import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './store';
import './index.css';
// pages imports
import AdminMainDepartmentPage from './page/AdminMainDepartmentPage';
import AdminMainTabBar from './page/AdminMainTabBar'

import * as constant from './constant';

import registerServiceWorker from './registerServiceWorker';


ReactDOM.render(
  (
    <Provider store={store}>


      <BrowserRouter>
        <div>
          {/* here Auth */}

          <Switch>
            {/* here Routes */}
            
            <Route path={`${constant.PATH_DEPARTMENT_OVERALL}/:rank`} component={AdminMainDepartmentPage}/>
            
            {/* to Tabs Navigation */}
            <Route component={AdminMainTabBar}/>
          </Switch>


        </div>
      
      </BrowserRouter>

    </Provider>
  )
  
  
  , document.getElementById('root'));
registerServiceWorker();
