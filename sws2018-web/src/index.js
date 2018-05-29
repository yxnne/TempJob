import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import registerServiceWorker from './registerServiceWorker';
// test antd
import { Button } from 'antd';
import 'antd/dist/antd.css';




ReactDOM.render(<Button type="primary" >Test Antd is ok??</Button>, document.getElementById('root'));
registerServiceWorker();
