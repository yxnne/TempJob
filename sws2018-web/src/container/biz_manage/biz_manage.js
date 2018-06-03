import React from 'react';
import { Layout, Card, Divider, Row, Col } from 'antd';

import OrganizationTreeComponent from '../../component/organization_tree/organization_tree';

const { Content } = Layout;
/**
 * 业务管理页面
 * 左侧组织树，右侧Tabs
 */
class PageBizManage extends React.Component{
  render(){
    return (
      <div>
        <Layout style={{backgroundColor:'transparent',margin:0, padding:0}}>
          <Content style={{margin:0, padding:0}}>

            <Card style={{marginTop:20, marginRight:20, borderRadius:6}}>

              <Row gutter={8}>
                {/* 组织树 */}
                <Col span={4} style={{borderRight: "1px solid #e8e8e8"}}>

                  <OrganizationTreeComponent />

                </Col>

                {/* 管理信息Tabs */}
                <Col span={20} >


                </Col>

              </Row>
            </Card>

          </Content>
        </Layout>
      </div>
    );
  }

}

export default PageBizManage;
