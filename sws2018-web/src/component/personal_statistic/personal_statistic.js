import React from 'react';
import { Row, Col, Card, DatePicker, Icon, Divider, Radio, Avatar, Progress } from 'antd';
import moment from 'moment';
const { RangePicker } = DatePicker;
const dateFormat = 'YYYY/MM/DD';
const monthFormat = 'YYYY/MM';
const RadioButton = Radio.Button;
const RadioGroup = Radio.Group;
/**
 * 个人统计
 */
class PersonalStatisticComponent extends React.Component{

  render(){
    const cardStyle = {
      marginTop:20,
      marginRight:24,
      marginLeft:0,
      paddingLeft:20,
      paddingRight:20,
      backgroundColor:'white',
      border:'solid #e8e8e8 1px',
      borderRadius:10,
      minHeight:880
    };

    const percent = 56.2


    return (
      <div >

        {/* 个人基本信息区域 */}


        <div style={cardStyle}>

          <div style={{marginTop:40}}>
            <Row type="flex" justify="space-around" align="middle">
              <Col span={12} style={{textAlign:'center'}}>
                <Avatar style={{ backgroundColor: '#f56a00', verticalAlign: 'middle' }} size="large">
                  N
                </Avatar>
              </Col>
              <Col span={12}>
                <h2>Name</h2>
                <p>关联胸卡：000006</p>
                <p>神经内科 医生</p>
              </Col>

            </Row>
          </div>
          <Divider />
          {/* 时间选择 */}
          <div style={{ marginTop: 16, textAlign:'center'}}>
            <RadioGroup defaultValue="a">
              <RadioButton value="a">近一周</RadioButton>
              <RadioButton value="b">近一月</RadioButton>
              <RadioButton value="c">今 日</RadioButton>
            </RadioGroup>
          </div>

          {/* 依从率部分 */}
          {/* 总体依从率 */}
          <Row gutter={8} style={{marginTop:80}}>
            <Col span={12} style={{textAlign:'center', borderRight:'solid 1px #e8e8e8'}}>
              <Progress type="dashboard" percent={percent} width={120} />
            </Col>
            <Col span={12} style={{paddingLeft:16, paddingRight:32, paddingTop:24}}>
              执行手卫生次数
              <br />
              <Progress percent={percent} format={percent => `123 次`}/>
              <br /><br />
              未执行次数
              <br />
              <Progress percent={100 - percent} format={percent => `20 次`}/>
            </Col>
          </Row>

          <Divider />

          {/* 四大时机 */}
          <Row style={{marginLeft:16}}>
            <Col span={12}>

              <Row gutter={8} >
                <Col span={14} style={{marginTop:20}}>
                  <p >接触患者前</p>
                  <Icon type="check" />&nbsp;&nbsp;&nbsp;20次
                  <Divider type="vertical"/>
                  <Icon type="close" />&nbsp;&nbsp;&nbsp;60次
                </Col>

                <Col span={10} style={{textAlign:"center", marginTop:18}}>
                  <Progress type="circle" percent={percent} width={60} />
                </Col>
              </Row>

            </Col>

            <Col span={12}>

              <Row gutter={8} >
                <Col span={14} style={{marginTop:20}}>
                  <p >接触患者后</p>
                  <Icon type="check" />&nbsp;&nbsp;&nbsp;{this.props.doTimes}次
                  <Divider type="vertical"/>
                  <Icon type="close" />&nbsp;&nbsp;&nbsp;{this.props.notDoTimes}次
                </Col>

                <Col span={10} style={{textAlign:"center", marginTop:18}}>
                  <Progress type="circle" percent={percent} width={60} />
                </Col>
              </Row>

            </Col>
          </Row>
          <Row style={{marginTop:20}} style={{marginLeft:16}}>
            <Col span={12}>

              <Row gutter={8} >
                <Col span={14} style={{marginTop:20}}>
                  <p >解除患者环境后</p>
                  <Icon type="check" />&nbsp;&nbsp;&nbsp;{this.props.doTimes}次
                  <Divider type="vertical"/>
                  <Icon type="close" />&nbsp;&nbsp;&nbsp;{this.props.notDoTimes}次
                </Col>

                <Col span={10} style={{textAlign:"center", marginTop:18}}>
                  <Progress type="circle" percent={percent} width={60} />
                </Col>
              </Row>

            </Col>

            <Col span={12}>

              <Row gutter={8} >
                <Col span={14} style={{marginTop:20}}>
                  <p >进入病区前</p>
                  <Icon type="check" />&nbsp;&nbsp;&nbsp;{this.props.doTimes}次
                  <Divider type="vertical"/>
                  <Icon type="close" />&nbsp;&nbsp;&nbsp;{this.props.notDoTimes}次
                </Col>

                <Col span={10} style={{textAlign:"center", marginTop:18}}>
                  <Progress type="circle" percent={percent} width={60} />
                </Col>
              </Row>

            </Col>
          </Row>

        </div>


      </div>
    );
  }
}

export default PersonalStatisticComponent;
