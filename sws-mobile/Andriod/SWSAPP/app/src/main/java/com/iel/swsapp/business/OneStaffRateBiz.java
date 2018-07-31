package com.iel.swsapp.business;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.iel.swsapp.application.MyApplication;
import com.iel.swsapp.entity.response.starff.Result;
import com.iel.swsapp.entity.response.starff.StaffRateResponse;
import com.iel.swsapp.utils.DateUtils;
import com.iel.swsapp.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Full Name  : com.iel.swsapp.business.StaffRateBiz
 * Author Name      : yxnne
 * Create Time      : 2017/5/12
 * Project Name     : SWSAPP
 * Descriptions     : 实现对接扩展接口，针对部门内员工
 */
public class OneStaffRateBiz {
    private static final String URL_ONE_STAFF_RATE_PATH = "rateByOneStaff.action";

    public static final String RESULT_ONE_STAFF = "rate_one_staff";

    /**
     * 传入员工ID，得到员工的依从率
     * @param context 上下文
     * @param rfid 员工Id
     * @param isOneDay 是否是一天数据
     */
    public void getOneStaffRateInfo(final Context context, String rfid,boolean isOneDay){

        String endTime = DateUtils.getNowDateString();
        String startTime = "";
        if(isOneDay){
            startTime = DateUtils.getNowDateString();
        }else{
            startTime = DateUtils.getDaysBeforeDate(endTime,30);
        }

        //拼接URL
        StringBuilder url = new StringBuilder(MyApplication.sUrlNotChangePart);
        url.append(URL_ONE_STAFF_RATE_PATH).append("?");
        url.append("treeId").append("=")
                .append(MyApplication.getCurrentUser().getDepartIds()).append("&");
        url.append("departIds").append("=")
                .append(MyApplication.getCurrentUser().getDepartIds()).append("&");
        url.append("staffId").append("=").append("-1").append("&");
        url.append("rfid").append("=").append(rfid).append("&");
        url.append("startTime").append("=").append(startTime).append("&");
        url.append("endTime").append("=").append(endTime).append("&");
        url.append("jobType=1,2,3,4,5");




        RxVolley.get(url.toString(), new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                //
                Gson gson = new Gson();
                StaffRateResponse response = gson.fromJson(t,
                        StaffRateResponse.class);
                //将response 进一步封装成
                List<StaffRateInfoItem> data = new ArrayList<>();
                for(Result r:response.getPage().getResult()){
                    StaffRateInfoItem item = new StaffRateInfoItem();
                    item.setCardno(r.getRfid());
                    item.setJobType(r.getDocType());
                    item.setRate(r.getRate());
                    item.setName(r.getDocName());
                    item.setDepartment(r.getDepartName());

                    item.setMoment0003(r.getNum0003());
                    item.setMoment0007(r.getNum0007());
                    item.setMoment0008(r.getNum0008());
                    item.setMoment0110(r.getNum0110());
                    item.setMoment0103(r.getNum0103());

                    item.setRank(r.getRank()+"");

                    item.setRateAfterCloseNick(r.getRateAfterCloseNick());
                    item.setRateAfterCloseNickEnvri(r.getRateAfterCloseNickEnvri());
                    item.setRateBeforeAsepticOperation(r.getRateBeforeAsepticOperation());
                    item.setRateBeforeCloseNick(r.getRateBeforeCloseNick());

                    data.add(item);
                }
                //可序列化对象
                StaffRateData resData = new StaffRateData();
                resData.setDatas(data);
                //使用Context 发送广播
                Intent intent = new Intent();
                intent.setAction(StaticClass.ACTION_GET_ONE_STAFF_RATE);
                Bundle bundle = new Bundle();
                bundle.putSerializable(RESULT_ONE_STAFF,resData);
                intent.putExtras(bundle);


                context.sendBroadcast(intent);
           }
        });

    }

    /**
     * 测试方法
     */
    public List<StaffRateInfoItem> getStaffRateInfoList(){
        List<StaffRateInfoItem> listInfo = new ArrayList<>();
        if(MyApplication.MY_MODEL == StaticClass.MODEL_FAKE_DATA) {

            String[] names = new String[]{
                    "立春橙", "笋丝艺", "人情扩",
                    "肤红亮", "人用抄", "留校严",
                    "养小囡", "成鹰", "掌事怦", "望牙杯",
                    "Semon","Jerry","Alex","Bob","Johnson"
                    ,"Adam","Cameron","Duncan"
            };

            for(int i = 0;i < names.length;i++){
                int no4 = (int) (Math.random()*10000);
                int no2 = (int) (Math.random()*100);

                listInfo.add(new StaffRateInfoItem(names[i],"00"+no4,no2+"%"));
            }

        }
        return listInfo;
    }

    public static class StaffRateData implements Serializable{
        private List<StaffRateInfoItem> datas;

        public List<StaffRateInfoItem> getDatas() {
            return datas;
        }

        public void setDatas(List<StaffRateInfoItem> datas) {
            this.datas = datas;
        }

        @Override
        public String toString() {
            return "StaffRateData{" +
                    "datas=" + datas +
                    '}';
        }
    }


    /**
     * 测试假数据用的 真实数据时候已经放弃
     */
    public static class StaffRateInfoItem implements Serializable{
        String name,cardno,rate,jobType,department,rank;
        int moment0003,moment0007,moment0110,moment0103,moment0008;
        double  rateAfterCloseNick, rateAfterCloseNickEnvri,
                rateBeforeAsepticOperation,rateBeforeCloseNick;

        public StaffRateInfoItem(){}

        public StaffRateInfoItem(String name, String cardno, String rate) {
            this.name = name;
            this.cardno = cardno;
            this.rate = rate;
        }

        public double getRateAfterCloseNick() {
            return rateAfterCloseNick;
        }

        public void setRateAfterCloseNick(double rateAfterCloseNick) {
            this.rateAfterCloseNick = rateAfterCloseNick;
        }

        public double getRateAfterCloseNickEnvri() {
            return rateAfterCloseNickEnvri;
        }

        public void setRateAfterCloseNickEnvri(double rateAfterCloseNickEnvri) {
            this.rateAfterCloseNickEnvri = rateAfterCloseNickEnvri;
        }

        public double getRateBeforeAsepticOperation() {
            return rateBeforeAsepticOperation;
        }

        public void setRateBeforeAsepticOperation(double rateBeforeAsepticOperation) {
            this.rateBeforeAsepticOperation = rateBeforeAsepticOperation;
        }

        public double getRateBeforeCloseNick() {
            return rateBeforeCloseNick;
        }

        public void setRateBeforeCloseNick(double rateBeforeCloseNick) {
            this.rateBeforeCloseNick = rateBeforeCloseNick;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getJobType() {
            return jobType;
        }

        public void setJobType(String jobType) {
            this.jobType = jobType;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCardno(String cardno) {
            this.cardno = cardno;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getName() {
            return name;
        }

        public String getCardno() {
            return cardno;
        }

        public String getRate() {
            return rate;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public int getMoment0003() {
            return moment0003;
        }

        public void setMoment0003(int moment0003) {
            this.moment0003 = moment0003;
        }

        public int getMoment0007() {
            return moment0007;
        }

        public void setMoment0007(int moment0007) {
            this.moment0007 = moment0007;
        }

        public int getMoment0110() {
            return moment0110;
        }

        public void setMoment0110(int moment0110) {
            this.moment0110 = moment0110;
        }

        public int getMoment0103() {
            return moment0103;
        }

        public void setMoment0103(int moment0103) {
            this.moment0103 = moment0103;
        }

        public int getMoment0008() {
            return moment0008;
        }

        public void setMoment0008(int moment0008) {
            this.moment0008 = moment0008;
        }

        @Override
        public String toString() {
            return "StaffRateInfoItem{" +
                    "name='" + name + '\'' +
                    ", cardno='" + cardno + '\'' +
                    ", rate='" + rate + '\'' +
                    ", jobType='" + jobType + '\'' +
                    ", department='" + department + '\'' +
                    ", rank='" + rank + '\'' +
                    ", moment0003=" + moment0003 +
                    ", moment0007=" + moment0007 +
                    ", moment0110=" + moment0110 +
                    ", moment0103=" + moment0103 +
                    ", moment0008=" + moment0008 +
                    ", rateAfterCloseNick=" + rateAfterCloseNick +
                    ", rateAfterCloseNickEnvri=" + rateAfterCloseNickEnvri +
                    ", rateBeforeAsepticOperation=" + rateBeforeAsepticOperation +
                    ", rateBeforeCloseNick=" + rateBeforeCloseNick +
                    '}';
        }
    }
}
