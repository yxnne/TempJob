import React from 'react';
import {Icon, Layout, Menu, Breadcrumb, Row, Col, Avatar} from 'antd';
import {BrowserRouter as Router, Route, Switch, Link} from 'react-router-dom'

//PagesComponent
import PCHomePage from './pc_modules/public_home_page';

import PCMonitorManualPage from './pc_modules/monitor_manual_page';

import PCManageGastroPage from './pc_modules/manage_gastro_page';
import PCManageDevicePage from './pc_modules/manage_device_page';
import PCManageStaffPage from './pc_modules/manage_staff_page';
import PCManageStationPage from './pc_modules/manage_station_page';
import PCManageFlowPage from './pc_modules/manage_flow_page';

import PCStatisticsUseratePage from './pc_modules/statistics_userate_page';
import PCStatisticsWorkPage from './pc_modules/statistics_work_page';
import PCStatisticsManualPage from './pc_modules/statistics_manual_page';
import PCStatisticsUseLogPage from './pc_modules/statistics_use_log_page';

import PCManageInfoInputPage from './pc_modules/sys_infoinput_page';
import PCSystemDeviceStatePage from './pc_modules/sys_devicestate_page';
import PCSystemUserManagePage from './pc_modules/sys_usermanage_page';
import PCSystemLoginlogPage from './pc_modules/sys_loginlog_page';

import PCRetrospectGastroPage from './pc_modules/retrospect_gastro';
import PCRetrospectStaffPage from './pc_modules/retrospect_staff';
import PCRetrospectPatientPage from './pc_modules/retrospect_patient';


// Menu
import MenuComponent from './components/menu_system';
const SubMenu = Menu.SubMenu;
const MenuItemGroup = Menu.ItemGroup;

const { Header, Footer, Sider, Content } = Layout;

class SystemComponent extends React.Component {  
    render() { 
        return (  
            <div>  
                {this.props.children}  
            </div>  
        )  
    }  
}  
/*
类   : PCIndex
作用 : PC端程序主体框架
*/
export default class PCIndex extends React.Component{

  render(){
    return (
      <div>
        <Router>
          <Switch>
            {/*首页*/}
            <Route path="/home" component={PCHomePage} />
            
            <Layout className="layout">

              {/*页头*/}
              <Header>
                <div className="logo" />
                <MenuComponent mode="horizontal" theme="dark" lineHeight="64px"/>
                <div className="avatar_container">
                  <Avatar icon="user" />
                </div>
              </Header>

              {/*内容*/}
              <Content style={{ padding: '0 50px' }}>

                <div className="pages_container" >
                {/*页面路由切换 Link标签在Menu中 */} 

                  {/*实时监控 模块 */}
                  <Route path="/monitor_manual" component={PCMonitorManualPage} />

                  {/*业务管理 模块 */}
                  <Route path="/manage_gastro" component={PCManageGastroPage} />
                  <Route path="/manage_device" component={PCManageDevicePage} />
                  <Route path="/manage_staff" component={PCManageStaffPage} />
                  <Route path="/manage_station" component={PCManageStationPage} />
                  <Route path="/manage_flow" component={PCManageFlowPage} />

                  {/*统计查询 模块 */}

                  <Route path="/statistics_work" component={PCStatisticsWorkPage} />
                  <Route path="/statistics_userate" component={PCStatisticsUseratePage} />
                  <Route path="/statistics_manual" component={PCStatisticsManualPage} />
                  <Route path="/statistics_uselog" component={PCStatisticsUseLogPage} />

                  {/*系统管理 模块 */}

                  <Route path="/sys_infoinput" component={PCManageInfoInputPage} />
                  <Route path="/sys_devicestate" component={PCSystemDeviceStatePage} />
                  <Route path="/sys_usermanage" component={PCSystemUserManagePage} />
                  <Route path="/sys_loginlog" component={PCSystemLoginlogPage} />

                  {/*内镜追溯 模块 */}
                  <Route path="/retrospect_gastro" component={PCRetrospectGastroPage} />
                  <Route path="/retrospect_staff" component={PCRetrospectStaffPage} />
                  <Route path="/retrospect_patient" component={PCRetrospectPatientPage} />
                  {/*内镜追溯 和移动端URL相对应上模块 */}
                  <Route path="/retrospect_gastro_detail" component={PCRetrospectGastroPage} />
                  
                </div>

              </Content>

              <Footer style={{ textAlign: 'center' }}>
                &copy;&nbsp;2018 Hangzhou YiTongQuan Technology Co., Ltd.,All Rights Reserved
              </Footer>

            </Layout>
          </Switch>
        </Router>
      </div>

    )
  }
}


