import React, { Component } from 'react';
//import { createForm } from 'rc-form';
import { Card, WhiteSpace,  DatePicker, List, Button, Checkbox } from 'antd-mobile';

import { CheckButton } from '../buttons/Buttons';

const CheckboxItem =  Checkbox.CheckboxItem;
const departData = [
  {name:'全院', value:0, checked:true},
  {name:'骨科', value:1, checked:false},
  {name:'神经内科', value:2, checked:false},
  {name:'神经外科', value:3, checked:false},
  {name:'消化科', value:4, checked:false},
  {name:'内分泌科', value:5, checked:false},
];

/** 查询条件 */
class CheckCondition extends Component {
  constructor(props){
    super(props);

    this.state = {
      start:"",
      end:"",
      departData:departData
    };

    this.onDepartmentSelected.bind(this);
  }

  onDepartmentSelected(value){
    console.log(value);
    let newDepartData = this.state.departData;
    newDepartData = newDepartData.map(i => {
      if(i.value === value){
        return {name:i.name, value:i.value, checked: !i.checked}
      } 
      return i;
    });

    this.setState({
      departData:newDepartData
    });

  }

  render() {
    const hideBtn = (
      <div style={{margin:6, marginRight:12}} onClick={f=>console.log('click hide')}>隐藏</div>
    );
    //const { getFieldProps } = this.props.form;
    return (
      

      <div>
        <WhiteSpace size="lg" />
        <Card full>
          <Card.Body>
            <List renderHeader={() => '选择查询时间'} style={{ backgroundColor: 'white' }}>
        
              <DatePicker mode="date"
                title="选择开始时间" extra="Optional"
                value={this.state.start} onChange={start => this.setState({ start })} >
                <List.Item arrow="horizontal">开始时间</List.Item>
              </DatePicker>

              <DatePicker mode="date"
                title="选择结束时间" extra="Optional"
                value={this.state.end} onChange={end => this.setState({ end })} >
                <List.Item arrow="horizontal">结束时间</List.Item>
              </DatePicker>

              {
                this.props.isDepartmentrequired?
                (
                  <List renderHeader={() => '选择查询科室'}>
                    {this.state.departData.map(i => (
                      <CheckboxItem checked={i.checked} key={i.value} onChange={() => this.onDepartmentSelected(i.value)}>
                        {i.name}
                      </CheckboxItem>
                    ))}
                    
                  </List>
                ):(null)
              }

            </List>
            <WhiteSpace size="lg" />
            <CheckButton style={{marginLeft:48, marginRight:48}} 
            type="ghost" size="small" onClick={()=>console.log('abc')}>
              查&nbsp;询
            </CheckButton>
            
            <WhiteSpace />
          </Card.Body>
          <Card.Footer content={null} extra={hideBtn} />
        </Card>

      </div>
    )
  }
}

export default CheckCondition;
