package com.iel.swsapp.business;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.iel.swsapp.activity.DepartRateActivity;
import com.iel.swsapp.application.MyApplication;
import com.iel.swsapp.entity.RolesRateOneDay;
import com.iel.swsapp.utils.CommenUtil;
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

/**
 * Class Full Name  : com.iel.swsapp.business.DepartRateOverAllBiz
 * Author Name      : yxnne
 * Create Time      : 2017/4/21
 * Project Name     : SWSAPP
 * Descriptions     : 部门总体依从率
 */
public class DepartRateOverAllBiz {
    private static final String URL_RATE_ROLE_PATH = "rateByDepartListSelect.action";

    public static final String RESULT_RATE = "rate_depart_overall";

    public void doDepartRate30(String depart,final Context context){
        String endTime = DateUtils.getNowDateString();
        String startTime = DateUtils.getDaysBeforeDate(endTime,30);

        doDepartRate(startTime,endTime,depart,context);
    }
    public void doDepartRateOneDay(String depart,final Context context){
        String endTime = DateUtils.getNowDateString();
        String startTime = endTime;

        doDepartRate(startTime,endTime,depart,context);
    }

    public void doDepartRate(String startTime,String endTime,String depart,final Context context){

        final String  strDepaert =  depart;


        //拼接URL
        StringBuilder url = new StringBuilder(MyApplication.sUrlNotChangePart);
        url.append(URL_RATE_ROLE_PATH).append("?");
        url.append("startTime").append("=").append(startTime).append("&");
        url.append("endTime").append("=").append(endTime).append("&");
        url.append("depart").append("=")
                .append(strDepaert);

        RxVolley.get(url.toString(), new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);

                //double rate = parseJSON(t);
                DepartRateResult result = parseJSONMoreInfo(t);

                result.setStrDepart(strDepaert);
                //发送 广播
                Intent intent = new Intent(StaticClass.ACTION_GET_DEPART_OVERALL_RATE);

                Bundle bundle = new Bundle();
                bundle.putSerializable(RESULT_RATE,result);

                intent.putExtras(bundle);
                //intent.putExtra(RESULT_RATE,rate);
                context.sendBroadcast(intent);

            }
        });

    }
    /**
     * 解析JSON数据成Resault对象
     */
    private double parseJSON(String t) {

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

        } catch (JSONException e) {


        }


        return rateDepart;

    }

    /**
     * 解析JSON数据成Resault对象
     */
    private DepartRateResult parseJSONMoreInfo(String t) {

        DepartRateResult result = new DepartRateResult();
        double rateDepart = 0;
        float rateAfterCloseNick = 0;
        float rateAfterCloseNickEnvri = 0;
        float rateBeforeAsepticOperation = 0;
        float rateBeforeCloseNick = 0;

        try {
            JSONObject jObjPage = new JSONObject(t).getJSONObject("page");
            JSONArray jArrResault = jObjPage.getJSONArray("result");
            JSONObject jObj0 = jArrResault.getJSONObject(0);
            //部门上 的 依从率 通过数字计算
            //String depaertRate = jObj0.getString("rate");
            String  rightCount = jObj0.getString("rightCount");
            String  wrongCount = jObj0.getString("wrongCount");
            rateDepart = StasticUtil.calcRate(rightCount,wrongCount);

            String strRateAfterCloseNick = jObj0.getString("rateAfterCloseNick");
            String strRateAfterCloseNickEnvri = jObj0.getString("rateAfterCloseNickEnvri");
            String strRateBeforeAsepticOperation = jObj0.getString("rateBeforeAsepticOperation");
            String strRateBeforeCloseNick = jObj0.getString("rateBeforeCloseNick");

            //得到两前两后数据
            rateAfterCloseNick = StasticUtil.StrNbr2Double(strRateAfterCloseNick);
            rateAfterCloseNickEnvri = StasticUtil.StrNbr2Double(strRateAfterCloseNickEnvri);
            rateBeforeAsepticOperation = StasticUtil.StrNbr2Double(strRateBeforeAsepticOperation);
            rateBeforeCloseNick = StasticUtil.StrNbr2Double(strRateBeforeCloseNick);


        } catch (JSONException e) {


        }
        result.setRateDepart(rateDepart);
        result.setRateAfterCloseNick(rateAfterCloseNick);
        result.setRateAfterCloseNickEnvri(rateAfterCloseNickEnvri);
        result.setRateBeforeAsepticOperation(rateBeforeAsepticOperation);
        result.setRateBeforeCloseNick(rateBeforeCloseNick);
        return result;
    }


    /**
     * 封装部门总体依从率的结果的内部类
     * 封装了两前两后的数据和
     * 部门总体依从率
     */
    public static class DepartRateResult implements Serializable{
        String strDepart;
        double rateDepart;
        float rateAfterCloseNick;
        float rateAfterCloseNickEnvri;
        float rateBeforeAsepticOperation;
        float rateBeforeCloseNick;

        public String getStrDepart() {
            return strDepart;
        }

        public void setStrDepart(String strDepart) {
            this.strDepart = strDepart;
        }

        public double getRateDepart() {
            return rateDepart;
        }

        public void setRateDepart(double rateDepart) {
            this.rateDepart = rateDepart;
        }

        public float getRateAfterCloseNick() {
            return rateAfterCloseNick;
        }

        public void setRateAfterCloseNick(float rateAfterCloseNick) {
            this.rateAfterCloseNick = rateAfterCloseNick;
        }

        public float getRateAfterCloseNickEnvri() {
            return rateAfterCloseNickEnvri;
        }

        public void setRateAfterCloseNickEnvri(float rateAfterCloseNickEnvri) {
            this.rateAfterCloseNickEnvri = rateAfterCloseNickEnvri;
        }

        public float getRateBeforeAsepticOperation() {
            return rateBeforeAsepticOperation;
        }

        public void setRateBeforeAsepticOperation(float rateBeforeAsepticOperation) {
            this.rateBeforeAsepticOperation = rateBeforeAsepticOperation;
        }

        public float getRateBeforeCloseNick() {
            return rateBeforeCloseNick;
        }

        public void setRateBeforeCloseNick(float rateBeforeCloseNick) {
            this.rateBeforeCloseNick = rateBeforeCloseNick;
        }
    }



}
