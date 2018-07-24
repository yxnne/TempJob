import React from 'react';
import PropTypes from 'prop-types';
import TweenOne from 'rc-tween-one';
import { Menu } from 'antd';

const Item = Menu.Item;

import '../../less/header_home.less';
import '../../less/home_content.less';

class HomeHeader extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      phoneOpen: false,
    };
  }

  phoneClick() {
    this.setState({
      phoneOpen: !this.state.phoneOpen,
    });
  }

  handleClick  (e)  {

    //console.log('click ', e.key);
    if (e.key == "0"){
      this.props.tofunction("main");
    } else if (e.key == 1) {
      this.props.tofunction("feature");
    } else if (e.key == "2") {
      this.props.tofunction("functions");
    }

  }
  render() {
    const props =   this.props ;
    const isMode = props.isMode;
    //delete props.isMode;
    const navData = { menu1: '首页', menu2: '特点', menu3: '功能', menu4: '进入系统' };;
    const navChildren = Object.keys(navData)
      .map((key, i) => (<Item key={i}>{navData[key]}</Item>));

    let menu = null;
    if (this.props.device_mode == "pc") {
      menu = (
        <div
        className={`${this.props.className}-phone-nav${this.state.phoneOpen ? ' open' : ''}`}
        id={`${this.props.id}-menu`}
      >
        <div
          className={`${this.props.className}-phone-nav-bar`}
          onClick={() => {
            this.phoneClick();
          }}
        >
          <em />
          <em />
          <em />
        </div>
        <div
          className={`${this.props.className}-phone-nav-text`}
        >
          <Menu
            defaultSelectedKeys={['0']}
            mode="inline"
            theme="dark"
            onClick={this.handleClick.bind(this)}
          >
            {navChildren}
          </Menu>
        </div>
      </div>
      ) ;
    } else if (this.props.device_mode == "mobile"){
      menu = (
        <TweenOne
        className={`${this.props.className}-nav`}
        animation={{ x: 30, type: 'from', ease: 'easeOutQuad' }}
      >
        <Menu
          mode="horizontal" defaultSelectedKeys={['0']}
          id={`${this.props.id}-menu`}
          onClick={this.handleClick.bind(this)}
        >
          {navChildren}
        </Menu>
      </TweenOne>
      );
    }
    return (
      <TweenOne
        component="header"
        animation={{ opacity: 0, type: 'from' }}
       {...props}>
      <TweenOne
        className={`${this.props.className}-logo`}
        animation={{ x: -30, type: 'from', ease: 'easeOutQuad' }}
        id={`${this.props.id}-logo`}>
        <img width="24%" src="images/logo_company_green_300_300.png" />
      </TweenOne>

      {menu}

    </TweenOne>);
  }
}

HomeHeader.propTypes = {
  className: PropTypes.string,
  dataSource: PropTypes.object,
  id: PropTypes.string,
};

HomeHeader.defaultProps = {
  className: 'header_home',
};

export default HomeHeader;
