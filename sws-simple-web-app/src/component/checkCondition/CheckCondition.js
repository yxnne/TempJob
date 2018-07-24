import React, { Component } from 'react';
//import { createForm } from 'rc-form';
import { Card, WhiteSpace,  DatePicker, List, Checkbox, Button } from 'antd-mobile';
import PropTypes from 'prop-types';
import { CheckButton } from '../buttons/Buttons';
import { getStringFormDate } from '../../util/dateUtils';

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

    const nowDate = new Date();
    const before7DaysDate = new Date(nowDate.getTime() - 24 * 60 * 60 * 1000 * 7);

    this.state = {
      conditionHide: true,//是否隐藏
      start:before7DaysDate, // 开始时间
      end:nowDate,   // 结束时间
      departData:departData   // 部门列表
    };

    this.onDepartmentSelected = this.onDepartmentSelected.bind(this);
    this.toggleConditionHide = this.toggleConditionHide.bind(this);
    this.onCheckClick = this.onCheckClick.bind(this);
  }

  componentDidMount(){

    if(this.props.departmentTypes){
      const newTypeData = this.props.departmentTypes.map(item=>({
        name:item.name, value:item.id, checked:false 
      }));

      this.setState({
        departData:[ {name:'全院', value:0, checked:true}, ...newTypeData ]
      });
    }
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

    const startDateStr =  getStringFormDate(this.state.start);
    const endDateStr =  getStringFormDate(this.state.end);

    let seletedDepartments = [];
    if(this.state.departData[0].checked){
      //如果全院被选中
      seletedDepartments = this.state.departData
      .map(item=>(item.value))
      .filter(item =>(item !== 0));
      
    } else {
      seletedDepartments = this.state.departData.filter(item=>(
        item.checked === true
      )).map(item=>(
        item.value
      ));
    }

    if(this.props.isDepartmentrequired){
      this.props.checkCallback(seletedDepartments, startDateStr, endDateStr );
    }else{
      this.props.checkCallback(startDateStr, endDateStr );
    }

    // console.log('被作为参数--->', seletedDepartments)

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
      color:'#33A3F4',
      paddingLeft:100,
      paddingRight:100,
    };
    const hideBtn = (
      <div style={{margin:12, marginRight:12}} onClick={this.toggleConditionHide}>隐藏条件选择</div>
    );

    return (

      <div>
        <div style={triggerStyle} >

          <Button type='ghost' size='small' onClick={this.toggleConditionHide}> 查询条件选择 </Button>

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
