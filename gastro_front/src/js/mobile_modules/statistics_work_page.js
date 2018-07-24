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

          <FormItem label="员工工号" {...formItemLayout}>
            {getFieldDecorator("staff_id")(
              <Input placeholder="员工工号" />
            )}
          </FormItem>

          <FormItem label="员工姓名" {...formItemLayout}>
            {getFieldDecorator("staff_name")(
              <Input placeholder="员工姓名" />
            )}
          </FormItem>

          <FormItem label="员工角色" {...formItemLayout}>
            {getFieldDecorator("staff_role")(
              <Input placeholder="员工角色" />
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
    title: '员工工号',
    dataIndex: 'staff_id',
  }, {
    title: '员工姓名',
    dataIndex: 'staff_name',
  }, {
    title: '员工角色',
    dataIndex: 'staff_role',
  }, {
    title: '清洗数量',
    dataIndex: 'clear_times',
  }, {
    title: '清洗占比',
    dataIndex: 'clear_rate',
  }];
//数据
const data = [];
for (let i = 0; i < 46; i++) {
  data.push({
    key:`enteroscopy_${i}`,
    serial: i,
    staff_id:`enteroscopy_${i}`,
    staff_name: "あおい そら 先生",
    staff_role: "Little Hot Nurse",
    clear_times: `300`,
    clear_rate: `30%`
  });
}

/*
组件 : MobileStatisticsWorkPage
作用 : 查询统计 / 工作量统计
*/
export default class MobileStatisticsWorkPage extends React.Component{
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
            <Breadcrumb.Item>统计查询</Breadcrumb.Item>
            <Breadcrumb.Item>工作量统计</Breadcrumb.Item>

          </Breadcrumb>

          {/*表格和选项*/}
          <div className="pages_container_without_breadcrumb" >
            <MobileTableNormalInForm data={data} columns={columns} iconBtnType="search"
            totalWidth={1080} searchClickHanlde={this.searchClickCallback.bind(this)}/>
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
