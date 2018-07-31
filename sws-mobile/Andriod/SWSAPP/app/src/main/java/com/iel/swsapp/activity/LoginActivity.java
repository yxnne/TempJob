package com.iel.swsapp.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;


import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.gson.Gson;
import com.iel.appcommenlib.util.SharedPreferenceUtil;
import com.iel.swsapp.R;
import com.iel.swsapp.application.MyApplication;
import com.iel.swsapp.business.GetDepartsBiz;
import com.iel.swsapp.business.GetStaffTypeBiz;
import com.iel.swsapp.business.LoginBiz;
import com.iel.swsapp.entity.User;
import com.iel.swsapp.entity.response.LoginResponse;
import com.iel.swsapp.utils.AnimatorUtil;
import com.iel.swsapp.utils.StaticClass;
import com.orhanobut.logger.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.iel.swsapp.utils.StaticClass.DEPARTMENT_ID_GANKONG;
import static com.iel.swsapp.utils.StaticClass.PREF_PWD;
import static com.iel.swsapp.utils.StaticClass.PREF_SERVER_IP;
import static com.iel.swsapp.utils.StaticClass.PREF_SERVER_PORT;
import static com.iel.swsapp.utils.StaticClass.PREF_USERNAME;
/**
 * Class Full Name  : com.iel.swsapp.activity.LoginActivity
 * Author Name      : yxnne
 * Create Time      : 2017/3
 * Project Name     : SWSAPP
 * Descriptions     :登录活动。本页面的主要工作：
 *                  1，获取用户名密码输入，登录成功跳转主页
 *                  2，配置服务器地址
 *                  3，用户名密码记录首选项
 *                  4，配置信息记录首选项
 *                  5，未输入信息点击登录动画
 *                  6，异常提示: a,用户名密码错误；b,没有网络;c,网络错误
 *
 */
