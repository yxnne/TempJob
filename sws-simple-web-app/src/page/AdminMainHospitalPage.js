import React, { Component } from 'react'
import { WingBlank, WhiteSpace } from 'antd-mobile'
import { view as OrganizationStatistic } from '../module/organization_statistic';
import { view as DepartmentListStatisttic} from '../module/department_list'
/**
 * Admin系统主页
 */
export default class AdminMainHospitalPage extends Component {
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
