import React from 'react';
import QueueAnim from 'rc-queue-anim';
import TweenOne from 'rc-tween-one';
import OverPack from 'rc-scroll-anim/lib/ScrollOverPack';

import '../../less/functions_intro.less';
import '../../less/home_content.less';

/*
功能说明 FunctionIntroContent 首页使用
*/
class FunctionIntroContent extends React.Component {

  getDelay (e) {
    return e % 3 * 100 + Math.floor(e / 3) * 100 + 300;
  }

  render() {
    //const props = { ...this.props };
    //delete props.isMode;
    const oneAnim = { y: '+=30', opacity: 0, type: 'from', ease: 'easeOutQuad' };
    const blockArray = [
      { icon: 'https://zos.alipayobjects.com/rmsportal/ScHBSdwpTkAHZkJ.png', title: '过程实时监控', content: '及时掌握洗消间动态，不管是机洗还是人工，包括任何细节。' },
      { icon: 'https://zos.alipayobjects.com/rmsportal/NKBELAOuuKbofDD.png', title: '内镜流程追溯', content: '详细到每条内镜的记录，时间地点，洗消者操作者甚至使用患者' },
      { icon: 'https://zos.alipayobjects.com/rmsportal/xMSBjgxBhKfyMWX.png', title: '数据统计查询', content: '针对内镜的使用率，员工的工作量等，统计结果一幕了然' },
      { icon: 'https://zos.alipayobjects.com/rmsportal/MNdlBNhmDBLuzqp.png', title: '动态平台管理', content: '随时随地，方便管理和配置系统的各项参数和定制化指标' },
      { icon: 'https://zos.alipayobjects.com/rmsportal/UsUmoBRyLvkIQeO.png', title: '覆盖平台广泛', content: '方便简单，不管是手机电脑浏览器，还是手机浏览器或APP，亦或微信小程序' },
      { icon: 'https://zos.alipayobjects.com/rmsportal/ipwaQLBLflRfUrg.png', title: '感控一站服务', content: '方便接入一站式、全周期大数据协同感控平台，包括医疗废弃物，手卫生' },
    ];
    const children = blockArray.map((item, i) => {
      const id = `block${i}`;
      const delay = this.getDelay(i);
      const liAnim = { opacity: 0, type: 'from', ease: 'easeOutQuad', delay };
      const childrenAnim = { opacity: 0, type: 'from', ease: 'easeOutQuad', delay, x: '+=10', delay: delay + 100,};
      return (<TweenOne
        component="li"
        animation={liAnim}
        key={i}
        id={`${this.props.id}-${id}`}
      >
        <TweenOne
          animation={{ x: '-=10', opacity: 0, type: 'from', ease: 'easeOutQuad' }}
          className="img"
          key="img"
        >
          <img src={item.icon} width="100%" />
        </TweenOne>
        <div className="text">
          <TweenOne key="h1" animation={childrenAnim} component="h1">
            {item.title}
          </TweenOne>
          <TweenOne key="p" animation={{ childrenAnim, delay: delay + 200 }} component="p">
            {item.content}
          </TweenOne>
        </div>
      </TweenOne>);
    });
    return (
      <div {...this.props} className={`content-template-wrapper ${this.props.className}-wrapper`}>
        <OverPack
          className={`content-template ${this.props.className}`}
          location={this.props.id}
        >
          <TweenOne
            key="h1"
            animation={oneAnim}
            component="h1"
            id={`${this.props.id}-title`}
            reverseDelay={100}
          >
             易同全内镜追溯系统专业的服务
          </TweenOne>
          <TweenOne
            key="p"
            animation={{ oneAnim, delay: 100 }}
            component="p"
            id={`${this.props.id}-titleContent`}
          >
            一个先进的医疗器械洗消追溯物联网解决方案
          </TweenOne>
          <QueueAnim
            key="ul"
            type="bottom"
            className={`${this.props.className}-contentWrapper`}
            id={`${this.props.id}-contentWrapper`}
          >
            <ul key="ul">
              {children}
            </ul>
          </QueueAnim>
        </OverPack>
      </div>
    );
  }
}


FunctionIntroContent.defaultProps = {
    className: 'fuctions_intro',
  };

export default FunctionIntroContent;
