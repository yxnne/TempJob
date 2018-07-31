package com.iel.swsapp.utils.chartutil;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.iel.swsapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class Full Name  : com.iel.swsapp.utils.chartutil.PieChartUtil
 * Author Name      : yxnne
 * Create Time      : 2017/2/22
 * Project Name     : SWSAPP
 * Descriptions     : 统一处理饼图的工具类
 */
public class PieChartUtil {


    /**
     * 生成类似于"环形饼图的工具方法"
     * @param pieChart 饼图的引用
     * @param context 上下文
     * @param percent 百分之多少 1--100
     * @param textMiddle 中间文本
     * @param textMiddleSize 中间文本大小
     * @param centerTextColor 中间文本颜色
     * @param circleBackgroundColor 背景圆环颜色
     * @param foregroudProgressColor 前景百分比颜色
     */
    public static void showTestPieDepartOverall(PieChart pieChart,
                                                Context context,
                                                float percent,
                                                String textMiddle,
                                                int textMiddleSize,
                                                int centerTextColor,
                                                int circleBackgroundColor,
                                                int foregroudProgressColor){

        //pieChart.setHoleColorTransparent(true);
        //设置中间圆半径 ，70就是70%
        pieChart.setHoleRadius(75f);
        //pieChart.setHoleColor(Color.WIHTE);
        //设置中间圆半径半透明半径
        pieChart.setTransparentCircleRadius(90f);
        pieChart.setTransparentCircleColor(
                ContextCompat.getColor(context, centerTextColor));

        //设置图例描述
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);

        //饼状图中间可以添加文字
        pieChart.setDrawCenterText(true);
        //设置是否中空
        pieChart.setDrawHoleEnabled(true);
        // 初始旋转角度
        pieChart.setRotationAngle(-85);
        // 可以手动旋转
        pieChart.setRotationEnabled(true);
        //显示成百分比
        pieChart.setUsePercentValues(false);
        //饼状图中间的文字
        pieChart.setCenterText(textMiddle);
        pieChart.setCenterTextSize(textMiddleSize);
        pieChart.setCenterTextColor(
                ContextCompat.getColor(context, circleBackgroundColor));


        //设置数据
        pieChart.setData(getPieData(2,context,percent,foregroudProgressColor));
        // undo all highlights
        // pieChart.highlightValues(null);
        //pieChart.invalidate();
        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
        //mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);
        mLegend.setEnabled(false);//不要图例了
        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);

    }

    /*私有方法 生成饼图内数据*/
    private static PieData getPieData(int count,
                                      Context context,
                                      float percent,
                                      int foregroudProgressColor) {

        /**
         * 将一个饼形图分成2部分， eg：2部分的数值比例为14:82
         * 所以 14代表的百分比就是14%
         */
        List<String> xValues = new ArrayList<>();

        //xVals用来表示每个饼块上的内容
        for (int i = 0; i < count; i++) {
            xValues.add("Quarterly" + (i + 1));

        }

        //yVals用来表示封装每个饼块的实际数据
        List<PieEntry> yValues = new ArrayList<>();

        // 饼图数据
        yValues.add(new PieEntry(percent, 0));
        yValues.add(new PieEntry(100-percent, 1));

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues,
                ""/*显示在比例图上*/);

        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离

        ArrayList<Integer> colors = new ArrayList<>();

