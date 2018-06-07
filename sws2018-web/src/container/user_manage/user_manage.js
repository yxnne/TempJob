import React from 'react';
import { Layout, Card, Divider, Tabs, Row, Col } from 'antd';

import OrganizationTreeComponent from '../../component/organization_tree/organization_tree';
import BtnGroupTableManage from '../../component/btn_group_table_manage/btn_group_table_manage';
import TableDeviceManageStaffComponent from '../../component/table/table_device_manage_staff/table_device_manage_staff';
import { getFakedata_manageStaff } from '../../util/fake/ui_fakedata_generator';

const { Content } = Layout;
const TabPane = Tabs.TabPane;
/**
 * 业务管理页面
 * 左侧组织树，右侧Tabs
 */
class PageUserManage extends React.Component{
  constructor(){
    super();
    this.state = {
      staffDatas:getFakedata_manageStaff(25),
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

                {/* 管理信息Tabs */}
                <Col span={20} style={{paddingLeft:32, paddingRight:32}}>
                  <Tabs defaultActiveKey="1" onChange={this.handelOnTabsChange}>

                    <TabPane tab="职工" key="1">
                      <BtnGroupTableManage />
                      <TableDeviceManageStaffComponent tableData={this.state.staffDatas}/>
                    </TabPane>

                    <TabPane tab="胸牌" key="2">

                    </TabPane>

                    <TabPane tab="床识别器" key="3">

                    </TabPane>

                    <TabPane tab="液瓶识别器" key="4">

                    </TabPane>

                    <TabPane tab="门识别器" key="5">

                    </TabPane>

                    <TabPane tab="AP管理" key="6">

                    </TabPane>
                  </Tabs>

                </Col>

              </Row>
            </Card>

          </Content>
        </Layout>
      </div>
    );
  }

}

export default PageUserManage;
