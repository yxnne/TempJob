package com.iel.swsapp.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.iel.swsapp.R;
import com.iel.swsapp.view.KeyRadioGroupV1;
import com.iel.swsapp.view.KeyRadioGroupV2;

import java.util.ArrayList;
import java.util.List;


public class ManualFragment extends Fragment {
    //控件声明
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    //Fragment 容器，ViewPager使用
    private List<Fragment> mFragments ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manual,null);
        //findViews(view);
        initFragments();
        mTabLayout = (TabLayout) view.findViewById(R.id.tablayout_fragment_manual);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_fragment_manual);

        //viewpager预加载
        mViewPager.setOffscreenPageLimit(mFragments.size());

        //设置adapter
        mViewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
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

        mTabLayout.setupWithViewPager(mViewPager);

        //尝试放置自定义布局
        //这里先移除所有tab
        mTabLayout.removeAllTabs();
        //LayoutInflater inflater = getActivity().getLayoutInflater();
        //tab1
        TabLayout.Tab tab1 = mTabLayout.newTab();
        tab1.setText("录入观察");
        mTabLayout.addTab(tab1);
        //tab2
        TabLayout.Tab tab2 = mTabLayout.newTab();
        tab2.setText("统计数据");

        mTabLayout.addTab(tab2);
        return view;
    }
    protected void initFragments() {
        //准备ViewPager中的fragment
        mFragments = new ArrayList<>();
        mFragments.add(new ManualInputFragment());
        mFragments.add(new ManualStatisticsFragment());



    }
}
