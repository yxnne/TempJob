import React from 'react';
import { Row, Col, Card, Icon, Divider, Progress } from 'antd';
/**
 * 卡片，时机依从率组件
 */
class CardOverallOccasionComponent extends React.Component{

  render(){
    const cardStyle = {
      marginTop:12,
      borderRadius:10,
      height:156
    };
    const allTimes = this.props.doTimes + this.props.notDoTimes;
    let percent = allTimes === 0 ? 0: this.props.doTimes / allTimes * 100;
    percent = percent.toFixed(1); // 保留小数位
    return (
      <Card style={cardStyle}>


        <Row gutter={8} >
          <Col span={14} style={{marginTop:20}}>
            <h3 >{this.props.title}</h3>
            <Icon type="check" />&nbsp;&nbsp;&nbsp;{this.props.doTimes}次
            <Divider type="vertical"/>
            <Icon type="close" />&nbsp;&nbsp;&nbsp;{this.props.notDoTimes}次
          </Col>

          <Col span={10} style={{textAlign:"center", marginTop:8}}>
            <Progress type="circle" percent={percent} width={90} />
          </Col>
        </Row>
      </Card>
    );
  }
}

export default CardOverallOccasionComponent;
