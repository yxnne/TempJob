import React from 'react';
import { Layout, Card, Divider, Tabs, Row, Col } from 'antd';

import OrganizationTreeComponent from '../../component/organization_tree/organization_tree';
import FormDevicesStatusComponent from '../../component/form/form_device_status/form_device_status';
import TableDeviceStatusCheckComponent from '../../component/table/table_device_status_check/table_device_status_check';
import { getFakedata_deviceStatus} from '../../util/fake/ui_fakedata_generator';

const { Content } = Layout;
const TabPane = Tabs.TabPane;
/**
 * 设备查看页面
 * 左侧组织树 右侧表
 */
class PageDeviceQuickCheck extends React.Component{
  constructor(){
    super();
    this.state = {
      tableData:getFakedata_deviceStatus(25),
    };
    this.handelOnTabsChange = this.handelOnTabsChange.bind(this);
  }

  handelOnTabsChange(tabKey){
    console.log(tabKey)
  }

  render(){
    return (
      <div>
        <Layout style={{backgroundColor:'transparent',margin:0, padding:0}}>
          <Content style={{margin:0, padding:0}}>

            <Card style={{marginTop:20, marginRight:20, borderRadius:6}}>

              <Row gutter={8}>
                {/* 组织树 */}
                <Col span={4} style={{borderRight: "1px solid #e8e8e8", minHeight:760 }}>

                  <OrganizationTreeComponent />

                </Col>

                <Col span={20} style={{paddingLeft:32, paddingRight:32}}>
                  {/* 查询条件 表单 */}
                  <FormDevicesStatusComponent />

                  <Divider />

                  {/* 查询结果 -- Table */}
                  <TableDeviceStatusCheckComponent  tableData={this.state.tableData} onNameClick={this.onTableNameClickCallback}/>

                </Col>
              </Row>
            </Card>

          </Content>
        </Layout>
      </div>
    );
  }

}

export default PageDeviceQuickCheck;
