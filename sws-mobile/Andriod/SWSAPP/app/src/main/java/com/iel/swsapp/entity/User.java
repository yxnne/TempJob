package com.iel.swsapp.entity;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Class Full Name  : com.iel.swsapp.entity.User
 * Author Name      : yxnne
 * Create Time      : 2017/4/12
 * Project Name     : SWSAPP
 * Descriptions     : 当前登录用户的基本信息
 */
public class User implements Serializable{
    private String UserName ;
    private String departIds ;
    private String department ;
    private String deviceName ;
    private String rfid ;
    private String phoneNbr ;
    private String rateInDepartment ;
    private String rankIndepartment ;
    private boolean isLogin ;

    public User() {
        this.UserName = "no_user";
        this.departIds = "no_depart_id";
        this.department = "no_depart_date";
        this.deviceName = "no_device_name";
        this.rfid = "no_card";
        this.phoneNbr = "no_phone";
        this.rateInDepartment = "no_rate";
        this.rankIndepartment = "no_rank";
    }

    @Override
    public String toString() {
        return "User{" +
                "UserName='" + UserName + '\'' +
                ", departIds='" + departIds + '\'' +
                ", department='" + department + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", rfid='" + rfid + '\'' +
                ", phoneNbr='" + phoneNbr + '\'' +
                ", rateInDepartment='" + rateInDepartment + '\'' +
                ", rankIndepartment='" + rankIndepartment + '\'' +
                ", isLogin=" + isLogin +
                '}';
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        if(userName != null && !TextUtils.isEmpty(userName)){
            UserName = userName;
        }

    }

    public String getDepartIds() {

        return departIds;
    }

    public void setDepartIds(String departIds) {
        if(departIds != null  && !TextUtils.isEmpty(departIds)){
            this.departIds = departIds;
        }
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        if(department != null  && !TextUtils.isEmpty(department)){

            this.department = department;
        }
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        if(deviceName != null && !TextUtils.isEmpty(deviceName)){

            this.deviceName = deviceName;
        }

    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        if(rfid != null  && !TextUtils.isEmpty(rfid)){

            this.rfid = rfid;
        }
    }

    public String getPhoneNbr() {
        return phoneNbr;
    }

    public void setPhoneNbr(String phoneNbr) {
        if(phoneNbr != null  && !TextUtils.isEmpty(phoneNbr)){

            this.phoneNbr = phoneNbr;
        }
    }

    public String getRateInDepartment() {
        return rateInDepartment;
    }

    public void setRateInDepartment(String rateInDepartment) {
        if(rateInDepartment != null  && !TextUtils.isEmpty(rateInDepartment)){

            this.rateInDepartment = rateInDepartment;
        }
    }

    public String getRankIndepartment() {
        return rankIndepartment;
    }

    public void setRankIndepartment(String rankIndepartment) {
        if(rankIndepartment != null  && !TextUtils.isEmpty(rankIndepartment)){

            this.rankIndepartment = rankIndepartment;
        }
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
