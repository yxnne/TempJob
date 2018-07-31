package com.iel.swsapp.business;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iel.swsapp.application.MyApplication;
import com.iel.swsapp.entity.StaffTypeLists;
import com.iel.swsapp.entity.StaffTypeResponse;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/**
 * Class Full Name  : com.iel.swsapp.business.DepartRateOverAllBiz
 * Author Name      : yxnne
 * Create Time      : 2017/10/19
 * Project Name     : SWSAPP
 * Descriptions     : 所有角色信息列表
 */
public class GetStaffTypeBiz {
    private static final String URL_GET_ROLE_PATH = "rateByStaffList.action";

    /**
     * 这个方法直接给全局变量赋值
     */
    public void doGetStaffTypeForAppGlobal( ){
        //拼接URL
        StringBuilder url = new StringBuilder(MyApplication.sUrlNotChangePart);
        url.append(URL_GET_ROLE_PATH);

        RxVolley.get(url.toString(), new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                try {
                    ArrayList<String> typeNames = new ArrayList<>();
                    ArrayList<String> typeIds = new ArrayList<>();

                    JSONObject jsonRoot = new JSONObject(t);
                    JSONObject jJobType  = jsonRoot.getJSONObject("jobType");

                    Gson gson = new Gson();
                    Type type = new TypeToken< Map<String,String> >(){}.getType();
                    Map<String,String > resMap = gson.fromJson(jJobType.toString(),type);

                    for(Map.Entry<String,String> entry : resMap.entrySet()){
                        typeNames.add(entry.getValue());
                        typeIds.add(entry.getKey());
                    }

                    //设置给全局变量
                    MyApplication.sRoleTypeNames = typeNames;
                    MyApplication.sRoleTypeIds = typeIds;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*
                Gson gson = new Gson();
                StaffTypeResponse typeResponse =
                        gson.fromJson(t,StaffTypeResponse.class );
                //给App内的 stafftype 对应
                ArrayList<String> typeNames = new ArrayList<>();
                ArrayList<String> typeIds = new ArrayList<>();
                //遍历
                for (StaffTypeLists type :typeResponse.getTypeLists()){
                    typeNames.add(type.getTypeName());
                    typeIds.add(type.getTypeId());
                }
                //设置给全局变量
                MyApplication.sRoleTypeNames = typeNames;
                MyApplication.sRoleTypeIds = typeIds;

                */
            }
        });
    }


}
