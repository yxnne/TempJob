import React from 'react';
import { Row, Col, Card, Progress } from 'antd';
/**
 * 卡片，总依从率：环形加条形图
 */
class CardOverallRateComponent extends React.Component{

  render(){
    const cardStyle = {
      marginTop:16,
      borderRadius:6,
      minHeight:300
    };
    return (
      <Card style={cardStyle}>
        <h3>部门总体依从率</h3>

        <Row gutter={8} style={{marginTop:30}}>
          <Col span={12} style={{textAlign:'center', borderRight:'solid 1px #e8e8e8'}}>
            <Progress type="dashboard" percent={75} width={180} />
          </Col>
          <Col span={12} style={{paddingLeft:16, paddingTop:24}}>
            执行手卫生次数
            <br />
            <Progress percent={30} format={percent => `123`}/>
            <br /><br />
            未执行次数
            <br />
            <Progress percent={50} />
          </Col>
        </Row>
      </Card>
    );
  }
}

export default CardOverallRateComponent;
