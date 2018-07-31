package com.iel.swsapp.activity;

import android.app.Activity;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.iel.appcommenlib.util.SharedPreferenceUtil;
import com.iel.swsapp.R;
import com.iel.swsapp.application.MyApplication;
import com.iel.swsapp.utils.CommenUtil;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import static com.iel.swsapp.utils.StaticClass.PREF_SERVER_IP;
import static com.iel.swsapp.utils.StaticClass.PREF_SERVER_PORT;
/**
 * Class Full Name  : com.iel.swsapp.activity.ConfigServerDialogActivity
 * Author Name      : yxnne
 * Create Time      : 2017/3
 * Project Name     : SWSAPP
 * Descriptions     : 针对APP局域网环境，需要配置下服务求，并测试通讯
 */
public class ConfigServerDialogActivity extends Activity implements View.OnClickListener {

    private static String TEST_URL = "/iel-hhms/web/app/serverConfigTest.action";

    private EditText mEtServerIp;
    private EditText mEtServerPort;

    private Button mBtnSure;
    private Button mBtnCancel;
    private Button mBtnTest;
    private boolean mConfigOk ;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Toast.makeText(ConfigServerDialogActivity.this,
                            getString(R.string.info_test_no_server_error),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private Runnable mCheckTask = new Runnable() {
        @Override
        public void run() {
            if(!mConfigOk){//没有配置成功
                mHandler.sendEmptyMessage(1);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //声明这里要使用了
        //这里使用下转场动画
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_config_server_dialog);
        mConfigOk = false;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.explode);
            Transition fade = TransitionInflater.from(this).inflateTransition(R.transition.fade);
            //退出时使用
            getWindow().setExitTransition(fade);
            //第一次进入时使用
            getWindow().setEnterTransition(explode);
            //再次进入时使用
            getWindow().setReenterTransition(explode);

        }
        findViews();

        Intent intent = getIntent();

        mEtServerIp.setText(intent.getStringExtra(LoginActivity.EXTRA_SERVER_IP));
        mEtServerPort.setText(intent.getStringExtra(LoginActivity.EXTRA_SERVER_PORT));

    }

    private void findViews() {
        mEtServerIp = (EditText) findViewById(R.id.et_config_server_ip_dialog);
        mEtServerPort = (EditText) findViewById(R.id.et_config_server_port_dialog);

        mBtnSure = (Button) findViewById(R.id.btn_config_server_ok);
        mBtnCancel = (Button) findViewById(R.id.btn_config_server_cancel);
        mBtnTest = (Button) findViewById(R.id.btn_config_server_test);

        mBtnSure.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        mBtnTest.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_config_server_ok:
                configOK();
                break;
            case R.id.btn_config_server_cancel:
                configCancel();
                break;
            case R.id.btn_config_server_test:
                testConfig();
                break;
        }

    }

    /**
     * 测试下配置信息
     * 逻辑很简单，不用封装到业务层
     * 原理根据edittext中的值，发送测试接口道服务器，拼接URL之后等待返回消息
     * 若返回，则表示测试成功，配置成功，如果不返回，网络或者配置可能有问题
     * http://192.168.124.20:8080/iel-hhms/web/app/serverConfigTest.action
     */
    private void testConfig() {
        mConfigOk = false;
        if(TextUtils.isEmpty(mEtServerIp.getText().toString())
                ||TextUtils.isEmpty(mEtServerPort.getText().toString())){
            Toast.makeText(this,getString(R.string.info_test_no_server_msg),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        String ip = mEtServerIp.getText().toString();
        String port = mEtServerPort.getText().toString();

        StringBuilder urlTest = new StringBuilder("");
        urlTest.append("http://")
        .append(ip).append(":").append(port).append(TEST_URL);

        //马上要发请求了，开启一个线程，计时5000ms
        //之后判断下
        mHandler.postDelayed(mCheckTask,5000);


        RxVolley.get(urlTest.toString(), new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                String result = "";
                try {
                    JSONObject jObj = new JSONObject(t);
                    result = jObj.getString("result");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(!"ok".equals(result)){//不成功
                    //返回
                    return;
                }

                //任务撤销
                mHandler.removeCallbacks(mCheckTask);
                //标致改变
                mConfigOk = true;
                //成功提示
                Toast.makeText(ConfigServerDialogActivity.this,
                        getString(R.string.info_test_server_comunicate),
                        Toast.LENGTH_SHORT).show();

            }
        });

    }

    /**
     * 配置取消
     */
    private void configCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    /**
     * 完成配置
     */
    private void configOK() {
        String serverIp = mEtServerIp.getText().toString().trim();
        String serverPort = mEtServerPort.getText().toString().trim();
        //写入首选项
        SharedPreferenceUtil.putSharedPref(this,PREF_SERVER_IP,serverIp);
        SharedPreferenceUtil.putSharedPref(this,PREF_SERVER_PORT,serverPort);

        //拼接下URL不变的的地方
        MyApplication.sUrlNotChangePart = CommenUtil.makeURLNotChange(serverIp,serverPort);

        //设置返回码，Intent，返回
        Intent intentBack = getIntent();
        intentBack.putExtra(LoginActivity.EXTRA_SERVER_IP,serverIp);
        intentBack.putExtra(LoginActivity.EXTRA_SERVER_PORT,serverPort);
        setResult(RESULT_OK,intentBack);
        finish();

    }
}
