/* 本文件对应业务：内镜管理 */
import React from 'react';
import {Link} from 'react-router-dom';
import {Breadcrumb, Table, Divider, Form, Row, Col, Input, Button, Icon, DatePicker, Select, Layout} from 'antd';

//组件引入
import PCTableNormalInFormComponent from '../components/pc_table_normal_in_form';
import PCTabListComponent from '../components/pc_tab_list';

const {Content, Sider } = Layout;
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
      labelCol: { lg: 8, sm:8, xxl:6},
      wrapperCol: { lg: 14, sm:14, xxl:16},
    };

    const dateRangeConfig = {
      rules: [{ type: 'array', required: false, message: 'Please select time!' }],
    };
    return (
      <div className="search_form_container">
        <Form onSubmit={this.handleSearchClick.bind(this)}>

          <Row gutter={24}>
            <Col span={6} >
              <FormItem label="患者编号" {...formItemLayout}>
                {getFieldDecorator("patient_id")(
                  <Input placeholder="患者编号" />
                )}
              </FormItem>
            </Col>
            <Col span={6} >
              <FormItem label="患者姓名" {...formItemLayout}>
                {getFieldDecorator("patient_name")(
                  <Input placeholder="患者姓名" />
                )}
              </FormItem>
            </Col>
            <Col span={6} >
              <FormItem label="患者就诊号" {...formItemLayout}>
                {getFieldDecorator("patient_hospital_id")(
                  <Input placeholder="患者就诊号" />
                )}
              </FormItem>
            </Col>
            <Col span={6} >
              <FormItem label="洗消人员" {...formItemLayout}>
                {getFieldDecorator("wash_operator")(
                  <Input placeholder="洗消人员" />
                )}
              </FormItem>
            </Col>
          </Row>

          <Row gutter={24}>
            <Col span={6} >
              <FormItem {...formItemLayout} label="洗消时间" >
                {getFieldDecorator('wash_time_end', dateRangeConfig)(
                  <RangePicker allowClear  placeholder={["开始","结束"]}/>
                )}
              </FormItem>
            </Col>

            <Col span={6} >
              <FormItem {...formItemLayout} label="使用时间" >
                {getFieldDecorator('use_time', dateRangeConfig)(
                  <RangePicker allowClear  placeholder={["开始","结束"]}/>
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
    title: '洗消时间',
    dataIndex: 'time_end',
    width: 150,
  }, {
    title: '洗消人员',
    dataIndex: 'wash_operator',
    width: 150,
  }, {
    title: '使用时间',
    dataIndex: 'time_use',
    width: 150,
  }, {
    title: '患者姓名',
    dataIndex: 'patient_name',
    width: 150,
  }, {
    title: '患者编号',
    dataIndex: 'patient_id',
    width: 150,
  }, {
    title: '患者就诊号',
    dataIndex: 'patient_hospital_id',
    width: 150,
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

/*
组件 : PCStatisticsUseLogPage
作用 : 统计查询 / 内镜使用日志
*/
export default class PCStatisticsUseLogPage extends React.Component{

  render(){

    return (
        <div>
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px 0' }} separator=">">
            <Breadcrumb.Item>统计查询</Breadcrumb.Item>
            <Breadcrumb.Item><Link to="/statistics_uselog">内镜使用日志</Link></Breadcrumb.Item>
          </Breadcrumb>

          {/*表格和选项*/}
          <div className="pages_container_without_breadcrumb" >
            <Layout>
              <Sider width={240} style={{ background: '#fff' }}>

                <PCTabListComponent />

              </Sider>
              <Divider type="vertical" />
              <Content style={{ background: '#fff'}}>

                {/*查询条件 Form */}
                <WrappedAdvancedSearchForm />

                {/*横向分割线*/}
                <div style={{margin:"12px"}}><Divider /></div>
                {/*查询结果 -- 表 */}
                <PCTableNormalInFormComponent data={data} columns={columns} totalWidth={1200} needDownloadTable={true}/>
              </Content>
            </Layout>
          </div>
        </div>
    )
  }
}
