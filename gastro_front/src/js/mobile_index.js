import React from 'react';
import {Icon, Layout, Avatar} from 'antd';
import {BrowserRouter as Router, Route, Switch, Link} from 'react-router-dom'
//Menu
import MenuComponent from './components/menu_system';
import LogoTitleComponent from './components/logo_title';
//Biz pages
import PCHomePage from './pc_modules/public_home_page';

import MobileMonitorManualPage from './mobile_modules/monitor_manual_page';

import MobileManageGastroPage from './mobile_modules/manage_gastro_page';
import MobileManageStaffPage from './mobile_modules/manage_staff_page';
import MobileManageDevicePage from './mobile_modules/manage_device_page';
import MobileManageStationPage from './mobile_modules/manage_station_page';
import MobileManageFlowPage from './mobile_modules/manage_flow_page';

import MobileStatisticsWorkPage from './mobile_modules/statistics_work_page';
import MobileStatisticsUseratePage from './mobile_modules/statistics_userate_page';
import MobileStatisticsManualPage from './mobile_modules/statistics_manual_page';
import MobileStatisticsUseLogPage from './mobile_modules/statistics_use_log_page';

import MobileInfoInputPage from './mobile_modules/sys_infoinput_page';
import MobileSystemDeviceStatePage from './mobile_modules/sys_devicestate_page';
import MobileSystemUserManagePage from './mobile_modules/sys_usermanage_page';
import MobileSystemLoginlogPage from './mobile_modules/sys_loginlog_page';

import MobileRetrospectGastroPage from './mobile_modules/retrospect_gastro_page';
import MobileRetrospectStaffPage from './mobile_modules/retrospect_staff_page';
import MobileRetrospectPatientPage from './mobile_modules/retrospect_patient_page';
import MobileRetrospectGastroDetailPage from './mobile_modules/retrospect_gastro_detail_page';

const { Header, Footer, Sider, Content } = Layout;
/*
类   : MobileIndex
作用 : Mobile端的主体框架
*/
export default class MobileIndex extends React.Component{

  constructor(){
    super();
    this.state = {
      collapsed : true,
      hasLogined:false
    };
  }


  login(){

  }

  logout(){

  }

  toggle(){
    this.setState({
      collapsed: !this.state.collapsed,
    });
  }
  render(){
    const userShow = this.state.hasLogined?
    <div>
    {/*<Link>*/}
      <Icon type="logout" onClick={this.logout.bind(this)} />
      <Icon type="user" />
    {/*</Link>*/}
    </div>
    :
    <Icon type="setting" onClick={this.login.bind(this)} />

    return (
      <div >
        <Router>
        
        <Switch>
          {/*首页 PCHomePage*/}
          <Route path="/home" component={PCHomePage} />
          
          <Layout >

            {/*左_侧边栏 放个Menu*/}
            <Sider breakpoint="lg" collapsedWidth="0" trigger={null} collapsed={this.state.collapsed}>
              <div id="logo_in_slide_container">
                <Avatar icon="user" />
              </div>
              {/*Menu*/}
              <MenuComponent  mode="inline" theme="dark" chooseSubCallback={this.toggle.bind(this)}/>
            </Sider>

            {/*右_侧 页面主体*/}
            <Layout>

              {/*页头 */}
              <Header id="mobile_header">
                <Icon id="trigger" type={this.state.collapsed ? 'menu-unfold' : 'menu-fold'} onClick={this.toggle.bind(this)}/>
                <LogoTitleComponent />
              </Header>

              {/*内容*/}
              <Content>
                {/*页面路由切换 Link标签在Menu中 */}
                
                    
                    {/*实时监控 模块 */}

                    <Route path="/monitor_manual" component={MobileMonitorManualPage} />

                    {/*业务管理 模块 */}
                    <Route path="/manage_gastro" component={MobileManageGastroPage} />
                    <Route path="/manage_device" component={MobileManageDevicePage} />
                    <Route path="/manage_staff" component={MobileManageStaffPage} />
                    <Route path="/manage_station" component={MobileManageStationPage} />
                    <Route path="/manage_flow" component={MobileManageFlowPage} />

                    {/*统计查询 模块 */}
                    <Route path="/statistics_work" component={MobileStatisticsWorkPage} />
                    <Route path="/statistics_userate" component={MobileStatisticsUseratePage} />
                    <Route path="/statistics_manual" component={MobileStatisticsManualPage} />
                    <Route path="/statistics_uselog" component={MobileStatisticsUseLogPage} />

                    {/*系统管理 模块 */}
                    <Route path="/sys_infoinput" component={MobileInfoInputPage} />
                    <Route path="/sys_devicestate" component={MobileSystemDeviceStatePage} />
                    <Route path="/sys_usermanage" component={MobileSystemUserManagePage} />
                    <Route path="/sys_loginlog" component={MobileSystemLoginlogPage} />

                    {/*内镜追溯 模块 */}

                    <Route path="/retrospect_gastro" component={MobileRetrospectGastroPage} />
                    <Route path="/retrospect_staff" component={MobileRetrospectStaffPage} />
                    <Route path="/retrospect_patient" component={MobileRetrospectPatientPage} />
                    <Route path="/retrospect_gastro_detail" component={MobileRetrospectGastroDetailPage} />

                


              </Content>

              {/*页脚 */}
              <Footer id="mobile_footer">
                &copy;&nbsp;2018 Hangzhou YiTongQuan Technology Co., Ltd.,All Rights Reserved
              </Footer>
            </Layout>

          </Layout>
          </Switch>
        </Router>
      </div>

    )
  }
}
