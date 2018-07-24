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
              <FormItem label="内镜编号" {...formItemLayout}>
                {getFieldDecorator("gastro_id")(
                  <Input placeholder="内镜编号" />
                )}
              </FormItem>
            </Col>
            <Col span={6} >
              <FormItem label="内镜名称" {...formItemLayout}>
                {getFieldDecorator("gastro_name")(
                  <Input placeholder="内镜名称" />
                )}
              </FormItem>
            </Col>
            <Col span={6} >
              <FormItem label="内镜类型" {...formItemLayout}>
                {getFieldDecorator("gastro_type")(
                  <Input placeholder="内镜类型" />
                )}
              </FormItem>
            </Col>
            <Col span={6} >
              <FormItem label="审核结果" {...formItemLayout}>
                {getFieldDecorator("result")(
                  <Select showSearch placeholder="请选择要查询的结果" >
                    <Select.Option value="ALL">全部</Select.Option>
                    <Select.Option value="OK">合格</Select.Option>
                    <Select.Option value="NO">不合格</Select.Option>
                  </Select>
                )}
              </FormItem>
            </Col>
          </Row>

          <Row gutter={24}>
            <Col span={6} >
              <FormItem label="洗消人员" {...formItemLayout}>
                {getFieldDecorator("wash_operator")(
                  <Input placeholder="洗消人员" />
                )}
              </FormItem>
            </Col>

            <Col span={6} >
              <FormItem {...formItemLayout} label="起止时间" >
                {getFieldDecorator('range-picker', dateRangeConfig)(
                  <RangePicker allowClear  placeholder={["开始","结束"]}/>
                )}
              </FormItem>
            </Col>

            <Col span={6} >
              <FormItem label="洗消日期" {...formItemLayout}>
                {getFieldDecorator("wash_data")(
                  <DatePicker allowClear placeholder="可选择某日" showToday />
                )}
              </FormItem>
            </Col>

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

/*
组件 : PCStatisticsManualPage
作用 : 统计查询 / 手工洗消日志
*/
export default class PCStatisticsManualPage extends React.Component{

  render(){

    return (
        <div>
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px 0' }} separator=">">
            <Breadcrumb.Item>统计查询</Breadcrumb.Item>
            <Breadcrumb.Item><Link to="/statistics_manual">手工洗消日志</Link></Breadcrumb.Item>
          </Breadcrumb>

          {/*表格和选项*/}
          <div className="pages_container_without_breadcrumb" >
            {/*查询条件 Form */}
            <WrappedAdvancedSearchForm />

            {/*横向分割线*/}
            <div style={{margin:"12px"}}><Divider /></div>

            {/*查询结果 -- 表 */}
            <PCTableNormalInFormComponent data={data} columns={columns} totalWidth={1780} needDownloadTable={true}/>
          </div>
        </div>
    )
  }
}
