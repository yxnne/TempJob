import React, { Component } from 'react';
//import { createForm } from 'rc-form';
import { Card, WhiteSpace,  DatePicker, List, Button, Checkbox } from 'antd-mobile';
import PropTypes from 'prop-types';
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
      conditionHide: true,//是否隐藏
      start:"", // 开始时间
      end:"",   // 结束时间
      departData:departData   // 部门列表
    };

    this.onDepartmentSelected = this.onDepartmentSelected.bind(this);
    this.toggleConditionHide = this.toggleConditionHide.bind(this);
    this.onCheckClick = this.onCheckClick.bind(this);
  }

  toggleConditionHide(){
    const hide = this.state.conditionHide;
    this.setState({
      conditionHide:!hide
    });
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

  onCheckClick(){
    console.log("this.state.start", this.state.start )
    console.log("this.state.end", this.state.end )
    console.log("this.state.departData", this.state.departData )

    if(this.props.checkCallback ){
      // 这里具体的形式待定
      this.props.checkCallback(this.state.start, this.state.end, this.state.departData )
    }
  }

  render() {
    // 是否隐藏
    const conditionStyle = this.state.conditionHide?{display:'none'}:{};
    const triggerDisplay = this.state.conditionHide?{}:{display:'none'};
    const triggerStyle = {
      ...triggerDisplay,
      textAlign:'center', 
      marginTop:30,
      marginBottom:10,
      color:'#33A3F4'
    };
    const hideBtn = (
      <div style={{margin:12, marginRight:12}} onClick={this.toggleConditionHide}>隐藏条件选择</div>
    );

    return (

      <div>
        <div style={triggerStyle} onClick={this.toggleConditionHide}>

          查询条件选择

        </div>

        <div style={conditionStyle}>
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
              type="ghost" size="small" onClick={this.onCheckClick}>
                查&nbsp;询
              </CheckButton>
              
              <WhiteSpace />
            </Card.Body>
            <Card.Footer content={null} extra={hideBtn} />
          </Card>

        </div>

      </div>
    )
  }
}

CheckCondition.propTypes = {
  checkCallback:PropTypes.func.isRequired
};

export default CheckCondition;
