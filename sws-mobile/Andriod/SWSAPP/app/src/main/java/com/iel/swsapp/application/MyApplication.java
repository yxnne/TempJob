package com.iel.swsapp.application;

import android.app.Activity;
import android.app.Application;

import com.iel.appcommenlib.util.SharedPreferenceUtil;
import com.iel.swsapp.BuildConfig;
import com.iel.swsapp.entity.DepaertLists;
import com.iel.swsapp.entity.User;
import com.iel.swsapp.utils.CommenUtil;
import com.iel.swsapp.utils.StaticClass;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

/**
 * 此版本是Git上库版本
 * Class Full Name  : com.iel.swsapp.application.MyApplication
 * Author Name      : yxnne
 * Create Time      : 2017/3/12
 * Project Name     : SWSAPP
 * Descriptions     : 系统的Application
 */
public class MyApplication extends Application{
    /**系统调试模式，在StaticClass中定义了*/
    public static final int MY_MODEL = StaticClass.MODEL_DEBUG;

    /**系统Application实例*/
    private static MyApplication sInstance;

    /**全局化 用户 变量*/
    private static User sCurrentUser;

    /**全局  当前系统内 部门和对应的ID*/
    //public static List<DepaertLists> sDepaertLists;
    public static List<String> sDepartmentNames;
    public static List<String> sDepartmentIds;

    /**全局  当前系统内 职工类型和对应的ID*/
    public static List<String> sRoleTypeNames;
    public static List<String> sRoleTypeIds;

    /**全局的 服务器url前面的内容的 变量*/
    public static String sUrlNotChangePart;

    /**geter：sInstance*/
    public static MyApplication getAppInstance(){
        return sInstance;
    }

    /**geter：mCurrentUser*/
    public static User getCurrentUser() {
        return sCurrentUser;
    }

    /**用户权限变量*/
    public static int sUserAuthority;

    /**一个管理Activity列表 ,在APP里面统一管理所有继承BaseActivity的引用*/
    public static List<Activity> sActivities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Buggly
        CrashReport.setIsDevelopmentDevice(getApplicationContext(),
                BuildConfig.DEBUG);
        CrashReport.initCrashReport(getApplicationContext(),
                StaticClass.BUGLY_APP_ID, false);
        //App对象引用
        sInstance = this;
        //先实例化一个User
        sCurrentUser = new User();
        //得到全局的Ulr不变的地方
        initURLNotChangePart();
    }

    private void initURLNotChangePart() {
        String serverIp = SharedPreferenceUtil.getSharedPrefStr(this,StaticClass.PREF_SERVER_IP);
        String port = SharedPreferenceUtil.getSharedPrefStr(this,StaticClass.PREF_SERVER_PORT);
        //拼接URL
        sUrlNotChangePart = CommenUtil.makeURLNotChange(serverIp,port);
    }
    /**
     * 关掉Activities集合里面所有的Activity
     */
    public static void closeAllActivities(){
        for(Activity act : sActivities){
            if(!act.isFinishing()){
                act.finish();
            }
        }
    }


}
