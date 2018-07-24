/* 本文件对应业务：内镜管理 */
import React from 'react';
import {Link} from 'react-router-dom';
import {Breadcrumb, Table, Divider, Form, Row, Col, Input, Button,
  Icon, DatePicker, Select, Layout, Card, Popconfirm} from 'antd';

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
              <FormItem {...formItemLayout} label="审核结果" >
                {getFieldDecorator('result')(
                  <Select style={{width:"60%"}} placeholder="所有结果">
                    <Option value="all">所有结果</Option>
                    <Option value="ok">合格</Option>
                    <Option value="not_ok" >不合格</Option>
                  </Select>
                )}
              </FormItem>
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


//假数据
//家结果
const fakeResult = ["合格", "不合格", "合格", "合格"];
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
    result:fakeResult[i % fakeResult.length],

  });
}


// 组件编辑状态的样式
const EditableCell = ({ editable, value, onChange }) => (
  <div>
    {editable
      ? <Input compact="true" size="small" style={{ margin: '-5px 0', width:'100%' }} value={value} onChange={e => onChange(e.target.value)} />
      : value
    }
  </div>
);
/*
组件 : PCManageInfoInputPage
作用 : 系统管理 / 信息登记
*/
export default class PCManageInfoInputPage extends React.Component{
  constructor(){
    super();
    this.state = { data };
    //表格设置
    this.columns = [{
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
        title: '开始时间',
        dataIndex: 'time_start',
        width: 150,
        fixed:"left",
        render: (text, record) => this.renderColumns(text, record, 'time_start'),
      }, {
        title: '初洗',
        dataIndex: 'wash_frist',
        width: 100,
        render: (text, record) => this.renderColumns(text, record, 'wash_frist'),
      }, {
        title: '酶洗',
        dataIndex: 'wash_enzyme',
        width: 100,
        render: (text, record) => this.renderColumns(text, record, 'wash_enzyme'),
      }, {
        title: '次洗',
        dataIndex: 'wash_second',
        width: 100,
        render: (text, record) => this.renderColumns(text, record, 'wash_second'),
      }, {
        title: '浸泡',
        dataIndex: 'wash_soak',
        width: 100,
        render: (text, record) => this.renderColumns(text, record, 'wash_soak'),
      }, {
        title: '末洗',
        dataIndex: 'wash_last',
        width: 100,
        render: (text, record) => this.renderColumns(text, record, 'wash_last'),
      }, {
        title: '干燥',
        dataIndex: 'wash_dry',
        width: 100,
        render: (text, record) => this.renderColumns(text, record, 'wash_dry'),
      },  {
        title: '洗消人员',
        dataIndex: 'wash_operator',
        render: (text, record) => this.renderColumns(text, record, 'wash_operator'),
        width: 100,
      },  {
        title: '洗消日期',
        dataIndex: 'wash_data',
        render: (text, record) => this.renderColumns(text, record, 'wash_data'),
        width: 150,
      },   {
        title: '结束时间',
        dataIndex: 'time_end',
        width: 150,
        render: (text, record) => this.renderColumns(text, record, 'time_end'),
      },  {
        title: '审核结果',
        dataIndex: 'result',
        width: 180,
        fixed:"right",
        sorter: (a, b) => a.result.length - b.result.length,
        render: (text, record) =>{
          const { editable } = record;
          let textStyle = {};
          if (record.result=="不合格"){
            textStyle = {color:"red"};
          }else if (record.result=="合格"){
            textStyle = {color:"#46cd71"};
          }

          return (
            <Row>
            <Col style={textStyle} span={12}>
              {record.result}
            </Col>
            <Col span={12}>
            {
              editable ?
                <span>
                  <Popconfirm title="确定这些修改？" onConfirm={() => this.save(record.key)}>
                    <Button shape="circle" icon="check" size="small" ></Button>
                  </Popconfirm>
                  <Divider type="vertical"/>

                  <Button shape="circle" icon="close" size="small" onClick={() => this.cancel(record.key)}></Button>
                </span>
                : <Button shape="circle" icon="edit" size="small" onClick={() => this.edit(record.key)}></Button>
              }

            </Col>
            </Row>
          );
        }
      }];


  }

  renderColumns(text, record, column) {
    return (
      <EditableCell
        editable={record.editable}
        value={text}
        onChange={value => this.handleChange(value, record.key, column)}
      />
    );
  }

  handleChange(value, key, column) {
    const newData = [...this.state.data];
    const target = newData.filter(item => key === item.key)[0];
    if (target) {
      target[column] = value;
      this.setState({ data: newData });
    }
  }

  edit(key) {
    const newData = [...this.state.data];
    const target = newData.filter(item => key === item.key)[0];
    if (target) {
      target.editable = true;
      this.setState({ data: newData });
    }
  }

  save(key) {
    const newData = [...this.state.data];
    const target = newData.filter(item => key === item.key)[0];
    if (target) {
      delete target.editable;
      this.setState({ data: newData });
      //this.cacheData = newData.map(item => ({ ...item }));
    }
  }

  cancel(key) {
    const newData = [...this.state.data];
    const target = newData.filter(item => key === item.key)[0];
    if (target) {
      //Object.assign(target, this.cacheData.filter(item => key === item.key)[0]);
      delete target.editable;
      this.setState({ data: newData });
    }
  }

  render(){
    const labelInCardStyle = {
      textAlign:"center",marginTop:"3px"
    };

    return (
        <div>
          {/*面包屑*/}
          <Breadcrumb style={{ margin: '16px 0' }} separator=">">
            <Breadcrumb.Item>系统管理</Breadcrumb.Item>
            <Breadcrumb.Item><Link to="/retrospect_gastro">信息登记</Link></Breadcrumb.Item>
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
                <PCTableNormalInFormComponent data={data} columns={this.columns} totalWidth={1630}
                isPaginationSmiple={true} paginationSize="small" pageNbr={13}/>

              </Content>
            </Layout>
          </div>
        </div>
    )
  }
}
