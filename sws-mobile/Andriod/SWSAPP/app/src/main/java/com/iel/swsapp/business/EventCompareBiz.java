package com.iel.swsapp.business;

import android.content.Context;

import com.iel.swsapp.application.MyApplication;
import com.iel.swsapp.utils.DateUtils;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.orhanobut.logger.Logger;

/**
 * Class Full Name  : com.iel.swsapp.business.EventCompareBiz
 * Author Name      : yxnne
 * Create Time      : 2017/4/21
 * Project Name     : SWSAPP
 * Descriptions     : 接触前和接触后的依从率比较
 */
public class EventCompareBiz {

    private static final String URL_EVENT_COMPARE_PATH = "eventCompareListSelect.action";

    public void doEventCompare30Days(final Context context){
        //获得时间
        String endTime = DateUtils.getNowDateString();
        String startTime = DateUtils.getDaysBeforeDate(endTime,30);
        //拼接URL
        StringBuilder url = new StringBuilder(MyApplication.sUrlNotChangePart);
        url.append(URL_EVENT_COMPARE_PATH).append("?");
        url.append("startTime").append("=").append(startTime).append("&");
        url.append("endTime").append("=").append(endTime).append("&");
        url.append("depart").append("=")
                .append(MyApplication.getCurrentUser().getDepartIds());


        RxVolley.get(url.toString(), new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
            }
        });

    }
}
