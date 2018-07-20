import React, { Component } from 'react';
import { connect } from 'react-redux';
import { WingBlank, WhiteSpace } from 'antd-mobile';
import { view as OrganizationStatistic } from '../module/organization_statistic';
import { view as DepartmentListStatisttic} from '../module/department_list';
import { getOrganizationInfo } from '../module/organization_statistic';

const HOSPITAL_ID = 1;
/**
 * Admin系统主页
 */
@connect(
  null,
  dispatch =>({
    getOrganizationInfo:(departmentId)=>dispatch(getOrganizationInfo(departmentId))
  })
)
export default class AdminMainHospitalPage extends Component {

  componentDidMount(){
    this.props.getOrganizationInfo(HOSPITAL_ID);
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
