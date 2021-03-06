import React from 'react';
import { Layout, Card, Divider, Tabs, Row, Col } from 'antd';

import OrganizationTreeComponent from '../../component/organization_tree/organization_tree';
import BtnGroupTableManage from '../../component/btn_group_table_manage/btn_group_table_manage';
import TableManageUserComponent from '../../component/table/table_manage_user/table_manage_user';
import { getFakedata_manageUser } from '../../util/fake/ui_fakedata_generator';

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
      staffDatas:getFakedata_manageUser(25),
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
                  {/* 用户管理 */}
                  <BtnGroupTableManage />
                  <TableManageUserComponent tableData={this.state.staffDatas}/>
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
