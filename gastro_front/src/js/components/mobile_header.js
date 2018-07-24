import React from 'react';
import {Icon} from 'antd';

export default class MobileHeader extends React.Component{

  constructor(){
    super();
    this.state = {
      hasLogined:false
    };
  }

  login(){

  }

  logout(){

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
      <div id="mobileheader">
        <header>
          <img src="images/logo.png" alt="logo"/>
          <span>Name</span>
          {userShow}
        </header>
      </div>
    )
  }
}
