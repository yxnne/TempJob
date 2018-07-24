import React from 'react';
import QueueAnim from 'rc-queue-anim';
import TweenOne from 'rc-tween-one';
import OverPack from 'rc-scroll-anim/lib/ScrollOverPack';

import '../../less/feature_intro_right.less';
import '../../less/home_content.less';

/*
FeatureIntroRightContent 组件 首页使用 pc mobile样式以适配
*/

class FeatureIntroRightContent extends React.Component {

  render() {
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
        queue:  'left',
        one:  { x: '+=30', opacity: 0, type: 'from' },
      };

    }

    return (
      <div
        {... this.props}
        className={`content-template-wrapper content-half-wrapper ${ this.props.className}-wrapper`}
      >
        <OverPack
          className={`content-template ${ this.props.className}`}
          location={ this.props.id}
        >
          <QueueAnim
            type={animType.queue}
            className={`${ this.props.className}-text`}
            key="text"
            leaveReverse
            ease={['easeOutCubic', 'easeInCubic']}
            id={`${ this.props.id}-textWrapper`}
          >
            <h1 key="h1" id={`${ this.props.id}-title`}>
              大数据分析统计
            </h1>
            <p key="p" id={`${ this.props.id}-content`}>
              一站式、全周期大数据协同工作平台，PB级数据处理、毫秒级数据分析工具。统计分析各项数据，先进大数据算法支持。数据和统计一目了然，方便应对各种查询和展示情景。

            </p>
          </QueueAnim>
          <TweenOne
            key="img"
            animation={animType.one}
            className={`${ this.props.className}-img`}
            id={`${ this.props.id}-imgWrapper`}
            resetStyleBool
          >
            <span id={`${ this.props.id}-img`}>
              <img width="100%" src="https://zos.alipayobjects.com/rmsportal/tvQTfCupGUFKSfQ.png" />
            </span>
          </TweenOne>
        </OverPack>
      </div>
    );
  }
}

FeatureIntroRightContent.defaultProps = {
    className: 'feature_intro_content_right',
  };

export default FeatureIntroRightContent;
