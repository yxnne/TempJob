import React from 'react';
import { Layout, Card, Divider, Select, Row, Col, Tabs } from 'antd';

import FormStatisticLiquidComponent from '../../component/form/form_statistic_liquid/form_statistic_liquid';
import CardOverallQuantityComponent from '../../component/card_overall_quantity/card_overall_quantity';
import CardTableQuantityComponent from '../../component/card_types_table_quantity/card_types_table_quantity';
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
                <Col span={12}>
                  <CardOverallQuantityComponent doTimes={165} notDoTimes={100}/>
                </Col>
                {/* 表格  */}
                <Col span={12}>
                  <CardTableQuantityComponent />
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
