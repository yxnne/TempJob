package com.iel.swsapp.business;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.iel.swsapp.application.MyApplication;
import com.iel.swsapp.entity.DepaertLists;
import com.iel.swsapp.entity.DepartsResponse;
import com.iel.swsapp.utils.DateUtils;
import com.iel.swsapp.utils.StasticUtil;
import com.iel.swsapp.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class Full Name  : com.iel.swsapp.business.DepartRateOverAllBiz
 * Author Name      : yxnne
 * Create Time      : 2017/5/19
 * Project Name     : SWSAPP
 * Descriptions     : 医院内所有部门的部门列表
 */
public class GetDepartsBiz {
    private static final String URL_RATE_ROLE_PATH = "getDepartments.action";

    public static final int HANDLER_WHAT_DEPARTS = 789;

    public void doGetDeparts(final Handler mHandler){
        //拼接URL
        StringBuilder url = new StringBuilder(MyApplication.sUrlNotChangePart);
        url.append(URL_RATE_ROLE_PATH).append("?");
        url.append("departIds=1");

        RxVolley.get(url.toString(), new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                Gson gson = new Gson();
                DepartsResponse departsResponse =
                        gson.fromJson(t,DepartsResponse.class );
                //ArrayList<>
                Message msg = mHandler.obtainMessage();
                msg.what = HANDLER_WHAT_DEPARTS;
                msg.obj = departsResponse;
                mHandler.sendMessage(msg);
            }
        });
    }

    /**
     * 这个方法直接给全局变量赋值
     */
    public void doGetDepartmentsForAppGlobal( ){
        //拼接URL
        StringBuilder url = new StringBuilder(MyApplication.sUrlNotChangePart);
        url.append(URL_RATE_ROLE_PATH).append("?");
        url.append("departIds=1");

        RxVolley.get(url.toString(), new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                Gson gson = new Gson();
                DepartsResponse departsResponse =
                        gson.fromJson(t,DepartsResponse.class );
                //给App内的 department 对应
                //MyApplication.sDepaertLists = departsResponse.getDepaertLists();
                ArrayList<String> departNames = new ArrayList<String>();
                ArrayList<String> departIds = new ArrayList<String>();
                //遍历
                for (DepaertLists depart :departsResponse.getDepaertLists()){

                    if ( !depart.getDepartName().equals( "感控科")){
                        departNames.add(depart.getDepartName());
                        departIds.add(depart.getDepartId());
                    }

                }
                //设置给全局变量
                MyApplication.sDepartmentNames = departNames;
                MyApplication.sDepartmentIds = departIds;
            }
        });
    }


}
