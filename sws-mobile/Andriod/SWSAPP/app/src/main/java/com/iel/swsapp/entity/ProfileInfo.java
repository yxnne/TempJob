package com.iel.swsapp.entity;

/**
 * Class Full Name  : com.iel.swsapp.entity.ProfileInfo
 * Author Name      : yxnne
 * Create Time      : 2017/3/14
 * Project Name     : SWSAPP
 * Descriptions     : 这是一个包含了用户基本信息的实体类
 */
public class ProfileInfo {
    private String userName;
    private String department;
    private String job;
    private String cardNo;
    private String phone;
    private String complianceRate;
    private String departRank;

    public ProfileInfo(){}

    public ProfileInfo(String userName, String department, String job, String cardNo, String phone, String complianceRate, String departRank) {
        this.userName = userName;
        this.department = department;
        this.job = job;
        this.cardNo = cardNo;
        this.phone = phone;
        this.complianceRate = complianceRate;
        this.departRank = departRank;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setComplianceRate(String complianceRate) {
        this.complianceRate = complianceRate;
    }

    public void setDepartRank(String departRank) {
        this.departRank = departRank;
    }

    public String getUserName() {
        return userName;
    }

    public String getDepartment() {
        return department;
    }

    public String getJob() {
        return job;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getPhone() {
        return phone;
    }

    public String getComplianceRate() {
        return complianceRate;
    }

    public String getDepartRank() {
        return departRank;
    }
}
