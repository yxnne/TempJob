package com.iel.swsapp.entity;

import java.io.Serializable;

/**
 * Class Full Name  : com.iel.swsapp.entity.RolesRateOneDay
 * Author Name      : yxnne
 * Create Time      : 2017/4/20
 * Project Name     : SWSAPP
 * Descriptions     : 五种类别的职工的依从率实体类
 * 分别是：
 "医生"
 "护师",
 "护士",
 "护工",
 "保洁" --先不写
 *
 */
public class RolesRateOneDay implements Serializable{
    double doctorRate;
    double physicianRate;
    double nurseRate;
    double supportWorkerRate;

    public RolesRateOneDay(){

    }

    /**
     * 全参浏览器
     */
    public RolesRateOneDay(double doctorRate, double physicianRate, double nurseRate, double supportWorkerRate) {
        this.doctorRate = doctorRate;
        this.physicianRate = physicianRate;
        this.nurseRate = nurseRate;
        this.supportWorkerRate = supportWorkerRate;
    }

    public double getDoctorRate() {
        return doctorRate;
    }

    public void setDoctorRate(double doctorRate) {
        this.doctorRate = doctorRate;
    }

    public double getPhysicianRate() {
        return physicianRate;
    }

    public void setPhysicianRate(double physicianRate) {
        this.physicianRate = physicianRate;
    }

    public double getNurseRate() {
        return nurseRate;
    }

    public void setNurseRate(double nurseRate) {
        this.nurseRate = nurseRate;
    }

    public double getSupportWorkerRate() {
        return supportWorkerRate;
    }

    public void setSupportWorkerRate(double supportWorkerRate) {
        this.supportWorkerRate = supportWorkerRate;
    }

    @Override
    public String toString() {
        return "RolesRateOneDay{" +
                "doctorRate=" + doctorRate +
                ", physicianRate=" + physicianRate +
                ", nurseRate=" + nurseRate +
                ", supportWorkerRate=" + supportWorkerRate +
                '}';
    }
}
