/* 本文件对应业务：内镜管理 */
import React from 'react';
import {Link} from 'react-router-dom';
import {Breadcrumb, Table, Divider, Form, Row, Col, Input, Button, Icon, DatePicker, Select} from 'antd';
// 默认语言为 en-US，如果你需要设置其他语言，推荐在入口文件全局设置 locale
import moment from 'moment';
import 'moment/locale/zh-cn';
moment.locale('zh-cn');

import PCTableNormalInFormComponent from '../components/pc_table_normal_in_form';

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
  }

  handleReset() {
    this.props.form.resetFields();
  }

  toggle() {
    const { expand } = this.state;
    this.setState({ expand: !expand });
  }

  render() {
    const { getFieldDecorator } = this.props.form

    const formItemLayout = {
      labelCol: { span: 6 },
      wrapperCol: { span: 16 },
    };

    const dateRangeConfig = {
      rules: [{ type: 'array', required: false, message: 'Please select time!' }],
    };
    return (
      <div className="search_form_container">
        <Form onSubmit={this.handleSearchClick.bind(this)}>

          <Row gutter={24}>
            <Col span={6} >
              <FormItem label="员工工号" {...formItemLayout}>
                {getFieldDecorator("staff_id")(
                  <Input placeholder="员工工号" />
                )}
              </FormItem>
            </Col>
            <Col span={6} >
              <FormItem label="员工姓名" {...formItemLayout}>
                {getFieldDecorator("staff_name")(
                  <Input placeholder="员工姓名" />
                )}
              </FormItem>
            </Col>
            <Col span={6} >
              <FormItem label="员工角色" {...formItemLayout}>
                {getFieldDecorator("staff_role")(
                  <Input placeholder="员工角色" />
                )}
              </FormItem>
            </Col>
            <Col span={6} ></Col>
          </Row>

          <Row gutter={24}>
            <Col span={6} >
            <FormItem {...formItemLayout} label="起止时间" >
              {getFieldDecorator('range-picker', dateRangeConfig)(
                <RangePicker allowClear  placeholder={["开始","结束"]}/>
              )}
            </FormItem>
            </Col>

            <Col span={6} ></Col>

            <Col span={6} ></Col>

          </Row>
          <Row>
            <Col span={24} style={{ textAlign: 'right' }}>
              <Button type="primary" htmlType="submit" icon="search">查询</Button>
              <Button style={{ marginLeft: 8 }} onClick={this.handleReset.bind(this)} icon="close">清空</Button>

            </Col>
          </Row>
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
组件 : PCStatisticsWorkPage
作用 : 查询统计 / 工作量统计
*/
export default class PCStatisticsWorkPage extends React.Component{

  render(){

    return (
        <div>
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px 0' }} separator=">">
            <Breadcrumb.Item>统计查询</Breadcrumb.Item>
            <Breadcrumb.Item><Link to="/statistics_work">工作量统计</Link></Breadcrumb.Item>
          </Breadcrumb>

          {/*表格和选项*/}
          <div className="pages_container_without_breadcrumb" >
            {/*查询条件 Form */}
            <WrappedAdvancedSearchForm />

            {/*横向分割线*/}
            <div style={{margin:"12px"}}><Divider /></div>

            {/*查询结果 -- 表 */}
            <PCTableNormalInFormComponent data={data} columns={columns} needDownloadTable={true}/>
          </div>
        </div>
    )
  }
}
