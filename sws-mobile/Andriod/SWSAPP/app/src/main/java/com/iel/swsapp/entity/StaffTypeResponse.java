package com.iel.swsapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Class Full Name  : com.iel.swsapp.entity.DepartsResponse
 * Author Name      : yxnne
 * Create Time      : 2017/10/19
 * Project Name     : SWSAPP
 * Descriptions     : 请求所有职工类型的时候的部门封装类
 */
public class StaffTypeResponse implements Serializable{

    private List<StaffTypeLists> typeLists;

    public List<StaffTypeLists> getTypeLists() {
        return typeLists;
    }

    public void setTypeLists(List<StaffTypeLists> typeLists) {
        this.typeLists = typeLists;
    }

    @Override
    public String toString() {
        return "StaffTypeResponse{" +
                "typeLists=" + typeLists +
                '}';
    }
}
