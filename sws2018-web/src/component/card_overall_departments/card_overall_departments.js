import React from 'react';
import { Card, List, Avatar } from 'antd';

const data = [
  {
    rank:1,
    color:'#f56a00',
    title: '神经内科',
    detail:'依从率 70% | 执行手卫生 500 次'
  },
  {
    rank:2,
    color:'#7265e6',
    title: '妇产科ICU',
    detail:'依从率 60.9% | 执行手卫生 490 次'
  },
  {
    rank:3,
    color:'#00a2ae',
    title: '肛肠科',
    detail:'依从率 59.8% | 执行手卫生 492 次'
  },

];
/**
 * 卡片，医院部门依从率排名
 */
class CardOverallDepartmentsComponent extends React.Component{

  render(){

    const cardStyle = {
      marginTop:12,
      borderRadius:6,
      minHeight:320
    };

    return (
      <Card style={cardStyle}>
        <h3>科室手卫生执行情况</h3>
        <List style={{marginTop:20, marginLeft:16}}
          itemLayout="horizontal"
          dataSource={data}
          renderItem={item => (
            <List.Item>
              <List.Item.Meta
                avatar={<Avatar style={{ backgroundColor: item.color, verticalAlign: 'middle' }} size="large">
                {item.rank}</Avatar>}
                title={<a href="#" style={{fontSize:16}}>{item.title}</a>}
                description={item.detail}
              />
              <span><a href="#">去了解</a></span>
            </List.Item>
          )}
        />

      </Card>
    );
  }
}

export default CardOverallDepartmentsComponent;
