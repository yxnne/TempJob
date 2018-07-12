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
            <div style={{display:'table-cell',width:'12%', verticalAlign: 'middle' }}>
              <div style={{margin:'0 auto', width:20, height:40 }} onClick={()=>console.log('front')}>
                <img style={{width:18, height:18}} 
                src={require('./image/front.svg')} alt=''/>
              </div>

            </div>

            <div style={{display:'table-cell',  width:'76%'}}>
              <StaffDetailCard />
            </div>

            <div style={{display:'table-cell', width:'12%',  verticalAlign: 'middle' }}>
              <div style={{margin:'0 auto', width:20, height:40}} onClick={()=>console.log('back')}>
                <img style={{width:18, height:18}} 
                src={require('./image/back.svg')} alt=''/>  
              </div>
            </div>


          </div>
        </div>
        

      </div>
    )
  }
}
