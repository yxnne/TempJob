package com.iel.swsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iel.swsapp.R;
import com.iel.swsapp.fragment.DeviceFragment;
import com.iel.swsapp.fragment.EventFragment;
import com.iel.swsapp.fragment.MainInfoFragment;
import com.iel.swsapp.fragment.ManualFragment;
import com.iel.swsapp.fragment.ProfileFragment;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.List;
/**
 * Class Full Name  : com.iel.swsapp.activity.MainActivity
 * Author Name      : yxnne
 * Create Time      : 2017/3
 * Project Name     : SWSAPP
 * Descriptions     : 主页面活动，四大标签页（Fragment）都在其中(ViewPager)
 */
public class MainActivity extends AppBaseActivity implements View.OnClickListener {

    //控件声明
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    //Fragment 容器，ViewPager使用
    private List<Fragment> mFragments ;
    //页面标题
    private TextView mTvPageTittle;
    private ImageView mIvSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews(savedInstanceState);

        //设置启始页
        mViewPager.setCurrentItem(2);
    }

    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        //不要ActionBar
        //getSupportActionBar().hide();
        mTvPageTittle = (TextView) findViewById(R.id.tv_page_tittle_main);
        mIvSetting = (ImageView) findViewById(R.id.iv_profile_setting);
        mIvSetting.setOnClickListener(this);

        mTabLayout = (TabLayout) findViewById(R.id.tablayout_main);
        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        //viewpager预加载
        mViewPager.setOffscreenPageLimit(mFragments.size());
        //设置adapter
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "test";
            }
        });

        //ViewPager 监听,设置下页面标题的文字
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mTvPageTittle.setText(getString(R.string.tab_text_manual));
                        mIvSetting.setVisibility(View.GONE);
                        break;
                    case 1:
                        mTvPageTittle.setText(getString(R.string.tab_text_search));
                        mIvSetting.setVisibility(View.GONE);
                        break;
                    case 2:
                        mTvPageTittle.setText(getString(R.string.tab_text_main));
                        mIvSetting.setVisibility(View.GONE);
                        break;
                    case 3:
                        mTvPageTittle.setText(getString(R.string.tab_text_device));
                        mIvSetting.setVisibility(View.GONE);
                        break;
                    case 4:
                        mTvPageTittle.setText(getString(R.string.tab_text_profile));
                        mIvSetting.setVisibility(View.VISIBLE);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);

        //尝试放置自定义布局
        //这里先移除所有tab
        mTabLayout.removeAllTabs();
        LayoutInflater inflater = getLayoutInflater();

        //调整顺序
        //观察 增加的Tab
        TabLayout.Tab tab0 = mTabLayout.newTab();
        View tabView0 = inflater.inflate(R.layout.tab_item_manual,null);
        tab0.setCustomView(tabView0);
        TextView tv0 = (TextView) tabView0.findViewById(R.id.tv);
        mTabLayout.addTab(tab0);

        //查询 tab2
        TabLayout.Tab tab2 = mTabLayout.newTab();
        View tabView2 = inflater.inflate(R.layout.tab_item_search,null);
        tab2.setCustomView(tabView2);
        TextView tv2 = (TextView) tabView2.findViewById(R.id.tv);
        mTabLayout.addTab(tab2);

        //主页 tab1
        TabLayout.Tab tab1 = mTabLayout.newTab();
        View tabView1 = inflater.inflate(R.layout.tab_item_main,null);
        tab1.setCustomView(tabView1);
        TextView tv1 = (TextView) tabView1.findViewById(R.id.tv);
        mTabLayout.addTab(tab1);

        //设备 tab3
        TabLayout.Tab tab3 = mTabLayout.newTab();
        View tabView3 = inflater.inflate(R.layout.tab_item_device,null);
        tab3.setCustomView(tabView3);
        TextView tv3 = (TextView) tabView3.findViewById(R.id.tv);
        mTabLayout.addTab(tab3);

        //个人 tab4
        TabLayout.Tab tab4 = mTabLayout.newTab();
        View tabView4 = inflater.inflate(R.layout.tab_item_profile,null);
        tab4.setCustomView(tabView4);
        TextView tv4 = (TextView) tabView4.findViewById(R.id.tv);
        mTabLayout.addTab(tab4);


    }

    protected void initDatas() {
        //准备ViewPager中的fragment
        mFragments = new ArrayList<>();
        //调整顺序 主页放到中间
        mFragments.add(new ManualFragment());   //观察
        mFragments.add(new EventFragment());    //查询
        mFragments.add(new MainInfoFragment()); //主页
        mFragments.add(new DeviceFragment());   //设备
        mFragments.add(new ProfileFragment());  //个人
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_profile_setting://响应设置按钮
                //Toast.makeText(this,"SETTING",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,SettingActivity.class));
                break;
        }
    }
}
