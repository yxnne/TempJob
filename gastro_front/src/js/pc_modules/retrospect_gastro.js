/* 本文件对应业务：内镜管理 */
import React from 'react';
import {Link} from 'react-router-dom';
import {Breadcrumb, Table, Divider, Form, Row, Col, Input, Button, Icon, DatePicker, Select, Layout, Card} from 'antd';

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
              <FormItem label="洗消人员" {...formItemLayout}>
                {getFieldDecorator("wash_operator")(
                  <Input placeholder="洗消人员" />
                )}
              </FormItem>
            </Col>
            <Col span={6} >
              <FormItem {...formItemLayout} label="洗消时间" >
                {getFieldDecorator('wash_time_end', dateRangeConfig)(
                  <RangePicker allowClear  placeholder={["开始","结束"]}/>
                )}
              </FormItem>
            </Col>
            <Col span={6} >

            </Col>
            <Col span={6} >
              <Button type="primary" htmlType="submit" icon="search">查询</Button>
              <Button style={{ marginLeft: 8 }} onClick={this.handleReset.bind(this)} icon="close">清空</Button>
            </Col>
          </Row>

          <Row gutter={24}>
            <Col span={6} >

            </Col>

            <Col span={6} >

            </Col>

          </Row>
          <Row>
            <Col span={24} style={{ textAlign: 'right' }}>

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

  });
}

/*
组件 : PCRetrospectGastroPage
作用 : 内镜追溯 / 内镜
*/
export default class PCRetrospectGastroPage extends React.Component{

  render(){
    const labelInCardStyle = {
      textAlign:"center",marginTop:"3px"
    };

    return (
        <div>
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px 0' }} separator=">">
            <Breadcrumb.Item>内镜追溯</Breadcrumb.Item>
            <Breadcrumb.Item><Link to="/retrospect_gastro">内镜详细信息</Link></Breadcrumb.Item>
          </Breadcrumb>

          {/*表格和选项*/}
          <div className="pages_container_without_breadcrumb" >
            <Layout>
              <Sider width={240} style={{ background: '#fff' }}>

                <PCTabListComponent />

              </Sider>
              <Divider type="vertical" />
              <Content style={{ background: '#fff' }} >

                {/*查询条件 Form */}
                <WrappedAdvancedSearchForm />
                {/*横向分割线*/}
                <div style={{margin:"12px"}}><Divider /></div>
                {/*查询结果 -- 表 */}
                <PCTableNormalInFormComponent data={data} columns={columns} totalWidth={1450}
                isPaginationSmiple={true} paginationSize="small" pageNbr={8}/>

                {/*使用信息*/}
                <div className="gastro_use_info_card_container">
                  <Card title="对应使用信息" className="gastro_use_info_card">
                    <Row>
                      <Col span={8} >
                        <Col lg={8} sm={8} xxl={6} style={labelInCardStyle}><label>使用病人:</label></Col>
                        <Col lg={14} sm={14} xxl={16} ><Input placeholder="张三" /></Col>
                      </Col>
                      <Col span={8} >
                        <Col lg={8} sm={8} xxl={6} style={labelInCardStyle}><label>就诊号:</label></Col>
                        <Col lg={14} sm={14} xxl={16} ><Input placeholder="GL33468451" /></Col>
                      </Col>
                      <Col span={8} >
                        <Col lg={8} sm={8} xxl={6} style={labelInCardStyle}><label>检查医生:</label></Col>
                        <Col lg={14} sm={14} xxl={16} ><Input placeholder="李四" /></Col>
                      </Col>

                    </Row>
                    <Row style={{marginTop:"24px"}}>
                      <Col span={8} >
                        <Col lg={8} sm={8} xxl={6} style={labelInCardStyle}><label>本次使用时间 :</label></Col>
                        <Col lg={14} sm={14} xxl={16} ><Input placeholder="2018-01-12" /></Col>
                      </Col>
                      <Col span={8} >
                        <Col lg={8} sm={8} xxl={6} style={labelInCardStyle}><label>前次使用时间:</label></Col>
                        <Col lg={14} sm={14} xxl={16} ><Input placeholder="2017-01-20" /></Col>
                      </Col>
                      <Col span={8} >
                        <Col lg={8} sm={8} xxl={6} style={labelInCardStyle}><label>上次使用病人:</label></Col>
                        <Col lg={14} sm={14} xxl={16} ><Input placeholder="PG ONE" /></Col>
                      </Col>

                    </Row>
                  </Card>
                </div>
              </Content>
            </Layout>
          </div>
        </div>
    )
  }
}
