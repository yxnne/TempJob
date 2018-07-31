package com.iel.swsapp.business;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.iel.swsapp.activity.DepartRateActivity;
import com.iel.swsapp.application.MyApplication;
import com.iel.swsapp.entity.RolesRateOneDay;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class Full Name  : com.iel.swsapp.business.RateRoleBiz
 * Author Name      : yxnne
 * Create Time      : 2017/4/19
 * Project Name     : SWSAPP
 * Descriptions     : 1.主要获得当前部门的按角色对比的依从率
 *                    2.这个接口也可以得到部门整体依从率
 * 主要提供这几个方法:
 * 1,给出某天日期，获得当天的按角色对比依从率
 * 2,给出起始日期和结束日期，计算这段时间内的依从率
 */
public class RateByRoleBiz {

    public static final String EXTRA_RESULT = "rate_role_result";

    public static final String EXTRA_RES_DATA = "rate_role_data";

    public static final String EXTRA_DATA_DAYS = "rate_role_data_days";

    public static final int  DATA_DAYS_1    = 101;
    public static final int  DATA_DAYS_30   = 130;


    public static final int  SUCCESS = 1;

    public static final int  FAILED = 9;

    private static final String URL_RATE_ROLE_PATH = "rateByDepartListSelect.action";

    private List<RateRoleBizOneDayResult> mRoleRates;



    /**
     * 根据时间字符串得到 当天 的按角色 依从率
     * @param dateStr 时间字符串
     * @param context 上下文，用于发送广播
     */
    public void doGetRateOneDay(final Context context, final String dateStr){
        //拼接URL
        StringBuilder url = new StringBuilder(MyApplication.sUrlNotChangePart);
        url.append(URL_RATE_ROLE_PATH).append("?");
        url.append("startTime").append("=").append(dateStr).append("&");
        url.append("endTime").append("=").append(dateStr).append("&");
        url.append("depart").append("=")
                .append(MyApplication.getCurrentUser().getDepartIds());

        //发送数据请求
        RxVolley.get(url.toString(), new HttpCallback() {

            @Override
            public void onSuccess(String t) {
                //super.onSuccess(t);

                RateRoleBizOneDayResult resault = ParseJSON(t);

                //发送 广播
                Intent intent = new Intent(StaticClass.ACTION_GET_ROLES_RATE);

                if(resault.getRolesRateOneDay() == null){
                    //说明有问题，没有解析到
                    Bundle bundle = new Bundle();
                    bundle.putInt(EXTRA_RESULT,FAILED);
                    intent.putExtras(bundle);

                }else{
                    //没有问题
                    resault.setDateStr(dateStr);
                    Bundle bundle = new Bundle();
                    bundle.putInt(EXTRA_RESULT,SUCCESS);
                    //resault.setStrDepaert();
                    bundle.putSerializable(EXTRA_RES_DATA,resault);

                    intent.putExtras(bundle);
                }
                //发送
                context.sendBroadcast(intent);

            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                //经过试验哈，Struts的错误代码，那一堆错误栈也会回调onSucess方法
            }
        });

    }



