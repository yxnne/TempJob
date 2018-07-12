import React, { Component } from 'react';
import { Switch, Route } from 'react-router-dom';

import NavTab from '../component/navTab/NavTab';
import AdminMainHospitalPage from './AdminMainHospitalPage';
import AdminRankPage from './AdminRankPage';

import * as constants from '../constant';

const pageList = [
  {
    path:constants.PATH_HOSPITAL_OVERALL,
    component:AdminMainHospitalPage,
    title:'概览',
    icon:'overall',
  },
  {
    path:constants.PATH_HOSPITAL_RANK,
    component:AdminRankPage,
    title:'排名',
    icon:'rank',
  }
];


export default class AdminMainTabBar extends Component {

  constructor(props){
    super(props);
    this.state = {selectedTab:"blueTab"};
  }

  render() {
    return (
      <div>
        <Switch>
          <div className="tab_center_coantainer">
          {
            pageList.map(item=>(<Route key={item.path} path={item.path} component={item.component} /> ))
          }
          </div>
        </Switch>

        <NavTab data={pageList} />
        
      </div>
    )
  }
}
