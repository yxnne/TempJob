/* 本文件对应业务：内镜管理 */
import React from 'react';
import {Link} from 'react-router-dom';
import {Breadcrumb, Table} from 'antd';

import PCTableBasicNoFormComponent from '../components/pc_table_basic_no_form';

//测试表格
//表格设置
const columns = [{
    title: '序号',
    dataIndex: 'serial',
  }, {
    title: '用户账号',
    dataIndex: 'user_id',
  }, {
    title: '用户姓名',
    dataIndex: 'user_name',
  }, {
    title: '联系电话',
    dataIndex: 'user_telephone',
  }, {
    title: '备注',
    dataIndex: 'remark',
  }];
//数据
const data = [];
for (let i = 0; i < 46; i++) {
  data.push({
    key:`enteroscopy_${i}`,
    serial: i,
    user_id:`enteroscopy_${i}`,
    user_name: "习远凹",
    user_telephone: "13566668888",
    remark: `Yu Ar Mi Des Ti Ny`,
  });
}

/*
组件 : PCManageDevicePage
作用 : 系统管理 / 用户管理 页面
*/
export default class PCSystemUserManagePage extends React.Component{

  render(){
    return (
        <div>
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px 0' }} separator=">">
            <Breadcrumb.Item>系统管理</Breadcrumb.Item>
            <Breadcrumb.Item><Link to="sys_usermanage">用户管理</Link></Breadcrumb.Item>
          </Breadcrumb>

          {/*表格和选项*/}
          <div className="pages_container_without_breadcrumb" >
            <PCTableBasicNoFormComponent data={data} columns={columns} />
          </div>
        </div>
    )
  }
}
