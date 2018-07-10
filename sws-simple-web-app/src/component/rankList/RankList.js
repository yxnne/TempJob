import React, { Component } from 'react';
import { List } from 'antd-mobile';
import PropTypes from 'prop-types'
import IconRank from '../iconRank/IconRank'

const Item = List.Item;
// rank样式
const makeAvatar = (rank, bgColor, textColor) =>(
  <IconRank rank={rank} height={28} bgColor={bgColor} textColor={textColor}>{rank}</IconRank>
);

// 内容样式
const makeContent = (name, times) =>{
  return (
    <div style={{display:'table', width:'100%'}}>
      <div style={{display:'table-row',  width:'100%'}}>
        <div style={{marginLeft:12, display:'table-cell',  width:'45%'}}>
        {name}
        </div>
        <div style={{marginLeft:12, display:'table-cell',  width:'40%'}}>
        {times}<span style={{fontSize:12, marginLeft:32}}>次</span>
        </div>
      </div>
    </div>

  );
};

/** 组件：排名列表 */
class RankList extends Component {
  render() {
    return (
      <div>
        <List>
          {
            this.props.dataList.map(item => (
              <Item thumb={makeAvatar(item.rank, "#0000ff", "white")} key={item.rank}
                arrow="horizontal" onClick={f=>f}>

                { makeContent(item.name, item.times) }
              </Item>
            ))
          }
        </List>
      </div>
    )
  }
}
RankList.propTypes = {
  dataList:PropTypes.array.isRequired
};

export default RankList;
