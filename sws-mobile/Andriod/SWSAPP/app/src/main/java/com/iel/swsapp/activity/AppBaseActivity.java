package com.iel.swsapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.iel.appcommenlib.activity.BaseActivty;
import com.iel.swsapp.R;
import com.iel.swsapp.application.MyApplication;
import com.iel.swsapp.utils.NetworkUtil;
/**
 * Class Full Name  : com.iel.swsapp.activity.ActionbarBackBaseActivity
 * Author Name      : yxnne
 * Create Time      : 2017/2/16
 * Project Name     : SWSAPP
 * Descriptions     : 1.点击两次退出关闭app
 *                    2.在onCreate和 onDestory中分别对Application类中静态声明的Activity集合操作
 *                      具体：onCreate中加入集合
 *                      onDeatory中从集合删除
 */

public abstract  class AppBaseActivity extends BaseActivty{

    /**退出记录点击次数 */
    private int mBackKeyPressedTimes;

    /**在程序的App类中维护这所有Activity的引用 ，
     * 在oncreate()中加入队列，在Ondestory中删除引用队列 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication.sActivities.add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.sActivities.remove(this);
    }

    /**
     * 检查Internet的链接,之后弹出对话框，提示用户打开相关设置
     */
    public void checkInternetState(){
        if (!NetworkUtil.isNetworkConnected(this)) {
            //没有网络,弹出对话框
            AlertDialog.Builder dialogBuider = new AlertDialog.Builder(this);
            dialogBuider.setMessage(R.string.info_no_internet_dialog_msg);
            dialogBuider.setTitle(R.string.info_no_internet_dialog_tittle);
            dialogBuider.setNegativeButton(R.string.info_dialog_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            //final Context context = this;
            dialogBuider.setPositiveButton(R.string.info_dialog_sure, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(context,"",Toast.LENGTH_SHORT).show();
                    //打开相关设置页面
                    Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(intent);

                }
            });
            dialogBuider.create().show();

        }

    }

    /**
     * 连续点击两次退出
     */
    @Override
    public void onBackPressed() {

        if (mBackKeyPressedTimes == 0) {
            Toast.makeText(this, "再按一次退出程序 ", Toast.LENGTH_SHORT).show();
            mBackKeyPressedTimes = 1;
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        mBackKeyPressedTimes = 0;
                    }
                }
            }.start();
            return;
        }
        else{
            finish();
        }

        super.onBackPressed();
    }
}
