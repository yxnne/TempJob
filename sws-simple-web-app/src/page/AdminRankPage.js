import React, { Component } from 'react';
import { Tabs, WingBlank } from 'antd-mobile';
import { StickyContainer, Sticky } from 'react-sticky';
import { view as StaffRanks } from '../module/staffRate_list';
import { view as DepartmentRanks } from '../module/department_list';


function renderTabBar(props) {
  return (<Sticky>
    {({ style }) => <div style={{ ...style, zIndex: 1 }}><Tabs.DefaultTabBar {...props} /></div>}
  </Sticky>);
}

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
  render() {
    return (
      <div>
        <StickyContainer>
          <Tabs tabs={tabs} initialPage={0}
            onChange={(tab, index) => { console.log('onChange', index, tab); }}
            onTabClick={(tab, index) => { console.log('onTabClick', index, tab); }} 
            renderTabBar={renderTabBar}>
            
            <div style={containerStyle}>
              <WingBlank>
                <DepartmentRanks />
              </WingBlank>
            </div>
            
            <div style={containerStyle}>
              <WingBlank>
                <StaffRanks />
              </WingBlank>
            </div>

          </Tabs>
        </StickyContainer>
      </div>
    )
  }
}
