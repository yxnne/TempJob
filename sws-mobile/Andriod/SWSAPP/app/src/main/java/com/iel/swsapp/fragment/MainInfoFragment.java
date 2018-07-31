package com.iel.swsapp.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.iel.swsapp.R;
import com.iel.swsapp.activity.DepartRateActivity;
import com.iel.swsapp.activity.NewsDetailActivity;
import com.iel.swsapp.application.MyApplication;
import com.iel.swsapp.business.DepartRateOverAllBiz;
import com.iel.swsapp.business.EventCompareBiz;
import com.iel.swsapp.business.RateByRoleBiz;
import com.iel.swsapp.entity.RolesRateOneDay;
import com.iel.swsapp.utils.CommenUtil;
import com.iel.swsapp.utils.DateUtils;
import com.iel.swsapp.utils.StaticClass;
import com.iel.swsapp.utils.chartutil.LineChartUtil;
import com.iel.swsapp.utils.chartutil.LineDataItemWrapper;
import com.iel.swsapp.utils.chartutil.PieChartUtil;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Class Full Name  : com.iel.swsapp.fragment.MainInfoFragment
 * Author Name      : yxnne
 * Create Time      : 2017/2/8
 * Project Name     : SWSAPP
 * Descriptions     : 首页的第一页fragment
 */
public class MainInfoFragment extends Fragment implements View.OnClickListener {

    //轮播banner
    private RollPagerView mRvpBanner;
    //卡片1 的reletivelayout
    private RelativeLayout mRlDepartOverAll;
    private TextView mTvTittleAndSource;
    //卡片2 的relativeLayout
    private RelativeLayout mRlTime;
    //卡片3 的relativeLayout
    private RelativeLayout mRlRoleCompare;

    /*图表控件*/
    //卡片1：整体依从率的饼状图
    private PieChart mPieChartDepaOverAll;
    private Double mRateDepartOverAll;

    //卡片2：五个小图
    //接触患者前饼图
    private PieChart mPieChart_BeforeContact;
    //无菌操作前饼图
    private PieChart mPieChart_BeforeAsepticOpt;
    //接触环境后
    private PieChart mPieChart_AfterContactEnv;
    //接触患者体液后
    private PieChart mPieChart_AfterContactFluids;
    //接触患者后
    private PieChart mPieChart_AfterContactPatient;
    //数据 "两前三后"变成了"两前两后"
    private float mRateAfterCloseNick;
    private float mRateAfterCloseNickEnvri;
    private float mRateBeforeAsepticOperation;
    private float mRateBeforeCloseNick;

    //卡片3：按角色对比折线图
    private LineChart mLineChartRoleCompare;
    private LinkedHashMap<String , Double[]> mWeekRolesRateMap;
    private float[] mWeekDataNurse;
    private float[] mWeekDataDoctor;
    private float[] mWeekDataPhysician;
    private float[] mWeekDataWorker;

    private String[] mWeekDateStr;
    private int count = 0;


    //广播接收器

    private BroadcastReceiver mBroadCastReciever;


