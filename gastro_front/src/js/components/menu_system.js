import React from 'react';
import {Menu } from 'antd';

import {Link ,BrowserRouter as Router} from 'react-router-dom';
// Menu
const SubMenu = Menu.SubMenu;
const MenuItemGroup = Menu.ItemGroup;

/*
组件 : MenuComponent
作用 : 提供页面的主体 Menu 导航,该组件PC端和移动端通用,表现形式可能有所不同
备注 :
  当前封装的 props 有：
  mode    -> 切换模式，值有：[vertical , inline]等
  theme   -> 切换主题，一般是 light 和 dark
*/
export default class MenuComponent extends React.Component{
  constructor(){
    super();
    this.state={
      current : "main"
    };
  }

  //选择菜单项目
  onMenuClick(event){
    this.setState({
      current : event.key
    });
    //这里是选定之后的回调，this.props.chooseSubCallback传递了一个函数
    //其实上层组件的Side的toggle方法
    if(this.props.chooseSubCallback ){
      this.props.chooseSubCallback();
    }

  }

  //子菜单点击的回调
  //这里主要是控制下，保证菜单展开每次只展开一组
  //openKeys就是每次点击展开之后所有需要被展开的菜单的key
  //目前这个方法适用于两级菜单，对本项目来说应该够用
  onSubMenuOpenChange(openKeys){
    if(openKeys.length > 1) {
      openKeys.splice(0, 1);
    }
  }

  render(){
    const menuStyle = {
      lineHeight:this.props.lineHeight,
      float:"left"
    };

    return (
      <Menu theme="light" mode={this.props.mode} theme={this.props.theme} style={menuStyle}
        selectedKeys={[this.state.current]} onClick={this.onMenuClick.bind(this)}
        onOpenChange={this.onSubMenuOpenChange.bind(this)}>

        <Menu.Item key="home"> <Link to={'/home'}>首页</Link></Menu.Item>

        <SubMenu title="实时监控">
          <Menu.Item key="monitor_manual"><Link to={'/monitor_manual'}>手工洗消</Link></Menu.Item>
          <Menu.Item key="monitor_auto" disabled>自动洗消</Menu.Item>
        </SubMenu>

        <SubMenu title="业务管理">
          <Menu.Item key="manage_gastro"><Link to={'/manage_gastro'}>内镜管理</Link></Menu.Item>
          <Menu.Item key="manage_device"><Link to={'/manage_device'}>设备管理</Link></Menu.Item>
          <Menu.Item key="manage_staff"><Link to={'/manage_staff'}>职工管理</Link></Menu.Item>
          <Menu.Item key="manage_flow"><Link to={'/manage_flow'}>流程管理</Link></Menu.Item>
          <Menu.Item key="manage_depart" disabled>科室管理</Menu.Item>
          <Menu.Item key="manage_station"><Link to={'/manage_station'}>工作站管理</Link></Menu.Item>
        </SubMenu>

        <SubMenu title="统计查询">
          <Menu.Item key="statistics_userate"><Link to={'/statistics_userate'}>内镜使用率</Link></Menu.Item>
          <Menu.Item key="statistics_work"><Link to={'/statistics_work'}>工作量统计</Link></Menu.Item>
          <Menu.Item key="statistics_score" disabled>人员绩效考核</Menu.Item>
          <Menu.Item key="statistics_manual"><Link to={'/statistics_manual'}>手工洗消日志</Link></Menu.Item>
          <Menu.Item key="statistics_auto" disabled>自动洗消日志</Menu.Item>
          <Menu.Item key="statistics_uselog"><Link to={'/statistics_uselog'}>内镜使用日志</Link></Menu.Item>
        </SubMenu>

        <SubMenu title="系统管理">
          <Menu.Item key="sys_infoinput"><Link to={'/sys_infoinput'}>信息登记</Link></Menu.Item>
          <Menu.Item key="sys_devicestate"><Link to={'/sys_devicestate'}>设备状态</Link></Menu.Item>
          <Menu.Item key="sys_usermanage"><Link to={'/sys_usermanage'}>用户管理</Link></Menu.Item>
          <Menu.Item key="sys_authority" disabled>权限管理</Menu.Item>
          <Menu.Item key="sys_loginlog"><Link to={'/sys_loginlog'}>登录日志</Link></Menu.Item>
        </SubMenu>

        <SubMenu title="内镜追溯">
          <Menu.Item key="retrospect_gastro"><Link to={'/retrospect_gastro'}>内镜详细信息</Link></Menu.Item>
          <Menu.Item key="retrospect_staff"><Link to={'/retrospect_staff'}>人员详细信息</Link></Menu.Item>
          <Menu.Item key="retrospect_patient"><Link to={'/retrospect_patient'}>患者详细信息</Link></Menu.Item>
        </SubMenu>

        <Menu.Item key="sws" disabled>
          手卫生统计
        </Menu.Item>
      </Menu>
    );
  }
}
