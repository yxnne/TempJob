import React from 'react';
import { Layout, Icon } from 'antd';
import { Switch, Route } from 'react-router-dom';
import MainHeaderComponent from '../../component/main_header/main_header';
import HospitalMenuComponent from '../../component/hospital_menu/hospital_menu';
import './hospital_dashboard.css';

import { getStaticMenusInfos } from './static_menu_config';

const { Content, Sider } = Layout;

// 测试
function T1(){
  return (
    <div>T1 Comp</div>
  );
}

function T2(){
  return (
    <div>XXXXX Comp</div>
  );
}

// 得到Menu信息对象
const menuInfos = getStaticMenusInfos();

/**
 * 页面，进入系统后的页面主框架
 * 包含Header Menu in Sider 以及 Content
 */
class PageHospitalDashboard extends React.Component{
  constructor(){
    super();

    this.state = {
      collapsed:false,
      menuTheme:'light'
    };
    this.toggle = this.toggle.bind(this);
  }

  toggle(){
    this.setState({
      menuTheme: this.state.collapsed?"light":"dark",
      collapsed: !this.state.collapsed,

   });
  }

  render(){
    return (
      <Layout style={{ background: '#333'}}>
        <MainHeaderComponent />

        <Layout>
          <Sider trigger={null} collapsible collapsed={this.state.collapsed}
            style={{backgroundColor:'transparent'}}>
            <HospitalMenuComponent theme={this.state.menuTheme} menuInfos={menuInfos}/>
          </Sider>

          {/* 这个Icon是控制Menu伸缩的*/}
          <div style={{background:'white'}}>
            <Icon className="menu-collapsed-trigger"
              type={this.state.collapsed ? 'menu-unfold' : 'menu-fold'}
              onClick={this.toggle}
            />
          </div>


          <Content style={{ background: '#fff', padding: 24, margin: 0, minHeight: 1000 }}>
            <Switch>
              <div>
                <Route path="/dashboard/p" component={T1}></Route>
                <Route path="/q" component={T2}></Route>

              </div>


            </Switch>
          </Content>
        </Layout>


      </Layout>
    );
  }

}

export default PageHospitalDashboard;
