import React, { Component } from 'react';
import { WingBlank, WhiteSpace } from 'antd-mobile';
import { view as OrganizationStatistic } from '../module/organization_statistic';
import { view as StaffRanks } from '../module/staffRate_list';
import BackToTop from '../component/backToTop/BackToTop';

const BACK_PATH = '/hospitalOverall';

/** 部门主页 */
export default class AdminMainDepartmentPage extends Component {
  constructor(props){
    super(props);
    this.state = {
      rank:0,
    };
  }

  componentDidMount(){
    // set Rank
    const rank = this.props.match.params.rank;
    this.setState({ rank });
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
