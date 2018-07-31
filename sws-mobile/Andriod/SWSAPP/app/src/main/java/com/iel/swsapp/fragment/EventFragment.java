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
import android.widget.TextView;

import com.iel.swsapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Full Name  : com.iel.swsapp.fragment.MainInfoFragment
 * Author Name      : yxnne
 * Create Time      : 2017/2/8
 * Project Name     : SWSAPP
 * Descriptions     : 事件查询的Fragment
 */
public class EventFragment extends Fragment{
    //控件声明
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    //Fragment 容器，ViewPager使用
    private List<Fragment> mFragments ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event,null);
        initFragments();
        mTabLayout = (TabLayout) view.findViewById(R.id.tablayout_fragment_event);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_fragment_event);

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
        View tabView1 = inflater.inflate(R.layout.tab_item_event_now,null);
        tab1.setCustomView(tabView1);
        TextView tv1 = (TextView) tabView1.findViewById(R.id.tv);
        mTabLayout.addTab(tab1);
        //tab2
        TabLayout.Tab tab2 = mTabLayout.newTab();
        View tabView2 = inflater.inflate(R.layout.tab_item_event_history,null);
        tab2.setCustomView(tabView2);
        TextView tv2 = (TextView) tabView2.findViewById(R.id.tv);
        mTabLayout.addTab(tab2);
        return view;
    }
    protected void initFragments() {
        //准备ViewPager中的fragment
        mFragments = new ArrayList<>();
        mFragments.add(new RealtimeEventFragment());
        mFragments.add(new HistoryEventFragment());



    }
}