    @Override
    public void onResume() {
        super.onResume();
        registerBroadcast();

        //每次在这里的时候
        if(MyApplication.getCurrentUser().getDepartIds().equals("1")){
            DepartRateActivity.sChooseDepartId = "1";
//            Toast.makeText(getActivity(),
//                    " DepartRateActivity.sChooseDepartId"+
//                            DepartRateActivity.sChooseDepartId,Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_info,null);
        //注册广播

        //部门总体依从率
        doRateOverall();
        //前后事件比较数据
        doEventCompare();
        //得到 按角色 一周依从率
        doRolesRate();

        initBanner(view);
        initCardDepart(view);

        //测试下接口
        String nowDate = DateUtils.getNowDateString();
        //new RateByRoleBiz().doGetRateOneDay(getActivity(),nowDate);


        return view;
    }

    private void doRateOverall() {
        DepartRateOverAllBiz biz = new DepartRateOverAllBiz();
        biz.doDepartRate30(MyApplication.getCurrentUser().getDepartIds(),getActivity());

    }

    private void doEventCompare() {
        EventCompareBiz biz = new EventCompareBiz();
        biz.doEventCompare30Days(getActivity());
    }

    /**
     * 得到按角色一周依从率,发送请求
     */
    private void doRolesRate() {
        mWeekDateStr = new String[7];
        mWeekRolesRateMap = new LinkedHashMap<>();
        //得到天数
        String lastDayMinus0 = DateUtils.getNowDateString();
        String lastDayMinus1 = DateUtils.getDaysBeforeDate(lastDayMinus0,1);
        String lastDayMinus2 = DateUtils.getDaysBeforeDate(lastDayMinus0,2);
        String lastDayMinus3 = DateUtils.getDaysBeforeDate(lastDayMinus0,3);
        String lastDayMinus4 = DateUtils.getDaysBeforeDate(lastDayMinus0,4);
        String lastDayMinus5 = DateUtils.getDaysBeforeDate(lastDayMinus0,5);
        String lastDayMinus6 = DateUtils.getDaysBeforeDate(lastDayMinus0,6);
        // 一周日期字符串赋值
        mWeekDateStr[0] = lastDayMinus6;
        mWeekDateStr[1] = lastDayMinus5;
        mWeekDateStr[2] = lastDayMinus4;
        mWeekDateStr[3] = lastDayMinus3;
        mWeekDateStr[4] = lastDayMinus2;
        mWeekDateStr[5] = lastDayMinus1;
        mWeekDateStr[6] = lastDayMinus0;

        //初始化
        mWeekRolesRateMap.put(lastDayMinus0,null);
        mWeekRolesRateMap.put(lastDayMinus1,null);
        mWeekRolesRateMap.put(lastDayMinus2,null);
        mWeekRolesRateMap.put(lastDayMinus3,null);
        mWeekRolesRateMap.put(lastDayMinus4,null);
        mWeekRolesRateMap.put(lastDayMinus5,null);
        mWeekRolesRateMap.put(lastDayMinus6,null);

        mWeekDataNurse = new float[7];
        mWeekDataDoctor = new float[7];
        mWeekDataPhysician = new float[7];
        mWeekDataWorker = new float[7];

        //初始化计数
        count = 0;

        //调用
        RateByRoleBiz biz = new RateByRoleBiz();
        biz.doGetRateOneDay(getActivity(),lastDayMinus0);
        biz.doGetRateOneDay(getActivity(),lastDayMinus1);
        biz.doGetRateOneDay(getActivity(),lastDayMinus2);
        biz.doGetRateOneDay(getActivity(),lastDayMinus3);
        biz.doGetRateOneDay(getActivity(),lastDayMinus4);
        biz.doGetRateOneDay(getActivity(),lastDayMinus5);
        biz.doGetRateOneDay(getActivity(),lastDayMinus6);


    }

    /**
     * 注册广播
     */
    private void registerBroadcast() {
        mBroadCastReciever = new MyBroadCast();
        IntentFilter intentFilter = new IntentFilter();
        // add Actions to Filter
        intentFilter.addAction(StaticClass.ACTION_GET_ROLES_RATE);
        intentFilter.addAction(StaticClass.ACTION_GET_DEPART_OVERALL_RATE);

        getActivity().registerReceiver(mBroadCastReciever,intentFilter);
    }

    @Override
    public void onDestroy() {
        //注销广播
        getActivity().unregisterReceiver(mBroadCastReciever);
        super.onDestroy();
    }

    /**
     * @param view 通过view初始化图表第一个卡片，部门整体依从率
     */
    private void initCardDepart(View view) {
        //卡片1 的内部相对布局
        mRlDepartOverAll = (RelativeLayout) view.findViewById(R.id.rl_depart_main);
        mRlDepartOverAll.setOnClickListener(this);
        //设置下数据来源
        mTvTittleAndSource = (TextView) view.findViewById(R.id.tv_main_frag_tittle_and_source);
        mTvTittleAndSource.setText(genCardTittle());

        //卡1 饼图，部门依从率
        mPieChartDepaOverAll = (PieChart) view.findViewById(R.id.piechart_depart_main);
//        PieChartUtil.showTestPieDepartOverall(mPieChartDepaOverAll,getActivity(),70,70+"%",24,
//                R.color.chartBackgroud1, R.color.chartBackgroud1,R.color.chartFront1);
        updatePieChart(true);
        //卡片2 的内部相对布局
        mRlTime = (RelativeLayout) view.findViewById(R.id.rl_time_main);
        mRlTime.setOnClickListener(this);
        //卡2 两前三后 饼图
        mPieChart_BeforeContact = (PieChart) view.findViewById(R.id.piechart_time_before_contact_main);
        mPieChart_BeforeAsepticOpt = (PieChart) view.findViewById(R.id.piechart_time_before_aseptic_opt_main);
        mPieChart_AfterContactEnv = (PieChart) view.findViewById(R.id.piechart_time_after_contact_env);
        mPieChart_AfterContactFluids = (PieChart) view.findViewById(R.id.piechart_time_after_contact_fluids);
        mPieChart_AfterContactPatient = (PieChart) view.findViewById(R.id.piechart_time_after_contact_patient);
        //设置图表数据 样式暂时按照总体依从率的来
        PieChartUtil.showTestPieDepartOverall(mPieChart_BeforeContact,getActivity(),10,10+"%",12,
                R.color.color_Do_BeforeContact, R.color.color_Do_BeforeContact,R.color.color_Do_BeforeContact_bg);

        PieChartUtil.showTestPieDepartOverall(mPieChart_BeforeAsepticOpt,getActivity(),10,10+"%",12,
                R.color.color_Do_BeforeAspeticOpt, R.color.color_Do_BeforeAspeticOpt,R.color.color_Do_BeforeAspeticOpt_bg);

        PieChartUtil.showTestPieDepartOverall(mPieChart_AfterContactEnv,getActivity(),10,10+"%",12,
                R.color.color_Do_AfterContactEnv, R.color.color_Do_AfterContactEnv,R.color.color_Do_AfterContactEnv_bg);

        PieChartUtil.showTestPieDepartOverall(mPieChart_AfterContactFluids,getActivity(),10,10+"%",12,
                R.color.color_Do_AfterContactFluids, R.color.color_Do_AfterContactFluids,R.color.color_Do_AfterContactFluids_bg);

        PieChartUtil.showTestPieDepartOverall(mPieChart_AfterContactPatient,getActivity(),10,10+"%",12,
                R.color.color_Do_AfterContactPatient, R.color.color_Do_AfterContactPatient,R.color.color_Do_AfterContactPatient_bg);

        //卡片3 的内部相对布局
        mRlRoleCompare = (RelativeLayout) view.findViewById(R.id.rl_role_main);
        mRlRoleCompare.setOnClickListener(this);
        //按角色对比 折线图
        mLineChartRoleCompare = (LineChart) view.findViewById(R.id.linechart_role_compare_main);


        updateLineChart(true);
        
    }

    private String genCardTittle() {
        StringBuilder sb = new StringBuilder("");
        sb.append(getString(R.string.text_tittle_departl_compliance_rate));
        sb.append(" (");
        sb.append(MyApplication.getCurrentUser().getDepartment());
        sb.append(")");


        return sb.toString();
    }


    /**
     * @param view 通过view初始化banner
     */
    private void initBanner(View view) {
        mRvpBanner = (RollPagerView) view.findViewById(R.id.rpv_banner);
        //适配器
        mRvpBanner.setAdapter(new RollAdapter(mRvpBanner));
        //指示器
        mRvpBanner.setHintView(new ColorPointHintView(getActivity(),
                ContextCompat.getColor(getActivity(),R.color.colorPrimary),
                Color.WHITE));
        //指示器背景透明度
        mRvpBanner.setHintAlpha(40);
        //rollPager事件响应
        mRvpBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String strUrl = "http://www.baidu.com/";
                switch (position){
                    //TODO 跳转
                    case 0:
                        strUrl = "http://mp.weixin.qq.com/s?src=3&timestamp=1489813834&ver=1&signature=WJe-xVQP1QUe0Yqe3fHfz28DNQ7icnvP91yAcc7y81h2qy0NR1xVqy2LfcJtKetjo2o*RRDNUDNV*AmcIphCqfvFuWNtgD-BWAvfysNIun6e-uP4DzpBOuK74scZR8bu5Las0SxMzP8p1U2fN0Mwonk7G4EWfh6OpgXT*JSquA4=";
                        break;
                    case 1:
                        strUrl = "http://mp.weixin.qq.com/s?src=3&timestamp=1489813834&ver=1&signature=aT3L9qs2yIcn9gqHfrDSQfxYs91H1F-lrgc7bwwmkds3DocYo0AXKWD61DFMi3zesQjehrHESBVObumyF15Z84TtPP8SfAV52o-cihwzYwe-OIwmE-lLcErKHc7*OnlTjEzj9NyhsB9hauO5RyaOBKA3x07ibcCNV621O8wXf2g=";
                        break;
                    case 2:
                        strUrl = "http://mp.weixin.qq.com/s?src=3&timestamp=1489814076&ver=1&signature=WJe-xVQP1QUe0Yqe3fHfz28DNQ7icnvP91yAcc7y81jutm2y2vC4H-T73jZRDUbiByQEUEvFY*MKPpL2KgE6OCcfcXNpaIWbKWY3eklBzcaHsQNxNNYXaK-s5LoDo7zOTtfEpUCqj*nijVKio3dTpg==";
                        break;
                }
                Intent i = new Intent(getActivity(), NewsDetailActivity.class);

                i.putExtra("url",strUrl);
                startActivity(i);

            }
        });

    }

    /**
     * 该 fragment上面的点击事件响应
     * @param v view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // 卡片1：部门整体
            case R.id.piechart_depart_main:
                break;
            case R.id.rl_depart_main:
            //卡片2： 洗手时机
            case R.id.rl_time_main:
            case R.id.rl_role_main:
                //Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), DepartRateActivity.class);
                startActivity(i);
                break;

        }

    }

    /**
     * Banner 轮播使用的适配器
     */
    private class RollAdapter extends LoopPagerAdapter {

        //banner的图片
        int[] bannerImags = new int[]{
                R.drawable.img_banner_1,
                R.drawable.img_banner_2,
                R.drawable.img_banner_3
        };
        //构造方法
        public RollAdapter(RollPagerView viewPager) {
            super(viewPager);

        }

        @Override
        public View getView(ViewGroup container, int position) {
            //轮播的图片
            ImageView ivBannerImg = new ImageView(getActivity());
            ivBannerImg.setImageResource(bannerImags[position]);
            ivBannerImg.setScaleType(ImageView.ScaleType.FIT_XY);
            ivBannerImg.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            return ivBannerImg;
        }

        @Override
        public int getRealCount() {
            return bannerImags.length;
        }
    }

    /**
     * 本Fragment的广播接受器
     */
    class MyBroadCast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {

            switch (intent.getAction()){
                case StaticClass.ACTION_GET_ROLES_RATE:
                    getRolesDate(intent);
                break;
                case StaticClass.ACTION_GET_DEPART_OVERALL_RATE:
                    Bundle bundle = intent.getExtras();
                    DepartRateOverAllBiz.DepartRateResult result = (DepartRateOverAllBiz.DepartRateResult)
                            bundle.getSerializable(DepartRateOverAllBiz.RESULT_RATE);
                    if(!MyApplication.getCurrentUser().getDepartIds().equals(result.getStrDepart())){
                        // 不是当前科室的 数据 不去处理
                        return;
                    }

                    mRateDepartOverAll = result. getRateDepart();
                    //mRateDepartOverAll = intent.getDoubleExtra(DepartRateOverAllBiz.RESULT_RATE,0);
                    mRateAfterCloseNick = result.getRateAfterCloseNick();
                    mRateAfterCloseNickEnvri = result.getRateAfterCloseNickEnvri();
                    mRateBeforeAsepticOperation = result.getRateBeforeAsepticOperation();
                    mRateBeforeCloseNick = result.getRateBeforeCloseNick();
                    updatePieChart(false);
                    updateTimesPieChart();
                    break;
            }
        }
    }



    /**
     * 更新Card 1 ：大饼图
     */
    private void updatePieChart(boolean isFirst) {
        if(isFirst){
            PieChartUtil.showTestPieDepartOverall(mPieChartDepaOverAll,getActivity(),
                    0,0+"%",24,
                    R.color.chartBackgroud1, R.color.chartBackgroud1,R.color.chartFront1);
        }else{
            PieChartUtil.showTestPieDepartOverall(mPieChartDepaOverAll,getActivity(),
                    mRateDepartOverAll.floatValue(),mRateDepartOverAll.floatValue()+"%",24,
                    R.color.chartBackgroud1, R.color.chartBackgroud1,R.color.chartFront1);

        }


    }
    /**
     * Card 2 更新饼图的方法 "两前两后"
     */
    private void updateTimesPieChart() {
        PieChartUtil.showTestPieDepartOverall(mPieChart_BeforeContact,getActivity(),mRateBeforeCloseNick,mRateBeforeCloseNick+"%",12,
                R.color.color_Do_BeforeContact, R.color.color_Do_BeforeContact,R.color.color_Do_BeforeContact_bg);

        PieChartUtil.showTestPieDepartOverall(mPieChart_BeforeAsepticOpt,getActivity(),mRateBeforeAsepticOperation,mRateBeforeAsepticOperation+"%",12,
                R.color.color_Do_BeforeAspeticOpt, R.color.color_Do_BeforeAspeticOpt,R.color.color_Do_BeforeAspeticOpt_bg);

        PieChartUtil.showTestPieDepartOverall(mPieChart_AfterContactEnv,getActivity(),mRateAfterCloseNickEnvri,mRateAfterCloseNickEnvri+"%",12,
                R.color.color_Do_AfterContactEnv, R.color.color_Do_AfterContactEnv,R.color.color_Do_AfterContactEnv_bg);

        PieChartUtil.showTestPieDepartOverall(mPieChart_AfterContactPatient,getActivity(),mRateAfterCloseNick,mRateAfterCloseNick+"%",12,
                R.color.color_Do_AfterContactPatient, R.color.color_Do_AfterContactPatient,R.color.color_Do_AfterContactPatient_bg);

    }
    /**
     * Card 3 更新折线图的方法
     */
    private void updateLineChart(boolean isFirst){
        //护士
        List<Float> listData1 ;
        //护工
        List<Float> listData2 ;
        //医生
        List<Float> listData3 ;
        //医师（护师）
        List<Float> listData4 ;

        //时间轴初始化
        List<String> xValueStrList = new ArrayList<>(Arrays.asList(
                DateUtils.getMonthAndDay(mWeekDateStr[0]),
                DateUtils.getMonthAndDay(mWeekDateStr[1]),
                DateUtils.getMonthAndDay(mWeekDateStr[2]),
                DateUtils.getMonthAndDay(mWeekDateStr[3]),
                DateUtils.getMonthAndDay(mWeekDateStr[4]),
                DateUtils.getMonthAndDay(mWeekDateStr[5]),
                DateUtils.getMonthAndDay(mWeekDateStr[6])
        ));
        if(isFirst){
            //护士
            listData1 = new ArrayList<>(Arrays.asList(
                    0.0f , 0.0f , 0.0f , 0.0f , 0.0f , 0.0f , 0.0f
            ));
            //护工
            listData2 = new ArrayList<>(Arrays.asList(
                    0.0f , 0.0f , 0.0f , 0.0f , 0.0f , 0.0f , 0.0f
            ));
            //医生
            listData3 = new ArrayList<>(Arrays.asList(
                    0.0f , 0.0f , 0.0f , 0.0f , 0.0f , 0.0f , 0.0f
            ));
            //医师（护师）
            listData4 = new ArrayList<>(Arrays.asList(
                    0.0f , 0.0f , 0.0f , 0.0f , 0.0f , 0.0f , 0.0f
            ));
        }else{

            //护士
            listData1 = new ArrayList<>(Arrays.asList(
                    mWeekRolesRateMap.get(mWeekDateStr[0])[0].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[1])[0].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[2])[0].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[3])[0].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[4])[0].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[5])[0].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[6])[0].floatValue()
            ));
            //护工
            listData2 = new ArrayList<>(Arrays.asList(
                    mWeekRolesRateMap.get(mWeekDateStr[0])[1].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[1])[1].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[2])[1].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[3])[1].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[4])[1].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[5])[1].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[6])[1].floatValue()
            ));
            //医生
            listData3 = new ArrayList<>(Arrays.asList(
                    mWeekRolesRateMap.get(mWeekDateStr[0])[2].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[1])[2].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[2])[2].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[3])[2].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[4])[2].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[5])[2].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[6])[2].floatValue()
            ));
            //医师（护师）
            listData4 = new ArrayList<>(Arrays.asList(
                    mWeekRolesRateMap.get(mWeekDateStr[0])[3].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[1])[3].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[2])[3].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[3])[3].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[4])[3].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[5])[3].floatValue() ,
                    mWeekRolesRateMap.get(mWeekDateStr[6])[3].floatValue()
            ));
        }

        //初始化
        LineChartUtil.initLineChart(mLineChartRoleCompare,getActivity(),xValueStrList);

        //数据封装
        LineDataItemWrapper lineDataItemWrapper1 = new LineDataItemWrapper(listData1,getString(R.string.text_role_nurse),R.color.colorRoleNurse);
        LineDataItemWrapper lineDataItemWrapper2 = new LineDataItemWrapper(listData2,getString(R.string.text_role_support_worker),R.color.colorRoleSuportWorker);
        LineDataItemWrapper lineDataItemWrapper3 = new LineDataItemWrapper(listData3,getString(R.string.text_role_doctor),R.color.colorRoleDoctor);
        LineDataItemWrapper lineDataItemWrapper4 = new LineDataItemWrapper(listData4,getString(R.string.text_role_physician),R.color.colorRolePhysician);
        //设置数据

        LineChartUtil.addDataLineChartData(getActivity(),mLineChartRoleCompare,
                lineDataItemWrapper1,lineDataItemWrapper2,lineDataItemWrapper3,lineDataItemWrapper4);

    }

    /**
     * 根据广播 得到 按角色依从率
     * @param intent 广播
     */
    private void getRolesDate(Intent intent) {

        int res = intent.getIntExtra(RateByRoleBiz.EXTRA_RESULT,0);
        switch (res){
            case RateByRoleBiz.FAILED:
                //Toast.makeText(getActivity(),"按角色数据失败",Toast.LENGTH_SHORT).show();
                break;
            case RateByRoleBiz.SUCCESS:
                Bundle bundle = intent.getExtras();
                RateByRoleBiz.RateRoleBizOneDayResult resultData =
                        (RateByRoleBiz.RateRoleBizOneDayResult) bundle.get(RateByRoleBiz.EXTRA_RES_DATA);
                resultData.getDateStr();
                //不涉及

//                if(!resultData.getStrDepaert().equals(MyApplication.getCurrentUser().getDepartIds())){
//                    return;
//                }

                RolesRateOneDay dayRolesData = resultData.getRolesRateOneDay();

                Double[] data = new Double[4];
                data[0] = dayRolesData.getNurseRate();
                data[1] = dayRolesData.getSupportWorkerRate();
                data[2] = dayRolesData.getDoctorRate();
                data[3] = dayRolesData.getPhysicianRate();

                //存放值
                mWeekRolesRateMap.put(resultData.getDateStr(),data);
                //计数
                count++;
                //校验
                if(count != 0 && count % 7 == 0){
                    //遍历map 看看都满了没
                    //mWeekRolesRateMap;
                    boolean dataOK = true;
                    for(Iterator it = mWeekRolesRateMap.keySet().iterator();it.hasNext();){
                        //boolean needTobreak = false;
                        if(mWeekRolesRateMap.get( it.next()) == null){
                            dataOK = false;
                            break;
                        }
                    }
                    if(dataOK){//数据结构满了
                        updateLineChart(false);
                    }

                }

                break;
            default:
        }

    }


    private String showArray(float[] arr) {
        String res = "";
        for(int i = 0; i <arr.length ;i ++ ){
            res = res + i+"--->\n "+arr[i]+"\n" ;
        }
        return res;

    }


}
