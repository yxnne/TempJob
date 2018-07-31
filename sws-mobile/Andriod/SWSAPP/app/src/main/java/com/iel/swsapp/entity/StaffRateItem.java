package com.iel.swsapp.entity;

import java.io.Serializable;

/**
 * Class Full Name  : com.iel.swsapp.entity.StaffRateItem
 * Author Name      : yxnne
 * Create Time      : 2017/4/25
 * Project Name     : SWSAPP
 * Descriptions     : TODO
 */
public class StaffRateItem implements Serializable {
    private String staffName;
    private String jobType;
    private String rfid;
    private String rate;

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "StaffRateItem{" +
                "staffName='" + staffName + '\'' +
                ", jobType='" + jobType + '\'' +
                ", rfid='" + rfid + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }
}
