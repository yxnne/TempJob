import React from 'react';
import { Row, Col, Select, Radio, Icon, DatePicker } from 'antd';
import moment from 'moment';

const { RangePicker } = DatePicker;
const dateFormat = 'YYYY/MM/DD';
const monthFormat = 'YYYY/MM';
const Option = Select.Option;

class FormStatisticDepartmentComponent extends React.Component{

  constructor(){
    super();
    this.handleSelectChange = this.handleSelectChange.bind(this);
    this.onRadioChange = this.onRadioChange.bind(this);
  }

  handleSelectChange(value) {
    console.log(`selected ${value}`);
  }
  onRadioChange(e) {
    // console.log(`radio change ${e.target.value}`);
    if(this.props.handleRadioChange){
      this.props.handleRadioChange(e.target.value);
    }
  }

  render(){
    return (
      <Row gutter={8}>
        <Col span={4}>
          <Select defaultValue="骨科"  style={{width:'100%'}} onChange={this.handleSelectChange }>
            <Option value="骨科">骨科</Option>
            <Option value="神经内科">神经内科</Option>
            <Option value="血透ICU" >血透ICU</Option>
          </Select>
        </Col>
        <Col span={1} />
        <Col span={1}><Icon type="clock-circle-o" style={{marginTop:6, fontSize:16 }}/></Col>
        <Col span={9}>

          <RangePicker
            defaultValue={[moment('2015/01/01', dateFormat), moment('2015/01/01', dateFormat)]}
            format={dateFormat} />
        </Col>


        <Col span={6}>
          <Radio.Group onChange={this.onRadioChange} defaultValue="overall">
            <Radio.Button value="overall">总览</Radio.Button>
            <Radio.Button value="staff">职工</Radio.Button>
          </Radio.Group>
        </Col>


      </Row>
    );
  }
}

export default FormStatisticDepartmentComponent;
