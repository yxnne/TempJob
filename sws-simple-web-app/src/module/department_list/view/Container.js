import React, { Component } from 'react'
import { Card, WhiteSpace } from 'antd-mobile';
import { connect } from 'react-redux';

import { RankList, TYPE_DEPARTMENT } from '../../../component/rankList/RankList';

import * as constants from '../../../constant';

import { getDepartListAction } from '../action';
const JUMP_PATH_TO_DEPARTMENT_STATISTIC_PAGE = constants.PATH_DEPARTMENT_OVERALL;

@connect(
  (state)=>({ 
    rankLists:state.departmentListStatistic.list
  }),
  dispatch =>({
    getDepartListAction:(startTime, endTime)=>dispatch(getDepartListAction(startTime, endTime))
  })
)
export default class Container extends Component {

  componentDidMount(){
    // 请求数据
    this.props.getDepartListAction('2018-06-20 2009:20:34', '2018-06-27 2009:20:34');
  }

  render() {
    return (
      <div>
        <WhiteSpace size="lg" />
        <Card>
          <Card.Header title={this.props.title} />
          <Card.Body>
            <RankList dataList={ this.props.rankLists } type={ TYPE_DEPARTMENT } 
            jumpPath={JUMP_PATH_TO_DEPARTMENT_STATISTIC_PAGE}/> 
          </Card.Body>
          <Card.Footer content={null}  />
        </Card>
        <WhiteSpace size="lg" />
      </div>
    )
  }
}
