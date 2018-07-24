/* 本文件对应业务：内镜管理 */
import React from 'react';
import {Link} from 'react-router-dom';
import {Breadcrumb, Table, Steps, Icon} from 'antd';


import PCTableBasicNoFormComponent from '../components/pc_table_basic_no_form';
import PCTableMonitor from '../components/pc_table_monitor';

const Step = Steps.Step;
//测试表格
//表格设置
const columns = [{
    title: '序号',
    dataIndex: 'serial',
  }, {
    title: '内镜编号',
    dataIndex: 'flow_name',
  }, {
    title: '内镜名称',
    dataIndex: 'flow_time_normal',
  }, {
    title: '清洗过程进度监控',
    dataIndex: 'flow_time_special',
    width:800
  }, {
    title: '最长清洗时间',
    dataIndex: 'flow_time_max',
  }, {
    title: '操作',
    dataIndex: 'operation',
  }];
//数据
const data = [];
const flowNames = ["初洗", "次洗", "酶洗", "浸泡", "末洗", "干燥"];
for (let i = 0; i < flowNames.length; i++) {
  data.push({
    key:`enteroscopy_${i}`,
    serial: i,
    flow_name:`${flowNames[i]}`,
    flow_time_normal: "30分60秒",
    flow_time_special: "60秒",
    flow_time_max: `300分60秒`,
    operation: `修改`,
  });
}


/*
组件 : MobileMonitorManualPage
作用 : 实时监控 / 手工洗消 手机端
*/
export default class MobileMonitorManualPage extends React.Component{

  render(){
    return (
        <div>
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px 0' }} separator=">">
            <Breadcrumb.Item>实时监控</Breadcrumb.Item>
            <Breadcrumb.Item><Link to="manage_gastro">手工洗消</Link></Breadcrumb.Item>
          </Breadcrumb>

          {/*表格和选项*/}
          <div className="pages_container_without_breadcrumb" >
            <PCTableMonitor data={data} columns={columns} />
          </div>
        </div>
    )
  }
}
