package com.iel.swsapp.utils;

import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Class Full Name  : com.iel.swsapp.utils.DateUtils
 * Author Name      : yxnne
 * Create Time      : 2017/3/13
 * Project Name     : SWSAPP
 * Descriptions     : 处理时间的简单工具
 */
public class DateUtils {
    /**
     * 将Date转换成 "2017-02-18 16:03:03"这种格式的字符串
     * @param date
     * @return String like "2017-02-18 16:03:03"
     */
    public static String getDateString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  sdf.format(date);
    }

    /**
     * 得到当前日期的字符串
     * @return 这个格式的字符串 "2017-1-30"
     */
    public static String getNowDateString(){
        Calendar now = Calendar.getInstance();
        int year =  now.get(Calendar.YEAR);
        int month =  now.get(Calendar.MONTH) + 1 ;
        int day = now.get(Calendar.DAY_OF_MONTH);
        return year+ "-" + month + "-" + day;

    }
    /**
     * 根据日期对象，得到 日期的字符串
     * @return 这个格式的字符串 "2017-1-30"
     */
    public static String getDateString(Calendar calendar){
        int year =  calendar.get(Calendar.YEAR);
        int month =  calendar.get(Calendar.MONTH) + 1 ;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year+ "-" + month + "-" + day;

    }



    /**
     * 得到7天前的日期字符串，按数据格式
     * 无参数的方法就是默认最后一天是当前一天·
     * @return 这个格式的字符串 "2017-1-30"
     */
    public static String get7DaysBeforeDate(){
        return getDaysBeforeDate(getNowDateString(),7);
    }

    /**
     * 得到相对于date，7天前的日期字符串，按数据格式
     * @param date String类型的时间字符串
     * @return 这个格式的字符串 "2017-1-30"
     */
    public static String get7DaysBeforeDate(String date){
        return getDaysBeforeDate(date,7);
    }

    /**
     * 在lastDate之前 beforeDays 的时间
     * @param lastDate 时间段最后的日期
     * @param beforeDays 向前挪动多少时间
     * @return String字符串
     */
    public static String getDaysBeforeDate(String lastDate,int beforeDays){
        //先分割字符串
        String[] dateNbrs = lastDate.split("-");
        // 得到时间
        Calendar date = Calendar.getInstance();
        date.set(Integer.valueOf(dateNbrs[0]),  //year
                Integer.valueOf(dateNbrs[1]) - 1,   //month 字符串值 计数值不一样的
                Integer.valueOf(dateNbrs[2])    //day
        );

        date.set(Calendar.DATE,date.get(Calendar.DATE) - beforeDays);

        // 返回日期字符串
        return getDateString(date);

    }

    /**
     * 将类似 2017-4-25，去掉年份，变成4-25
     * @param ymd 原始字符串
     * @return 变化之后
     */
    public static String getMonthAndDay(String ymd){
        String[] ymds = ymd.split("-");
        return ymds[1]+"-"+ymds[2];

    }
    /**
     * 将类似 2017-04-11T13:21:31，去掉年份，变成4-25 13:25:11
     * @param time 原始字符串
     * @return 变化之后*
     *
     */
    public static String getMonthAndDayAndCMS(String time){
        String[] timeParts = time.split("T");
        String part1 = getMonthAndDay(timeParts[0]);

        return part1+" "+timeParts[1];

    }

    /**
     * 04-17 14:15:02 这个格式的比较大小
     * @return
     */
    public static boolean compareTime_MD_Hms(String str1,String str2){
        //
        String[] str1_Sps = str1.split(" ");
        String[] str1_MD =  str1_Sps[0].split("-");
        String str1_month = str1_MD[0];
        String str1_day = str1_MD[1];
        String[] str1_hms =  str1_Sps[1].split(":");
        String str1_hour = str1_hms[0];
        String str1_minite = str1_hms[1];
        String str1_second = str1_hms[2];

        //
        String[] str2_Sps = str2.split(" ");
        String[] str2_MD =  str2_Sps[0].split("-");
        String str2_month = str2_MD[0];
        String str2_day = str2_MD[1];
        String[] str2_hms =  str2_Sps[1].split(":");
        String str2_hour = str2_hms[0];
        String str2_minite = str2_hms[1];
        String str2_second = str2_hms[2];

        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.MONTH,Integer.valueOf(str1_month)-1);
        c1.set(Calendar.DAY_OF_MONTH,Integer.valueOf(str1_day));
        c1.set(Calendar.HOUR,Integer.valueOf(str1_hour));
        c1.set(Calendar.MINUTE,Integer.valueOf(str1_minite));
        c1.set(Calendar.SECOND,Integer.valueOf(str1_second));

        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.MONTH,Integer.valueOf(str2_month)-1);
        c2.set(Calendar.DAY_OF_MONTH,Integer.valueOf(str2_day));
        c2.set(Calendar.HOUR,Integer.valueOf(str2_hour));
        c2.set(Calendar.MINUTE,Integer.valueOf(str2_minite));
        c2.set(Calendar.SECOND,Integer.valueOf(str2_second));

        return  c1.before(c2);

    }



}
