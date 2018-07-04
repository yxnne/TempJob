import React, { Component } from 'react'
import { view as OrganizationStatistic } from '../module/organization_statistic';
/**
 * Admin系统主页
 */
export default class MainPageAdmin extends Component {
  render() {
    return (
      <div>
        mian page admin
        <OrganizationStatistic />
      </div>
    )
  }
}
