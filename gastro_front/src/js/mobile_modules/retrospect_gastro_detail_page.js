/* 本文件对应业务：内镜管理 */
import React from 'react';
import {Breadcrumb, Table, Tabs, Modal, Divider, Row, Col, Input, Button, DatePicker} from 'antd';
import {Link} from 'react-router-dom'

import MobileTableBasicNoFormComponent from '../components/mobile_table_basic_no_form';

const TabPane = Tabs.TabPane;
const RangePicker = DatePicker.RangePicker;
/*
组件 : MobileRetrospectGastroPage
功能 : 移动端： 内镜追溯 / 内镜  / 详细
*/
export default class MobileRetrospectGastroDetailPage extends React.Component{

  constructor(){
    super();
    this.state = {
      useModalVisible:false
    }
  }

  showUseInfoModal(){
    this.setState({
      useModalVisible:true
    });
  }

  hideUseInfoModal(){
    this.setState({
      useModalVisible:false
    });
  }

  render(){
    //测试表格
    //表格设置
    const columns = [{
        title: '内镜编号',
        dataIndex: 'gastro_id',
        width: 150,
 
      }, {
        title: '内镜名称',
        dataIndex: 'gastro_name',
        width: 150,

      }, {
        title: '更多',
        dataIndex: 'more_action',
        width: 100,
        render: (text, record) => (
          <span>
            <a onClick={this.showUseInfoModal.bind(this)}>{record.more_action}</a>
          </span>
        ),
      }, {
        title: '初洗',
        dataIndex: 'wash_frist',
        width: 100,
      }, {
        title: '酶洗',
        dataIndex: 'wash_enzyme',
        width: 100,
      }, {
        title: '次洗',
        dataIndex: 'wash_second',
        width: 100,
      }, {
        title: '浸泡',
        dataIndex: 'wash_soak',
        width: 100,
      }, {
        title: '末洗',
        dataIndex: 'wash_last',
        width: 100,
      }, {
        title: '干燥',
        dataIndex: 'wash_dry',
        width: 100,
      },  {
        title: '洗消人员',
        dataIndex: 'wash_operator',
        width: 100,
      },  {
        title: '洗消日期',
        dataIndex: 'wash_data',
        width: 150,
      },  {
        title: '开始时间',
        dataIndex: 'time_start',
        width: 150,
      },  {
        title: '结束时间',
        dataIndex: 'time_end',
        width: 150,
      }];
    //数据
    const data = [];
    for (let i = 0; i < 46; i++) {
      data.push({
        key:`enteroscopy_${i}`,
        serial: i,
        gastro_id:`enteroscopy_1`,
        gastro_name: "Entero_0",
        wash_frist: "30分20秒",
        wash_enzyme: `130分60秒`,
        wash_second: `300分20秒`,
        wash_soak: `3分`,
        wash_last:'120分20秒',
        wash_dry: `30分20秒`,
        wash_operator: `张震岳`,
        wash_data: `2017-11-12`,
        time_start: `10-20 02:15:20`,
        time_end: `11-20 23:15:20`,
        time_total: `500分钟20秒`,
        more_action:"使用信息",
      });
    }

    return (
        <div>
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px' }}>
            <Breadcrumb.Item>内镜追溯</Breadcrumb.Item>
            <Breadcrumb.Item><Link to="retrospect_gastro">追溯列表</Link></Breadcrumb.Item>
            <Breadcrumb.Item>内镜：Entero_0 的详细信息</Breadcrumb.Item>
          </Breadcrumb>
          {/*简单搜索栏*/}

          {/*表格和选项*/}
          <div id="pages_container_without_breadcrumb" >
            <div id="basic_table_and_form_container">
              {/*简单搜索栏*/}
              <div style={{paddingTop:"18px", paddingBottom:"10px"}}>
                <Row>
                    <Col span={8}><Input placeholder="清洗员工查询"/></Col>
                    <Col span={1} />
                    <Col span={8}><RangePicker allowClear  placeholder={["开始","结束"]}/></Col>
                    <Col span={2} />
                    <Col span={3}><Button icon="search" type="primary" >查询</Button></Col>
                </Row>
              </div>
              <Table dataSource={data} columns={columns} scroll={{x:1500}}/>
            </div>
          </div>

          <Modal
            title="内镜使用信息"
            visible={this.state.useModalVisible}
            onOk={this.hideUseInfoModal.bind(this)}
            onCancel={this.hideUseInfoModal.bind(this)}>
              <p>本次使用时间 : 2017 - 06 - 20</p>
              <p>本次使用病人 : 卓伟</p>
              <p>就诊号      : PATIENT_74236945</p>
              <p>检查医生     : 2017 - 06 - 20</p>
              <Divider />
              <p>上次使用病人 : 章子怡</p>
              <p>上次使用时间 : 2014 - 06 - 20</p>
          </Modal>
        </div>
    )
  }
}
