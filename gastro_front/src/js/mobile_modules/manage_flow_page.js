/* 本文件对应业务：内镜管理 */
import React from 'react';
import {Breadcrumb, Table, message} from 'antd';

import {
  url_findAllProcess,
  url_updateProcess,
 } from '../utils/urls';

import {post} from '../utils/fetch_request';

//借用下PC端的
import PCTableEditable from '../components/pc_table_editable';
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
组件 : MobileManageFlowPage
作用 : 业务管理 / 流程管理页面
*/
export default class MobileManageFlowPage extends React.Component{

  constructor(){
    super();
    this.state = {
      data:[],
      processId:-1
    };
  }

  componentWillMount(){
    // 请求所有步骤数据
    this.findAllProess();
  }

  // 业务:请求所有步骤数据
  findAllProess(){
    post(url_findAllProcess(), {}) //该接口目前没有请求参数
    .then((reponseData)=>{
      this.setState({
        data:this.encapsulateData(reponseData.result),
      });
    }).catch(err=>{
      console.log("this err is  : ", err);
    });
  }

  // 根据参数封装 每一条数据
  makeProcessDataObj(keyID, name, normal, longest){
    return {
      key:keyID,
      serial: keyID,
      flow_name:name,
      flow_time_normal: normal,
      //flow_time_special: "60秒",
      flow_time_max: longest,
    };
  }

  // 业务 更改数据
  modifyProcessDataObj(modifyData){
    const formData = this.unencapsulateData(modifyData);
    post(url_updateProcess(), formData) //该接口目前没有请求参数
    .then((reponseData)=>{
      //修改成功
      message.success("修改成功");
      this.findAllProess();
    }).catch(err=>{
      // console.log("this err is  : ", err);
      message.error("修改失败");
      this.findAllProess();
    });
  }

  // 封装数据:json - > obj
  encapsulateData(result){
    const allProcessInfo = result[0];
    // 设置ID
    this.setState({processId:allProcessInfo.processId});
    // processData
    let processData = [];
    //共6步
    processData.push(this.makeProcessDataObj(1, "初洗", allProcessInfo.initialNormal, allProcessInfo.initialLongest));
    processData.push(this.makeProcessDataObj(2, "次洗", allProcessInfo.secondNormal, allProcessInfo.secondLongest));
    processData.push(this.makeProcessDataObj(3, "酶洗", allProcessInfo.enzymeNormal, allProcessInfo.enzymeLongest));
    processData.push(this.makeProcessDataObj(4, "浸泡", allProcessInfo.soakNormal, allProcessInfo.soakLongest));
    processData.push(this.makeProcessDataObj(5, "末洗", allProcessInfo.endNormal, allProcessInfo.endLongest));
    processData.push(this.makeProcessDataObj(6, "干燥", allProcessInfo.dryNormal, allProcessInfo.dryLongest));

    return processData
  }

  // 逆向封装
  unencapsulateData(modifyData){
    console.log("modifyData",modifyData);
    return {
      "processId":this.state.processId,
      "updateTime":new Date().getTime(),
      "initialNormal":modifyData[0].flow_time_normal,
      "initialLongest":modifyData[0].flow_time_max,
      "secondNormal":modifyData[1].flow_time_normal,
      "secondLongest":modifyData[1].flow_time_max,
      "enzymeNormal":modifyData[2].flow_time_normal,
      "enzymeLongest":modifyData[2].flow_time_max,
      "soakNormal":modifyData[3].flow_time_normal,
      "soakLongest":modifyData[3].flow_time_max,
      "endNormal":modifyData[4].flow_time_normal,
      "endLongest":modifyData[4].flow_time_max,
      "dryNormal":modifyData[5].flow_time_normal,
      "dryLongest":modifyData[5].flow_time_max,
    };
  }

  render(){

    return (
        <div>
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px ' }}>
            <Breadcrumb.Item>业务管理</Breadcrumb.Item>
            <Breadcrumb.Item>内镜管理</Breadcrumb.Item>
          </Breadcrumb>

          {/*表格和选项*/}
          <div id="pages_container_without_breadcrumb" >
            <PCTableEditable data={this.state.data} columns={columns}
            onSaveChange={this.modifyProcessDataObj.bind(this)}
            onCancelChange={this.findAllProess.bind(this)}/>
          </div>
        </div>
    )
  }
}
