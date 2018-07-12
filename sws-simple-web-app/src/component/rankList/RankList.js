import React, { Component } from 'react';
import { List } from 'antd-mobile';
import PropTypes from 'prop-types'
import IconRank from '../iconRank/IconRank';
import { withRouter } from 'react-router-dom';

// 定义下类型
const TYPE_DEPARTMENT = 'TYPE_DEPARTMENT';
const TYPE_STAFF = 'TYPE_STAFF';

const Item = List.Item;
// rank样式
const makeAvatar = (rank, bgColor, textColor) =>(
  <IconRank rank={rank} height={28} bgColor={bgColor} textColor={textColor}>{rank}</IconRank>
);

// 内容样式
const makeDepartmentContent = (name, times) =>{
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

const makeStaffContent = (name, role, times) =>{
  return (
    <div style={{display:'table', width:'100%'}}>
      <div style={{display:'table-row',  width:'100%'}}>
        <div style={{marginLeft:12, display:'table-cell',  width:'40%'}}>
        {name}
        </div>
        <div style={{marginLeft:12, display:'table-cell',  width:'30%'}}>
        {role}
        </div>
        <div style={{marginLeft:12, display:'table-cell',  width:'30%'}}>
        {times}<span style={{fontSize:12, marginLeft:8}}>次</span>
        </div>
      </div>
    </div>

  );
};

/** 组件：排名列表 */
@withRouter
class RankList extends Component {

  onItemClick(rank){
    const path = this.props.jumpPath;
    if(path){
      this.props.history.push(`${path}/${rank}`);
    }
  }

  render() {
    return (
      <div>
        <List>
          {
            this.props.dataList.map(item => (
              <Item thumb={makeAvatar(item.rank, "#0000ff", "white")} key={item.rank}
                arrow="horizontal" onClick={()=>this.onItemClick(item.rank)}>

                { 
                  this.props.type === TYPE_DEPARTMENT?
                  makeDepartmentContent(item.name, item.times)
                  :makeStaffContent(item.name, item.role ,item.times)
                }
              </Item>
            ))
          }
        </List>
      </div>
    )
  }
}

RankList.propTypes = {
  dataList:PropTypes.array.isRequired,
  type:PropTypes.string.isRequired,
};

export { RankList, TYPE_DEPARTMENT, TYPE_STAFF } ;
