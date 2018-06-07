import React from 'react';
import { Row, Col, Select, Radio, Icon, DatePicker } from 'antd';
import moment from 'moment';

const { RangePicker } = DatePicker;
const dateFormat = 'YYYY/MM/DD';
const monthFormat = 'YYYY/MM';
const Option = Select.Option;

class FormStatisticLiquidComponent extends React.Component{

  constructor(){
    super();
    this.handleSelectChange = this.handleSelectChange.bind(this);
  }

  handleSelectChange(value) {
    console.log(`selected ${value}`);
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


      </Row>
    );
  }
}

export default FormStatisticLiquidComponent;
