package com.iel.swsapp.entity;

import com.iel.swsapp.utils.DateUtils;

import java.io.Serializable;

/**
 * Class Full Name  : com.iel.swsapp.entity.EventItem
 * Author Name      : yxnne
 * Create Time      : 2017/3/13
 * Project Name     : SWSAPP
 * Descriptions     : TODO
 */
public class EventItem implements Serializable,Comparable<EventItem>{

    private String roleName;
    private String event;
    private String time;

    public EventItem( ) {

    }

    public EventItem(String roleName, String event, String time) {
        this.roleName = roleName;
        this.event = event;
        this.time = time;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "EventItem{" +
                "roleName='" + roleName + '\'' +
                ", event='" + event + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    @Override
    public int compareTo(EventItem another) {


        if(DateUtils.compareTime_MD_Hms(this.getTime(),another.getTime())){
            return 1;
        }else{
            return 0;
        }
    }
}
