package com.iel.swsapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ArrayRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.iel.swsapp.R;
import com.iel.swsapp.application.MyApplication;
import com.iel.swsapp.business.GetDepartsBiz;
import com.iel.swsapp.entity.DepaertLists;
import com.iel.swsapp.entity.DepartsResponse;
import com.iel.swsapp.fragment.DepartRateFragment;
import com.iel.swsapp.fragment.StaffRateFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Class Full Name  : com.iel.swsapp.activity.DepartRateActivity
 * Author Name      : yxnne
 * Create Time      : 2017/3
 * Project Name     : SWSAPP
 * Descriptions     : 部门内依从率分析，包括两个Fragment，一个是依从率，一个是员工
 */
public class DepartRateActivity extends ActionbarBackBaseActivity {

    //控件声明
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private CardView mCvChoose;
    //SpinnerChoose
    private Spinner mSpinnerChoose;
    //Spinner Datas
    private List<DepaertLists> mDepartDatas;
    //
    public static String sChooseDepartId = "1";

    private List<String> mDepartNames = new ArrayList<>();
    //Fragment 容器，ViewPager使用
    private List<Fragment> mFragments ;
    private ArrayAdapter<String> mAdapter;

    //
    //private onChooseSelectedListner mOnChooseSelectedListner;
    //private List<onChooseSelectedListner> mOnChooseSelectedListnerList;

    private Map<String , onChooseSelectedListner> mOnChooseSelectedListnerMap;

    public void setOnChooseSelectedListner(String strFragment,onChooseSelectedListner lis){
        if(mOnChooseSelectedListnerMap == null){
            mOnChooseSelectedListnerMap = new HashMap<>();
        }
        mOnChooseSelectedListnerMap.put(strFragment,lis);
    }

    //Handler
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == GetDepartsBiz.HANDLER_WHAT_DEPARTS){
                //获得了信息
                //数据得到
                DepartsResponse response = (DepartsResponse)msg.obj;
                if(mDepartDatas == null){
                    mDepartDatas = new ArrayList<>();
                }

                List<DepaertLists> tempDeparts = response.getDepaertLists();


                for(DepaertLists depart : tempDeparts){
                    if( !depart.getDepartName().equals("感控科")){
                        mDepartNames.add(depart.getDepartName());
                        mDepartDatas.add(depart);
                    }

                }

                //更新Spinner
                mAdapter.notifyDataSetChanged();
            }

        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart_rate);
        //设置自己的ActionBar
        setMyActionBar(R.id.rl_myactionbar_back,
                R.id.iv_myactionbar_menu,
                R.id.tv_myactionbar_tittle,
                false,getString(R.string.text_compliance_rate));

        initData();
        initViews();

        //控制显示
        if(MyApplication.getCurrentUser().getDepartIds().equals("1")){
            // 全院的数据
            // 显示选择科室的
            mCvChoose.setVisibility(View.VISIBLE);
            // 这时还需要去加载到一个显示的部门列表
            doGetDepartsList();
            mAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mDepartNames);

            mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerChoose.setAdapter(mAdapter);
            mSpinnerChoose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    sChooseDepartId = mDepartDatas.get(position).getDepartId();
//                  Toast.makeText(DepartRateActivity.this,sChooseDepartId,Toast.LENGTH_SHORT).show();
                    //mOnChooseSelectedListner.onChooseCheck();
                    //遍历下监听器了
                    for(Map.Entry entry : mOnChooseSelectedListnerMap.entrySet()){
                        ((onChooseSelectedListner)entry.getValue()).onChooseCheck();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }else{
            // 不显示科室选择的
            mCvChoose.setVisibility(View.GONE);
        }

    }

    /**
     * 请求医院所有部门的部门Id和名字
     */
    private void doGetDepartsList() {
        GetDepartsBiz biz = new GetDepartsBiz();
        biz.doGetDeparts(mHandler);
    }

    private void initData() {
        //准备fragment
        mFragments = new ArrayList<>();
        mFragments.add(new DepartRateFragment());
        mFragments.add(new StaffRateFragment());
    }

    private void initViews() {


        mTabLayout = (TabLayout) findViewById(R.id.fab_depart_rate);
        mViewPager = (ViewPager) findViewById(R.id.vp_depart_rate);

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

        mTabLayout.setupWithViewPager(mViewPager);

        //尝试放置自定义布局
        //这里先移除所有tab
        mTabLayout.removeAllTabs();
        //LayoutInflater inflater = getActivity().getLayoutInflater();
        //tab1
        TabLayout.Tab tab1 = mTabLayout.newTab();
        View tabView1 = getLayoutInflater().inflate(R.layout.tab_item_rate_depart,null);
        tab1.setCustomView(tabView1);
        TextView tv1 = (TextView) tabView1.findViewById(R.id.tv);
        mTabLayout.addTab(tab1);
        //tab2
        TabLayout.Tab tab2 = mTabLayout.newTab();
        View tabView2 = getLayoutInflater().inflate(R.layout.tab_item_rate_staff,null);
        tab2.setCustomView(tabView2);
        TextView tv2 = (TextView) tabView2.findViewById(R.id.tv);
        mTabLayout.addTab(tab2);

        //选择的CardView//cardview_choose_depart_departrate_frag
        mCvChoose = (CardView) findViewById(R.id.cardview_choose_depart_departrate_frag);
        mSpinnerChoose = (Spinner) findViewById(R.id.spinner_depart_type);

    }

    /**
     * 定义一个接口，Fragment去实现
     */
    public interface onChooseSelectedListner{
        void onChooseCheck();
    }
}
