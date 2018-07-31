package com.iel.swsapp.utils;

import android.util.Log;

/**
 * Class Full Name  : com.iel.swsapp.utils.L
 * Author Name      : yxnne
 * Create Time      : 2017/2/9
 * Project Name     : SWSAPP
 * Descriptions     : 封装android系统自带的Logcat的log输出
 */
public class L {

    /**
     * 简单的Log封装
     * @param tag 标签
     * @param msg 消息
     */
    public void d(String tag,String msg){
        Log.d(tag,msg);
    }


}
