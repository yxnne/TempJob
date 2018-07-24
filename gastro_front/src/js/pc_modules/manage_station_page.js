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

/*
组件 : PCManageDevicePage
作用 : 业务管理 / 工作站管理页面
*/
export default class PCManageStationPage extends React.Component{

  render(){

    return (
        <div>
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px 0' }} separator=">">
            <Breadcrumb.Item>业务管理</Breadcrumb.Item>
            <Breadcrumb.Item><Link to="manage_station">工作站管理</Link></Breadcrumb.Item>
          </Breadcrumb>

          {/*表格和选项*/}
          <div className="pages_container_without_breadcrumb" >

            <Layout>
              <Sider width={240} style={{ background: '#fff' }}>

                <PCOrganiseTreeComponent needActoion={true}/>

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
