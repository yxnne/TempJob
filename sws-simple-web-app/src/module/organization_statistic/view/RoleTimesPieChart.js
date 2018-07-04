import React from 'react'
import { Chart, Geom, Axis, Tooltip, Coord, Legend } from 'bizcharts';
import DataSet from '@antv/data-set'

const { DataView } = DataSet;

const cols = {
  percent: {
    formatter: val => {
      val = (val * 100) + '%';
      return val;
    }
  }
}   
// 边距
const ChartPadding = {
  top: 0, right: 0, bottom: 100, left: 0
};


/** 饼图组件: 各种角色的洗手时机 */
export default ({height, data})=>{

  const dv = new DataView();
  dv.source(data).transform({
    type: 'percent',
    field: 'times',
    dimension: 'role',
    as: 'percent'
  });

  return (
    <Chart height={height} data={dv} scale={cols} padding={ChartPadding} forceFit >
      <Coord type={'theta'} radius={0.75} innerRadius={0.6} />
      <Axis name="percent" />
      <Legend position='bottom'  />
  
      <Geom type="intervalStack" position="percent"
        color='role' style={{lineWidth: 1,stroke: '#fff'}}
        tooltip={['times*role', (times, role) => {
          return { //自定义 tooltip 上显示的 title 显示内容等。
          name: role,
          title: role + times,
          value: times
          };
        }]} >
      </Geom>
      <Tooltip showTitle={false} 
        itemTpl='<li><span style="background-color:{color};" class="g2-tooltip-marker"></span>{name}: {value}</li>'
      />
    </Chart>
  )
};
