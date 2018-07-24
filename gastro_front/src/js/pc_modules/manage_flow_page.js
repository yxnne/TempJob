/* 本文件对应业务：内镜管理 */
import React from 'react';
import {Link} from 'react-router-dom';
import {Breadcrumb, Table} from 'antd';

import PCTableBasicNoFormComponent from '../components/pc_table_basic_no_form';
import PCTableEditable from '../components/pc_table_editable';

//测试表格
//表格设置
const columns = [{
    title: '序号',
    dataIndex: 'serial',
  }, {
    title: '流程名称',
    dataIndex: 'flow_name',
  }, {
    title: '正常流程时间',
    dataIndex: 'flow_time_normal',
  }, {
    title: '特殊清洗时间',
    dataIndex: 'flow_time_special',
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
组件 : PCManageFlowPage
作用 : 业务管理 / 流程管理页面
*/
export default class PCManageFlowPage extends React.Component{

  render(){
    return (
        <div>
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px 0' }} separator=">">
            <Breadcrumb.Item>业务管理</Breadcrumb.Item>
            <Breadcrumb.Item><Link to="manage_gastro">内镜管理</Link></Breadcrumb.Item>
          </Breadcrumb>

          {/*表格和选项*/}
          <div className="pages_container_without_breadcrumb" >
            <PCTableEditable data={data} columns={columns} />
          </div>
        </div>
    )
  }
}
