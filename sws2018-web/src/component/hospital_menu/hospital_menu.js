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
  }


  // componentDidMount(){
  //   console.log('this.props', this.props);
  // }


  render(){
    return (
      <Menu defaultSelectedKeys={['3']} defaultOpenKeys={['sub1']}
          mode='inline' theme={this.props.theme} style={{ lineHeight: '64px', height:'100%' }}>

          {

            this.props.menuInfos.map(info =>(

                  info.subMenus?(
                    <SubMenu title={<span><Icon type={info.icon} /><span>{info.title}</span></span>}>
                      {
                        info.subMenus.map(item =>(
                          <Menu.Item key={item.title} >
                            <Link to={item.linkPath}>
                              <span>{item.title}</span>
                            </Link>
                          </Menu.Item>
                        ))
                      }
                    </SubMenu>
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

        </Menu>
    );
  }
}

export default HospitalMenuComponent;
