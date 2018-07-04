import React, { Component } from 'react'
import { WingBlank } from 'antd-mobile'
import { view as OrganizationStatistic } from '../module/organization_statistic';
/**
 * Admin系统主页
 */
export default class MainPageAdmin extends Component {
  render() {
    return (
      <div>
        <WingBlank>
          mian page admin
          <OrganizationStatistic />
        </WingBlank>

      </div>
    )
  }
}
