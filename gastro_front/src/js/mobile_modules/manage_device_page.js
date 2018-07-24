/* 本文件对应业务：内镜管理 */
import React from 'react';
import {Breadcrumb, Table} from 'antd';

import MobileTableBasicNoFormComponent from '../components/mobile_table_basic_no_form';

//测试表格
//表格设置
const columns = [{
    title: '序号',
    dataIndex: 'serial',
  }, {
    title: '内镜编号',
    dataIndex: 'gastro_id',
  }, {
    title: '内镜名称',
    dataIndex: 'gastro_name',
  }, {
    title: '内镜类型',
    dataIndex: 'gastro_type',
  }, {
    title: 'RFID识别码',
    dataIndex: 'gastro_rfid',
  }];
//数据
const data = [];
for (let i = 0; i < 46; i++) {
  data.push({
    key:`enteroscopy_${i}`,
    serial: i,
    gastro_id:`enteroscopy_${i}`,
    gastro_name: "Entero_"+i,
    gastro_type: "Enteroscopy",
    gastro_rfid: `RFID_ENTRO_NO11${i*10}${i}`,
  });
}

export default class MobileManageDevicePage extends React.Component{

  render(){

    return (
        <div>
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px' }}>
            <Breadcrumb.Item>业务管理</Breadcrumb.Item>
            <Breadcrumb.Item>设备管理</Breadcrumb.Item>
          </Breadcrumb>

          {/*表格和选项*/}
          <div id="pages_container_without_breadcrumb" >
            <MobileTableBasicNoFormComponent data={data} columns={columns} needTree iconBtnType="plus"/>
          </div>
        </div>
    )
  }
}
