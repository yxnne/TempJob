import React from 'react';
import { Layout } from 'antd';
import './main_header.css';
const { Header } = Layout;

/**
 * 主要Header组件，包括：logo, sys-name, avatar & username
 * TODO Details
 */
class MainHeaderComponent extends React.Component{

  render(){
    return (
      <Header className="main-header" >
        <div className="logo" >

        </div>
        <h1>Title</h1>

        <div>Test</div>

      </Header>
    );
  }

}
export default MainHeaderComponent;
