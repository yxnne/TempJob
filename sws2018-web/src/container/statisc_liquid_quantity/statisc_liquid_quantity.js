import React from 'react';
import { Layout, Card, Divider, Select, Row, Col, Tabs } from 'antd';
import FormStatisticLiquidComponent from '../../component/form/form_statistic_liquid/form_statistic_liquid';
import CardOverallRateComponent from '../../component/card_overall_rate/card_overall_rate';
import CardOverallTendencyComponent from '../../component/card_overall_tendency/card_overall_tendency';
import CardOverallOccasionComponent from '../../component/card_overall_occassion/card_overall_occassion';
import TableDeviceStatusCheckComponent from '../../component/table/table_device_status_check/table_device_status_check';
import CardsRateStatisticStaffTypeComponent from '../../component/cards_rate_statistic_staff_type/cards_rate_statistic_staff_type';


// 假数据生产器
import { getFakedata_deviceStatus } from '../../util/fake/ui_fakedata_generator';


const { Content, Sider } = Layout;
const Option = Select.Option;
const TabPane = Tabs.TabPane;

/**
 * 洗手液统计
 * 类似依从率统计页面，顶上选择时间和部门
 */
class PageStatisticLiquid extends React.Component{
  constructor(){
    super();
    this.state = {
      infoPanelCollaped:true,
      radio:'overall',
      tableData:getFakedata_deviceStatus(30),
    };

    this.toggle = this.toggle.bind(this);
    this.onTableNameClickCallback = this.onTableNameClickCallback.bind(this);
  }

  handleChange(value) {
    console.log(`selected ${value}`);
  }
  // 右侧伸缩板子控制
  toggle(){
    this.setState({
      infoPanelCollaped: !this.state.infoPanelCollaped,

   });
  }

  // Table的名字点击时的回调函数
  onTableNameClickCallback(clickedRecordRow){
    // console.log(clickedRecordRow);
    // 拉开 右侧侧滑菜单
    this.toggle();
  }

  // Radio切换，控制state
  handleRadioChange(tag){
    this.setState({
      radio:tag
    });
  }

  render(){

    // 员工依从率表
    const viewStaffRateTable = (

      <Tabs defaultActiveKey="1" tabPosition="right"  >
        <TabPane tab="职工" key="1">
          <TableDeviceStatusCheckComponent tableData={this.state.tableData} onNameClick={this.onTableNameClickCallback}/>
        </TabPane>
        <TabPane tab="类型" key="2">
          <CardsRateStatisticStaffTypeComponent />
        </TabPane>

      </Tabs>
    );



    return (
      <div>
        <Layout style={{backgroundColor:'transparent',margin:0, padding:0}}>
          <Content style={{margin:0, padding:0}}>

            <Card style={{marginTop:20, marginRight:20, borderRadius:6}}>

              {/* 查询条件 表单 */}
              <FormStatisticLiquidComponent />

              <Divider />

              <Row gutter={8}>
                {/* 部门总体使用情况 */}
                <Col span={10}>
                  <CardOverallRateComponent doTimes={165} notDoTimes={100}/>
                </Col>
                {/* 折线趋势图 */}
                <Col span={14}>
                  <CardOverallTendencyComponent />
                </Col>
              </Row>
              <Row gutter={8}>
                <Col span={6}>
                  <CardOverallOccasionComponent title="接触患者前" doTimes={50} notDoTimes={90} />
                </Col>
                <Col span={6}>
                  <CardOverallOccasionComponent title="接触患者后" doTimes={150} notDoTimes={30} />
                </Col>
                <Col span={6}>
                  <CardOverallOccasionComponent title="接触患者环境后" doTimes={250} notDoTimes={190} />
                </Col>
                <Col span={6}>
                  <CardOverallOccasionComponent title="离开患者后" doTimes={20} notDoTimes={118} />
                </Col>
              </Row>


            </Card>

          </Content>
          <Sider collapsed={this.state.infoPanelCollaped} collapsedWidth={0} width={400}>

          </Sider>
        </Layout>
      </div>
    );
  }
}

export default PageStatisticLiquid;
