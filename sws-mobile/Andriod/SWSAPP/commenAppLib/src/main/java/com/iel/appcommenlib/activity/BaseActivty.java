package com.iel.appcommenlib.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yxnne on 2016/12/15.
 * 这是一个抽象类
 * 重新定义的 生命周期方法：
 *
 */

public abstract class BaseActivty extends AppCompatActivity {

    /**
     * 逻辑是：先初始化数据然后再初始化控件
     * @param savedInstanceState 用于恢复的保存的变量
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initDatas();
        //initViews(savedInstanceState);

        //setContentView(R.layout.activity_main);
    }
    /**
     * 初始化UI的逻辑
     */
    //protected abstract void initViews(Bundle savedInstanceState);


    /**
     * 初始化数据
     */
    //protected abstract void initDatas();



}
