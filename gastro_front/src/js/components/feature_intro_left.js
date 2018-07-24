import React from 'react';
import QueueAnim from 'rc-queue-anim';
import TweenOne from 'rc-tween-one';
import OverPack from 'rc-scroll-anim/lib/ScrollOverPack';

import '../../less/feature_intro_left.less';
import '../../less/home_content.less';

/*
FeatureIntroLeftContent 组件 首页使用 pc mobile样式以适配
*/
class FeatureIntroLeftContent extends React.Component {

  render() {
    //const props = { ...this.props };
    const isMode = this.props.device_mode;

    let animType ={};
    //delete props.isMode;
    if ( isMode =="pc"){
      animType = {
        queue: 'bottom' ,
        one:  { y: '+=30', opacity: 0, type: 'from' },
      };

    } else if (isMode =="pc"){
      animType = {
        queue:  'right',
        one:  { x: '-=30', opacity: 0, type: 'from' },
      };

    }

    return (
      <div
        {...this.props}
        className={`content-template-wrapper content-half-wrapper ${this.props.className}-wrapper`}
      >
        <OverPack
          className={`content-template ${this.props.className}`}
          location={this.props.id}
        >
          <TweenOne
            key="img"
            animation={animType.one}
            className={`${this.props.className}-img`}
            id={`${this.props.id}-imgWrapper`}
            resetStyleBool
          >
            <span id={`${this.props.id}-img`}>
              <img width="100%" src="https://zos.alipayobjects.com/rmsportal/nLzbeGQLPyBJoli.png" />
            </span>
          </TweenOne>
          <QueueAnim
            className={`${this.props.className}-text`}
            type={animType.queue}
            key="text"
            leaveReverse
            ease={['easeOutCubic', 'easeInCubic']}
            id={`${this.props.id}-textWrapper`}
          >
            <h1 key="h1" id={`${this.props.id}-title`}>
              云系统内镜管理
            </h1>
            <p key="p" id={`${this.props.id}-content`}>
              针对各个医院，各个科室，乃至以及各个洗消间内镜资源统一追溯管理。将硬件采集到的数据在云资源集中编排、弹性伸缩、持续发布和部署，高可用及容灾。
            </p>
          </QueueAnim>
        </OverPack>
      </div>
    );
  }
}

FeatureIntroLeftContent.defaultProps = {
    className: 'feature_intro_content',
  };

export default FeatureIntroLeftContent;
