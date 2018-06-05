import React from 'react';
import { Row, Col, Card, Progress } from 'antd';
/**
 * 卡片，总依从率：环形加条形图
 */
class CardOverallRateComponent extends React.Component{

  render(){
    const cardStyle = {
      marginTop:12,
      borderRadius:6,
      minHeight:300,
      height:450
    };
    const allTimes = this.props.doTimes + this.props.notDoTimes;
    let percent = allTimes === 0 ? 0: this.props.doTimes / allTimes * 100;
    percent = percent.toFixed(1); // 保留小数位
    return (
      <Card style={cardStyle}>
        <h3>部门总体依从率</h3>

        <Row gutter={8} style={{marginTop:80}}>
          <Col span={12} style={{textAlign:'center', borderRight:'solid 1px #e8e8e8'}}>
            <Progress type="dashboard" percent={percent} width={180} />
          </Col>
          <Col span={12} style={{paddingLeft:16, paddingTop:24}}>
            执行手卫生次数
            <br />
            <Progress percent={percent} format={percent => `${this.props.doTimes} 次`}/>
            <br /><br />
            未执行次数
            <br />
            <Progress percent={100 - percent} format={percent => `${this.props.notDoTimes} 次`}/>
          </Col>
        </Row>
      </Card>
    );
  }
}

export default CardOverallRateComponent;
