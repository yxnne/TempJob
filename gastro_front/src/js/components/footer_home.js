import React from 'react';
import TweenOne from 'rc-tween-one';
import OverPack from 'rc-scroll-anim/lib/ScrollOverPack';

import '../../less/footer_home.less';
import '../../less/home_content.less';

/*
页脚组件 HomeFooter 首页使用
*/

class HomeFooter extends React.Component {

  render() {
    const props =  this.props ;
    delete props.isMode;
    return (<OverPack
      {...props}
      playScale={0.05}
    >
      <TweenOne
        animation={{ y: '+=30', opacity: 0, type: 'from' }}
        key="footer"
      >
        <span id={`${props.id}-content`}>
          Copyright © 2018 The Product by <a href="#">Hangzhou YiTongQuan Technology</a>. All Rights Reserved
        </span>
      </TweenOne>
    </OverPack>);
  }
}

HomeFooter.defaultProps = {
  className: 'footer_home',
};

export default HomeFooter;
