import React, { Component } from 'react';
import { Card } from 'antd-mobile';

import TextAvatar from '../textAvatar/TextAvatar';

// 样式定义
const cardStyle = {minHeight:420};
const rankInfoContainer = {  width:'100%', height:60 };
const rankTextStyle = { fontSize:36, margin:'0px 8px'}
const infoContainer = { textAlign:'center', fontSize:18};
const infoItemStyle = { margin:'10px auto', fontSize:18 };
const mainTimesTextStyle = { fontSize:32, margin:'0px 8px'};



/** 员工详情的卡片 */
export default class StaffDetailCard extends Component {

  constructor(props){
    super(props);
    this.state = {
      detailShow:true
    };

    this.toggleDetailShow = this.toggleDetailShow.bind(this);
  }

  toggleDetailShow(){
    const show = this.state.detailShow;
    this.setState({
      detailShow:!show
    });
  }

  render() {
    // 更多细节的按钮
    const moreEle = (
      <div style={{ marginTop:50 }} onClick={this.toggleDetailShow}>
        <div>{this.state.detailShow?'收起展开':'更多细节'}</div>
        <div>
          <img style={{ width:40,height:40 }} src={this.state.detailShow?require('./image/up.svg'):require('./image/down.svg')} alt=''/>
        </div>
      </div>
    );
    return (
      <Card >
        <Card.Body>

          <div style={cardStyle}>


            {/* 名次的容器 */}
            <div style={rankInfoContainer}>
              第<span style={rankTextStyle}>2</span>名
            </div>

            <div style={infoContainer}>
              <TextAvatar bgColor='#33a4f4' text='张'/>
              <div style={infoItemStyle}>神经内科</div>
              <div style={infoItemStyle}>张飞 &nbsp; , &nbsp; 医生</div>

              <div style={{...infoItemStyle, marginTop:60}}>
                共计洗手<span style={mainTimesTextStyle}>144</span>次
              </div>

              { moreEle }              

              {
                this.state.detailShow?
                (
                  <div>这里细节出现</div>
                ):(<div></div>)
              }

            </div>
          </div>

        </Card.Body>
        <Card.Footer content={null} extra={null} />
      </Card>
    )
  }
}
