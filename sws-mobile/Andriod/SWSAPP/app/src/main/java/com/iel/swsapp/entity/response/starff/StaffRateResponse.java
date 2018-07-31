package com.iel.swsapp.entity.response.starff;

import java.io.Serializable;

/**
 * Class Full Name  : com.iel.swsapp.entity.response.starff.StaffRateResponse
 * Author Name      : yxnne
 * Create Time      : 2017/4/24
 * Project Name     : SWSAPP
 * Descriptions     : 部门员工依从率总实体类
 */
public class StaffRateResponse implements Serializable {

    private Page page;

    public void setPage(Page page){
        this.page = page;
    }
    public Page getPage(){
        return this.page;
    }

    @Override
    public String toString() {
        return "StaffRateResponse{" +
                "page=" + page +
                '}';
    }
}
