package com.iel.swsapp.business;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.iel.swsapp.application.MyApplication;
import com.iel.swsapp.entity.response.LoginResponse;
import com.iel.swsapp.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Class Full Name  : com.iel.swsapp.business.LoginBiz
 * Author Name      : yxnne
 * Create Time      : 2017/3/12
 * Project Name     : SWSAPP
 * Descriptions     :处理登录的业务
 */
public class LoginBiz {

    private static final String URL_LOGIN_PATH = "appLogin.action";

    /**原因码*/
    public static final int LOGIN_ERROR         = 9;
    public static final int USER_OR_PWD_ERROR   = 8;

    public static final int LOGIN_OK            = 1;


    /**
     * 内部类封装结果
     * 包涵了一个原因码
     * 一个布尔值
     * 一个封装好的返回实体
     */
    public static class LoginResault{

        private boolean loginResault  ;
        private int reason ;
        private LoginResponse loginResponseEntity;

        //private


        public void setLoginResault(boolean loginResault) {
            this.loginResault = loginResault;
        }

        public void setReason(int reason) {
            this.reason = reason;
        }

        public void setLoginResponseEntity(LoginResponse loginResponseEntity) {
            this.loginResponseEntity = loginResponseEntity;
        }

        public boolean getLoginResault() {
            return loginResault;
        }

        public int getReason() {
            return reason;
        }

        public LoginResponse getLoginResponseEntity() {
            return loginResponseEntity;
        }
    }


    /**
     * 执行登录的方法
     * @param username 用户名
     * @param pwd 密码
     * @param serverIp 服务器IP地址
     * @param port 端口号
     */
    public void doLogin(final Handler handler, String username, String pwd, String serverIp, String port){

        try {
            username = URLEncoder.encode(username, "utf-8");
            pwd = URLEncoder.encode(pwd, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder url = new StringBuilder(MyApplication.sUrlNotChangePart);
        url.append(URL_LOGIN_PATH).append("?");
        url.append("userName").append("=").append(username).append("&");
        url.append("passWord").append("=").append(pwd);
        Logger.i(url.toString());

        final LoginResault resault = new LoginResault();
        /**假数据实现方案*/
        if(MyApplication.MY_MODEL == StaticClass.MODEL_FAKE_DATA){
            if(TextUtils.equals(username,"admin")||TextUtils.equals(username,"yxn")||TextUtils.equals(username,"test")){
                if(TextUtils.equals(pwd,"12345")){
                    resault.loginResault = true;
                    resault.reason = LOGIN_OK;
                }else{
                    resault.loginResault = false;
                    resault.reason = USER_OR_PWD_ERROR;
                }

            }else{
                resault.loginResault = false;
                resault.reason = USER_OR_PWD_ERROR;

            }
            //发送消息
            Message msg = Message.obtain();
            msg.obj = resault;
            handler.sendMessage(msg);
        }
        //非假数据方案
        else
        {

            RxVolley.get(url.toString(), new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    //super.onSuccess(t);
                    try{

                        Gson gson = new Gson();
                        LoginResponse loginResponse = gson.fromJson(
                                t,LoginResponse.class);
                        Logger.i(loginResponse.toString());
                        //封装resault对象
                        resault.setLoginResponseEntity(loginResponse);
                        if(!loginResponse.isSuccess()){
                            resault.setReason(USER_OR_PWD_ERROR);
                        }
                        resault.setLoginResault(loginResponse.isSuccess());

                    }catch (Exception e){
                        resault.setLoginResault(false);
                        resault.setReason(LOGIN_ERROR);

                    }

                    //发送消息
                    Message msg = Message.obtain();
                    msg.obj = resault;
                    handler.sendMessage(msg);
                }
            });
        }



    }

}
