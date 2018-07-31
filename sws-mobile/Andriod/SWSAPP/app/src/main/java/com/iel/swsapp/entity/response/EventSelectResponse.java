package com.iel.swsapp.entity.response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/4.
 * 事件查询的实体----最外层实体
 *
 */

public class EventSelectResponse implements Serializable{

    private Page page;

    public void setPage(Page page){
        this.page = page;
    }
    public Page getPage(){
        return this.page;
    }

    @Override
    public String toString() {
        return "EventSelectResponse{" +
                "page=" + page +
                '}';
    }
}
