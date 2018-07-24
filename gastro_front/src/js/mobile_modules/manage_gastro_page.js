/* 本文件对应业务：内镜管理 */
import React from 'react';
import {Breadcrumb, Table, Modal, Form, Input, Select, Row, Col, Button, message} from 'antd';

import MobileTableBasicNoFormComponent from '../components/mobile_table_basic_no_form';

//测试表格
import {generateFakeDate_gastroscope as fakeData} from '../utils/fakeDataUtil';
import {post} from '../utils/fetch_request';
import {
  url_findAllEndoscopesByPage,
  url_addNewEndoscope,
  url_deleteOneEndoscope,
  url_updateEndoscope,
} from '../utils/urls';

const FormItem = Form.Item;
//表格设置
const columns = [{
    title: '序号',
    dataIndex: 'serial',
  }, {
    title: '内镜名称',
    dataIndex: 'endoscopeName',
  }, {
    title: '内镜编号',
    dataIndex: 'endoscopeNumber',
  }, {
    title: '内镜类型',
    dataIndex: 'endoscopeType',
  }, {
    title: 'RFID识别码',
    dataIndex: 'rfid',
  }];
//数据
const data = fakeData();

//本页面用到的表单:添加内镜
class SearchForm extends React.Component {
  constructor(){
    super();
    this.state = {
      expand: false,
    };
  }

  handleSearchClick(e) {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      console.log('Received values of form: ', values);
      if(!err){
        if (this.props.onOkClickattached) {
          this.props.onOkClickattached(values);
        }
      }
    });

  }

  handleOnCancel() {
    this.props.form.resetFields();
    if (this.props.onCancelClickAttached) {
      this.props.onCancelClickAttached();
    }
  }

  toggle() {
    const { expand } = this.state;
    this.setState({ expand: !expand });
  }

  //这个方法用来校验空格输入
  checkInputNoJustSpace(rule, value, callback){
    const form = this.props.form;
    if (value && value.trim() == "") {
      callback('输入不可用 : 您只输入了空格');
    } else {
      callback();
    }
  }

  render() {
    const { getFieldDecorator } = this.props.form

    const formItemLayout = {
      labelCol: { span: 4 },
      wrapperCol: { span: 14 },
    };

    const dateRangeConfig = {
      rules: [{ type: 'array', required: false, message: 'Please select time!' }],
    };

    let defaultSet = this.props.defaultValuesSet;


    return (
      <div className="search_form_container">
        <Form onSubmit={this.handleSearchClick.bind(this)}>

          <FormItem label="内镜名称" {...formItemLayout}>
            {getFieldDecorator("endoscopeName", {
              rules: [{ required: true, message: '请您填入添加内镜的名称',},{validator: this.checkInputNoJustSpace.bind(this)}],
              initialValue:defaultSet?defaultSet.endoscopeName:undefined
            })(
              <Input placeholder="内镜名称" />
            )}
          </FormItem>

          <FormItem label="内镜编号" {...formItemLayout}>
            {getFieldDecorator("endoscopeNumber", {
              rules: [{ required: true, message: '请您填入添加内镜的编号',},{validator: this.checkInputNoJustSpace.bind(this)}],
              initialValue:defaultSet?defaultSet.endoscopeNumber:undefined
            })(
              <Input placeholder="内镜编号" />
            )}
          </FormItem>

          <FormItem label="RFID设置" {...formItemLayout}>
            {getFieldDecorator("rfid", {
              rules: [{ required: true, message: '请您填入添加设备的RFID识别码',},{validator: this.checkInputNoJustSpace.bind(this)}],
              initialValue:defaultSet?defaultSet.rfid:undefined
            })(
              <Input placeholder="RFID设置" />
            )}
          </FormItem>

          <FormItem label="内镜类型" {...formItemLayout}>
            {getFieldDecorator("endoscopeType", {
              rules: [{ required: true, message: '请您选择添加内镜类型',}],
              initialValue:defaultSet?defaultSet.endoscopeType:undefined
            })(
              <Select showSearch placeholder="选择内镜的类别" >
                <Select.Option value="胃镜">胃镜</Select.Option>
                <Select.Option value="肠镜">肠镜</Select.Option>

              </Select>
            )}
          </FormItem>

          <FormItem label="备注" {...formItemLayout}>
            {getFieldDecorator("remark", {initialValue:defaultSet?defaultSet.remark:undefined})(
              <Input placeholder="备注" />
            )}
          </FormItem>

          <div id="modal_btn_container">
            <Row>
              <Col sm={12} xs={2}/>
              <Col sm={6} xs={11}>
                <Button type="primary" htmlType="submit" icon={this.props.submitButtonIcon}>{this.props.submitButtonText}</Button>
              </Col>
              <Col sm={6} xs={11}>
                <Button style={{ marginLeft: 8 }} onClick={this.handleOnCancel.bind(this)} icon="close">取消</Button>
              </Col>
            </Row>
          </div>
        </Form>
      </div>
    );
  }
}

// antd 表单组件在封装
const WrappedAdvancedSearchForm = Form.create()(SearchForm);


/*
内镜管理页面
*/
export default class MobileManageGastroPage extends React.Component{

