package com.iel.swsapp.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iel.appcommenlib.util.SharedPreferenceUtil;
import com.iel.swsapp.R;
import com.iel.swsapp.utils.StaticClass;
/**
 * Class Full Name  : com.iel.swsapp.activity.SplashActivity
 * Author Name      : yxnne
 * Create Time      : 2017/3
 * Project Name     : SWSAPP
 * Descriptions     : 该活动用于：
 *                      1，延时两秒用于展示logo，公司版权信息和标语
 *                      2，判断sharedPreference中的字段值是否是第一次启动程序，从而
 *                      确定下一个跳转的activity
 *                      3，还有一个全屏主题，应用在manifest的theme，自定义在style里面
 */
public class SplashActivity extends AppCompatActivity {

    //handler
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case StaticClass.WAIT_A_MOMENT://处理延时事件
                    Intent intent;
                    if(isFirstStartApp()){
                        //第一次启动，就跳转到GlideActivity，引导页面
                        intent = new Intent(SplashActivity.this,
                                GuideActivity.class);
                    }else{
                        //跳转到主页
                        intent = new Intent(SplashActivity.this,
                                LoginActivity.class);
                    }
                    startActivity(intent);
                    //结束当前Activity
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 根据sharedpreference的字段判断
     * @return 是否是第一次启动程序
     */
    private boolean isFirstStartApp() {
        //得到对应字段的结果，如果不存在的话相应的值应该是true
        boolean isFirst =
                SharedPreferenceUtil.getSharedPrefBoolean(
                        this,StaticClass.SP_BOOL_FIRST_START,true);

        if(isFirst){
            //修改sharedpreference的值,false,再返回
            SharedPreferenceUtil.putSharedPref(this,StaticClass.SP_BOOL_FIRST_START,false);
            return true;
        }else {
            return false;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();
        //延时
        mHandler.sendEmptyMessageDelayed(StaticClass.WAIT_A_MOMENT,2000);
    }

    /**
     * 初始化控件
     */
    private void initViews() {

    }


}
