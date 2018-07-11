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
          mian page admin
          <OrganizationStatistic />
          <DepartmentListStatisttic/>
        </WingBlank>
        
        <WhiteSpace size='xl'/>
        <WhiteSpace size='xl'/>

      </div>
    )
  }
}
