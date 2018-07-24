/* 本文件对应业务：职工管理 */
import React from 'react';
import {Breadcrumb, Table, Modal, Form,DatePicker, Select, Input, Button, Icon, Row, Col} from 'antd';

import MobileTableNormalInForm from '../components/mobile_table_normal_in_form';

const FormItem = Form.Item;
const RangePicker = DatePicker.RangePicker;
//本页面用到的表单
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
    });
    if (this.props.onOkClickattached) {
      this.props.onOkClickattached();
    }
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

  render() {
    const { getFieldDecorator } = this.props.form

    const formItemLayout = {
      labelCol: { span: 4 },
      wrapperCol: { span: 14 },
    };

    const dateRangeConfig = {
      rules: [{ type: 'array', required: false, message: 'Please select time!' }],
    };
    return (
      <div className="search_form_container">
        <Form onSubmit={this.handleSearchClick.bind(this)}>

          <FormItem label="日志类型" {...formItemLayout}>
            {getFieldDecorator("log_type")(
              <Input placeholder="日志类型" />
            )}
          </FormItem>

          <FormItem label="操作用户" {...formItemLayout}>
            {getFieldDecorator("user")(
              <Input placeholder="操作用户" />
            )}
          </FormItem>

          <FormItem {...formItemLayout} label="起止时间" >
            {getFieldDecorator('range-picker', dateRangeConfig)(
              <RangePicker allowClear  placeholder={["开始","结束"]}/>
            )}
          </FormItem>

          <div id="modal_btn_container">
            <Row>
              <Col sm={12} xs={2}/>
              <Col sm={6} xs={11}>
                <Button type="primary" htmlType="submit" icon="search">查询</Button>
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


const WrappedAdvancedSearchForm = Form.create()(SearchForm);

//测试表格
//表格设置
const columns = [{
    title: '序号',
    dataIndex: 'serial',
  }, {
    title: '日志类型',
    dataIndex: 'log_type',
  }, {
    title: '操作用户',
    dataIndex: 'user',
  }, {
    title: '操作内容',
    dataIndex: 'content',
  }, {
    title: '操作时间',
    dataIndex: 'time',
  }];
//数据
const data = [];
for (let i = 0; i < 46; i++) {
  data.push({
    key:`enteroscopy_${i}`,
    serial: i,
    log_type:`登入`,
    user: "Administrator",
    content: "登入系统",
    time: `2018-01-01 23:59:59`,
  });
}

/*
组件 : MobileSystemLoginlogPage
作用 : 系统管理 / 工作量统计
*/
export default class MobileSystemLoginlogPage extends React.Component{
  constructor(){
    super();
    this.state={
      modalVisible: false
    };
  }

  //显示对话框
  showModal(){
    this.setState({
      modalVisible:true
    });
  }
  //关闭对话框
  closeModal(){
    this.setState({
      modalVisible:false
    });
  }

  searchClickCallback(){
    this.showModal();
  }

  render(){

    return (
        <div>
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px' }}>
            <Breadcrumb.Item>系统管理</Breadcrumb.Item>
            <Breadcrumb.Item>登录日志</Breadcrumb.Item>

          </Breadcrumb>

          {/*表格和选项*/}
          <div className="pages_container_without_breadcrumb" >
            <MobileTableNormalInForm data={data} columns={columns} iconBtnType="search"
            totalWidth={880} searchClickHanlde={this.searchClickCallback.bind(this)}/>
          </div>
          {/*模态框*/}
          <Modal title="请填写查询条件" visible={this.state.modalVisible}
            onCancel={()=>{this.closeModal()}} footer={null} >
            <WrappedAdvancedSearchForm onCancelClickAttached={this.closeModal.bind(this) }
            onOkClickattached={this.closeModal.bind(this)}/>
          </Modal>
        </div>
    )
  }
}
