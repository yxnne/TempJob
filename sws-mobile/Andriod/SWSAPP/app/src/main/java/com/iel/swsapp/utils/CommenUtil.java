package com.iel.swsapp.utils;

import java.util.Calendar;

/**
 * Class Full Name  : com.iel.swsapp.utils.CommenUtil
 * Author Name      : yxnne
 * Create Time      : 2017/4/19
 * Project Name     : SWSAPP
 * Descriptions     : 公用的一些方法
 */
public class CommenUtil {

    public static String makeURLNotChange(String serverIp,String port){
        StringBuilder urlNotChange = new StringBuilder("http://");
        urlNotChange.append(serverIp)
                .append(":")
                .append(port)
                .append("/")
                .append("iel-hhms/web/app/");
        return urlNotChange.toString();
    }


}
