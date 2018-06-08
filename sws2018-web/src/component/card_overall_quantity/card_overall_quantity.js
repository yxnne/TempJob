import React from 'react';
import { Row, Col, Card, Progress } from 'antd';
import { Chart, Geom, Axis, Tooltip, Coord, Label, Legend, View, Guide, Shape } from 'bizcharts';
import { DataView } from '@antv/data-set';
const { Html } = Guide;
/**
 * 卡片，按类型使用量(饼图)以及总使用量，和趋势
 */
class CardOverallQuantityComponent extends React.Component{

  render(){
    const cardStyle = {
      marginTop:12,
      borderRadius:10,
      minHeight:300,
      height:700
    };
    const allTimes = this.props.doTimes + this.props.notDoTimes;
    let percent = allTimes === 0 ? 0: this.props.doTimes / allTimes * 100;
    percent = percent.toFixed(1); // 保留小数位

    const data = [
      { item: '类型1', count: 400 },
      { item: '类型2', count: 2100 },
      { item: '类型3', count: 1700 },
      { item: '类型4', count: 1314 },
      { item: '类型5', count: 900 }
    ];
    const dv = new DataView();
    dv.source(data).transform({
      type: 'percent',
      field: 'count',
      dimension: 'item',
      as: 'percent'
    });
    const cols = {
      percent: {
        formatter: val => {
          val = (val * 100) + '%';
          return val;
        }
      }
    }


    const dataL = [
      { year: "1991", value: 3 },
      { year: "1992", value: 4 },
      { year: "1993", value: 3.5 },
      { year: "1994", value: 5 },
      { year: "1995", value: 4.9 },
      { year: "1996", value: 6 },
      { year: "1997", value: 7 },
      { year: "1998", value: 9 },
      { year: "1999", value: 13 }
    ];

    const colsL = {
      'value': { min: 0 },
      'year': {range: [ 0 , 1] }
    };
    return (
      <Card style={cardStyle}>
        <h3>洗手液使用情况</h3>

        <div className="">
          总体使用 <span>XXX: </span> mL
        </div>

        {/* 饼图 */}
        <div>
          <Chart height={350} data={dv} scale={cols} padding={[ 80, 100, 80, 80 ]} forceFit>
            <Coord type={'theta'} radius={0.75} innerRadius={0.6} />
            <Axis name="percent" />
            <Legend position='right' offsetY={-400 / 2 + 120} offsetX={-80} />
            <Tooltip
              showTitle={false}
              itemTpl='<li><span style="background-color:{color};" class="g2-tooltip-marker"></span>{name}: {value}</li>'
              />

            <Geom
              type="intervalStack"
              position="percent"
              color='item'
              tooltip={['item*percent',(item, percent) => {
                percent = (percent * 100).toFixed(1) + '%';
                return {
                  name: item,
                  value: percent
                };
              }]}
              style={{lineWidth: 1,stroke: '#fff'}}
              >
              <Label content='percent' formatter={(val, item) => {

                  percent = (item.point.percent * 100).toFixed(1) + '%';
                  return item.point.item + ': ' + percent;}} />
            </Geom>
          </Chart>

        </div>


        {/* 折线图 */}
        <div>

          <Chart height={300} data={dataL} scale={colsL} forceFit>
            <Axis name="year" />
            <Axis name="value" />
            <Tooltip crosshairs={{type : "y"}}/>
            <Geom type="line" position="year*value" size={2} />
            <Geom type='point' position="year*value" size={4} shape={'circle'} style={{ stroke: '#fff', lineWidth: 1}} />
          </Chart>
        </div>



      </Card>
    );
  }
}

export default CardOverallQuantityComponent;
