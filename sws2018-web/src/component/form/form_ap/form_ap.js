import React from 'react';
import { Form, Input, Select, DatePicker, Row, Col, Button } from 'antd';
import moment from 'moment';

const FormItem = Form.Item;
const Option = Select.Option;
const RangePicker = DatePicker.RangePicker;
const dateFormat = 'YYYY/MM/DD';
/**
 * 表单组件：用于事件查询
 */
class FormEventComponentUnwrapped extends React.Component{

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
                  <Input placeHolder='请输入职设备名或IMEI码'/>
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
            {/* 状态选择查询框  */}
            <Col span={8}>
              <FormItem {...formItemLayout} label="状态">
                {getFieldDecorator('status', {
                  rules: [],
                })(
                  <Select placeholder='请选择手卫生状态'>
                    <Option value="全部">全部</Option>
                    <Option value="链接">链接</Option>
                    <Option value="断开">断开</Option>

                  </Select>
                )}
              </FormItem>
            </Col>


            <Col span={8} />

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
const FormEventComponent = Form.create()(FormEventComponentUnwrapped);

export default FormEventComponent;
