package com.iel.swsapp.utils.chartutil;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.iel.swsapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class Full Name  : com.iel.swsapp.utils.chartutil.PieChartUtil
 * Author Name      : yxnne
 * Create Time      : 2017/2/22
 * Project Name     : SWSAPP
 * Descriptions     : 处理雷达图的工具类
 */
public class RadarChartUtil {

    // 一些基础设置
    private static void radarChartBasicSetting(final Context context,
                                        RadarChart mChart){
        mChart.setBackgroundColor(Color.WHITE);

        mChart.getDescription().setEnabled(false);

        mChart.setWebLineWidth(1f);
        mChart.setWebColor(Color.LTGRAY);
        mChart.setWebLineWidthInner(1f);
        mChart.setWebColorInner(Color.LTGRAY);
        mChart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MarkerView mv = new RadarMarkerView(context, R.layout.radarchart_makerview);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        mChart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);


    }

    /**
     * 直接从map里面取出角色名和其数据，绘制雷达图
     * @param context context
     * @param mChart ref of radar chart
     * @param mapData radar map data
     */
    public static void getRadarChartFromMap(final Context context,
                                            RadarChart mChart,
                                            Map<String, Double > mapData){
        radarChartBasicSetting(context, mChart);

        setDataAndXY(context, mChart, mapData);

        mChart.getLegend().setEnabled(false);


    }


    /**
     * 根据修改雷达图UI的方法真实数据
     * @param context 上下文
     * @param mChart 图表引用
     * @param allData 全部门数据
     * @param nurseData 护士数据
     * @param doctorData 医生数据
     * @param physicianData 医师数据
     * @param workerData 护工数据
     */
    public static void getRadarChart_5(final Context context,
                                       RadarChart mChart,
                                       double allData,
                                       double nurseData,
                                       double doctorData,
                                       double physicianData,
                                       double workerData){
        mChart.setBackgroundColor(Color.WHITE);

        mChart.getDescription().setEnabled(false);

        mChart.setWebLineWidth(1f);
        mChart.setWebColor(Color.LTGRAY);
        mChart.setWebLineWidthInner(1f);
        mChart.setWebColorInner(Color.LTGRAY);
        mChart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MarkerView mv = new RadarMarkerView(context, R.layout.radarchart_makerview);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        setData(context,mChart, allData, nurseData,
                doctorData, physicianData, workerData);

        mChart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = mChart.getXAxis();
        //xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] mActivities = new String[]{
                    context.getString(R.string.text_role_all),
                    context.getString(R.string.text_role_physician),
                    context.getString(R.string.text_role_nurse),
                    context.getString(R.string.text_role_support_worker),
                    context.getString(R.string.text_role_doctor),
            };

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(ContextCompat.getColor(context,R.color.textNormalColor));
        xAxis.setTextSize(13f);

        YAxis yAxis = mChart.getYAxis();
        //yAxis.setTypeface(mTfLight);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(12f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);
        yAxis.setXOffset(100f);
        mChart.getLegend().setEnabled(false);
    }


    /**
     * 测试UI使用随机生成的数据
     */
    public static void getRadarChart_5(final Context context, RadarChart mChart){
        mChart.setBackgroundColor(Color.WHITE);

        mChart.getDescription().setEnabled(false);

        mChart.setWebLineWidth(1f);
        mChart.setWebColor(Color.LTGRAY);
        mChart.setWebLineWidthInner(1f);
        mChart.setWebColorInner(Color.LTGRAY);
        mChart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MarkerView mv = new RadarMarkerView(context, R.layout.radarchart_makerview);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        setData(context,mChart);

        mChart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = mChart.getXAxis();
        //xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] mActivities = new String[]{
                    context.getString(R.string.text_role_all),
                    context.getString(R.string.text_role_physician),
                    context.getString(R.string.text_role_nurse),
                    context.getString(R.string.text_role_support_worker),
                    context.getString(R.string.text_role_doctor),
                    };

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(ContextCompat.getColor(context,R.color.textNormalColor));
        xAxis.setTextSize(13f);

        YAxis yAxis = mChart.getYAxis();
        //yAxis.setTypeface(mTfLight);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(12f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);
        mChart.getLegend().setEnabled(false);

    }

    /**
     * 设置雷达图数据，根据map
     * @param context
     * @param mChart
     * @param dataMap
     */
    private static void setDataAndXY(final Context context, RadarChart mChart,
                                     Map<String , Double> dataMap){
        final ArrayList<String> strRoleName = new ArrayList<>();

        ArrayList<RadarEntry> entries1 = new ArrayList<>();
        //ArrayList<RadarEntry> entries2 = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        // all
        for (Map.Entry<String, Double> entry : dataMap.entrySet()){
            entries1.add(new RadarEntry(entry.getValue().floatValue()));
            strRoleName.add(entry.getKey());
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "Roles Rate");
        set1.setColor(ContextCompat.getColor(context,R.color.colorPrimary_simliar_1));
        set1.setFillColor(ContextCompat.getColor(context,R.color.color_Radar_bg));
        set1.setDrawFilled(true);
        set1.setFillAlpha(130);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        //sets.add(set2);

        RadarData data = new RadarData(sets);
        //data.setValueTypeface(mTfLight);
        data.setValueTextSize(10f);

        data.setDrawValues(true);
        //data.setValueFormatter(new PercentFormatter());
        data.setValueTextColor(ContextCompat.getColor(context,R.color.textNormalColor));

        data.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return  value + "%";
            }
        });
        mChart.setData(data);
        mChart.invalidate();

        XAxis xAxis = mChart.getXAxis();
        //xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(9f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private ArrayList<String> mActivities = strRoleName;

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                // return mActivities[(int) value % mActivities.length];
                return ".     "+mActivities.get((int) value % mActivities.size());
            }
        });

        xAxis.setTextColor(ContextCompat.getColor(context,R.color.textNormalColor));
        xAxis.setTextSize(13f);

        YAxis yAxis = mChart.getYAxis();
        //yAxis.setTypeface(mTfLight);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(12f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);

    }


    /**
     * 根据角色数据设雷达图的数据
     * @param context 上下文
     * @param mChart 图表引用
     * @param allData 全员数据
     * @param nurseData 护士
     * @param doctorData 医生
     * @param physicianData 医师
     * @param workerData 护工
     * 数据顺序
     *      role_all
     *      physician
     *      nurse
     *      support_worker
     *      doctor
     */
    private static void setData(Context context, RadarChart mChart,
                                double allData, double nurseData,
                                double doctorData, double physicianData,
                                double workerData) {

        ArrayList<RadarEntry> entries1 = new ArrayList<>();
        //ArrayList<RadarEntry> entries2 = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        // all
        entries1.add(new RadarEntry((float) allData));
        entries1.add(new RadarEntry((float) physicianData));
        entries1.add(new RadarEntry((float) nurseData));
        entries1.add(new RadarEntry((float) workerData));
        entries1.add(new RadarEntry((float) doctorData));


        RadarDataSet set1 = new RadarDataSet(entries1, "Roles Rate");
        set1.setColor(ContextCompat.getColor(context,R.color.colorPrimary_simliar_1));
        set1.setFillColor(ContextCompat.getColor(context,R.color.color_Radar_bg));
        set1.setDrawFilled(true);
        set1.setFillAlpha(130);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        //sets.add(set2);

        RadarData data = new RadarData(sets);
        //data.setValueTypeface(mTfLight);
        data.setValueTextSize(10f);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextColor(ContextCompat.getColor(context,R.color.textNormalColor));

        mChart.setData(data);
        mChart.invalidate();

    }

    /**
     * 生成随机数测试数据，并将雷达图设置数据
     */
    public static void setData(Context context ,RadarChart mChart) {

        float mult = 80;
        float min = 20;
        int cnt = 5;

        ArrayList<RadarEntry> entries1 = new ArrayList<>();
        //ArrayList<RadarEntry> entries2 = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < cnt; i++) {
            float val1 = (float) (Math.random() * mult) + min;
            entries1.add(new RadarEntry(val1));

//            float val2 = (float) (Math.random() * mult) + min;
//            entries2.add(new RadarEntry(val2));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "Roles Rate");
        set1.setColor(ContextCompat.getColor(context,R.color.colorPrimary_simliar_1));
        set1.setFillColor(ContextCompat.getColor(context,R.color.color_Radar_bg));
        set1.setDrawFilled(true);
        set1.setFillAlpha(130);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        //RadarDataSet set2 = new RadarDataSet(entries2, "This Week");
//        set2.setColor(Color.rgb(121, 162, 175));
//        set2.setFillColor(Color.rgb(121, 162, 175));
//        set2.setDrawFilled(true);
//        set2.setFillAlpha(180);
//        set2.setLineWidth(2f);
//        set2.setDrawHighlightCircleEnabled(true);
//        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        //sets.add(set2);

        RadarData data = new RadarData(sets);
        //data.setValueTypeface(mTfLight);
        data.setValueTextSize(10f);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextColor(ContextCompat.getColor(context,R.color.textNormalColor));

        mChart.setData(data);
        mChart.invalidate();
    }

}
