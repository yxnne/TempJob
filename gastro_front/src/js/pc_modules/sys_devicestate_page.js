/* 本文件对应业务：内镜管理 */
import React from 'react';
import {Link} from 'react-router-dom';
import {Breadcrumb, Table} from 'antd';

//import PCTableBasicNoFormComponent from '../components/pc_table_basic_no_form';

//测试表格
//表格设置
const columns = [{
    title: '序号',
    dataIndex: 'serial',
  }, {
    title: '设备类型',
    dataIndex: 'device_type',
  }, {
    title: '设备名称',
    dataIndex: 'device_name',
  }, {
    title: '设备状态',
    dataIndex: 'device_state',
  }];
//数据
const data = [];
for (let i = 0; i < 46; i++) {
  data.push({
    key:`enteroscopy_${i}`,
    serial: i,
    device_type:`enteroscopy_${i}`,
    device_name: "Gasro_Name_"+i,
    device_state: "正常",
  });
}

/*
组件 : PCSystemDeviceStatePage
作用 : 系统管理 / 设备状态 页面
*/
export default class PCSystemDeviceStatePage extends React.Component{

  render(){
    return (
        <div>
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px 0' }} separator=">">
            <Breadcrumb.Item>系统管理</Breadcrumb.Item>
            <Breadcrumb.Item><Link to="sys_devicestate">设备状态</Link></Breadcrumb.Item>
          </Breadcrumb>

          {/*表格和选项*/}
          <div className="pages_container_without_breadcrumb" >
            <div className="basic_table_and_form_container" style={{paddingTop:"12px"}}>
              <Table dataSource={data} columns={columns} />
            </div>
          </div>
        </div>
    )
  }
}
