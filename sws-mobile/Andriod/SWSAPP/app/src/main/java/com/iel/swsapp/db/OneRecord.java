package com.iel.swsapp.db;

/**
 * class for db cursor
 * Created by yxnne on 2018/4/26.
 */

public class OneRecord{
    public int id;
    public String nbr;
    public String obey;
    public String name;
    public String role;
    public String depart;
    public String way;
    public String occasion;
    public String correct;
    public Long time;

@Override
public String toString() {
        return id + "\n" + name + "\n" + role + "\n"+
        depart + "\n"+ way + "\n" + occasion + "\n obey->"+ obey + "\n";
        }
        }