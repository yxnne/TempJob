import React from 'react';
import { Row, Col, Card, Icon, Divider, Progress } from 'antd';
/**
 * 卡片，总依从率：环形加条形图
 */
class CardOverallOccasionComponent extends React.Component{

  render(){
    const cardStyle = {
      marginTop:16,
      borderRadius:6,
    };
    return (
      <Card style={cardStyle}>


        <Row gutter={8} >
          <Col span={14} style={{marginTop:12}}>
            <h3 >{this.props.title}</h3>
            <Icon type="check" />200次
            <Divider type="vertical"/>
            <Icon type="close" />59次
          </Col>

          <Col span={10} style={{textAlign:"center"}}>
            <Progress type="circle" percent={75} width={80} />
          </Col>
        </Row>
      </Card>
    );
  }
}

export default CardOverallOccasionComponent;
