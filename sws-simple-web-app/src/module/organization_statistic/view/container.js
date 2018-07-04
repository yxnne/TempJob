import React, { Component } from 'react'
import { Flex, WhiteSpace, Card } from 'antd-mobile';
import { connect } from 'react-redux';
import WeekTimesBarChart from './WeekTimesBarChart';
import RoleTimesPieChart from './RoleTimesPieChart';
import { testAction } from '../action';

@connect(
  state => ({
    roleTimesData:state.organizationStatistic.roleTimes,
    weekTimesData:state.organizationStatistic.weekTimes,
  }), 
  // dispatch =>({
  //   test:()=>dispatch(testAction())
  // })
  // 这种注释掉的写法和原生connect的dispatchToProps是一个意思
  {test:testAction}
)
export default class Container extends Component {
  render() {
    return (
      <div>
        module container here
        <WhiteSpace size="lg" />

        <Flex>
          {/* 近一周执行 总次数 */}
          <Flex.Item>
            <button onClick={this.props.test}>Test</button>

          </Flex.Item>
          {/* 近一周执行 按角色饼状图 */}
          <Flex.Item>
            <Card>
              <Card.Header title="近一周执行次数" />
              <Card.Body>
                <RoleTimesPieChart height={200} data={this.props.roleTimesData}/>
              </Card.Body>
              <Card.Footer content={null}  />
            </Card>
          </Flex.Item>
        </Flex>
        
        <WhiteSpace size="lg" />
        {/* 近一周执行次数 柱状图 */}
        <Card>
          <Card.Header title="近一周执行次数" />
          <Card.Body>
            <WeekTimesBarChart height={200} data={this.props.weekTimesData}/>
          </Card.Body>
          <Card.Footer content={null}  />
        </Card>
      </div>
    )
  }
}
