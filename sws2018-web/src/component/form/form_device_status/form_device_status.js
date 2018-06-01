import React from 'react';
import { Form, Input, Select, DatePicker, Row, Col, Button } from 'antd';
import moment from 'moment';

const FormItem = Form.Item;
const Option = Select.Option;
const RangePicker = DatePicker.RangePicker;
const dateFormat = 'YYYY/MM/DD';
/**
 * 表单组件：用于设备状态查询(报警)
 */
class FormDeviceStatusComponentUnwrapped extends React.Component{

  constructor(){
    super();
    this.handleSubmit = this.handleSubmit.bind(this);
  }
  handleSubmit(){
    console.log('handle submit clicked! ');
  }

  render(){
    const { getFieldDecorator } = this.props.form;
    const formItemLayout = {
      labelCol: {
        sm: { span: 4 },
      },
      wrapperCol: {
        sm: { span: 16 },
      },
    };

    return (
      <div>
        <Form onSubmit={this.handleSubmit}>

          <Row gutter={16}>

            {/* 职工查询框  */}
            <Col span={8}>
              <FormItem {...formItemLayout} label="设备">
                {getFieldDecorator('name', {
                  rules: [],
                })(
                  <Input placeHolder='请输入设备名或设备编号'/>
                )}
              </FormItem>
            </Col>

            {/* 时间选择查询框  */}
            <Col span={8}>
              <FormItem {...formItemLayout} label="上报时间">
                {getFieldDecorator('time', {
                  rules: [],
                })(
                  <RangePicker format={dateFormat} showToday
                    defaultValue={[moment('2015/01/01', dateFormat), moment('2015/01/01', dateFormat)]}
                  />
                )}
              </FormItem>
            </Col>
          </Row>

          <Row gutter={16}>
            {/* 事件选择查询框  */}
            <Col span={8}>
              <FormItem {...formItemLayout} label="警报类型">
                {getFieldDecorator('event', {
                  rules: [],
                })(
                  <Select placeholder='请选择查询事件'>
                    <Option value="进入病区">进入病区</Option>
                    <Option value="手卫生完成">手卫生完成</Option>
                    <Option value="解除患者" >解除患者</Option>
                    <Option value="未手卫生">未手卫生</Option>
                  </Select>
                )}
              </FormItem>
            </Col>

            {/* 状态选择查询框  */}
            <Col span={8}>
              <FormItem {...formItemLayout} label="科室所属">
                {getFieldDecorator('status', {
                  rules: [],
                })(
                  <Select placeholder='请选择手卫生状态'>
                    <Option value="清洁">清洁</Option>
                    <Option value="不清洁">不清洁</Option>

                  </Select>
                )}
              </FormItem>
            </Col>

            <Col span={8} >
              <FormItem >
                <Button  type="ghost" style={{marginLeft:20, paddingLeft:28, paddingRight:28}}>清空</Button>
                <Button  icon="search" type="primary" htmlType="submit" style={{marginLeft:20, paddingLeft:28, paddingRight:28}}>查询</Button>
              </FormItem>
            </Col>

          </Row>

      </Form>

      </div>
    );
  }

}

// 封装表单组件
const FormDevicesStatusComponent = Form.create()(FormDeviceStatusComponentUnwrapped);

export default FormDevicesStatusComponent;
