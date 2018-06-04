import React from 'react';
import { Row, Col, Card, DatePicker, Icon, Divider } from 'antd';
import moment from 'moment';
const { RangePicker } = DatePicker;
const dateFormat = 'YYYY/MM/DD';
const monthFormat = 'YYYY/MM';
/**
 * 组件：基本信息定
 */
class CardBasicTitleInfoComponent extends React.Component{

  render(){
    const cardStyle = {
      marginTop:20,
      marginRight:20,
      borderRadius:6,
      paddingLeft:16,
      paddingRight:16,
      paddingTop:8,
      paddingBottom:8,
      backgroundColor:'white',
      border:'solid #e8e8e8 1px'
    };

    return (
      <div style={cardStyle}>
        <Row gutter={8}>
          <Col span={24}>
            <h2 style={{display:'inline-block', marginRight:20}}>武汉协和医院总览</h2>
            <Divider type="vertical"/>
            系统中注册科室5个
            <Divider type="vertical"/>
            注册员工20人
            <Divider type="vertical"/>
            注册设备100个
            <span style={{float:'right'}}>
              <h2 style={{display:'inline-block', color:'transparent'}}>X</h2>
              <Icon type="clock-circle-o" style={{marginRight:16}}/>
              <RangePicker size="small"
                defaultValue={[moment('2015/01/01', dateFormat), moment('2015/01/01', dateFormat)]}
                format={dateFormat} />

            </span>
          </Col>

        </Row>
      </div>
    );
  }
}

export default CardBasicTitleInfoComponent;
