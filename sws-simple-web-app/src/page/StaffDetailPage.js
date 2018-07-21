import React, { Component } from 'react';
import { WhiteSpace } from 'antd-mobile'
import { connect } from 'react-redux';

import StaffDetailCard from '../component/staffDetailCard/StaffDetailCard';
import BackToTop from '../component/backToTop/BackToTop';
import * as constants from '../constant';
import {textOnlyToast } from '../component/toasts/Toasts';

const BACK_PATH = constants.PATH_HOSPITAL_RANK;

/** 员工详情页面 */
@connect(
  state => ({
    staffs:state.staffListStatistic.list
  }),
  null
)
export default class StaffDetailPage extends Component {

  constructor(props){
    super(props);

    this.state = {
      index:0, 
      total:0
    }

    this.onFowardClick = this.onFowardClick.bind(this);
    this.onBackClick = this.onBackClick.bind(this);
  }

  componentDidMount(){
    // 获得页面参数
    const index  = this.props.match.params.rank - 1 ;
    const total = this.props.staffs?this.props.staffs.length:0;
    this.setState({
      index,total
    });
  }

  onFowardClick(){
    if (this.state.index === 0){
      textOnlyToast('当前是第一，无法再向前');
      return;
    }
    const newIndex = this.state.index - 1;
    this.setState({
      index:newIndex
    });
  }

  onBackClick(){
    if (this.state.index === this.state.total - 1){
      textOnlyToast('当前是最后了，无法再向后');
      return;
    }
    const newIndex = this.state.index + 1;
    this.setState({
      index:newIndex
    });
  }

  render() {
    // 从stafflist中获得具体员工信息
    const oneInfo = this.props.staffs[this.state.index];

    return (
      <div>
        <BackToTop backPath={BACK_PATH} title="员工详情" />
        <WhiteSpace size="xl" />

        <div style={{display:'table', width:'100%'}}>
          <div style={{display:'table-row',width:'100%'}}>
            <div style={{display:'table-cell',width:'12%', verticalAlign: 'middle' }}>
              <div style={{margin:'0 auto', width:20, height:40 }} onClick={this.onFowardClick}>
                <img style={{width:18, height:18}} 
                src={require('./image/front.svg')} alt=''/>
              </div>

            </div>

            <div style={{display:'table-cell',  width:'76%'}}>
              <StaffDetailCard staffInfo={oneInfo}/>
            </div>

            <div style={{display:'table-cell', width:'12%',  verticalAlign: 'middle' }}>
              <div style={{margin:'0 auto', width:20, height:40}} onClick={this.onBackClick}>
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
