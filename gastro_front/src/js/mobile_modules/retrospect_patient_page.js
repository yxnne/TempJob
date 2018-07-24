/* 本文件对应业务：内镜管理 */
import React from 'react';
import {Breadcrumb, Table, Tabs, Row, Col, Button, Input} from 'antd';
import {Link} from 'react-router-dom'

import MobileTableBasicNoFormComponent from '../components/mobile_table_basic_no_form';

const TabPane = Tabs.TabPane;
//测试表格
//表格设置
const columns = [{
    title: '序号',
    dataIndex: 'serial',
  }, {
    title: '患者就诊号',
    dataIndex: 'gastro_id',
  }, {
    title: '患者姓名',
    dataIndex: 'gastro_name',
  }, {
    title: '更多',
    dataIndex: 'more_action',
    render: (text, record) => (
      <span>
        <Link to={record.linkTo}>{record.more_action} ></Link>
      </span>
    ),
  }];
//胃镜假数据 1
const data = [];

for (let i = 0; i < 46; i++) {
  data.push({
    key:`enteroscopy_${i}`,
    serial: i,
    gastro_id:`PATIENT_${i}`,
    gastro_name:`病患_${i}`,

    more_action:"洗消信息",
    linkTo: "/retrospect_gastro_detail",
  });
}

/*
组件 : MobileRetrospectStaffPage
功能 : 移动端： 内镜追溯 / 患者
*/
export default class MobileRetrospectPatientPage extends React.Component{

  render(){

    return (
        <div>
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px' }}>
            <Breadcrumb.Item>内镜追溯</Breadcrumb.Item>
            <Breadcrumb.Item>员工追溯详细信息</Breadcrumb.Item>
          </Breadcrumb>

          {/*表格和选项*/}
          <div id="pages_container_without_breadcrumb" >
            <div id="basic_table_and_form_container">

              {/*简单搜索栏*/}
              <div style={{paddingTop:"18px", paddingBottom:"10px"}}>
                <Row>
                    <Col span={8}><Input placeholder="就诊号查询"/></Col>
                    <Col span={1} />
                    <Col span={8}><Input placeholder="病人姓名查询"/></Col>
                    <Col span={2} />
                    <Col span={3}><Button icon="search" type="primary">查询</Button></Col>
                </Row>
              </div>
              <Table dataSource={data} columns={columns} />

            </div>
          </div>
        </div>
    )
  }
}
