package com.iel.swsapp.entity.response;

import java.io.Serializable;

/**
 * Class Full Name  : com.iel.swsapp.entity.response.Result
 * Author Name      : yxnne
 * Create Time      : 2017/4/23
 * Project Name     : SWSAPP
 * Descriptions     : TODO
 */
public class Result implements Serializable{

    private String apNo;

    private String content;

    private String createTime;

    private String creator;

    private String departName;

    private String departmentNo;

    private String deviceNo;

    private String deviceType;

    private String deviceTypeName;

    private String docName;

    private String eventType;

    private String eventTypeName;

    private String hospitalNo;

    private int id;

    private String ip;

    private String postCode;

    private Properties properties;

    private String remark;

    private String rfid;

    private int rfidStatus;

    private String rfidStatusName;

    private int status;

    private String timeStr;

    private String type;

    private String updateTime;

    public void setApNo(String apNo){
        this.apNo = apNo;
    }
    public String getApNo(){
        return this.apNo;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
    public void setCreator(String creator){
        this.creator = creator;
    }
    public String getCreator(){
        return this.creator;
    }
    public void setDepartName(String departName){
        this.departName = departName;
    }
    public String getDepartName(){
        return this.departName;
    }
    public void setDepartmentNo(String departmentNo){
        this.departmentNo = departmentNo;
    }
    public String getDepartmentNo(){
        return this.departmentNo;
    }
    public void setDeviceNo(String deviceNo){
        this.deviceNo = deviceNo;
    }
    public String getDeviceNo(){
        return this.deviceNo;
    }
    public void setDeviceType(String deviceType){
        this.deviceType = deviceType;
    }
    public String getDeviceType(){
        return this.deviceType;
    }
    public void setDeviceTypeName(String deviceTypeName){
        this.deviceTypeName = deviceTypeName;
    }
    public String getDeviceTypeName(){
        return this.deviceTypeName;
    }
    public void setDocName(String docName){
        this.docName = docName;
    }
    public String getDocName(){
        return this.docName;
    }
    public void setEventType(String eventType){
        this.eventType = eventType;
    }
    public String getEventType(){
        return this.eventType;
    }
    public void setEventTypeName(String eventTypeName){
        this.eventTypeName = eventTypeName;
    }
    public String getEventTypeName(){
        return this.eventTypeName;
    }
    public void setHospitalNo(String hospitalNo){
        this.hospitalNo = hospitalNo;
    }
    public String getHospitalNo(){
        return this.hospitalNo;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setIp(String ip){
        this.ip = ip;
    }
    public String getIp(){
        return this.ip;
    }
    public void setPostCode(String postCode){
        this.postCode = postCode;
    }
    public String getPostCode(){
        return this.postCode;
    }
    public void setProperties(Properties properties){
        this.properties = properties;
    }
    public Properties getProperties(){
        return this.properties;
    }
    public void setRemark(String remark){
        this.remark = remark;
    }
    public String getRemark(){
        return this.remark;
    }
    public void setRfid(String rfid){
        this.rfid = rfid;
    }
    public String getRfid(){
        return this.rfid;
    }
    public void setRfidStatus(int rfidStatus){
        this.rfidStatus = rfidStatus;
    }
    public int getRfidStatus(){
        return this.rfidStatus;
    }
    public void setRfidStatusName(String rfidStatusName){
        this.rfidStatusName = rfidStatusName;
    }
    public String getRfidStatusName(){
        return this.rfidStatusName;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setTimeStr(String timeStr){
        this.timeStr = timeStr;
    }
    public String getTimeStr(){
        return this.timeStr;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setUpdateTime(String updateTime){
        this.updateTime = updateTime;
    }
    public String getUpdateTime(){
        return this.updateTime;
    }

    @Override
    public String toString() {
        return "Result{" +
                "apNo='" + apNo + '\'' +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                ", creator='" + creator + '\'' +
                ", departName='" + departName + '\'' +
                ", departmentNo='" + departmentNo + '\'' +
                ", deviceNo='" + deviceNo + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceTypeName='" + deviceTypeName + '\'' +
                ", docName='" + docName + '\'' +
                ", eventType='" + eventType + '\'' +
                ", eventTypeName='" + eventTypeName + '\'' +
                ", hospitalNo='" + hospitalNo + '\'' +
                ", id=" + id +
                ", ip='" + ip + '\'' +
                ", postCode='" + postCode + '\'' +
                ", properties=" + properties +
                ", remark='" + remark + '\'' +
                ", rfid='" + rfid + '\'' +
                ", rfidStatus=" + rfidStatus +
                ", rfidStatusName='" + rfidStatusName + '\'' +
                ", status=" + status +
                ", timeStr='" + timeStr + '\'' +
                ", type='" + type + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
