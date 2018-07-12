import React, { Component } from 'react'
import { Card, WhiteSpace } from 'antd-mobile';
import { connect } from 'react-redux';

import { RankList, TYPE_DEPARTMENT } from '../../../component/rankList/RankList';

const JUMP_PATH_TO_DEPARTMENT_STATISTIC_PAGE = '/departmentOverall';

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
