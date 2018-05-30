import React from 'react';
import { Menu, Icon } from 'antd';
import { withRouter, Link } from 'react-router-dom';

const { SubMenu } = Menu;
/**
 * Hospital的主Menu
 */
 @withRouter
class HospitalMenuComponent extends React.Component{

  constructor(){
    super();
    this.handleMenuItemClick = this.handleMenuItemClick.bind(this);

  }

  handleMenuItemClick(path){
    // this.props.history.push(path);
    console.log(path)
  }

  componentDidMount(){
    console.log('this.props', this.props);
  }


  render(){
    return (
      <Menu defaultSelectedKeys={['3']} defaultOpenKeys={['sub1']}
          mode='inline' theme={this.props.theme} style={{ lineHeight: '64px', height:'100%' }}>

          {

            this.props.menuInfos.map((info) =>(

                  info.subMenus?(
                    <div />
                  ):(
                    <Menu.Item key={info.title} >
                      <Link to={info.linkPath}>
                        <Icon type={info.icon} />
                        <span>{info.title}</span>
                      </Link>
                    </Menu.Item>

                  )

            ))

          }


          <Menu.Item key="1">
            <Icon type="mail" />
            <span>Option 1</span>
          </Menu.Item>

          <Menu.Item key="2">
            <Icon type="calendar" />
            <span>Option 1</span>
          </Menu.Item>

          <SubMenu key="sub1" title={<span><Icon type="appstore" /><span>Navigation Three</span></span>}>
            <Menu.Item key="3">Option 3</Menu.Item>
            <Menu.Item key="4">Option 4</Menu.Item>
            <SubMenu key="sub1-2" title="Submenu">
              <Menu.Item key="5">Option 5</Menu.Item>
              <Menu.Item key="6">Option 6</Menu.Item>
            </SubMenu>
          </SubMenu>

          <SubMenu key="sub2" title={<span><Icon type="setting" /><span>Navigation Four</span></span>}>
            <Menu.Item key="7">Option 7</Menu.Item>
            <Menu.Item key="8">Option 8</Menu.Item>
            <Menu.Item key="9">Option 9</Menu.Item>
            <Menu.Item key="10">Option 10</Menu.Item>
          </SubMenu>

        </Menu>
    );
  }
}

export default HospitalMenuComponent;
