import React from 'react';
import PropTypes from 'prop-types';
import { Button, Icon } from 'antd';
import QueueAnim from 'rc-queue-anim';
import TweenOne from 'rc-tween-one';
import OverPack from 'rc-scroll-anim/lib/ScrollOverPack';

import LogoGather from './animate_logo_gather';

import '../../less/banner.less';
import '../../less/home_content.less';
/*
Banner 组件 首页使用 pc mobile样式以适配
*/
class Banner extends React.Component {
  render() {
    //const props = {...this.props };
    //delete this.props.isMode;
    return (
      <OverPack replay playScale={[0.3, 0.1]} {...this.props}>
        <QueueAnim type={['bottom', 'top']} delay={200}  key="text"  id={`${this.props.id}-wrapper`}
        className={`${this.props.className}-wrapper`}>
          <span className="title" key="title"  id={`${this.props.id}-title`}>

              <img width="100%" src="images/logo_company_green_light_300_300.png" />
            {/*
            <LogoGather
              image="images/logo_company_300_300.png"
              pixSize={15}
              pointSizeMin={15}/>

            */}

          </span>

          <p key="content"  id={`${this.props.id}-content`} style={{marginTop:"16px"}}>
            高效医院内镜洗消追溯解决方案
          </p>

          <Button type="ghost" key="button" id={`${this.props.id}-button`} >
            进入系统
          </Button>

        </QueueAnim>

        <TweenOne animation={{ y: '-=20', yoyo: true, repeat: -1, duration: 1000 }}
           key="icon" className={`${this.props.className}-icon`} className={`${this.props.className}-icon`}>
          <Icon type="down" />
        </TweenOne>

      </OverPack>
    );
  }
}
Banner.propTypes = {
  className: PropTypes.string,
};
Banner.defaultProps = {
  className: 'banner0',
};


export default Banner;
