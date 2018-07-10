import React, { Component } from 'react'
import { Card, WhiteSpace } from 'antd-mobile';
import { connect } from 'react-redux';

import RankList from '../../../component/rankList/RankList';

@connect(
  (state)=>({ 
    rankLists:state.departmentListStatistic.list
  }),
  null
)
export default class Container extends Component {
  render() {
    return (
      <div>
        <WhiteSpace size="lg" />
        <Card>
          <Card.Header title="执行次数每日趋势" />
          <Card.Body>
            <RankList dataList={ this.props.rankLists } /> 
          </Card.Body>
          <Card.Footer content={null}  />
        </Card>
        <WhiteSpace size="lg" />
      </div>
    )
  }
}
