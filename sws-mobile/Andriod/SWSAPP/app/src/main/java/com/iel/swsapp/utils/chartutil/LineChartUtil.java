package com.iel.swsapp.utils.chartutil;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.TimeUtils;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.iel.swsapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.RIGHT;

/**
 * Class Full Name  : com.iel.swsapp.utils.ChartUtil
 * Author Name      : yxnne
 * Create Time      : 2017/2/14
 * Project Name     : SWSAPP
 * Descriptions     : 图表相关的工具集
 */
public class LineChartUtil {

    /**
     * 初始化折线图表
     * @param chart 折线图引用
     * @param context 上下文
     * @param xValues x轴名称集合
     * @return 折线图引用
     */
    public static LineChart initLineChart(LineChart chart, final Context context, final List<String> xValues) {

        // 设置显示数据描述
        chart.getDescription().setEnabled(false);
        // 设置没有数据的时候，显示“暂无数据”
        chart.setNoDataText("On Loading ...");
        // 设置显示表格颜色
        chart.setDrawGridBackground(false);
        // 设置可以缩放
        chart.setScaleEnabled(false);
        // 设置显示y轴右边的值
        chart.getAxisRight().setEnabled(false);


        // 不显示图例
        Legend legend = chart.getLegend();
        legend.setEnabled(true);
        legend.setHorizontalAlignment(RIGHT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setForm(Legend.LegendForm.CIRCLE);

        // 向左偏移15dp，抵消y轴向右偏移的30dp
        chart.setExtraLeftOffset(-15);

        /** X 轴相关的设置*/
        XAxis xAxis = chart.getXAxis();
        // 设置显示x轴
        xAxis.setDrawAxisLine(true);
        // 设置x轴数据的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(ContextCompat.getColor(context, R.color.textNormalColor));
        xAxis.setTextSize(14);

        //xAxis.setGridColor(Color.parseColor("#30FFFFFF"));
        //X轴的网格线，设置成透明就看不见了
        xAxis.setGridColor(ContextCompat.getColor(context, R.color.transparent));
        // 设置x轴数据偏移量
        xAxis.setYOffset(0);

        /**X坐标轴设置*/
        //final String[] quarters = new String[] { "Q1", "Q2", "Q3", "Q4","Q5","Q6","Q7" };

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xValues.get((int)value);
                //return quarters[(int) value];
            }


        };
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);

        /** Y轴相关设置*/
        YAxis yAxis = chart.getAxisLeft();
        // 显示y轴,不显示
        yAxis.setDrawAxisLine(false);
        // 设置y轴数据的位置
        yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        //数据显示成透明
        yAxis.setGridColor(ContextCompat.getColor(context, R.color.textNormalColor));
        //从y轴发出横向直线
        yAxis.setDrawGridLines(true);
        //设置虚线线型
        yAxis.setGridDashedLine(new DashPathEffect(
                new float[]{8.0f, 8.0f}, 3.0f));
        //yAxis.setGridLineWidth(10f);
        yAxis.setTextColor(ContextCompat.getColor(context, R.color.transparent));
        yAxis.setTextSize(12);
        // 设置y轴数据偏移量
        yAxis.setXOffset(30);
        yAxis.setYOffset(-3);
        yAxis.setAxisMinimum(0);

        //Matrix matrix = new Matrix();
        // x轴缩放1.5倍
        //matrix.postScale(1.5f, 1f);
        // 在图表动画显示之前进行缩放
        //chart.getViewPortHandler().refresh(matrix, chart, false);

        // x轴执行动画
        chart.animateX(1500);
        chart.invalidate();
        return chart;
    }

    /**
     * 只有一种线的时候
     * 设置线性图表的数据集合
     * @param lineChart 待设置的图表引用
     * @param listData  数据集合
     * @param dataName  数据名字
     */
    public static void setDataLineChartData(LineChart lineChart,
                                            List<Entry> listData,
                                            String dataName) {
        LineDataSet lineDataSet;
        if (lineChart.getData() != null) {
            // 已经存在数据了
            lineDataSet = (LineDataSet) lineChart.getLineData().getDataSetByIndex(0);
            lineDataSet.setValues(listData);
            lineChart.getData().notifyDataChanged();
            lineChart.invalidate();
            lineChart.notifyDataSetChanged();

        } else {
            // 之前就没有数据
            lineDataSet = new LineDataSet(listData, dataName);
            LineData lineData = new LineData();

            // 设置曲线颜色
            //lineDataSet.setColor(Color.parseColor("#FFFFFF"));
            // 设置平滑曲线
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            // 设置显示坐标点的小圆点
            lineDataSet.setDrawCircles(true);
            // 设置显示坐标点的数据
            lineDataSet.setDrawValues(true);
            // 设置不显示定位线
            lineDataSet.setHighlightEnabled(false);

            lineData.addDataSet(lineDataSet);

            lineChart.setData(lineData);

            //这行代码必须放到这里，这里设置的是图表这个视窗能显示，x坐标轴，从最大值到最小值之间
            //多少段，好像这个库没有办法设置x轴中的间隔的大小
            lineChart.setVisibleXRangeMaximum(10);
            lineChart.notifyDataSetChanged();
            lineChart.invalidate();
        }

    }

    /**
     * 设置多重折线的集合
     * @param context 上下文
     * @param lineChart 图表引用
     * @param lineDataWrappers 可变参数，数据和颜色的封装
     */
    public static void addDataLineChartData(Context context,
                                            LineChart lineChart,
                                            LineDataItemWrapper... lineDataWrappers) {

        List<LineDataSet > dataSetsList = new ArrayList<>();

        for(LineDataItemWrapper dataItem:lineDataWrappers){
            dataSetsList.add(setLineDataItem(context,
                    dataItem.getListData(),
                    dataItem.getDataSetame(),
                    dataItem.getColor()));
        }

        lineChart.setData(setLineDataSets(dataSetsList));

        //这行代码必须放到这里，这里设置的是图表这个视窗能显示，x坐标轴，从最大值到最小值之间
        //多少段，好像这个库没有办法设置x轴中的间隔的大小
        lineChart.setVisibleXRangeMaximum(10);
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }
    /**从单个数据集构，造多个数据集的集合，并设置*/
    private static LineData setLineDataSets(List<LineDataSet> lineDataSets){
        List<ILineDataSet> dataSets = new ArrayList<>();
        for(LineDataSet dataSetItem: lineDataSets){
            dataSets.add(dataSetItem);
        }
        LineData lineData = new LineData(dataSets);
        return lineData;
    }
    /**构造单个数据集，并设置*/
    private  static LineDataSet setLineDataItem(Context context,
                                               List<Entry> listData,
                                               String dataName,
                                               int color){
        LineDataSet lineDataSet = new LineDataSet(listData, dataName);
        // 设置平滑曲线
        lineDataSet.setMode(LineDataSet.Mode.LINEAR);
        // 设置显示坐标点的小圆点
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawCircleHole(true);

        lineDataSet.setCircleColor(color);

        // 设置显示坐标点的数据
        lineDataSet.setDrawValues(true);
        // 设置不显示定位线
        lineDataSet.setHighlightEnabled(false);
        // 颜色
        lineDataSet.setColor(ContextCompat.getColor(context,color));

        return lineDataSet;


    }
}
