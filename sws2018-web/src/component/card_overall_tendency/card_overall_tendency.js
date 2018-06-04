import React from 'react';
import { Row, Col, Card, Progress } from 'antd';
/**
 * 卡片，总依从率：环形加条形图
 */
class CardOverallTendencyComponent extends React.Component{

  render(){
    const cardStyle = {
      marginTop:16,
      borderRadius:6,
      marginRight:20,
      minHeight:300
    };
    return (
      <Card style={cardStyle}>
        <h3>手卫生执行情况走势</h3>

        <Row gutter={8} style={{marginTop:30}}>
 
        </Row>
      </Card>
    );
  }
}

export default CardOverallTendencyComponent;
