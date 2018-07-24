/* 本文件对应业务：内镜管理 */
import React from 'react';
import {Breadcrumb, Table, message} from 'antd';

import {
  url_findAllDevices
 } from '../utils/urls';

import {post} from '../utils/fetch_request';
//测试表格
//表格设置
const columns = [{
    title: '序号',
    dataIndex: 'serial',
  }, {
    title: '设备类型',
    dataIndex: 'deviceType',
  }, {
    title: '设备名称',
    dataIndex: 'deviceName',
  }, {
    title: '所属站点',
    dataIndex: 'deviceAddress',
  },  {
    title: '设备状态',
    dataIndex: 'status',
    render:(text, record) =>{
      let textStyle = record.status != "正常"?
      {color:"red"}:{};
      return (
        <span style={textStyle}>{record.status}</span>
      );
    }
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
组件 : MobileSystemDeviceStatePage
作用 : 系统管理 / 设备状态 页面
*/
export default class MobileSystemDeviceStatePage extends React.Component{

  constructor(){
    super();
    this.state = {
      data:[]
    };
  }

  componentWillMount(){
    // 开启定时查询
    // timerID是为了撤销用
    this.findDeviceState();
    this.timerID = setInterval(
      () => this.findDeviceState(),
      5000
    );

  }

  componentWillUnmount() {
      clearInterval(this.timerID);
  }

  // 业务查询所有设备状态
  findDeviceState(){
    //console.log("in findDeviceState")
    let formData = {
      "pageNum": 0,
      "pageSize": 1,
      "stationId":1
    };

    post(url_findAllDevices(), formData) //该接口目前没有请求参数
    .then((reponseData)=>{
      this.setState({
        data:this.addKeyToResponseData(reponseData.result.list),
      });
    }).catch(err=>{
      console.log("this err is  : ", err);
      message.error("获取设备状态失败");
    });

  }

  addKeyToResponseData(reponseDataResult){
    let tempDatas = reponseDataResult.map((item)=>{
      item.key = item.deviceId;
      return item;
    });
    return this.updateSerial(tempDatas);
  }

  // 更新每列序号
  updateSerial(data){
    data.reverse();
    return data.map((item, index ) =>{
      item.serial = ++index;
      return item;
    });
  }

  render(){

    return (
        <div>
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px ' }}>
            <Breadcrumb.Item>系统管理</Breadcrumb.Item>
            <Breadcrumb.Item>设备状态</Breadcrumb.Item>
          </Breadcrumb>

          {/*表格和选项*/}
          <div id="pages_container_without_breadcrumb" >
            <div className="basic_table_and_form_container" style={{paddingTop:"12px"}}>
              <Table dataSource={this.state.data} columns={columns} />
            </div>
          </div>
        </div>
    )
  }
}
