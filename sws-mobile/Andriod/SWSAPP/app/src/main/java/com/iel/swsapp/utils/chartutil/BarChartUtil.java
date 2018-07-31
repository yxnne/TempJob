package com.iel.swsapp.utils.chartutil;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.iel.swsapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Full Name  : com.iel.swsapp.utils.chartutil.BarChartUtil
 * Author Name      : yxnne
 * Create Time      : 2017/3/14
 * Project Name     : SWSAPP
 * Descriptions     : 条形图工具类
 */
public class BarChartUtil {


    public static void initBarChartUI(Context context, BarChart barChart) {

        List<BarEntry> entries = new ArrayList<>();


        for (int i = 0; i < 5; i++) {

            entries.add(new BarEntry(i, 20 * i,"xxx"));

        }
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            xValues.add("第" + (i + 1) + "季度");
        }


        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setEnabled(false);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setEnabled(false);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);


        //创建数据集
        // add entries to dataset
        BarDataSet dataSet = new BarDataSet(entries, "BarDataSet1 is the first dataSet");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        //设置数据显示颜色：柱子颜色
        dataSet .setColor(Color.BLUE);
        //dataSet.setBarBorderColor(Color.YELLOW);

        dataSet.setHighLightColor(context.getResources().getColor(R.color.colorAccent));

        List<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        //dataSets.add(dataSet2);
        //柱状图数据集
        BarData barData = new BarData(dataSets);
        //设置柱子宽度
        barData.setBarWidth(0.9f);
        //清除数据
        //barData.clearValues();
        //设置高亮
        barData.setHighlightEnabled(true);

        //设置阴影颜色
       // dataSet2.setBarShadowColor(Color.BLACK);
        //设置高亮条选中的透明度
        //dataSet2.setHighLightAlpha(255);
        dataSet.setHighLightAlpha(255);
        //为条形堆栈的不同值设置标签，如果有的话。
        dataSet.setStackLabels(new String[]{"a","b","c","d"});


//        data.addDataSet(dataSet2);
//        data.addDataSet(dataSet);

        barChart.setData(barData);//装载数据
        barChart.setFitBars(true); //X轴自适应所有柱形图
        //设置图例
        legendSetting(barChart);
//        modifyingViewport(mChart);

        barChart.invalidate();//刷新
    }



    /* @描述  */
    private static void modifyingViewport(BarChart mChart) {
        //设置视口可以显示的最大x范围
        mChart.setVisibleXRangeMaximum(30f);
        //设置视口可以显示的最小x范围
        mChart.setVisibleXRangeMinimum(0f);
        //设置Y轴可以显示的最大值
        mChart.setVisibleYRangeMaximum(500f,YAxis.AxisDependency.LEFT);
        //设置视口View偏移
        mChart.setViewPortOffsets(0f,0f,0f,0f);
        //添加额外的视口偏移
        mChart.setExtraOffsets(-10f,-10f,-10f,-10f);
        //重置视口偏移
        mChart.resetViewPortOffsets();
    }

    /* @描述 设置图例 */
    private static void legendSetting(BarChart mChart) {
        //获取图例
        Legend legend = mChart.getLegend();
        //是否开启设置图例
        legend.setEnabled(true);
        //设置图例文字大小
        legend.setTextSize(10f);
        //设置图例文字颜色
        legend.setTextColor(Color.BLUE);
        //如果设置为true，那么当图例过多或者过长一行显示不下的时候就会自适应换行
        legend.setWordWrapEnabled(true);
        //设置表格的最大相对大小，默认为0.95f(95%)
        legend.setMaxSizePercent(0.95f);
        //设置图例位置
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        //设置图例形状:如SQUARE、CIRCLE、LINE、DEFAULT
        legend.setForm(Legend.LegendForm.CIRCLE);
        //设置图例XY方向的间隔宽度
        legend.setXEntrySpace(20f);
        legend.setYEntrySpace(20f);
        //设置图例标签到文字之间的距离
        legend.setFormToTextSpace(20f);
        //设置文本包装
        legend.setWordWrapEnabled(true);

        //自定义设置图例
        LegendEntry legendEntry = new LegendEntry();
        legendEntry.label = "yxnewe";
        legendEntry.formColor = Color.RED;
        legendEntry.formSize = 10;
        //设置图例
        legend.setCustom(new LegendEntry[]{legendEntry});
        //动态设置自定义图例
        legend.setExtra(new LegendEntry[]{legendEntry});
        //重置取消自定义的图例
        //legend.resetCustom();
    }
}
