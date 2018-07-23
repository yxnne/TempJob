import React, { Component } from 'react';
import { connect } from 'react-redux';
import { WingBlank, WhiteSpace } from 'antd-mobile';
import { view as OrganizationStatistic } from '../module/organization_statistic';
import { view as DepartmentListStatisttic} from '../module/department_list';
import { getOrganizationInfo } from '../module/organization_statistic';
import { getDepartList } from '../module/department_list';
import { getDepartmentTypes } from '../module/departments';
import { getOneWeekStartAndEndTimeString, getTestDaysString } from '../util/dateUtils';

const HOSPITAL_ID = 1;
/**
 * Admin系统主页
 */
@connect(
  null,
  dispatch =>({
    getOrganizationInfo:(departmentId)=>dispatch(getOrganizationInfo(departmentId)),
    getDepartList:(startTime, endTime)=>dispatch(getDepartList(startTime, endTime)),
    getDepartmentTypes:()=>dispatch(getDepartmentTypes())
  })
)
export default class AdminMainHospitalPage extends Component {

  componentDidMount(){
    // 查询一周的信息
    const startTime = getOneWeekStartAndEndTimeString().startTime;
    const endTime = getOneWeekStartAndEndTimeString().endTime;
    this.props.getDepartmentTypes();
    this.props.getOrganizationInfo(HOSPITAL_ID);
    this.props.getDepartList(startTime, endTime);
    
  }

  render() {
    return (
      <div>
        <WingBlank>
          <OrganizationStatistic overallTitle="一周执行情况" tendencyTitle="一周变化趋势"/>
          <DepartmentListStatisttic title="一周科室排名"/>
        </WingBlank>
        
        <WhiteSpace size='xl'/>
        <WhiteSpace size='xl'/>

      </div>
    )
  }
}
