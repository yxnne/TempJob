import React, { Component } from 'react';
import { Tabs, WingBlank } from 'antd-mobile';
import { StickyContainer, Sticky } from 'react-sticky';
import { view as StaffRanks } from '../module/staffRate_list';
import { view as DepartmentRanks } from '../module/department_list';
import { connect } from 'react-redux'

import CheckCondition from '../component/checkCondition/CheckCondition';
import { getStaffStatisticList } from '../module/staffRate_list';
import { getDepartList } from '../module/department_list';
import { getDepartmentTypes } from '../module/departments';
import { getOneWeekStartAndEndTimeString, getTestDaysString } from '../util/dateUtils';

const HOSPITAL_ID = 1;

// 固定Tab
function renderTabBar(props) {
  return (<Sticky>
    {({ style }) => <div style={{ ...style, zIndex: 1 }}><Tabs.DefaultTabBar {...props} /></div>}
  </Sticky>);
}

// Tab内容
const tabs = [
  { title: '部门洗手排名', sub: '1' },
  { title: '人员洗手排名', sub: '2' },
];

const containerStyle = {
  //display: 'flex',
  alignItems: 'center', 
  justifyContent: 'center', 
  mixHeight: '800', 
  marginBottom:40,

};

/** 排名页面 */
@connect(
  state=>({
    departmentTypes:state.departmentTypes
  }), dispatch => ({
    getDepartmentTypes:()=>dispatch(getDepartmentTypes()),
    getDepartList:(startTime, endTime)=>dispatch(getDepartList(startTime, endTime)),
    getStaffList:( departmentIds, startTime, endTime )=>dispatch(getStaffStatisticList(departmentIds, startTime, endTime ))
  })
)
export default class AdminRankPage extends Component {
  constructor(props){
    super(props);

    this.state = { };

    this.onDepartmentCheck = this.onDepartmentCheck.bind(this);
    this.onStaffCheck = this.onStaffCheck.bind(this);
  }

  componentDidMount(){
    // console.log('abcd');
    const startTime = getTestDaysString().startTime;
    const endTime = getTestDaysString().endTime;
    // 默认取一周数据
    this.props.getDepartList(startTime, endTime);
    //this.props.getStaffList( HOSPITAL_ID, startTime, endTime);

    // 看下redux中是否有关于department的数据
    if ( this.props.departmentTypes.length === 0 || 
      Object.keys(this.props.departmentTypes).length === 0 ){
      //调用下接口，请求部门列表
      this.props.getDepartmentTypes();
    } else {
      //全院的数组
      const allDepartmentsIds = this.props.departmentTypes.map(item =>(item.id));
      this.props.getStaffList( allDepartmentsIds, startTime, endTime);
    }

    //console.log('this.props.departmentTypes', this.props.departmentTypes);
  }

  onDepartmentCheck(startTime, endTime){
    this.props.getDepartList( startTime, endTime);
  }

  onStaffCheck( hospitalIds, startTime, endTime ){
    this.props.getStaffList( hospitalIds, startTime, endTime)
  }

  render() {
    return (
      <div>
        <StickyContainer>
          <Tabs tabs={tabs} initialPage={0}
            onChange={(tab, index) => { }}
            onTabClick={(tab, index) => { }} 
            renderTabBar={renderTabBar}>
            
            {/* 第一个Tab内容 -- 部门排名 */}
            <div style={containerStyle}>
              <CheckCondition checkCallback={this.onDepartmentCheck}/>
              <WingBlank>
                <DepartmentRanks title="科室排名"/>
              </WingBlank>
            </div>

            {/* 第一个Tab内容 -- 员工排名 */}
            <div style={containerStyle}>
              <CheckCondition isDepartmentrequired checkCallback={this.onStaffCheck}
              departmentTypes={this.props.departmentTypes}/>
              <WingBlank>
                <StaffRanks title="员工排名"/>
              </WingBlank>
            </div>

          </Tabs>
        </StickyContainer>
      </div>
    )
  }
}