public class LoginActivity extends AppBaseActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    public static final String EXTRA_SERVER_IP = "server_ip";
    public static final String EXTRA_SERVER_PORT = "server_port";

    ///"iel-hhms/web/index.action/app/appLogin.action"
    private static final String URL_LOGIN_TEST = "http://192.168.1.6/cjmHandSystem/web/app/appLogin.action?userName=yx2n&passWord=12345";

    private static final int USER_EMPTY = 11001;
    private static final int PWD_EMPTY = 11002;
    private static final int REQUEST_CODE_TO_CONFIG = 1102;

    private String mServerIp;
    private String mServerPort;

    //按钮
    private Button mBtnLogin;
    private Button mBtnConfig;
    //输入框
    private EditText mEtUserName;
    private EditText mEtPassword;
    //输入框错误警告
    private ImageView mIvWarnningUser;
    private ImageView mIvWarnningPwd;
    //布局，用于属性动画
    private LinearLayout mLinearLayoutMain;

    private String mUserNameToLogin,mPwdToLogin;

    /**该handler用于处理网络请求的结果一些消息*/
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            LoginBiz.LoginResault resault = (LoginBiz.LoginResault) msg.obj;
            if(resault.getLoginResault()){
                //登录成功
                //1.写首选项
                SharedPreferenceUtil.putSharedPref(LoginActivity.this,PREF_USERNAME,mUserNameToLogin);
                SharedPreferenceUtil.putSharedPref(LoginActivity.this,PREF_PWD,mPwdToLogin);
                //2.设置全局变量：用户
                setLoginCurrentUser(resault);
                //3.请求全局：系统内部门

                //4.请求全局：系统内角色

                //5.提示 并页面跳转
                Toast.makeText(LoginActivity.this,getString(R.string.info_login_sucess),Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                //6.关闭当前页面
                finish();

            }else{//登录失败
                //根据错误码提示
                String info = "";
                switch (resault.getReason()){
                case LoginBiz.LOGIN_ERROR:
                    info = getString(R.string.info_login_error);
                    break;
                case LoginBiz.USER_OR_PWD_ERROR:
                    info = getString(R.string.info_login_username_pwd_error);
                    break;

                default:break;
            }
            Toast.makeText(LoginActivity.this,info,Toast.LENGTH_SHORT).show();

        }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews(savedInstanceState);
    }

    /**
     * login成功之后设置全局变量的一些字段
     * @param resault 结果封装
     */
    private void setLoginCurrentUser(LoginBiz.LoginResault resault) {

        LoginResponse responseEntity = resault.getLoginResponseEntity();
        User user = MyApplication.getCurrentUser();

        user.setLogin(true);
        user.setUserName(responseEntity.getUserName());
        user.setDepartment(responseEntity.getDepartment());
        if (responseEntity.getDepartIds().equals(StaticClass.DEPARTMENT_ID_GANKONG)){
            // 这里是特殊逻辑，因为感控科的常量是132，而感控科和全院状态级别状态一致
            // 1 代表全院
            user.setDepartIds(StaticClass.DEPARTMENT_ID_HOSPITAL);
        }else{
            user.setDepartIds(responseEntity.getDepartIds());
        }

        user.setRfid(responseEntity.getRfid());
        user.setDeviceName(responseEntity.getDeviceName());


    }

    /**
     * 其他活动进入本活动的"入口"
     * @param context 上下文对象
     * @return Intent
     */
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        return intent;
    }


    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        //寻找控件
        //EditText
        mEtUserName = (EditText) findViewById(R.id.et_username_login);
        mEtPassword = (EditText) findViewById(R.id.et_password_login);
        //解决下字体不一样的问题
        mEtPassword.setTypeface(Typeface.DEFAULT);
        mEtPassword.setTransformationMethod(new PasswordTransformationMethod());

        //ImageView
        mIvWarnningUser = (ImageView) findViewById(R.id.iv_warnning_user_login);
        mIvWarnningPwd = (ImageView) findViewById(R.id.iv_warnning_pwd_login);
        //设置显示状态
        mIvWarnningUser.setVisibility(View.GONE);
        mIvWarnningPwd.setVisibility(View.GONE);

        //Button
        mBtnLogin = (Button) findViewById(R.id.btn_login_login);
        mBtnConfig = (Button) findViewById(R.id.btn_config_server_login);
        mBtnLogin.setOnClickListener(this);
        mBtnConfig.setOnClickListener(this);

        mLinearLayoutMain = (LinearLayout) findViewById(R.id.ll_login);

        //读取sharedPreference 的首选项内容 用户名 和密码,并显示
        String usernamePref = SharedPreferenceUtil.getSharedPrefStr(this,PREF_USERNAME);
        String passwordPref = SharedPreferenceUtil.getSharedPrefStr(this,PREF_PWD);
        if(!TextUtils.equals("",usernamePref)){
            mEtUserName.setText(usernamePref);
        }
        if(!TextUtils.equals("",passwordPref)){
            mEtPassword.setText(passwordPref);
        }

        //读取sharedPreference 的首选项内容服务器和端口,并显示
        mServerIp = SharedPreferenceUtil.getSharedPrefStr(this, PREF_SERVER_IP);
        mServerPort = SharedPreferenceUtil.getSharedPrefStr(this,PREF_SERVER_PORT);
        if(TextUtils.equals("",mServerIp.trim())){
            //没有读到首选项的IP 和 Port内容
            mBtnConfig.setText(getString(R.string.btn_config_server));
        }else{
            mBtnConfig.setText(getString(R.string.btn_config_server_modify));
        }

    }



    /**
     * TEST：调用登录接口
     */
    private void doTestLogin() {
        Logger.d( "doLogin");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Logger.d( "run");
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try{
                    URL url = new URL(URL_LOGIN_TEST);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    /*
                    //POST请求不行
                    connection.setRequestMethod("POST");
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.writeBytes("userName=yxn&passWord=12345");
                    */
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    //读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null ){
                        response.append(line);
                    }
                    Logger.d(response);

                    //试下Gson,解析JSON
                    Gson gson = new Gson();
                    LoginResponse responseEntity = gson.fromJson(response.toString(),
                            LoginResponse.class);
                    Logger.d(responseEntity);

                }catch (Exception e){

                }finally {
                    if(reader != null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    if(connection != null ){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_login://登录的点击事件
                checkInternetState();
                doLogin();
            break;
            case R.id.btn_config_server_login://设置服务器的点击事件
                showConfigDialog();
            break;
        }

    }


    /**
     * 处理配置信息对话框，其实是开启一个activity
     */
    private void showConfigDialog() {
        //隐藏掉主体内容，因为透明背景
        mLinearLayoutMain.setVisibility(View.GONE);
        //startActivity(new Intent(this,ConfigServerDialogActivity.class));
        Intent toConfigIntent = new Intent(this,ConfigServerDialogActivity.class);
        toConfigIntent.putExtra(EXTRA_SERVER_IP,mServerIp);
        toConfigIntent.putExtra(EXTRA_SERVER_PORT,mServerPort);
        //startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //带上转场动画
            startActivityForResult(toConfigIntent,REQUEST_CODE_TO_CONFIG,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
            startActivityForResult(toConfigIntent,REQUEST_CODE_TO_CONFIG);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                mServerIp = data.getStringExtra(EXTRA_SERVER_IP);
                mServerPort = data.getStringExtra(EXTRA_SERVER_PORT);
                //格式化下字符串
                String strServer = getString(R.string.info_server_config_ok);
                Toast.makeText(this,String.format(strServer,mServerIp,mServerPort),Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        mLinearLayoutMain.setVisibility(View.VISIBLE);

    }

    /**
     * 处理登录的逻辑
     */
    private void doLogin() {
        //获得edittext的值，校验不为空
        mUserNameToLogin = mEtUserName.getText().toString().trim();
        mPwdToLogin = mEtPassword.getText().toString().trim();

        //先检查用户名是空，再检查密码是否空
        if(TextUtils.isEmpty(mUserNameToLogin)){
            tellInputError(USER_EMPTY);
        }else if(TextUtils.isEmpty(mPwdToLogin)){
            tellInputError(PWD_EMPTY);
        }
        if(TextUtils.equals("",mServerIp)||TextUtils.equals("",mServerPort)){
            Toast.makeText(this, R.string.info_login_serverinfo_not_config, Toast.LENGTH_SHORT).show();
            return;
        }
        //判断登录成功
        //如果成功，写入首选项，跳转页面
        new LoginBiz().doLogin(mHandler,mUserNameToLogin,mPwdToLogin,mServerIp,mServerPort);
        //获取系统内的参数
        //部门类型
        new GetDepartsBiz().doGetDepartmentsForAppGlobal();
        //职工类型
        new GetStaffTypeBiz().doGetStaffTypeForAppGlobal();
    }

    /**提示动画:输入不能为空*/
    private void tellInputError(int type) {
        final View toWarnning;
        switch (type){
            case USER_EMPTY:
                //mEtUserName.setError("");
                Toast.makeText(this,getString(R.string.info_user_input_empty),
                        Toast.LENGTH_SHORT).show();
                toWarnning = mIvWarnningUser;
            break;
            case PWD_EMPTY:
               // mEtPassword.setError("");
                Toast.makeText(this,getString(R.string.info_pwd_input_empty),
                        Toast.LENGTH_SHORT).show();
                toWarnning = mIvWarnningPwd;
            break;
            default:
                toWarnning = new View(this);
                break;
        }

        ObjectAnimator objectAnimator = AnimatorUtil.nope(mLinearLayoutMain );
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                toWarnning.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                toWarnning.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        objectAnimator.start();

    }
}
