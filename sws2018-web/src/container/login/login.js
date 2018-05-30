import React from 'react';
import { Form, Icon, Input, Button, Checkbox, Card } from 'antd';
import LoginFormComponent from '../../component/login-form/login_form';
import MainHeaderComponent from '../../component/main_header/main_header';
import './login.css';
/**
 * 页面:处理登录
 */
class PageLogin extends React.Component{

  render(){
    return (
      <div>
        <MainHeaderComponent />
        <h1>12345</h1>
        <div className='login-mainform-container'>
          <Card style={{ width: '100%' }}>
            <LoginFormComponent />
          </Card>
        </div>
      </div>
    );
  };
}

export default PageLogin;
