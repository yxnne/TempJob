import React, { Component } from 'react';
import { WingBlank, WhiteSpace } from 'antd-mobile';
import { connect } from 'react-redux'
import { view as OrganizationStatistic } from '../module/organization_statistic';
import { view as StaffRanks } from '../module/staffRate_list';
import BackToTop from '../component/backToTop/BackToTop';
import * as constants from '../constant';

import { getOrganizationInfo } from '../module/organization_statistic';
import { getStaffStatisticList } from '../module/staffRate_list';
import { getOneWeekStartAndEndTimeString, getTestDaysString } from '../util/dateUtils';

const BACK_PATH = constants.PATH_HOSPITAL_OVERALL;

/** 部门主页 */
@connect(
  null,  
  dispatch =>({
    getOrganizationInfo:(departmentId)=>dispatch(getOrganizationInfo(departmentId)),
    getStaffStatisticList:(departmentId,startTime, endTime)=>dispatch(getStaffStatisticList(departmentId,startTime, endTime))
  })
)
export default class AdminMainDepartmentPage extends Component {


  componentDidMount(){
    // set Rank
    // const rank = this.props.match.params.rank;
    // this.setState({ rank });
    // console.log('onMount');
    const startTime = getOneWeekStartAndEndTimeString().startTime;
    const endTime = getOneWeekStartAndEndTimeString().endTime;

    const id = this.props.match.params.id;
    //console.log('id => ', id);
    this.props.getOrganizationInfo(id);
    this.props.getStaffStatisticList([id], startTime, endTime);
    
  }
  render() {
    return (
      <div>

        <BackToTop backPath={BACK_PATH} title="部门概览"/>

        
        <WingBlank>
          <OrganizationStatistic overallTitle="一周执行情况" tendencyTitle="一周变化趋势"/>
          <StaffRanks title="一周员工排名"/>
        </WingBlank>
        
        <WhiteSpace size='xl'/>
        <WhiteSpace size='xl'/>

      </div>
    )
  }
}
