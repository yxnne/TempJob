import React, { Component } from 'react';
import { Card, WingBlank, List  } from 'antd-mobile';

import TextAvatar from '../textAvatar/TextAvatar';

// 样式定义
const cardStyle = {minHeight:420, paddingBottom:20};
const rankInfoContainer = {  width:'100%', height:60 };
const rankTextStyle = { fontSize:36, margin:'0px 8px'}
const infoContainer = { textAlign:'center', fontSize:18};
const infoItemStyle = { margin:'10px auto', fontSize:18, paddingTop:4 };
const mainTimesTextStyle = { fontSize:32, margin:'0px 8px'};

const makeDetailContent = (position, times) =>{
  return (
    <div style={{display:'table', width:'100%'}}>
      <div style={{display:'table-row',  width:'100%'}}>
        <div style={{marginLeft:12, display:'table-cell',  width:'60%', textAlign:'center'}}>
        {position}
        </div>
        <div style={{marginLeft:12, display:'table-cell',  width:'40%', textAlign:'center'}}>
        {times}<span style={{fontSize:12, marginLeft:8}}>次</span>
        </div>
      </div>
    </div>

  );
};

/** 员工详情的卡片 */
export default class StaffDetailCard extends Component {

  constructor(props){
    super(props);
    this.state = {
      detailShow:false
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

    const one = this.props.staffInfo? this.props.staffInfo : {
      name:'姓名', rank:'?',department:'部门', detail:[],role:'职责',times:'次数'
    };

    // 更多细节的按钮
    const moreEle = (
      <div style={{ marginTop:50 ,padding:10, color:'#33A3F4'}} onClick={this.toggleDetailShow}>

        {this.state.detailShow ? '收起展开' : '更多细节'}&nbsp;&nbsp;&nbsp;
        <img style={{ width:18,height:18, marginTop:6 }} src={this.state.detailShow?require('./image/up.svg'):require('./image/down.svg')} alt=''/>

      </div>
    );
    return (
      <Card >
        <Card.Body>

          <div style={cardStyle}>


            {/* 名次的容器 */}
            <div style={rankInfoContainer}>
              第<span style={rankTextStyle}>{one.rank}</span>名
            </div>

            <div style={infoContainer}>
              <TextAvatar bgColor='#33a4f4' text={one.name[0]}/>
              <div style={infoItemStyle}>{one.department}</div>
              <div style={infoItemStyle}>{one.name}&nbsp; , &nbsp; {one.role}</div>

              <div style={{...infoItemStyle, marginTop:60}}>
                共计洗手<span style={mainTimesTextStyle}>{one.times}</span>次
              </div>

              {/* 更多按钮 */}
              { moreEle }              

              {/* 细节组件 */}
              {
                this.state.detailShow?
                (
                  <WingBlank>
                    <List >
                      {
                        one.detail.map(item=>(
                          <List.Item>
                            { makeDetailContent(item.position, item.times)}
                          </List.Item>
                        ))
                      }

                    </List>
                  </WingBlank>
                ):(null)
              }

            </div>
          </div>

        </Card.Body>
        <Card.Footer content={null} extra={null} />
      </Card>
    )
  }
}