//        // 饼图颜色
//        colors.add(ContextCompat.getColor(context, R.color.chartFront1));
//        colors.add(ContextCompat.getColor(context, R.color.transparent));
//
        colors.add(ContextCompat.getColor(context, foregroudProgressColor));//第一部分数据的颜色
        colors.add(ContextCompat.getColor(context, R.color.transparent));

        pieDataSet.setColors(colors);
       // pieDataSet.setValueTextColor(R.color.myWhite);
        pieDataSet.setDrawValues(false);//不绘制文本数值了

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float px = 1.2f * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        return new PieData( pieDataSet);
    }



    public static void showTestPieDepart_Details(PieChart pieChart,
                                                Context context){
        pieChart.setHoleRadius(75f);
        //pieChart.setHoleColor(Color.WIHTE);
        //设置中间圆半径半透明半径
        pieChart.setTransparentCircleRadius(80f);
        pieChart.setTransparentCircleColor(
                ContextCompat.getColor(context, R.color.transparent));

        //设置图例描述
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);


        pieChart.setUsePercentValues(true);
        //饼状图中间可以添加文字
        pieChart.setDrawCenterText(true);
        //设置是否中空
        pieChart.setDrawHoleEnabled(true);
        //初始旋转角度
        pieChart.setRotationAngle(-90);
        // 可以手动旋转
        pieChart.setRotationEnabled(true);
        //显示成百分比
        pieChart.setUsePercentValues(false);
        //数据
        pieChart.setData(getPieData5(5,context,25,15,10,35,15));
        //动画
        pieChart.animateXY(1000, 1000);  //设置动画


    }
    /*私有方法 生成饼图内数据*/
    private static PieData getPieData5(int count,
                                      Context context,
                                      int percent1,
                                      int percent2,
                                      int percent3,
                                      int percent4,
                                      int percent5
                                      ) {

        //yVals用来表示封装每个饼块的实际数据
        List<PieEntry> yValues = new ArrayList<>();
        //颜色列表
        ArrayList<Integer> colors = new ArrayList<>();
        if(percent1 != 0){
            colors.add(ContextCompat.getColor(context, R.color.color_wash_times));//第一部分数据的颜色
            yValues.add(new PieEntry(percent1,0));
        }
        if(percent2 != 0){
            colors.add(ContextCompat.getColor(context, R.color.color_leave_long_no_wash));
            yValues.add(new PieEntry(percent2,1));

        }
        if(percent3 != 0 ){
            colors.add(ContextCompat.getColor(context, R.color.color_close_bed_no_wash));
            yValues.add(new PieEntry(percent3,2));

        }

        if(percent4 != 0 ){
            colors.add(ContextCompat.getColor(context, R.color.color_leave_room_no_wash));
            yValues.add(new PieEntry(percent4,3));

        }
        if(percent5 != 0){
            colors.add(ContextCompat.getColor(context, R.color.color_acess_no_wash));
            yValues.add(new PieEntry(percent5,4));

        }

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues,
                ""/*显示在比例图上*/);

        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离



        pieDataSet.setColors(colors);
        pieDataSet.setValueTextColor(Color.RED);

        pieDataSet.setValueTextSize(10);
        pieDataSet.setDrawValues(true);// 绘制文本数值了
        pieDataSet.setValueLineColor(R.color.textNormalColor);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float px = 1.8f * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        pieDataSet.setValueLinePart1OffsetPercentage(80.f);
        pieDataSet.setValueLineWidth(1f);
        pieDataSet.setValueLinePart1Length(0.2f);
        pieDataSet.setValueLinePart2Length(0.1f);
        pieDataSet.setValueLineVariableLength(true);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        return new PieData(pieDataSet);
    }

    public static void setPieChartUI_5(Context context,PieChart pieChart,
                                     int v1,int v2,int v3,int v4 ,int v5){
        //圆环型的饼图
        pieChart.setHoleRadius(75f);

        //设置中间圆半径半透明半径
        pieChart.setTransparentCircleRadius(80f);
        pieChart.setTransparentCircleColor(
                ContextCompat.getColor(context, R.color.transparent));

        //设置图例描述，图例描述不要
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        //饼状图中间可以添加文字
        pieChart.setDrawCenterText(true);
        //设置是否中空
        pieChart.setDrawHoleEnabled(true);
        //初始旋转角度
        pieChart.setRotationAngle(-90);
        // 可以手动旋转
        pieChart.setRotationEnabled(true);
        //显示成百分比
        pieChart.setUsePercentValues(true);
        //数据
        PieData data =getPieData5(5,context,v1,v2,v3,v4,v5);

        List<String> allLabels = new ArrayList<>(Arrays.asList(
                "洗手次数","长时离开病床未洗手",
                "接近病床未洗手","离开病房未洗手","进入病房未洗手"));
        //准备label
        List<String > labels = new ArrayList<>();
        if(v1 != 0){
            labels.add(allLabels.get(0));
        }
        if(v2 != 0 ){
            labels.add(allLabels.get(1));
        }
        if(v3 != 0 ){
            labels.add(allLabels.get(2));
        }
        if(v4 != 0){
            labels.add(allLabels.get(3));
        }
        if(v5 != 0){
            labels.add(allLabels.get(4));
        }

        data.setValueFormatter(new MyPieFormatter(labels));

        data.setValueTextSize(11f);

        data.setValueTextColor(Color.BLACK);


       // pieChart.setData(getPieData5(5,context,v1,v2,v3,v4,v5));
        pieChart.setData( data);

        //图例设置
        //pieChart.getLegend(). setEnabled(false);

        //动画
        pieChart.animateXY(1000, 1000);  //设置动画
    }

    public static void updateUI(Context context,PieChart pieChart,int v1,int v2,int v3,int v4 ,int v5){
        PieData data =getPieData5(5,context,v1,v2,v3,v4,v5);
        //准备label
        List<String> labels = new ArrayList<>(Arrays.asList(
                "洗手次数","长时离开病床未洗手",
                "接近病床未洗手","离开病房未洗手","进入病房未洗手"));
        data.setValueFormatter(new MyPieFormatter(labels));

        data.setValueTextSize(11f);

        data.setValueTextColor(Color.BLACK);


        // pieChart.setData(getPieData5(5,context,v1,v2,v3,v4,v5));
        pieChart.setData( data);
        //pieChart.animateX(1000);  //设置动画
        pieChart.invalidate();

    }


    
}
