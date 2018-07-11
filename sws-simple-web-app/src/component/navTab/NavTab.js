import React from 'react';
import { TabBar } from 'antd-mobile';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router-dom';
/**
 * TabNav, Component in the Screen bottom for navgation
 */
 @withRouter
class NavTab extends React.Component{

  // data props pass here must be an array
  static propTypes = {
    data:PropTypes.array.isRequired
  };

  render(){
    const navList = this.props.data;
    const { pathname } = this.props.location;

    return (
      <TabBar unselectedTintColor="#949494"
        tintColor="#33A3F4"
        barTintColor="white">
        {
          navList.map((i)=>{
            return (
              <TabBar.Item title={i.title} key={i.path}
                icon={{uri:require(`./image/${i.icon}.svg`)}}
                selectedIcon={{uri:require(`./image/${i.icon}-select.svg`)}}
                selected={pathname === i.path}
                onPress={()=>{
                  this.props.history.push(i.path)
                }}>
              </TabBar.Item>
            )
          })
        }

      </TabBar>
    );
  }
}

export default NavTab;