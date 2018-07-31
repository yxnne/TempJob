package com.iel.swsapp.entity;

import java.io.Serializable;

/**
 * Class Full Name  : com.iel.swsapp.entity.DepaertLists
 * Author Name      : yxnne
 * Create Time      : 2017/10/19
 * Project Name     : SWSAPP
 * Descriptions     : 每一个类型员工的id 和 name
 */
public class StaffTypeLists implements Serializable {
    private String typeId;

    private String typeName;

    public String getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "StaffTypeLists{" +
                "typeId='" + typeId + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
