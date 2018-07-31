package com.iel.swsapp.utils.chartutil;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Class Full Name  : com.iel.swsapp.utils.chartutil.MyPieFormatter
 * Author Name      : yxnne
 * Create Time      : 2017/3/16
 * Project Name     : SWSAPP
 * Descriptions     : TODO
 */
public class MyPieFormatter implements IValueFormatter, IAxisValueFormatter {


    private List<String> mLabelList;

    private long count = 0;

    protected DecimalFormat mFormat;

    public MyPieFormatter() {
        mFormat = new DecimalFormat("###,###,##0.0");
    }

    public MyPieFormatter(List<String> labels) {
        mLabelList = labels;
        mFormat = new DecimalFormat("###,###,##0.0");
    }

    /**
     * Allow a custom decimalformat
     *
     * @param format
     */
    public MyPieFormatter(DecimalFormat format) {
        this.mFormat = format;
    }

    // IValueFormatter
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return mFormat.format(value) + "% "+ mLabelList.get((int)(count ++ % (mLabelList.size())));
    }

    // IAxisValueFormatter
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value) + " %";
    }

    public int getDecimalDigits() {
        return 1;
    }


}