  constructor(){
    super();
    this.state = {
      data:undefined,
      modalAddVisible:false,
      modalModifyVisible:false,
      formDefaultData:undefined,
      modifyId:undefined,

    }

    // bind方法
    this.addKeyToResponseData = this.addKeyToResponseData.bind(this);
    this.updateSerial = this.updateSerial.bind(this);
  }
  componentWillMount(){
    // 请求所有员工的数据
    this.findAllEndoscope();
  }
  // 后台接口没有提供key这里对数据再封装下，把key弄进去
  addKeyToResponseData(reponseDataResult){
    let tempDatas = reponseDataResult.map((item)=>{
      item.key = item.endoscopeId;
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

  // 打开添加对话框
  openAddModal(){
    this.setState({modalAddVisible:true });
  }

  // 关闭添加对话框
  closeAddModal(){
    this.setState({modalAddVisible:false });
  }

  // 打开更改对话框
  openModifyModal(selectID){
    this.setState({modalModifyVisible:true });

    console.log("selectID is ",selectID);
    // console.log("selectID Data is  ",);
    this.setState({
      formDefaultData:this.findEndoscopeByIdFromLocalData(selectID),
      modifyId:selectID,
    });
  }

  // 关闭更改对话框
  closeModifyModal(){
    this.setState({
      modalModifyVisible:false ,
      formDefaultData:undefined ,
      modifyId:undefined,
    });
  }


  // 业务 : 查找所有内镜
  findAllEndoscope(){
    post(url_findAllEndoscopesByPage(), {}) //该接口目前没有请求参数
    .then((reponseData)=>{
      this.setState({
        data:this.addKeyToResponseData(reponseData.result.list),

      });
    }).catch(err=>{
      console.log("this err is  : ", err);
    });
  }

  // 业务 : 添加内镜
  addNewEndoScope(formData){

    console.log("in add new endoscope",formData);
    // formData 是对话框表单中拿到的表单上的数据
    // 这里需要将数据再封装下方能作为参数
    formData.hospitalId = 1;
    formData.stationId = 1;
    formData.createTime = new Date().getTime();

    post(url_addNewEndoscope(), formData) //该接口目前没有请求参数
    .then((reponseData) => {
      if (reponseData.reason == 'success') {
        //设置提示
        message.success("插入成功");
        //重新查询
        this.findAllEndoscope();

      } else {
        console.log("Fail to insert");
        message.error('插入失败');
      }
    }).catch(err=>{
      console.log("this err is  : ", err);
      message.error('插入失败');
    });

    this.closeAddModal();
  }

  // 业务 :删除内镜
  deleteEndoscope(endoscopeID){
    let formData = {
      "endoscopeId":endoscopeID
    };

    post(url_deleteOneEndoscope(), formData) //该接口目前没有请求参数
    .then((reponseData) => {
      if (reponseData.reason == 'success') {
        //设置提示
        message.success("删除成功");
        //重新查询
        this.findAllEndoscope();

      } else {
        console.log("Fail to insert");
        message.error('删除失败');
      }
    }).catch(err=>{
      console.log("this err is  : ", err);
      message.error('删除失败');
    });

    this.closeAddModal();
  }

  // 业务 : 更改内镜信息
  modifyEndoscope(formData){
    formData.endoscopeId = this.state.modifyId;
    post(url_updateEndoscope(), formData) //该接口目前没有请求参数
    .then((reponseData) => {
      if (reponseData.reason == 'success') {
        //设置提示
        message.success("更改成功");
        //重新查询
        this.findAllEndoscope();

      } else {
        console.log("Fail to insert");
        message.error('更改失败');
      }
    }).catch(err=>{
      console.log("this err is  : ", err);
      message.error('更改失败');
    });

    this.closeModifyModal();

  }

  // 本地 通过ID查找员工
  findEndoscopeByIdFromLocalData(id){
    let index = 0;
    while (index < this.state.data.length){
      const item = this.state.data[index];
      if (item.key == id){
        return item;
      }
      index ++;
    }
    return undefined;
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
            <MobileTableBasicNoFormComponent data={this.state.data} columns={columns} iconBtnType="plus"
            onAddClick={this.openAddModal.bind(this)} addloadingTime={0}
            onDeleteClick={this.deleteEndoscope.bind(this)}
            onModifyClick={this.openModifyModal.bind(this)}/>
          </div>

          {/* 用作添加条目的模态框 */}
          <Modal title="添加一条内镜" visible={this.state.modalAddVisible}
          onCancel={()=>{this.closeAddModal()}} footer={null} >

            <WrappedAdvancedSearchForm
            onCancelClickAttached={this.closeAddModal.bind(this)}
            defaultValuesSet={this.state.formDefaultData}
            onOkClickattached={this.addNewEndoScope.bind(this)}
            submitButtonText="添加" submitButtonIcon="plus"/>
          </Modal>

          {/* 用作添加更改的模态框 */}
          <Modal title="更改内镜信息" visible={this.state.modalModifyVisible}
          onCancel={()=>{this.closeModifyModal()}} footer={null} >

            <WrappedAdvancedSearchForm onCancelClickAttached={this.closeModifyModal.bind(this)}
            defaultValuesSet={this.state.formDefaultData}
            onOkClickattached={this.modifyEndoscope.bind(this)}
            submitButtonText="更改" submitButtonIcon="setting"/>
          </Modal>
        </div>
    )
  }
}
