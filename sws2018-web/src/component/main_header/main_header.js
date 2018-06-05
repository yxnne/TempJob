import React from 'react';
import { Layout, Row, Col, Avatar, Icon, Popconfirm } from 'antd';
import './main_header.css';
import logo from '../../swslogo.png';

const { Header } = Layout;
const appName = '智控手卫生物联网管理系统';
/**
 * 主要Header组件，包括：logo, sys-name, avatar & username
 * TODO Details
 */
class MainHeaderComponent extends React.Component{
  constructor(){
    super();
    this.state = {
      user:'Admin'
    };
    this.handleUserClick = this.handleUserClick.bind(this);
    this.handleLogoutClick = this.handleLogoutClick.bind(this);
  }

  // 用户按钮的点击处理
  handleUserClick(){
    console.log('handleUserClick');
  }
  // 登出按钮的点击处理
  handleLogoutClick(){
    console.log('handleLogoutClick');
  }

  render(){
    return (
      <Header className="main-header" >

        <Row gutter={8}>
          {/*logo*/}
          <Col span={2} >
            <img className='logo-img' src={logo} alt='Logo'/>
          </Col>

          {/*System Name Title*/}
          <Col span={18} >
            <h1>{appName}</h1>
          </Col>

          {/*User About*/}
          <Col span={4} >
            <Row >
              <Col span={16} >
                <div className='user-info' onClick={this.handleUserClick}>
                  <Avatar style={{ backgroundColor: '#00a2ae', verticalAlign: 'middle'}} size='small' >
                    {this.state.user}
                  </Avatar>
                  <span className='username-span'>ABC</span>
                </div>
              </Col>

              <Col span={8} >
                <Popconfirm placement="bottomRight" title='您确定要退出登录吗？'
                  onConfirm={this.handleLogoutClick} okText="Yes" cancelText="No">
                  <Icon type="logout" className='logoout-btn'
                    style={{color:'white', verticalAlign: 'middle', fontSize: 20}}/>
                </Popconfirm>

              </Col>
            </Row>

          </Col>
        </Row>
      </Header>
    );
  }

}
export default MainHeaderComponent;
