import React, { Component } from 'react'
import { Card, WhiteSpace } from 'antd-mobile';
import { connect } from 'react-redux';

import { RankList, TYPE_STAFF } from '../../../component/rankList/RankList';
import * as constants from '../../../constant';
const JUMP_PATH_TO_STAFF_DETAIL_PAGE = constants.PATH_STAFF_DETAIL;

@connect(
  (state)=>({ 
    rankLists:state.staffListStatistic.list
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
          <RankList dataList={ this.props.rankLists } type={ TYPE_STAFF } 
            jumpPath={JUMP_PATH_TO_STAFF_DETAIL_PAGE}/> 
          </Card.Body>
          <Card.Footer content={null}  />
        </Card>
        <WhiteSpace size="lg" />
      </div>
    )
  }
}
