/* 本文件对应业务：内镜管理 */
import React from 'react';
import {Link} from 'react-router-dom';
import {Breadcrumb, Table ,Layout, Divider} from 'antd';

import PCTableBasicNoFormComponent from '../components/pc_table_basic_no_form';
import PCOrganiseTreeComponent from '../components/pc_organise_tree';

const {Content, Sider } = Layout;
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

/*
组件 : PCManageDevicePage
作用 : 业务管理 / 设备管理页面
*/
export default class PCManageDevicePage extends React.Component{

  render(){

    return (
        <div >
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px 0' }} separator=">">
            <Breadcrumb.Item>业务管理</Breadcrumb.Item>
            <Breadcrumb.Item><Link to="manage_device">设备管理</Link></Breadcrumb.Item>
          </Breadcrumb>

          {/*表格和选项*/}
          <div className="pages_container_without_breadcrumb" >

            <Layout>
              <Sider width={240} style={{ background: '#fff' }}>

                <PCOrganiseTreeComponent needActoion={false}/>

              </Sider>
              <Divider type="vertical" />
              <Content style={{ background: '#fff'}}>
                <PCTableBasicNoFormComponent data={data} columns={columns} />
              </Content>
            </Layout>
          </div>
        </div>
    )
  }
}
