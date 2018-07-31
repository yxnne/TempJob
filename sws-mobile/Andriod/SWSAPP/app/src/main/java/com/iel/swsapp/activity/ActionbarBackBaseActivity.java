package com.iel.swsapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Class Full Name  : com.iel.swsapp.activity.ActionbarBackBaseActivity
 * Author Name      : yxnne
 * Create Time      : 2017/2/16
 * Project Name     : SWSAPP
 * Descriptions     : 主要是针对有些需要在ActionBar上带有回退键的基础Activity
 *                    在这里统一控制这类Activity的基础样式
 */
public class ActionbarBackBaseActivity extends AppBaseActivity {
    //标题
    protected TextView mActionbarTittle;
    //返回键
    //protected ImageView mActionbarBack;
    protected RelativeLayout mRlActionbarBack;
    //菜单键
    protected ImageView mActionbarMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //显示返回键，Actionbar的
//        getSupportActionBar().setElevation(0);//去掉actionbar的阴影
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //不使用系统自带的ActionBar了
        //使用自己的
    }

    /**
     * 自己的ActionBar的设置
     * @param viewBackRes 左侧后退键的资源ID
     * @param viewMenuRes 右侧菜单键的资源ID
     * @param viewTittleRes 中间的Tittle的ID
     * @param isNeedMenu 是否需要菜单
     * @param tittle 中间标题
     */
    public void setMyActionBar(int viewBackRes,int viewMenuRes,int viewTittleRes,boolean isNeedMenu,String tittle){
        mRlActionbarBack = (RelativeLayout) findViewById(viewBackRes);
        mActionbarMenu = (ImageView) findViewById(viewMenuRes);
        mActionbarTittle = (TextView) findViewById(viewTittleRes);
        //设置标题
        mActionbarTittle.setText(tittle);
        //是否需要menu
        if(!isNeedMenu){
            mActionbarMenu.setVisibility(View.GONE);
        }
        //返回键负责关闭
        mRlActionbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
