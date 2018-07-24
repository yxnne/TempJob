import React from 'react';
import ReactDOM from 'react-dom';

// 默认语言为 en-US，如果你需要设置其他语言，推荐在入口文件全局设置 locale
import moment from 'moment';
import 'moment/locale/zh-cn';
moment.locale('zh-cn');

// 响应式插件 MediaQuery
import  MediaQuery  from 'react-responsive';
// antd样式引入
// import 'antd/dist/antd.css';
import 'antd/dist/antd.css';

import PCIndex from './pc_index';
import MobileIndex from './mobile_index';
//入口
const AppEntry = ()=>(
  <div>
    {/* PC */}
    <MediaQuery query='(min-width: 1224px)'>
      <PCIndex/>
    </MediaQuery>

    {/* Moblie */}
    <MediaQuery query='(max-Width: 1224px)'>
      <MobileIndex/>
    </MediaQuery>
  </div>
);

ReactDOM.render(
  <AppEntry />,
  document.getElementById('root')
);
