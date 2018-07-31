package com.iel.swsapp.entity.response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/4.
 * 登录回复的实体
 * 正常回复如下：
      查询成功返回{"departMap":{"6":"测试科室"}}
 */

public class GetDepartmentResponse implements Serializable{

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
