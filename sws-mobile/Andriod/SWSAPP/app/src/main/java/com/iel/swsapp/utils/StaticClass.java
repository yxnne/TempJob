package com.iel.swsapp.utils;

/**
 * Class Full Name  : com.iel.swsapp.utils.StaticClass
 * Author Name      : yxnne
 * Create Time      : 2017/2/13
 * Project Name     : SWSAPP
 * Descriptions     : 定义一些常量
 */
public class StaticClass {

    // 感控科 和全院的固定ID
    public static final String DEPARTMENT_ID_GANKONG = "132";
    public static final String DEPARTMENT_ID_HOSPITAL = "1";

    // Bugly的APP ID
    public static final String BUGLY_APP_ID = "cf694de99a";
    /**int类型        ---------------------------------------------*/
    /**Handler使用的msg的 what值得定义*/
    //系统模式
    public static final int MODEL_RELEASE       = 101;
    public static final int MODEL_DEBUG         = 102;
    public static final int MODEL_FAKE_DATA     = 103;

    //用户权限值
    public static final int AUTHORITY_HOSPITAL  = 1111;
    public static final int AUTHORITY_DEPART    = 1112;

    //splash activity
    public static final int WAIT_A_MOMENT       = 1001;


    /**String类型    ---------------------------------------------*/

    /**系统中不变的部门编号*/
    public static final String SYS_DEPART_ID_ADIMN   = "1";     /*admin*/
    public static final String SYS_DEPART_ID_GANKONG = "132";   /*感控科*/
    /**SharedPreference*/
    public static final String SP_BOOL_FIRST_START = "is_first_app";


    public static final String PREF_SERVER_IP = "server_ip";
    public static final String PREF_SERVER_PORT = "server_port";

    public static final String PREF_USERNAME = "username";
    public static final String PREF_PWD = "password";

    /** Broadcast Intent Actions*/
    public static final String ACTION_GET_ROLES_RATE = "com.iel.sws.action_get_roles_rate";
    public static final String ACTION_GET_ROLES_RATE_RADAR = "com.iel.sws.action_get_roles_rate_radar";
    public static final String ACTION_GET_DEPART_MOMENT_PIE = "com.iel.sws.action_get_depart_moment_pie";

    public static final String ACTION_GET_DEPART_OVERALL_RATE = "com.iel.sws.action_get_depart_overall_rate";
    public static final String ACTION_GET_HIS_EVENT = "com.iel.sws.action_get_history_event";
    public static final String ACTION_GET_REAL_EVENT = "com.iel.sws.action_get_real_event";
    public static final String ACTION_GET_STAFF_RATE = "com.iel.sws.action_get_staff_rate";
    public static final String ACTION_GET_ONE_STAFF_RATE = "com.iel.sws.action_get_one_staff_rate";

}
