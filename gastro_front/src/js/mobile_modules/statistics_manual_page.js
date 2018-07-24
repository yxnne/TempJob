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

          <FormItem label="内镜编号" {...formItemLayout}>
            {getFieldDecorator("gastro_id")(
              <Input placeholder="内镜编号" />
            )}
          </FormItem>

          <FormItem label="内镜名称" {...formItemLayout}>
            {getFieldDecorator("gastro_name")(
              <Input placeholder="内镜名称" />
            )}
          </FormItem>

          <FormItem label="内镜类型" {...formItemLayout}>
            {getFieldDecorator("gastro_type")(
              <Input placeholder="内镜类型" />
            )}
          </FormItem>

          <FormItem label="审核结果" {...formItemLayout}>
            {getFieldDecorator("result")(
              <Select showSearch placeholder="请选择要查询的结果" >
                <Select.Option value="ALL">全部</Select.Option>
                <Select.Option value="OK">合格</Select.Option>
                <Select.Option value="NO">不合格</Select.Option>
              </Select>
            )}
          </FormItem>

          <FormItem label="洗消人员" {...formItemLayout}>
            {getFieldDecorator("wash_operator")(
              <Input placeholder="洗消人员" />
            )}
          </FormItem>

          <FormItem {...formItemLayout} label="起止时间" >
            {getFieldDecorator('range-picker', dateRangeConfig)(
              <RangePicker allowClear  placeholder={["开始","结束"]}/>
            )}
          </FormItem>

          <FormItem label="洗消日期" {...formItemLayout}>
            {getFieldDecorator("wash_data")(
              <DatePicker allowClear placeholder="可选择某日" showToday />
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
    width: 80,
    fixed: 'left'
  }, {
    title: '内镜编号',
    dataIndex: 'gastro_id',
    width: 150,
    fixed: 'left'
  }, {
    title: '内镜名称',
    dataIndex: 'gastro_name',
    width: 150,
    fixed: 'left'
  }, {
    title: '初洗',
    dataIndex: 'wash_frist',
    width: 100,
  }, {
    title: '酶洗',
    dataIndex: 'wash_enzyme',
    width: 100,
  }, {
    title: '次洗',
    dataIndex: 'wash_second',
    width: 100,
  }, {
    title: '浸泡',
    dataIndex: 'wash_soak',
    width: 100,
  }, {
    title: '末洗',
    dataIndex: 'wash_last',
    width: 100,
  }, {
    title: '干燥',
    dataIndex: 'wash_dry',
    width: 100,
  },  {
    title: '洗消人员',
    dataIndex: 'wash_operator',
    width: 100,
  },  {
    title: '洗消日期',
    dataIndex: 'wash_data',
    width: 150,
  },  {
    title: '开始时间',
    dataIndex: 'time_start',
    width: 150,
  },  {
    title: '结束时间',
    dataIndex: 'time_end',
    width: 150,
  }, {
    title: '耗时',
    width: 150,
    dataIndex: 'time_total',
  }, {
    title: '审核结果',
    dataIndex: 'result',
    fixed: 'right',
    width: 100,
  }];
//数据
const data = [];
for (let i = 0; i < 46; i++) {
  data.push({
    key:`enteroscopy_${i}`,
    serial: i,
    gastro_id:`enteroscopy_${i}`,
    gastro_name: "Entero_"+i,
    wash_frist: "30分20秒",
    wash_enzyme: `130分60秒`,
    wash_second: `300分20秒`,
    wash_soak: `3分`,
    wash_last:'120分20秒',
    wash_dry: `30分20秒`,
    wash_operator: `霍元甲`,
    wash_data: `2017-11-12`,
    time_start: `10-20 02:15:20`,
    time_end: `11-20 23:15:20`,
    time_total: `500分钟20秒`,
    result: `不合格`,
  });
}

export default class MobileStatisticsManualPage extends React.Component{
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
            <Breadcrumb.Item>手工洗消日志</Breadcrumb.Item>

          </Breadcrumb>

          {/*表格和选项*/}
          <div className="pages_container_without_breadcrumb" >
            <MobileTableNormalInForm data={data} columns={columns} iconBtnType="search"
            totalWidth={1780} searchClickHanlde={this.searchClickCallback.bind(this)}/>
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
