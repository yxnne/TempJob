package com.iel.swsapp.utils;


import android.content.Intent;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Class Full Name  : com.iel.swsapp.utils.StasticUtil
 * Author Name      : yxnne
 * Create Time      : 2017/4/20
 * Project Name     : SWSAPP
 * Descriptions     : 计算需要的小工具
 */
public class StasticUtil {
    /**
     *
     * @param rightCount 正确的次数
     * @param wrongCount 错误的次数
     * @return double
     */
    public static double calcRate(int rightCount,int wrongCount){
        double total = rightCount + wrongCount;
        if(total == 0){
            return 0;
        }
        double rate =  rightCount/total;

        BigDecimal b =   new   BigDecimal(rate);
        return  b.setScale(3 , BigDecimal.ROUND_HALF_UP).doubleValue()*100;

    }

    /**
     *
     * @param rightCount 正确的次数字符串
     * @param wrongCount 错误的次数字符串
     * @return double
     */
    public static double calcRate(String rightCount,String wrongCount){

        int right = Integer.valueOf(rightCount);
        int wrong = Integer.valueOf(wrongCount);

        return  calcRate(right,wrong);
    }

    /**
     * 将字符串格式的String 转变成double
     * 保留一位小数点
     * @param strNbr 字符串
     * @return double类型
     */
    public static float StrNbr2Double(String strNbr){
        float temp = Float.valueOf(strNbr);
        DecimalFormat df = new DecimalFormat("#.0");
        //df.format(temp);
        return Float.valueOf(df.format(temp));

    }


}
