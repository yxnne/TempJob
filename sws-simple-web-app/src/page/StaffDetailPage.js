import React, { Component } from 'react';
import { WhiteSpace } from 'antd-mobile'

import StaffDetailCard from '../component/staffDetailCard/StaffDetailCard';
import BackToTop from '../component/backToTop/BackToTop';
import * as constants from '../constant';


const BACK_PATH = constants.PATH_HOSPITAL_RANK;

/** 员工详情页面 */
export default class StaffDetailPage extends Component {
  render() {
    return (
      <div>
        <BackToTop backPath={BACK_PATH} title="员工详情" />
        <WhiteSpace size="xl" />

        <div style={{display:'table', width:'100%'}}>
          <div style={{display:'table-row',width:'100%'}}>
            <div style={{display:'table-cell',width:'10%'}}>


            </div>

            <div style={{display:'table-cell',  width:'80%'}}>
              <StaffDetailCard />
            </div>

            <div style={{display:'table-cell', width:'10%'}}>


            </div>


          </div>
        </div>
        

      </div>
    )
  }
}
