import React, { Component } from 'react';
import { Tabs, WingBlank } from 'antd-mobile';
import { StickyContainer, Sticky } from 'react-sticky';
import { view as StaffRanks } from '../module/staffRate_list';
import { view as DepartmentRanks } from '../module/department_list';

import CheckCondition from '../component/checkCondition/CheckCondition';

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

export default class AdminRankPage extends Component {
  constructor(props){
    super(props);

    this.state = {

    };
  }
  render() {
    return (
      <div>
        <StickyContainer>
          <Tabs tabs={tabs} initialPage={0}
            onChange={(tab, index) => { console.log('onChange', index, tab); }}
            onTabClick={(tab, index) => { console.log('onTabClick', index, tab); }} 
            renderTabBar={renderTabBar}>
            
            {/* 第一个Tab内容 -- 部门排名 */}
            <div style={containerStyle}>
              <CheckCondition checkCallback={f=>f}/>
              <WingBlank>
                <DepartmentRanks title="一周科室排名"/>
              </WingBlank>
            </div>

            {/* 第一个Tab内容 -- 员工排名 */}
            <div style={containerStyle}>
              <CheckCondition isDepartmentrequired checkCallback={f=>f}/>
              <WingBlank>
                <StaffRanks title="全院一周员工排名"/>
              </WingBlank>
            </div>

          </Tabs>
        </StickyContainer>
      </div>
    )
  }
}
