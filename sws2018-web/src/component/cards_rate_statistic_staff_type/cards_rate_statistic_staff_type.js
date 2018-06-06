import React from 'react';
import { Row, Col, Card, Avatar, Progress, Divider } from 'antd';




class CardsRateStatisticStaffTypeComponent extends React.Component{

  render(){
    const data = [
      {
        title:'医生',
        okTimes:230,
        notOkTimes:300,
        occasion1_ok:50,
        occasion1_not:60,
        occasion2_ok:50,
        occasion2_not:60,
        occasion3_ok:50,
        occasion3_not:60,
        occasion4_ok:50,
        occasion4_not:60,

      },{
        title:'护士',
        okTimes:230,
        notOkTimes:300,
        occasion1_ok:50,
        occasion1_not:60,
        occasion2_ok:50,
        occasion2_not:60,
        occasion3_ok:50,
        occasion3_not:60,
        occasion4_ok:50,
        occasion4_not:60,

      },{
        title:'医师',
        okTimes:230,
        notOkTimes:300,
        occasion1_ok:50,
        occasion1_not:60,
        occasion2_ok:50,
        occasion2_not:60,
        occasion3_ok:50,
        occasion3_not:60,
        occasion4_ok:50,
        occasion4_not:60,

      },{
        title:'保洁',
        okTimes:230,
        notOkTimes:300,
        occasion1_ok:50,
        occasion1_not:60,
        occasion2_ok:50,
        occasion2_not:60,
        occasion3_ok:50,
        occasion3_not:60,
        occasion4_ok:50,
        occasion4_not:60,

      },{
        title:'其他',
        okTimes:230,
        notOkTimes:300,
        occasion1_ok:50,
        occasion1_not:60,
        occasion2_ok:50,
        occasion2_not:60,
        occasion3_ok:50,
        occasion3_not:60,
        occasion4_ok:50,
        occasion4_not:60,

      },
    ];
    return (
      <div>

        {data.map(item=>(
          <Card bordered style={{float:"left", width:300, marginRight:20, marginTop:20,borderRadius:10}}>
            <Card.Meta avatar={<Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />}
              title={item.title} />

            <Row gutter={8} type="flex" justify="space-between" align="bottom" style={{marginTop:32}}>
              <Col span={4}>总体</Col>
              <Col span={16}><Progress type="circle" percent={75} /></Col>
              <Col span={4}></Col>
            </Row>


            <Row gutter={8} style={{marginTop:32}}>
              <Col span={8}>离开患者后</Col>
              <Col span={16}><Progress percent={30} /></Col>
            </Row>
            <Row gutter={8} style={{marginTop:16}}>
              <Col span={8}>离开患者后</Col>
              <Col span={16}><Progress percent={30} /></Col>
            </Row>
            <Row gutter={8} style={{marginTop:16}}>
              <Col span={8}>离开患者后</Col>
              <Col span={16}><Progress percent={30} /></Col>
            </Row>
            <Row gutter={8} style={{marginTop:16}}>
              <Col span={8}>离开患者后</Col>
              <Col span={16}><Progress percent={30} /></Col>
            </Row>


          </Card>
        ))}


      </div>


    );
  }


}

export default CardsRateStatisticStaffTypeComponent;
