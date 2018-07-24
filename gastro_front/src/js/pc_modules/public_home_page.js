/* 本文件对应业务：内镜管理 */
import React from 'react';
import enquire from 'enquire.js';
import {Link} from 'react-router-dom';
import { BackTop} from 'antd';

import HomeHeader from '../components/menu_home';
import Banner from '../components/banner';
import FeatureIntroLeftContent from '../components/feature_intro_left';
import FeatureIntroRightContent from '../components/feature_intro_right';
import FunctionIntroContent from '../components/fuctions_intro';
import HomeFooter from '../components/footer_home';

/*
组件 : PCHomePage
作用 : pc 和 mobile共同的首页
*/
export default class PCHomePage extends React.Component{
  constructor() {
    super();
    this.state = {
      // isPC true 代表pc端 ,false 代码移动端
      isPC: false
    };
    this.scrollToAnchor = this.scrollToAnchor.bind(this);
  }
  componentDidMount() {
    // 适配手机屏幕;
    this.enquireScreen((isPC) => {
      console.log(this.state.isPC);
      this.setState({ isPC });
    });
  }

  enquireScreen (cb) {
    /* eslint-disable no-unused-expressions */
    enquire.register('only screen and (min-width: 320px) and (max-width: 767px)', {
      match: () => {
        cb && cb(true);
      },
      unmatch: () => {
        cb && cb();
      },
    });
    /* eslint-enable no-unused-expressions */
  }

  //实现锚点
  scrollToAnchor(anchor){
        let anchorElement = document.getElementById(anchor);
        // 如果对应id的锚点存在，就跳转到锚点
        if(anchorElement) {
          //console.log(test);
          anchorElement.scrollIntoView();
        }

  }

  render(){

    return (
        <div className="templates-wrapper" >
          <a id="main"/>
          <HomeHeader id="nav_0_0" key="nav_0_0" device_mode={this.state.isPC?"pc":"mobile"}
          tofunction={this.scrollToAnchor}/>
          <Banner id="content_0_0" key="content_0_0" device_mode={this.state.isPC?"pc":"mobile"}/>
          <a id="feature"/>
          <FeatureIntroLeftContent id="content_1_0" key="content_1_0" device_mode={this.state.isPC?"pc":"mobile"} />
          <FeatureIntroRightContent id="content_2_0" key="content_2_0" device_mode={this.state.isPC?"pc":"mobile"} />
          <FunctionIntroContent id="content_3_0" key="content_3_0" device_mode={this.state.isPC?"pc":"mobile"} />
          <a id="functions"/>
          <HomeFooter id="content_4_0" key="content_4_0" device_mode={this.state.isPC?"pc":"mobile"} />
          <BackTop />
        </div>
    )
  }
}
