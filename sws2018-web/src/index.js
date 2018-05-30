import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware, compose } from 'redux';
import thunk from 'redux-thunk';
import { BrowserRouter, Route, Switch, Redirect } from 'react-router-dom';
// antd's style import test
// import 'antd/dist/antd.css'; this is useless config it in the package json
import { Button } from 'antd';
// import reducers of application
import reducers from './reducers';

// import pages in container folder
import PageLogin from './container/login/login';

// redux调试工具
const reduxDevTool = window.__REDUX_DEVTOOLS_EXTENSION__?window.__REDUX_DEVTOOLS_EXTENSION__():f=>f;
const store = createStore(reducers, compose(applyMiddleware(thunk), reduxDevTool));


function C1(){
  return <Button type='primary'>SHIT</Button>;
};
function C2(){
  return <Button type='ghost'>SHIT</Button>;
}


ReactDOM.render((
  <Provider stroe={store}>
    <BrowserRouter>
      <div>
        <Switch>
          <Route path="/login" component={PageLogin}></Route>
          <Route path="/b2" component={C2}></Route>
        </Switch>

      </div>
    </BrowserRouter>

  </Provider>
), document.getElementById('root'));
