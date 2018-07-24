import React from 'react';
import {Link} from 'react-router-dom';
import {Breadcrumb, Table, Modal, Form, Input, Select, Row, Col, Button, message} from 'antd';

import MobileTableBasicNoFormComponent from '../components/mobile_table_basic_no_form';
import {
  url_findAllUsers,
  url_addNewUser,
  url_deleteByUserId,
  url_updateUser,
} from '../utils/urls';
import {post} from '../utils/fetch_request';

const FormItem = Form.Item;
//本页面用到的表单:添加用户
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

          <FormItem label="用户名" {...formItemLayout}>
            {getFieldDecorator("username", {
              rules: [{ required: true, message: '请您填入添加用户名',},{validator: this.checkInputNoJustSpace.bind(this)}],
              initialValue:defaultSet?defaultSet.username:undefined
            })(
              <Input placeholder="用户名" />
            )}
          </FormItem>

          <FormItem label="用户密码" {...formItemLayout}>
            {getFieldDecorator("password", {
              rules: [{ required: true, message: '密码不能为空',},{validator: this.checkInputNoJustSpace.bind(this)}],
              initialValue:defaultSet?defaultSet.password:undefined
            })(
              <Input placeholder="用户密码" />
            )}
          </FormItem>

          <FormItem label="联系电话" {...formItemLayout}>
            {getFieldDecorator("phoneNumber", {
              rules: [{ required: false, message: '请您填入联系电话',},{validator: this.checkInputNoJustSpace.bind(this)}],
              initialValue:defaultSet?defaultSet.phoneNumber:undefined
            })(
              <Input placeholder="联系电话" />
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
//测试表格
//表格设置
const columns = [{
    title: '序号',
    dataIndex: 'serial',
  }, {
    title: '用户名',
    dataIndex: 'username',
  }, {
    title: '联系电话',
    dataIndex: 'phoneNumber',
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
组件 : MobileSystemUserManagePage
作用 : 系统管理 用户管理
*/
export default class MobileSystemUserManagePage extends React.Component{

  constructor(){
    super();
    this.state = {
      data:[],
      modalAddVisible:false,
      modalModifyVisible:false,
      formDefaultData:undefined,
      modifyId:undefined,
    };
  }
  componentWillMount(){
    this.findAllUser();
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
    // console.log("selectID Data is  ",);
    this.setState({
      formDefaultData:this.findUserByIdFromLocalData(selectID),
      modifyId:selectID,
    });
    console.log("this.findUserByIdFromLocalData(selectID)--",this.findUserByIdFromLocalData(selectID));
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
  findAllUser(){
    post(url_findAllUsers(), {}) //该接口目前没有请求参数
    .then((reponseData)=>{
      this.setState({
        data:this.addKeyToResponseData(reponseData.result),

      });
    }).catch(err=>{
      console.log("this err is  : ", err);
    });
  }
  // 业务:添加用户
  addNewUser(formData){
    console.log("in add new User",formData);
    // formData 是对话框表单中拿到的表单上的数据
    // 这里需要将数据再封装下方能作为参数
    formData.hospitalId = 1;
    formData.createTime = new Date().getTime();

    post(url_addNewUser(), formData) //该接口目前没有请求参数
    .then((reponseData) => {
      if (reponseData.reason == 'success') {
        //设置提示
        message.success("添加成功");
        //重新查询
        this.findAllUser();

      } else {
        console.log("Fail to insert");
        message.error('添加失败');
      }
    }).catch(err=>{
      console.log("this err is  : ", err);
      message.error('添加失败');
    });

    this.closeAddModal();

  }

  // 业务删除用户
  deleteUser(id){
    let formData = {
      "userId":id
    };

    post(url_deleteByUserId(), formData) //该接口目前没有请求参数
    .then((reponseData) => {
      if (reponseData.reason == 'success') {
        //设置提示
        message.success("删除成功");
        //重新查询
        this.findAllUser();

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

  // 业务: 更改用户信息
  modifyUser(formData){
    formData.userId = this.state.modifyId;
    post(url_updateUser(), formData) //该接口目前没有请求参数
    .then((reponseData) => {
      if (reponseData.reason == 'success') {
        //设置提示
        message.success("更改成功");
        //重新查询
        this.findAllUser();

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

  // 后台接口没有提供key这里对数据再封装下，把key弄进去
  addKeyToResponseData(reponseDataResult){
    let tempDatas = reponseDataResult.map((item)=>{
      item.key = item.userId;
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

  // 本地 通过ID查找用户
  findUserByIdFromLocalData(id){
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
            <Breadcrumb.Item>系统管理</Breadcrumb.Item>
            <Breadcrumb.Item>用户管理</Breadcrumb.Item>
          </Breadcrumb>

          {/*表格和选项*/}
          <div id="pages_container_without_breadcrumb" >
            <MobileTableBasicNoFormComponent data={this.state.data} columns={columns} iconBtnType="plus"
            onAddClick={this.openAddModal.bind(this)} addloadingTime={0}
            onDeleteClick={this.deleteUser.bind(this)}
            onModifyClick={this.openModifyModal.bind(this)}
            />
          </div>

          {/* 用作添加条目的模态框 */}
          <Modal title="添加新用户" visible={this.state.modalAddVisible}
          onCancel={()=>{this.closeAddModal()}} footer={null} >

            <WrappedAdvancedSearchForm
            onCancelClickAttached={this.closeAddModal.bind(this)}
            defaultValuesSet={this.state.formDefaultData}
            onOkClickattached={this.addNewUser.bind(this)}
            submitButtonText="添加" submitButtonIcon="plus"/>
          </Modal>

          {/* 用作添加更改的模态框 */}
          <Modal title="更改用户信息" visible={this.state.modalModifyVisible}
          onCancel={()=>{this.closeModifyModal()}} footer={null} >

            <WrappedAdvancedSearchForm onCancelClickAttached={this.closeModifyModal.bind(this)}
            defaultValuesSet={this.state.formDefaultData}
            onOkClickattached={this.modifyUser.bind(this)}
            submitButtonText="更改" submitButtonIcon="setting"/>
          </Modal>
        </div>


    )
  }
}
