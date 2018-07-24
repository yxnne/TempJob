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
    title: '工作站名称',
    dataIndex: 'station_name',
  }, {
    title: '人员数量',
    dataIndex: 'staff_nbr',
  }, {
    title: '清洗槽数量',
    dataIndex: 'channel_nbr',
  }, {
    title: 'RFID地址',
    dataIndex: 'rfid_address',
  }, {
    title: '备注',
    dataIndex: 'reamrk',
  }];
//数据
const data = [];
for (let i = 0; i < 46; i++) {
  data.push({
    key:`enteroscopy_${i}`,
    serial: i,
    station_name:`清洗工作站_${i}`,
    staff_nbr: 20,
    channel_nbr: 4,
    rfid_address: `192.168.124.15`,
    reamrk: `No Need Remarks`,
  });
}

export default class MobileManageStationPage extends React.Component{


  render(){

    return (
        <div>
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px ' }}>
            <Breadcrumb.Item>业务管理</Breadcrumb.Item>
            <Breadcrumb.Item>工作站管理</Breadcrumb.Item>
          </Breadcrumb>

          {/*表格和选项*/}
          <div id="pages_container_without_breadcrumb" >
            <MobileTableBasicNoFormComponent data={data} columns={columns}
            needTree needTreeSetting iconBtnType="plus" />
          </div>
        </div>
    )
  }
}
