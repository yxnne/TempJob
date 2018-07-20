import React  from 'react'
import { Chart, Geom, Axis, Tooltip } from 'bizcharts';

// 定义度量
const cols = {
  times: { alias: '洗手次数' }, // y
  day: { alias: '日期' } // x
};

// 边距
const lineChartPadding = {
  top: 20, right: 20, bottom: 50, left: 30
};

/** 条形图组件: 一周的洗手时机 */
export default ({height, data })=>(
  <Chart height={height} data={data} scale={cols} forceFit padding={lineChartPadding}>
    <Axis name="day" />
    <Axis name="times" />
    <Tooltip />
    <Geom type="interval" position="day*times" color="day" />
  </Chart>
);


