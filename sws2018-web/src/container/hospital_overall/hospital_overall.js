import React from 'react';
import { Layout, Card, Divider, Progress, Row, Col } from 'antd';
import CardBasicTitleInfoComponent from '../../component/card_basic_title_info/card_basic_title_info';

import CardOverallRateComponent from '../../component/card_overall_rate/card_overall_rate';
import CardOverallTendencyComponent from '../../component/card_overall_tendency/card_overall_tendency';
import CardOverallOccasionComponent from '../../component/card_overall_occassion/card_overall_occassion';
import CardOverallDepartmentsComponent from '../../component/card_overall_departments/card_overall_departments';


const { Content } = Layout;

/**
 * 医院总览页面
 * 环形饼图，折线图等
 */
class PageHospitalOverall extends React.Component{

  render(){

    return (
      <div>
        <Layout style={{backgroundColor:'transparent',margin:0, padding:0}}>
          <Content style={{margin:0, padding:0,minHeight: 930}}>

            {/* 最顶上类似标题：基本信息 */}
            <CardBasicTitleInfoComponent />
            <Row gutter={8}>
              {/* 部门总体依从率 */}
              <Col span={10}>
                <CardOverallRateComponent doTimes={165} notDoTimes={100}/>
              </Col>
              {/* 折线趋势图 */}
              <Col span={14}>
                <CardOverallTendencyComponent />
              </Col>
            </Row>



            <Row gutter={8} style={{marginRight:20}}>

              {/* 时机依从率图 */}
              <Col span={14}>

                <Row gutter={8}>
                  <Col span={12}>
                    <CardOverallOccasionComponent title="接触患者前" doTimes={50} notDoTimes={90} />
                  </Col>
                  <Col span={12}>
                    <CardOverallOccasionComponent title="接触患者后" doTimes={150} notDoTimes={30} />
                  </Col>
                </Row>
                <Row gutter={8}>
                  <Col span={12}>
                    <CardOverallOccasionComponent title="接触患者环境后" doTimes={250} notDoTimes={190} />
                  </Col>
                  <Col span={12}>
                    <CardOverallOccasionComponent title="离开患者后" doTimes={20} notDoTimes={118} />
                  </Col>
                </Row>
              </Col>

              {/* 科室前五名 */}
              <Col span={10}>
                <CardOverallDepartmentsComponent />
              </Col>

            </Row>

          </Content>

        </Layout>
      </div>
    );
  }
}

export default PageHospitalOverall;
