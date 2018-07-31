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
import java.util.List;

/**
 * Class Full Name  : com.iel.swsapp.business.RateRoleBiz
 * Author Name      : yxnne
 * Create Time      : 2017/5/4
 * Project Name     : SWSAPP
 * Descriptions     : 1.获得当前部门的洗手时机 次数，
 *                  适配扩展接口
 * 主要提供这几个方法:
 * 1,给出某天日期，获得当天的当前部门的洗手时机 次数
 * 2,给出起始日期和结束日期，计算这段时间内的当前部门的洗手时机 次数
 */
public class RateDepartrMomentBiz {

    public static final String EXTRA_RESULT = "rate_role_result";

    public static final String EXTRA_RES_DATA = "rate_role_data";

    public static final String EXTRA_DATA_DAYS = "rate_role_data_days";

    public static final int  DATA_DAYS_1    = 101;
    public static final int  DATA_DAYS_30   = 130;

    public static final int  SUCCESS = 1;

    public static final int  FAILED = 9;

    private static final String URL_RATE_DEPART_MOMENT_PATH = "rateMomentDepart.action";
    /**
     * for Pie Chart 根据时间字符串得到 30 的洗手时机
     * @param context 上下文，用于发送广播
     */
    public void doGetMoment30(final Context context){
        String strDepaert = "";
        if(MyApplication.getCurrentUser().getDepartIds().equals("1")){
            //这时候说明是全院
            strDepaert = DepartRateActivity.sChooseDepartId;

        }else{
            strDepaert = MyApplication.getCurrentUser().getDepartIds();
        }

        String endTime = DateUtils.getNowDateString();
        String startTime = DateUtils.getDaysBeforeDate(endTime,30);
        //拼接URL
        StringBuilder url = new StringBuilder(MyApplication.sUrlNotChangePart);
        url.append(URL_RATE_DEPART_MOMENT_PATH).append("?");
        url.append("startTime").append("=").append(startTime).append("&");
        url.append("endTime").append("=").append(endTime).append("&");
        url.append("departIds").append("=")
                .append(strDepaert).append("&");
        url.append("treeId").append("=")
                .append(MyApplication.getCurrentUser().getDepartIds()).append("&");
        url.append("staffType=1,2,3,4,5");
        //发送数据请求
        RxVolley.get(url.toString(), new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //super.onSuccess(t);
                DepartMomentResult resault = ParseJSON(t);
                //发送 广播
                Intent intent = new Intent(StaticClass.ACTION_GET_DEPART_MOMENT_PIE);

                //没有问题
                Bundle bundle = new Bundle();
                bundle.putInt(EXTRA_RESULT,SUCCESS);
                bundle.putInt(EXTRA_DATA_DAYS,DATA_DAYS_30);
                bundle.putSerializable(EXTRA_RES_DATA,resault);

                intent.putExtras(bundle);

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
     * for Pie Chart 根据时间字符串得到 当天 的洗手时机
     * @param context 上下文，用于发送广播
     */
    public void doGetMomentToday(final Context context){

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
        url.append(URL_RATE_DEPART_MOMENT_PATH).append("?");
        url.append("startTime").append("=").append(endTime).append("&");
        url.append("endTime").append("=").append(endTime).append("&");
        url.append("departIds").append("=")
                .append(strDepaert).append("&");
        url.append("treeId").append("=")
                .append(MyApplication.getCurrentUser().getDepartIds()).append("&");
        url.append("staffType=1,2,3,4,5");
        //发送数据请求
        RxVolley.get(url.toString(), new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //super.onSuccess(t);
                DepartMomentResult resault = ParseJSON(t);
                //发送 广播
                Intent intent = new Intent(StaticClass.ACTION_GET_DEPART_MOMENT_PIE);

                //没有问题
                Bundle bundle = new Bundle();
                bundle.putInt(EXTRA_RESULT,SUCCESS);
                bundle.putInt(EXTRA_DATA_DAYS,DATA_DAYS_1);
                bundle.putSerializable(EXTRA_RES_DATA,resault);

                intent.putExtras(bundle);

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
     * 格式:
     * {"treeId":"2",
     * "0007":0,
     * "staffCount":5,
     * "0003":255,
     * "0103":67,
     * "0110":42,
     * "0008":0}
     */
    private DepartMomentResult ParseJSON(String t) {
        DepartMomentResult res = new DepartMomentResult();
        try {
            JSONObject jObj = new JSONObject(t);
            String str0007 = jObj.getString("0007");
            String str0003 = jObj.getString("0003");
            String str0103 = jObj.getString("0103");
            String str0110 = jObj.getString("0110");
            String str0008 = jObj.getString("0008");

            res.setDepartId(jObj.getString("treeId"));
            res.setMoment0003(Integer.valueOf(str0003));
            res.setMoment0007(Integer.valueOf(str0007));
            res.setMoment0103(Integer.valueOf(str0103));
            res.setMoment0110(Integer.valueOf(str0110));
            res.setMoment0008(Integer.valueOf(str0008));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;

    }


    /**
     * 这个是查询一天的返回结果
     * 包涵了部门当日的依从率
     * 包含了五个角色当日的依从率
     *
     */
    public static class DepartMomentResult implements Serializable{
        private String departId;
        private Integer moment0007;
        private Integer moment0003;
        private Integer moment0103;
        private Integer moment0110;
        private Integer moment0008;

        public String getDepartId() {
            return departId;
        }

        public void setDepartId(String departId) {
            this.departId = departId;
        }

        public Integer getMoment0007() {
            return moment0007;
        }

        public void setMoment0007(Integer moment0007) {
            this.moment0007 = moment0007;
        }

        public Integer getMoment0003() {
            return moment0003;
        }

        public void setMoment0003(Integer moment0003) {
            this.moment0003 = moment0003;
        }

        public Integer getMoment0103() {
            return moment0103;
        }

        public void setMoment0103(Integer moment0103) {
            this.moment0103 = moment0103;
        }

        public Integer getMoment0110() {
            return moment0110;
        }

        public void setMoment0110(Integer moment0110) {
            this.moment0110 = moment0110;
        }

        public Integer getMoment0008() {
            return moment0008;
        }

        public void setMoment0008(Integer moment0008) {
            this.moment0008 = moment0008;
        }

        @Override
        public String toString() {
            return "DepartMomentResult{" +
                    "departId='" + departId + '\'' +
                    ", moment0007=" + moment0007 +
                    ", moment0003=" + moment0003 +
                    ", moment0103=" + moment0103 +
                    ", moment0110=" + moment0110 +
                    ", moment0008=" + moment0008 +
                    '}';
        }

    }


}
