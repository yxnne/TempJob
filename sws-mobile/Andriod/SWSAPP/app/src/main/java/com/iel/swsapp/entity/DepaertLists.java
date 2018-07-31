package com.iel.swsapp.entity;

import java.io.Serializable;

/**
 * Class Full Name  : com.iel.swsapp.entity.DepaertLists
 * Author Name      : yxnne
 * Create Time      : 2017/5/19
 * Project Name     : SWSAPP
 * Descriptions     : 每一个部门的id 和 name
 */
public class DepaertLists implements Serializable {
    private String departId;

    private String departName;

    public void setDepartId(String departId){
        this.departId = departId;
    }
    public String getDepartId(){
        return this.departId;
    }
    public void setDepartName(String departName){
        this.departName = departName;
    }
    public String getDepartName(){
        return this.departName;
    }

    @Override
    public String toString() {
        return "DepaertLists{" +
                "departId='" + departId + '\'' +
                ", departName='" + departName + '\'' +
                '}';
    }
}
