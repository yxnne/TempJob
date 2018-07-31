package com.iel.swsapp.entity.response;

import java.io.Serializable;

/**
 * 登录回复的实体
 * Created by Administrator on 2017/1/4.
 * 正常回复如下：
         {"rfid":"","deviceName":"","departIds":"2","department":"演示","userName":"yxn","success":true}

 失败回复：
        {"success":false}
        使用admin登录时会有问题：action报错误，原因是department报空。
 */

public class LoginResponse implements Serializable{

    public static final String KEY_SUCCESS      = "success";
    public static final String KEY_RFID         = "rfid";
    public static final String KEY_DEVICE_NAME  = "deviceName";
    public static final String KEY_DEPARTMENT   = "department";
    public static final String KEY_USERNAME     = "userName";

    private String rfid;
    private String deviceName;
    private String departIds;
    private String department;
    private String userName;
    private boolean success;

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDepartIds() {
        return departIds;
    }

    public void setDepartIds(String departIds) {
        this.departIds = departIds;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "rfid='" + rfid + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", departIds='" + departIds + '\'' +
                ", department='" + department + '\'' +
                ", userName='" + userName + '\'' +
                ", success=" + success +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
