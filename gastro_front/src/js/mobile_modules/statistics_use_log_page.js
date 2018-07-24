/* 本文件对应业务：职工管理 */
import React from 'react';
import {Breadcrumb, Table, Modal, Form,DatePicker, Select, Input, Button, Icon, Row, Col} from 'antd';

import MobileTableNormalInForm from '../components/mobile_table_normal_in_form';

const InputGroup = Input.Group;
const Option = Select.Option;

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
      labelCol: { lg: 8, sm:8, xxl:6},
      wrapperCol: { lg: 14, sm:14, xxl:16},
    };

    const dateRangeConfig = {
      rules: [{ type: 'array', required: false, message: 'Please select time!' }],
    };
    return (
      <div className="search_form_container">
        <Form onSubmit={this.handleSearchClick.bind(this)}>

          <FormItem label="患者编号" {...formItemLayout}>
            {getFieldDecorator("patient_id")(
              <Input placeholder="患者编号" />
            )}
          </FormItem>

          <FormItem label="患者姓名" {...formItemLayout}>
            {getFieldDecorator("patient_name")(
              <Input placeholder="患者姓名" />
            )}
          </FormItem>

          <FormItem label="患者就诊号" {...formItemLayout}>
            {getFieldDecorator("patient_hospital_id")(
              <Input placeholder="患者就诊号" />
            )}
          </FormItem>

          <FormItem label="洗消人员" {...formItemLayout}>
            {getFieldDecorator("wash_operator")(
              <Input placeholder="洗消人员" />
            )}
          </FormItem>

          <FormItem {...formItemLayout} label="洗消时间" >
            {getFieldDecorator('wash_time_end', dateRangeConfig)(
              <RangePicker allowClear  placeholder={["开始","结束"]}/>
            )}
          </FormItem>

          <FormItem {...formItemLayout} label="使用时间" >
            {getFieldDecorator('use_time', dateRangeConfig)(
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
    title: '内镜编号',
    dataIndex: 'gastro_id',


  }, {
    title: '洗消时间',
    dataIndex: 'time_end',

  }, {
    title: '洗消人员',
    dataIndex: 'wash_operator',

  }, {
    title: '使用时间',
    dataIndex: 'time_use',

  }, {
    title: '患者姓名',
    dataIndex: 'patient_name',

  }, {
    title: '患者编号',
    dataIndex: 'patient_id',

  }, {
    title: '患者就诊号',
    dataIndex: 'patient_hospital_id',

  }];
//数据
const data = [];
for (let i = 0; i < 46; i++) {
  data.push({
    key:`enteroscopy_${i}`,
    serial: i,
    gastro_id:`enteroscopy_${i}`,
    gastro_name: "Entero_"+i,
    time_end: `11-20 23:15:20`,
    time_use: `10-20 02:15:20`,
    wash_operator:'李小璐(PGOne)',
    patient_name: `甄乃暗`,
    patient_id: "Patient_"+i,
    patient_hospital_id:'Patient_Hos'+i
  });
}

export default class MobileStatisticsUseLogPage extends React.Component{
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
    const gastroSelectTool =(
      <InputGroup compact>
        <Select defaultValue="Option1-1">
          <Option value="Option1-1">胃镜</Option>
          <Option value="Option1-2">肠镜</Option>
        </Select>
        <Select defaultValue="Option2-1">
          <Option value="Option2-1">Gastroscope_01</Option>
          <Option value="Option2-3">Gastroscope_02</Option>
          <Option value="Option2-4">Gastroscope_03</Option>
          <Option value="Option2-5">Gastroscope_04</Option>
          <Option value="Option2-6">Gastroscope_05</Option>

        </Select>
      </InputGroup>
    );
    return (
        <div>
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px' }}>
            <Breadcrumb.Item>统计查询</Breadcrumb.Item>
            <Breadcrumb.Item>内镜使用日志</Breadcrumb.Item>
          </Breadcrumb>

          {/*表格和选项*/}
          <div className="pages_container_without_breadcrumb" >
            <MobileTableNormalInForm data={data} columns={columns}
            iconBtnType="search" tableTool={gastroSelectTool}
            totalWidth={1200} searchClickHanlde={this.searchClickCallback.bind(this)}/>
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
