/* 本文件对应业务：职工管理 */
import React from 'react';
import {Link} from 'react-router-dom';
import {Breadcrumb, Table} from 'antd';

import PCTableBasicNoFormComponent from '../components/pc_table_basic_no_form';

import {generateFakeDate_staffs as fakeData} from '../utils/fakeDataUtil';
import {url_findDecontaminationRealTime as url} from '../utils/urls';

//测试表格
//表格设置
const columns = [{
    title: '序号',
    dataIndex: 'serial',
  }, {
    title: '工号',
    dataIndex: 'employeeId',
  }, {
    title: '姓名',
    dataIndex: 'employeeName',
  }, {
    title: '角色',
    dataIndex: 'staff_role',
  }, {
    title: 'RFID识别码',
    dataIndex: 'rfid',
  }, {
    title: '备注',
    dataIndex: 'remark',
  }];
//测试数据
const data = fakeData();
/*
组件 : PCManageStaffPage
作用 : 业务管理 / 职工管理页面
*/
export default class PCManageStaffPage extends React.Component{

  render(){

    return (
        <div>
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px 0' }} separator=">">
            <Breadcrumb.Item>业务管理</Breadcrumb.Item>
            <Breadcrumb.Item><Link to="manage_staff">职工管理</Link></Breadcrumb.Item>
          </Breadcrumb>

          {/*表格和选项*/}
          <div className="pages_container_without_breadcrumb" >
            <PCTableBasicNoFormComponent data={data} columns={columns} />
          </div>
        </div>
    )
  }
}
