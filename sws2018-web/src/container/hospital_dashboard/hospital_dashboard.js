import React from 'react';
import { Layout, Icon } from 'antd';
import { Switch, Route } from 'react-router-dom';
// import Header and Menu
import MainHeaderComponent from '../../component/main_header/main_header';
import HospitalMenuComponent from '../../component/hospital_menu/hospital_menu';
import AppFooter from '../../component/footer/app_footer';
// import Pages for Menu Click
import PageEventCheck from '../event_check/event_check';
import PageHospitalOverall from '../hospital_overall/hospital_overall';
import PageDeviceStatusCheck from '../device_status_check/device_status_check';
import PageBizManage from '../biz_manage/biz_manage';
import PageStatisticDepartmentRate from '../statistic_department_rate/statistic_department_rate';
import PageStatisticLiquid from '../statisc_liquid_quantity/statisc_liquid_quantity';
import PageApCheckManage from '../ap_check_manage/ap_check_manage';
import PageDeviceQuickCheck from '../device_quick_check/device_quick_check';
import PageUserManage from '../user_manage/user_manage';
import PageSetting from '../setting_system/setting_system';

import './hospital_dashboard.css';

import { getStaticMenusInfos, getPagePaths } from './static_menu_config';

const { Content, Sider, Footer } = Layout;
const paths = getPagePaths();
// 测试
// function T1(){
//   return (
//     <div>T1 Comp</div>
//   );
// }
//
// function T2(){
//   return (
//     <div>XXXXX Comp</div>
//   );
// }

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
      menuTheme:'light',
      menuToggleShow:'hidden', //控制menu的那个按钮的显示或者隐藏状态
    };
    this.toggle = this.toggle.bind(this);
  }

  // 点击处理Menu显示或者隐藏
  toggle(){
    this.setState({
      menuTheme: this.state.collapsed?"light":"light",
      collapsed: !this.state.collapsed,

   });
  }

  // 控制toggleBtn的显示和隐藏
  showToggleBtn(){
    this.setState({
      menuToggleShow:'visible'
    });
  }

  hideToggleBtn(){
    this.setState({
      menuToggleShow:'hidden'
    });
  }

  render(){
    return (
      <Layout style={{ background: '#333'}}>
        <MainHeaderComponent />

        <Layout>
          <Sider trigger={null} collapsible collapsed={this.state.collapsed}
            onMouseOver={()=>this.showToggleBtn()} onMouseOut={()=>this.hideToggleBtn()}
            style={{backgroundColor:'transparent'}}>
            <HospitalMenuComponent theme={this.state.menuTheme} menuInfos={menuInfos}/>
          </Sider>

          <Content style={{ margin: 0, maxHeight: 1016 }}>

            {/* 这个Icon是控制主Menu伸缩的 */}
            <div className='trigger-container'
              onMouseOver={()=>this.showToggleBtn()} onMouseOut={()=>this.hideToggleBtn()}>
              <Icon className="menu-collapsed-trigger" style={{visibility:this.state.menuToggleShow}}
                type={this.state.collapsed ? 'menu-unfold' : 'menu-fold'}
                onClick={this.toggle}
              />
            </div>

            {/* 主体内容在卡片内切换*/}
            <Switch>
              <div>
                <Route path={paths.hospitalOverall} component={PageHospitalOverall}></Route>
                <Route path={paths.eventCheck} component={PageEventCheck}></Route>
                <Route path={paths.deviceStatusCheck} component={PageDeviceStatusCheck}></Route>
                <Route path={paths.bizManage} component={PageBizManage}></Route>
                <Route path={paths.statisticDepartmentRate} component={PageStatisticDepartmentRate}></Route>
                <Route path={paths.statisticLiquid} component={PageStatisticLiquid}></Route>
                <Route path={paths.deviceQuickCheck} component={PageDeviceQuickCheck}></Route>
                <Route path={paths.apManage} component={PageApCheckManage}></Route>
                <Route path={paths.userManage} component={PageUserManage}></Route>
                <Route path={paths.setting} component={PageSetting}></Route>

              </div>
            </Switch>

            <AppFooter />

          </Content>

        </Layout>
      </Layout>
    );
  }

}

export default PageHospitalDashboard;
