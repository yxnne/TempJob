package com.iel.swsapp.entity.response.starff;

import com.iel.swsapp.entity.response.Properties;

import java.io.Serializable;

/**
 * Class Full Name  : com.iel.swsapp.entity.response.Result
 * Author Name      : yxnne
 * Create Time      : 2017/4/23
 * Project Name     : SWSAPP
 * Descriptions     : TODO
 */
public class Result implements Serializable{
    private String departName;

    private String docName;

    private String docType;

    private int errorNum;

    private int normalNum;

    private int num0003;

    private int num0007;

    private int num0008;

    private int num0103;

    private int num0110;

    private int rank;

    private double rateAfterCloseNick;

    private double rateAfterCloseNickEnvri;

    private double rateBeforeAsepticOperation;

    private double rateBeforeCloseNick;

    private String rate;

    private String rfid;


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

    public int getNum0003() {
        return num0003;
    }

    public void setNum0003(int num0003) {
        this.num0003 = num0003;
    }

    public int getNum0007() {
        return num0007;
    }

    public void setNum0007(int num0007) {
        this.num0007 = num0007;
    }

    public int getNum0008() {
        return num0008;
    }

    public void setNum0008(int num0008) {
        this.num0008 = num0008;
    }

    public int getNum0103() {
        return num0103;
    }

    public void setNum0103(int num0103) {
        this.num0103 = num0103;
    }

    public int getNum0110() {
        return num0110;
    }

    public void setNum0110(int num0110) {
        this.num0110 = num0110;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setDepartName(String departName){
        this.departName = departName;
    }
    public String getDepartName(){
        return this.departName;
    }
    public void setDocName(String docName){
        this.docName = docName;
    }
    public String getDocName(){
        return this.docName;
    }
    public void setDocType(String docType){
        this.docType = docType;
    }
    public String getDocType(){
        return this.docType;
    }
    public void setErrorNum(int errorNum){
        this.errorNum = errorNum;
    }
    public int getErrorNum(){
        return this.errorNum;
    }
    public void setNormalNum(int normalNum){
        this.normalNum = normalNum;
    }
    public int getNormalNum(){
        return this.normalNum;
    }
    public void setRate(String rate){
        this.rate = rate;
    }
    public String getRate(){
        return this.rate;
    }
    public void setRfid(String rfid){
        this.rfid = rfid;
    }
    public String getRfid(){
        return this.rfid;
    }

    @Override
    public String toString() {
        return "Result{" +
                "departName='" + departName + '\'' +
                ", docName='" + docName + '\'' +
                ", docType='" + docType + '\'' +
                ", errorNum=" + errorNum +
                ", normalNum=" + normalNum +
                ", num0003=" + num0003 +
                ", num0007=" + num0007 +
                ", num0008=" + num0008 +
                ", num0103=" + num0103 +
                ", num0110=" + num0110 +
                ", rank=" + rank +
                ", rateAfterCloseNick=" + rateAfterCloseNick +
                ", rateAfterCloseNickEnvri=" + rateAfterCloseNickEnvri +
                ", rateBeforeAsepticOperation=" + rateBeforeAsepticOperation +
                ", rateBeforeCloseNick=" + rateBeforeCloseNick +
                ", rate='" + rate + '\'' +
                ", rfid='" + rfid + '\'' +
                '}';
    }
}
