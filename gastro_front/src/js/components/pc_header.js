import React from 'react';
import {Row , Col} from 'antd';
import { Menu, Icon } from 'antd';

// Menu
import MenuComponent from './menu';
const SubMenu = Menu.SubMenu;
const MenuItemGroup = Menu.ItemGroup;

export default class PCHeader extends React.Component{

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
  }

  render(){

    //背景色 样式
    const bg_color = {
      backgroundColor: this.props.bg_color,
    };

    return (
      <div style={bg_color}>
        <Row>
          <Col span={2}></Col>
          <Col span={5}>
            <a href="/" className="logo">
              {/*js打包了之后的bundle.js是在src下面 所以注意这个资源路径*/}
              <img src="images/logo.png" alt="logo" />
              <span>系统名字</span>
            </a>
          </Col>
          <Col span={15} >
            <MenuComponent  current={this.state.current} onMenuClick={this.onMenuClick.bind(this)}
            mode="horizontal" theme="dark"/>
          </Col>
          <Col span={2}></Col>
        </Row>

      </div>
    )
  }
}
