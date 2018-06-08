import React from 'react';
import { Row, Col, Card, Progress } from 'antd';
import { Chart, Geom, Axis, Tooltip, Coord, Label, Legend, View, Guide, Shape } from 'bizcharts';
import TableQuantityComponent from '../../component/table/table_quantity/table_quantity';

/**
 * 卡片，表格洗手液瓶使用情况
 */
class CardTableQuantityComponent extends React.Component{

  render(){
    const cardStyle = {
      marginTop:12,
      borderRadius:10,
      minHeight:300,
      height:700
    };

    const tabListNoTitle = [{
        key: '全部类型',
        tab: 'all',
      }, {
        key: '类型1',
        tab: 'type1',
      }, {
        key: '类型2',
        tab: 'type2',
      }, {
        key: '类型3',
        tab: 'type3',
      }, {
        key: '类型4',
        tab: 'type4',
      }, {
        key: '类型5',
        tab: 'type5',
      }];
    const data = [
      {name:'水洗1', type:'类型1', belong:'神经内科', times:90, quantity:90*500},
      {name:'水洗1', type:'类型1', belong:'神经内科', times:90, quantity:90*500},
      {name:'水洗1', type:'类型1', belong:'神经内科', times:90, quantity:90*500},
      {name:'水洗1', type:'类型1', belong:'神经内科', times:90, quantity:90*500},
      {name:'水洗1', type:'类型1', belong:'神经内科', times:90, quantity:90*500},
      {name:'水洗1', type:'类型1', belong:'神经内科', times:90, quantity:90*500},
      {name:'水洗1', type:'类型1', belong:'神经内科', times:90, quantity:90*500},
      {name:'水洗1', type:'类型1', belong:'神经内科', times:90, quantity:90*500},
      {name:'水洗1', type:'类型1', belong:'神经内科', times:90, quantity:90*500},

    ];
    return (
      <Card style={cardStyle}  tabList={tabListNoTitle} >
        <TableQuantityComponent tableData={data}/>


      </Card>
    );
  }
}

export default CardTableQuantityComponent;
