package com.iel.swsapp.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.iel.swsapp.R;
import com.iel.swsapp.application.MyApplication;
/**
 * Class Full Name  : com.iel.swsapp.activity.SettingActivity
 * Author Name      : yxnne
 * Create Time      : 2017/4
 * Project Name     : SWSAPP
 * Descriptions     : 设置页面，配置些时间，退出APP或了解其信息
 */
public class SettingActivity extends ActionbarBackBaseActivity {
    private ListView mListViewSetting;
    private ListView mListViewAbout;

    //第一个设置部分
    private String[] mSettingsName1 ;

    //第二个设置部分
    private String[] mSettingsName2 ;

    //退出登录
    private Button mBtnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mSettingsName1 = new  String[]{
                getString(R.string.text_setting_main_stastic_time),
                getString(R.string.text_setting_stastic_graphic_update_time),
                getString(R.string.text_setting_main_realtime_update_time)
        };

        mSettingsName2 = new  String[]{
                getString(R.string.text_setting_modify_pwd),
                getString(R.string.text_setting_about_app)
        };
        //设置自己的ActionBar
        setMyActionBar(R.id.rl_myactionbar_back,
                R.id.iv_myactionbar_menu,
                R.id.tv_myactionbar_tittle,
                false,getString(R.string.text_tittle_Setting));

        //listView 1
        mListViewSetting = (ListView) findViewById(R.id.lv_setting_setting);
        mListViewSetting.setAdapter(new MyAdapterSetting());

        //listview 2
        mListViewAbout = (ListView) findViewById(R.id.lv_about_setting);
        mListViewAbout.setAdapter(new MyAdapterAbout());

        //退出按钮 btn_logout_setting
        mBtnLogout = (Button) findViewById(R.id.btn_logout_setting);
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭所有的页面
                MyApplication.closeAllActivities();
                //而后 隐式启动登录页面
                Intent i = new Intent("com.iel.swsapp.activity.login");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MyApplication.getAppInstance().startActivity(i);
            }
        });

    }

    /**
     * 设置列表
     */
    class MyAdapterSetting extends BaseAdapter{

        @Override
        public int getCount() {
            return mSettingsName1.length;
        }

        @Override
        public Object getItem(int position) {
            return mSettingsName1[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = View.inflate(SettingActivity.this,R.layout.item_setting_item,null);
            TextView tvName = (TextView) convertView.findViewById(R.id.tv_item_setting_name);
            TextView tvValue = (TextView) convertView.findViewById(R.id.tv_item_setting_value);

            String strName = mSettingsName1[position];
            String strValue = "";
            //获得值
            switch (position){
                case 0:
                    strValue = "近一个月";
                    break;
                case 1:
                    strValue = "3min";
                    break;
                case 2:
                    strValue = "10s";
                    break;
            }

            tvName.setText(strName);
            tvValue.setText(strValue);
            return convertView;
        }
    }

    /**
     * 设置关于
     */
    class MyAdapterAbout extends BaseAdapter{

        @Override
        public int getCount() {
            return mSettingsName2.length;
        }

        @Override
        public Object getItem(int position) {
            return mSettingsName2[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = View.inflate(SettingActivity.this,R.layout.item_setting_item,null);
            TextView tvName = (TextView) convertView.findViewById(R.id.tv_item_setting_name);
            TextView tvValue = (TextView) convertView.findViewById(R.id.tv_item_setting_value);

            tvName.setText(mSettingsName2[position]);
            tvValue.setVisibility(View.INVISIBLE);

            //设置关于的监听器
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position){
                        case 0://修改密码
                            break;
                        case 1://跳转到关于APP页面
                            Intent intent = new Intent(SettingActivity.this, VersionIntroActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
            });


            return convertView;
        }
    }

}
