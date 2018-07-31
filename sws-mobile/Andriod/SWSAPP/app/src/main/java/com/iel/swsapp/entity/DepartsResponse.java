package com.iel.swsapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Class Full Name  : com.iel.swsapp.entity.DepartsResponse
 * Author Name      : yxnne
 * Create Time      : 2017/5/19
 * Project Name     : SWSAPP
 * Descriptions     : 请求所有部门的时候的部门封装类
 */
public class DepartsResponse implements Serializable{

    private List<DepaertLists> depaertLists;

    public void setDepaertLists(List<DepaertLists> depaertLists){
        this.depaertLists = depaertLists;
    }
    public List<DepaertLists> getDepaertLists(){
        return this.depaertLists;
    }

    @Override
    public String toString() {
        return "DepartsResponse{" +
                "depaertLists=" + depaertLists +
                '}';
    }
}
