import React from 'react';
import { Layout, Card, Divider, Progress, Row, Col } from 'antd';
import CardBasicTitleInfoComponent from '../../component/card_basic_title_info/card_basic_title_info';

import CardOverallRateComponent from '../../component/card_overall_rate/card_overall_rate';
import CardOverallTendencyComponent from '../../component/card_overall_tendency/card_overall_tendency';
import CardOverallOccasionComponent from '../../component/card_overall_occassion/card_overall_occassion';


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
              <Col span={10}>
                <CardOverallRateComponent />
              </Col>

              <Col span={14}>
                <CardOverallTendencyComponent />
              </Col>
            </Row>
            {/*时机依从率图*/}
            <Row gutter={8} style={{marginRight:20}}>
              <Col span={6}>
                <CardOverallOccasionComponent title="接触患者前" />
              </Col>

              <Col span={6}>
                <CardOverallOccasionComponent title="接触患者后" />
              </Col>
              <Col span={6}>
                <CardOverallOccasionComponent title="接触患者环境后" />
              </Col>
              <Col span={6}>
                <CardOverallOccasionComponent title="离开患者后" />
              </Col>
            </Row>

          </Content>

        </Layout>
      </div>
    );
  }
}

export default PageHospitalOverall;
