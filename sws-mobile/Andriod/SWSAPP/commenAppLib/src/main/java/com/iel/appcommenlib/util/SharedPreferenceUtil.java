package com.iel.appcommenlib.util;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * Class Full Name  : com.iel.appcommenlib.util.X
 * Author Name      : yxnne
 * Create Time      : 2017/1/7
 * Project Name     : SWSAPP
 * Descriptions     : 提供首选项的基础方法
 */
public class SharedPreferenceUtil {
    //首选项文件名
    private static final String SHARED_PREF_NAME = "shared_pref";
    //得到String
    //取出whichSp中field字段对应的String类型的值
    public static String getSharedPrefStr(Context mContext, String field){
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREF_NAME, 0);
        return sp.getString(field,"");//如果该字段没对应值，则取出字符串""
    }
    //取出whichSp中field字段对应的String类型的值
    public static String getSharedPrefStr(Context mContext, String field,String defaultValue){
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREF_NAME, 0);
        return sp.getString(field,defaultValue);//如果该字段没对应值，则取出字符串""
    }

    //得到int
    //取出whichSp中field字段对应的int类型的值
    public static int getSharedPrefInt(Context mContext,String field){
        SharedPreferences sp =  mContext.getSharedPreferences(SHARED_PREF_NAME, 0);
        return  sp.getInt(field,0);//如果该字段没对应值，则取出0

    }
    //取出whichSp中field字段对应的int类型的值，重载带默认值的
    public static int getSharedPrefInt(Context mContext,String field,int defaultValue){
        SharedPreferences sp =  mContext.getSharedPreferences(SHARED_PREF_NAME, 0);
        return  sp.getInt(field,defaultValue);//如果该字段没对应值，则取出0

    }

    //得到布尔
    //取出whichSp中field字段对应的boolean类型的值
    public static boolean getSharedPrefBoolean(Context mContext,String field){
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREF_NAME, 0);
        return sp.getBoolean(field, false);//如果该字段没对应值，则取出false

    }
    //取出whichSp中field字段对应的boolean类型的值，重载了一个有默认值的
    public static boolean getSharedPrefBoolean(Context mContext,String field,boolean defaultValue){
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREF_NAME, 0);
        return sp.getBoolean(field, defaultValue);//如果该字段没对应值，则取出false

    }
    //保存string类型的value到whichSp中的field字段
    public static void putSharedPref(Context mContext,String field,String value){
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREF_NAME, 0);
        sp.edit().putString(field, value).apply();
    }
    //保存int类型的value到whichSp中的field字段
    public static void putSharedPref(Context mContext,String field,int value){
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREF_NAME, 0);
        sp.edit().putInt(field, value).apply();
    }

    //保存boolean类型的value到whichSp中的field字段
    public static void putSharedPref(Context mContext,String field,Boolean value){
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREF_NAME, 0);
        sp.edit().putBoolean(field, value).apply();
    }
    //清空保存的数据
    public static void clearAllSharedPref(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREF_NAME, 0);
        sp.edit().clear().apply();

    }

}
