package com.iel.swsapp.entity.response.starff;


import java.io.Serializable;
import java.util.List;

/**
 * Class Full Name  : com.iel.swsapp.entity.response.EventPage
 * Author Name      : yxnne
 * Create Time      : 2017/4/23
 * Project Name     : SWSAPP
 * Descriptions     : TODO
 */
public class Page implements Serializable{

    private int first;

    private int pageNo;

    private int pageSize;

    private List<Result> result;

    private int total;

    private int totalPage;

    public void setFirst(int first){
        this.first = first;
    }
    public int getFirst(){
        return this.first;
    }
    public void setPageNo(int pageNo){
        this.pageNo = pageNo;
    }
    public int getPageNo(){
        return this.pageNo;
    }
    public void setPageSize(int pageSize){
        this.pageSize = pageSize;
    }
    public int getPageSize(){
        return this.pageSize;
    }
    public void setResult(List<Result> result){
        this.result = result;
    }
    public List<Result> getResult(){
        return this.result;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }
    public void setTotalPage(int totalPage){
        this.totalPage = totalPage;
    }
    public int getTotalPage(){
        return this.totalPage;
    }


    @Override
    public String toString() {
        return "EventPage{" +
                "first=" + first +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", result=" + result +
                ", total=" + total +
                ", totalPage=" + totalPage +
                '}';
    }
}