    /**
     * for Radar Chart 根据时间字符串得到 30 的按角色 依从率
     * @param context 上下文，用于发送广播
     */
    public void doGetRate30Day(final Context context){
        String strDepaert = "";
        if(MyApplication.getCurrentUser().getDepartIds().equals("1")){
            //这时候说明是全院
            strDepaert = DepartRateActivity.sChooseDepartId;

        }else{
            strDepaert = MyApplication.getCurrentUser().getDepartIds();
        }

        final String depart = strDepaert;

        String endTime = DateUtils.getNowDateString();
        String startTime = DateUtils.getDaysBeforeDate(endTime,30);
        //拼接URL
        StringBuilder url = new StringBuilder(MyApplication.sUrlNotChangePart);
        url.append(URL_RATE_ROLE_PATH).append("?");
        url.append("startTime").append("=").append(startTime).append("&");
        url.append("endTime").append("=").append(endTime).append("&");
        url.append("depart").append("=")
                .append(strDepaert);

        Logger.i(url.toString());

        //发送数据请求
        RxVolley.get(url.toString(), new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //super.onSuccess(t);

                RateRoleBizOneDayResult resault = ParseJSON(t);
                //发送 广播
                Intent intent = new Intent(StaticClass.ACTION_GET_ROLES_RATE_RADAR);

                if(resault.getRolesRateMap() == null){
                    //说明有问题，没有解析到
                    Bundle bundle = new Bundle();
                    bundle.putInt(EXTRA_RESULT,FAILED);
                    intent.putExtras(bundle);

                }else{
                    //没有问题
                    Bundle bundle = new Bundle();
                    bundle.putInt(EXTRA_RESULT,SUCCESS);
                    bundle.putInt(EXTRA_DATA_DAYS,DATA_DAYS_30);
                    resault.setStrDepaert(depart);
                    bundle.putSerializable(EXTRA_RES_DATA,resault);
                    Logger.i("resault->"+resault);
                    intent.putExtras(bundle);
                }
                //发送
                context.sendBroadcast(intent);


            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                //经过试验哈，Struts的错误代码，那一堆错误栈也会回调onSucess方法
            }
        });

    }

    /**
     * for Radar Chart 根据时间字符串得到 当天 的按角色 依从率
     * @param context 上下文，用于发送广播
     */
    public void doGetRateToday(final Context context){
        String strDepaert = "";
        if(MyApplication.getCurrentUser().getDepartIds().equals("1")){
            //这时候说明是全院
            strDepaert = DepartRateActivity.sChooseDepartId;

        }else{
            strDepaert = MyApplication.getCurrentUser().getDepartIds();
        }

        String endTime = DateUtils.getNowDateString();
        //拼接URL
        StringBuilder url = new StringBuilder(MyApplication.sUrlNotChangePart);
        url.append(URL_RATE_ROLE_PATH).append("?");
        url.append("startTime").append("=").append(endTime).append("&");
        url.append("endTime").append("=").append(endTime).append("&");
        url.append("depart").append("=")
                .append(strDepaert);

        //发送数据请求
        RxVolley.get(url.toString(), new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //super.onSuccess(t);

                RateRoleBizOneDayResult resault = ParseJSON(t);
                //发送 广播
                Intent intent = new Intent(StaticClass.ACTION_GET_ROLES_RATE_RADAR);

                if(resault.getRolesRateMap() == null){
                    //说明有问题，没有解析到
                    Bundle bundle = new Bundle();
                    bundle.putInt(EXTRA_RESULT,FAILED);
                    intent.putExtras(bundle);

                }else{
                    //没有问题
                    Bundle bundle = new Bundle();
                    bundle.putInt(EXTRA_RESULT,SUCCESS);
                    bundle.putInt(EXTRA_DATA_DAYS,DATA_DAYS_30);
                    bundle.putSerializable(EXTRA_RES_DATA,resault);
                    Logger.i("resault->"+resault);
                    intent.putExtras(bundle);
                }
                //发送
                context.sendBroadcast(intent);


            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                //经过试验哈，Struts的错误代码，那一堆错误栈也会回调onSucess方法
            }
        });

    }


    /**
     * 解析JSON数据成Resault对象
     */
    private RateRoleBizOneDayResult ParseJSON(String t) {
        RateRoleBizOneDayResult res = new RateRoleBizOneDayResult();
        //RolesRateOneDay rolesRate = new RolesRateOneDay();
        Map<String, Double> rolesRateMap = new HashMap<>();
        double rateDepart = 0;
        try {
            JSONObject jObjPage = new JSONObject(t).getJSONObject("page");
            JSONArray jArrResault = jObjPage.getJSONArray("result");
            JSONObject jObj0 = jArrResault.getJSONObject(0);
            //部门上 的 依从率 通过数字计算
            //String depaertRate = jObj0.getString("rate");
            String  rightCount = jObj0.getString("rightCount");
            String  wrongCount = jObj0.getString("wrongCount");
            rateDepart = StasticUtil.calcRate(rightCount,wrongCount);

            //角色依从率 封装
            //角色正确洗手次数数组
            JSONArray rightObjArray = jObj0.getJSONArray("rightCountList");

            //角色错误洗手次数数组
            JSONArray wrongObjArray = jObj0.getJSONArray("wrongCountList");

            //角色名字的json数组
            JSONArray namesObjArray = jObj0.getJSONArray("nameList");
            // 得到角色和相对应的依从率统计数据
            for(int i = 0 ; i < namesObjArray.length(); i ++ ){
                String role = namesObjArray.getString(i);
                double rate = StasticUtil.calcRate(
                        rightObjArray.getString(i),wrongObjArray.getString(i));
                rolesRateMap.put(role, rate);
            }

        } catch (JSONException e) {}

        res.setDepartRate(rateDepart);
        res.setRolesRateMap(rolesRateMap);


    return res;

    }


    /**
     * 这个是查询一天的返回结果
     * 包涵了部门当日的依从率
     * // 包含了五个角色当日的依从率
     * 修改 4-27 2018 不一定是五个角色哦
     *
     */
    public static class RateRoleBizOneDayResult implements Serializable{
        private String strDepaert;
        private double mDepartRate;
        private String mDateStr;
        private RolesRateOneDay mRolesRateOneDay;
        //上面的废弃了，角色和依从率用map对应起来
        private Map<String, Double> rolesRateMap;

        public Map<String, Double> getRolesRateMap() {
            return rolesRateMap;
        }

        public void setRolesRateMap(Map<String, Double> rolesRateMap) {
            this.rolesRateMap = rolesRateMap;
        }

        public String getStrDepaert() {
            return strDepaert;
        }

        public void setStrDepaert(String strDepaert) {
            this.strDepaert = strDepaert;
        }

        public String getDateStr() {
            return mDateStr;
        }

        public void setDateStr(String dateStr) {
            mDateStr = dateStr;
        }

        public double getDepartRate() {
            return mDepartRate;
        }

        public void setDepartRate(double departRate) {
            mDepartRate = departRate;
        }

        public RolesRateOneDay getRolesRateOneDay() {
            return mRolesRateOneDay;
        }

        public void setRolesRateOneDay(RolesRateOneDay rolesRateOneDay) {
            mRolesRateOneDay = rolesRateOneDay;
        }

        @Override
        public String toString() {
            return "RateRoleBizOneDayResult{" +
                    "strDepaert='" + strDepaert + '\'' +
                    ", mDepartRate=" + mDepartRate +
                    ", mDateStr='" + mDateStr + '\'' +
                    ", mRolesRateOneDay=" + mRolesRateOneDay +
                    ", rolesRateMap=" + rolesRateMap +
                    '}';
        }
    }


}
