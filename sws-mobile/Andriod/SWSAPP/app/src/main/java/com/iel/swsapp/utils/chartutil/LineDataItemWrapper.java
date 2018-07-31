package com.iel.swsapp.utils.chartutil;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Full Name  : com.iel.swsapp.utils.chartutil.LineDataWrapper
 * Author Name      : yxnne
 * Create Time      : 2017/3/10
 * Project Name     : SWSAPP
 * Descriptions     : 工具实体类:
 *                    封装数据键值对，封装颜色，和数据名字
 */
public class LineDataItemWrapper {

    private List<Entry> listData;
    private int color;
    private String dataSetame;

    public LineDataItemWrapper(List<Float> listData,
                           String dataSetame,
                           int color){
        //this(entryList,dataSetame,color);
        List<Entry> entryList = new ArrayList<>();
        for(int i = 0 ; i < listData.size(); i++){
            entryList.add(new Entry(i,listData.get(i)));
        }
        this.listData = entryList;
        this.dataSetame = dataSetame;
        this.color = color;
    }

    public List<Entry> getListData() {
        return listData;
    }

    public void setListData(List<Entry> listData) {
        this.listData = listData;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDataSetame() {
        return dataSetame;
    }

    public void setDataSetame(String dataSetame) {
        this.dataSetame = dataSetame;
    }
}
