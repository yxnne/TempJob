package com.iel.swsapp.fragment;

import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.iel.swsapp.R;
import com.iel.swsapp.activity.DepartRateActivity;
import com.iel.swsapp.application.MyApplication;
import com.iel.swsapp.business.DepartRateOverAllBiz;
import com.iel.swsapp.business.RateByRoleBiz;
import com.iel.swsapp.business.RateDepartrMomentBiz;
import com.iel.swsapp.entity.RolesRateOneDay;
import com.iel.swsapp.utils.StaticClass;
import com.iel.swsapp.utils.chartutil.BarChartUtil;
import com.iel.swsapp.utils.chartutil.PieChartUtil;
import com.iel.swsapp.utils.chartutil.RadarChartUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Class Full Name  : com.iel.swsapp.fragment.MainInfoFragment
 * Author Name      : yxnne
 * Create Time      : 2017/2/8
 * Project Name     : SWSAPP
 * Descriptions     : 部门整体 Fragment
 */
public class DepartRateFragment extends Fragment
        implements DepartRateActivity.onChooseSelectedListner{
    private static final String KEY_LISTNER_DEPART = "DepartRateFragment";

    //图表控件
    //饼图
    //饼图 30天数据
    private int mMoment0003_30days;
    private int mMoment0007_30days;
    private int mMoment0103_30days;
    private int mMoment0110_30days;
    private int mMoment0008_30days;
    //饼图 一天数据
    private int mMoment0003_onedays;
    private int mMoment0007_onedays;
    private int mMoment0103_onedays;
    private int mMoment0110_onedays;
    private int mMoment0008_onedays;


    private PieChart mPieChart;
    //雷达图
    private RadarChart mRadarChart;

    //雷达图 30天数据
    private double mRolesDataNurse30days;
    private double mRolesDataWorker30days;
    private double mRolesDataPhysician30days;
    private double mRolesDataDoctor30days;
    private double mRolesDataAll30days;
    //雷达图 一天数据
    private double mRolesDataNurseOneday;
    private double mRolesDataWorkerOneday;
    private double mRolesDataPhysicianOneday;
    private double mRolesDataDoctorOneday;
    private double mRolesDataAllOneday;

    //数据 "两前三后"变成了"两前两后"
    private Double mRateDepartOverAll;//总体
    private float mRateAfterCloseNick;
    private float mRateAfterCloseNickEnvri;
    private float mRateBeforeAsepticOperation;
    private float mRateBeforeCloseNick;

    //RadioGroup
    private RadioButton mRbtnToday;
    private RadioButton mRbtnRecent30;

    //下拉刷新控件
    private PtrFrameLayout mPtrFrame;

    //用ProgressBar做的条形图
    private ProgressBar mPbBeforeContactRate,mPbBeforeAsepticRate,
            mPbAfterContactEvn,mPbAfterContactFluid,mPbAfterContactPatient;

    private TextView mTvBeforeContactRate,mTvBeforeAsepticRate,
            mTvAfterContactEvn,mTvAfterContactFluid,mTvAfterContactPatient;

    private BroadcastReceiver mBroadcastReceiver;

    @Override
    public void onResume() {
        super.onResume();
        mBroadcastReceiver = new MyBroadCast();
        IntentFilter intentFilter = new IntentFilter();
        // add Actions to Filter
        intentFilter.addAction(StaticClass.ACTION_GET_ROLES_RATE_RADAR);
        intentFilter.addAction(StaticClass.ACTION_GET_DEPART_MOMENT_PIE);
        intentFilter.addAction(StaticClass.ACTION_GET_DEPART_OVERALL_RATE);
        getActivity().registerReceiver(mBroadcastReceiver,intentFilter);

        //设置监听器
        ((DepartRateActivity)getActivity()).setOnChooseSelectedListner(KEY_LISTNER_DEPART,this);

    }

    @Override
    public void onStop() {
        super.onStop();
        //这里先不注销广播，会有bug 具体再分析
//        if(mBroadcastReceiver != null){
//            getActivity().unregisterReceiver(mBroadcastReceiver);
//        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        if(mBroadcastReceiver != null){
//            getActivity().unregisterReceiver(mBroadcastReceiver);
//        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_depart_rate,null);
        //显示控制


        //卡片一的数据获得
        doGetPieData();

        //卡片二的数据获得
        doGetProgBarData();

        //卡片三 的数据获得
        doGetRadarData();


        mPtrFrame = (PtrFrameLayout) view.findViewById(R.id.ptframe_depart_fragment);
        mPtrFrame.disableWhenHorizontalMove(true);
        mPtrFrame.setDurationToCloseHeader(3000);

        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                    }
                }, 1000);
            }
        });

        mPieChart = (PieChart) view.findViewById(R.id.pieChart_depart_rate_fragment);
        //mBarChart = (BarChart) view.findViewById(R.id.barchart_depart_rate_fragment);
        mRadarChart = (RadarChart) view.findViewById(R.id.radarchart_depart_rate_fragment);

        //切换数据项
        mRbtnToday = (RadioButton) view.findViewById(R.id.rbtn_depart_today);
        mRbtnRecent30 = (RadioButton) view.findViewById(R.id.rbtn_depart_rencent30);
        mRbtnRecent30.setChecked(true);
        mRbtnRecent30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //卡片一  数据获得
                doGetPieData();

                //卡片二  数据获得
                doGetProgBarData();

                //卡片三  数据获得
                doGetRadarData();
            }
        });

        //
        PieChartUtil.setPieChartUI_5(getActivity(),mPieChart,30,58,48,19,50);

        //BarChartUtil.initBarChartUI(getActivity(),mBarChart);
        RadarChartUtil.getRadarChart_5(getActivity(),mRadarChart);
        //柱状图初始化
        findBars(view);
        setBarsUI();

        return view;
    }

    /**
     * 为条形图 两前两后请求数据
     */
    private void doGetProgBarData() {
        if(mRbtnRecent30!= null){
            if(mRbtnRecent30.isChecked()){
                DepartRateOverAllBiz biz = new DepartRateOverAllBiz();
                biz.doDepartRate30(DepartRateActivity.sChooseDepartId,getActivity());
            }else{
                DepartRateOverAllBiz biz = new DepartRateOverAllBiz();
                biz.doDepartRateOneDay(DepartRateActivity.sChooseDepartId,getActivity());
            }
        }else{
            DepartRateOverAllBiz biz = new DepartRateOverAllBiz();
            biz.doDepartRate30(DepartRateActivity.sChooseDepartId,getActivity());
        }

    }

    /**
     * 为雷达图 请求数据
     */
    private void doGetRadarData() {
        if(mRbtnRecent30!= null){
            if(mRbtnRecent30.isChecked()){
                RateByRoleBiz biz = new RateByRoleBiz();
                biz.doGetRate30Day(getActivity());
            }else{
                RateByRoleBiz biz = new RateByRoleBiz();
                biz.doGetRateToday(getActivity());
            }
        }else{
            RateByRoleBiz biz = new RateByRoleBiz();
            biz.doGetRate30Day(getActivity());
        }

    }


    /**
     * 为饼状图 请求数据 card 1
     */
    private void doGetPieData() {
        if(mRbtnRecent30!= null){
            if(mRbtnRecent30.isChecked()){
                RateDepartrMomentBiz biz = new RateDepartrMomentBiz();
                biz.doGetMoment30(getActivity());
            }else{
                RateDepartrMomentBiz biz = new RateDepartrMomentBiz();
                biz.doGetMomentToday(getActivity());
            }
        }else{
            RateDepartrMomentBiz biz = new RateDepartrMomentBiz();
            biz.doGetMoment30(getActivity());
        }

    }

    private void setBarsUI() {
        setProgressValueAndAnimator(mPbBeforeContactRate,0,mTvBeforeContactRate,0f);
        setProgressValueAndAnimator(mPbBeforeAsepticRate,0,mTvBeforeAsepticRate,0f);
        setProgressValueAndAnimator(mPbAfterContactEvn,0,mTvAfterContactEvn,0f);
        setProgressValueAndAnimator(mPbAfterContactFluid,0,mTvAfterContactFluid,0f);
        setProgressValueAndAnimator(mPbAfterContactPatient,0,mTvAfterContactPatient,0f);

    }
    /**
     * 柱状图出初始化
     */
    private void findBars(View v) {

        mPbBeforeContactRate = (ProgressBar) v.findViewById(R.id.pb_personal_bar_before_contact);
        mPbBeforeAsepticRate = (ProgressBar) v.findViewById(R.id.pb_personal_bar_before_aseptic);
        mPbAfterContactEvn = (ProgressBar) v.findViewById(R.id.pb_personal_bar_after_contact_envv);
        mPbAfterContactFluid = (ProgressBar) v.findViewById(R.id.pb_personal_bar_after_contact_body_fluids);
        mPbAfterContactPatient = (ProgressBar) v.findViewById(R.id.pb_personal_bar_after_contact_patients);

        mTvBeforeContactRate = (TextView) v.findViewById(R.id.tv_personal_value_before_contact);
        mTvBeforeAsepticRate = (TextView) v.findViewById(R.id.tv_personal_value_before_aseptic);
        mTvAfterContactEvn = (TextView) v.findViewById(R.id.tv_personal_value_bar_after_contact_env);
        mTvAfterContactFluid = (TextView) v.findViewById(R.id.tv_personal_value_bar_after_contact_body_fluids);
        mTvAfterContactPatient = (TextView) v.findViewById(R.id.tv_personal_value_bar_after_contact_patients);

    }

    private void setProgressValueAndAnimator(final ProgressBar pb, int pbValue, final TextView tv, float tvValue) {


        ValueAnimator animPb = ValueAnimator.ofInt(0,pbValue);
        animPb.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatorValue = (int)animation.getAnimatedValue();
                pb.setProgress(animatorValue);
            }
        });
        animPb.setDuration(1000);
        animPb.setTarget(pb);
        //animPb.setRepeatCount(1);
        //animPb.setRepeatMode(ValueAnimator.INFINITE);


        ValueAnimator animTv = ValueAnimator.ofInt(0,(int)tvValue);
        animTv.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatorValue = (int)animation.getAnimatedValue();
                tv.setText(animatorValue+"%");
            }
        });
        animTv.setDuration(1000);
        animTv.setTarget(tv);
        //animTv.setRepeatCount(0);
        animPb.start();
        animTv.start();

    }

    @Override
    public void onChooseCheck() {
        //卡片一  数据获得
        doGetPieData();
        //卡片二  数据获得
        doGetProgBarData();
        //卡片三  数据获得
        doGetRadarData();
    }

    /**
     * 接受消息的广播
     */
    private class MyBroadCast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                //雷达图来啦
                case StaticClass.ACTION_GET_ROLES_RATE_RADAR:
                    Logger.i("onReceive  ACTION_GET_ROLES_RATE_RADAR");
                    getRolesDate(intent);

                    break;
                //饼状图来啦
                case StaticClass.ACTION_GET_DEPART_MOMENT_PIE:
                    getMomentData(intent);
                    break;
                //条形图来啦：
                case StaticClass.ACTION_GET_DEPART_OVERALL_RATE:
                    getProgBarData(intent);
                    break;
            }
        }
    }
    /**
     * 根据广播 得到 部门洗手时机依从率
     * @param intent 广播
     */
    private void getMomentData(Intent intent) {
        Bundle bundle = intent.getExtras();
        RateDepartrMomentBiz.DepartMomentResult resultData =
                (RateDepartrMomentBiz.DepartMomentResult) bundle.get(RateDepartrMomentBiz.EXTRA_RES_DATA);
        switch (bundle.getInt(RateDepartrMomentBiz.EXTRA_DATA_DAYS)){
            case RateDepartrMomentBiz.DATA_DAYS_30:

                mMoment0003_30days = resultData.getMoment0003();
                mMoment0007_30days = resultData.getMoment0007();
                mMoment0103_30days = resultData.getMoment0103();
                mMoment0110_30days = resultData.getMoment0110();
                mMoment0008_30days = resultData.getMoment0008();
                break;
            case RateDepartrMomentBiz.DATA_DAYS_1:

                mMoment0003_onedays = resultData.getMoment0003();
                mMoment0007_onedays = resultData.getMoment0007();
                mMoment0103_onedays = resultData.getMoment0103();
                mMoment0110_onedays = resultData.getMoment0110();
                mMoment0008_onedays = resultData.getMoment0008();
                break;
        }
        updatePieUI();
    }
    //getProgBarData();
    /**
     * 根据广播 得到 部门洗手 两千两后
     * @param intent 广播
     */
    private void getProgBarData(Intent intent) {
        Bundle bundle = intent.getExtras();
        DepartRateOverAllBiz.DepartRateResult result =
                ( DepartRateOverAllBiz.DepartRateResult) bundle.get(DepartRateOverAllBiz.RESULT_RATE);
        mRateDepartOverAll = result. getRateDepart();
        //mRateDepartOverAll = intent.getDoubleExtra(DepartRateOverAllBiz.RESULT_RATE,0);
        mRateAfterCloseNick = result.getRateAfterCloseNick();
        mRateAfterCloseNickEnvri = result.getRateAfterCloseNickEnvri();
        mRateBeforeAsepticOperation = result.getRateBeforeAsepticOperation();
        mRateBeforeCloseNick = result.getRateBeforeCloseNick();
        updateProgBarUI();
    }

    /**
     * 根据广播 得到 按角色依从率
     * @param intent 广播
     */
    private void getRolesDate(Intent intent) {
        Bundle bundle = intent.getExtras();
        RateByRoleBiz.RateRoleBizOneDayResult resultData =
                (RateByRoleBiz.RateRoleBizOneDayResult) bundle.get(RateByRoleBiz.EXTRA_RES_DATA);
        Logger.i("resultData.getRolesRateMap()->" + resultData.getRolesRateMap() );

        // 得到角色依从率数据，Map
        if (resultData.getRolesRateMap() == null) {
            return;
        }



        // 根据Map，直接更新雷达图数据
        RadarChartUtil.getRadarChartFromMap(getActivity(), mRadarChart,resultData.getRolesRateMap());


//        if(resultData == null||resultData.getDateStr() == null){
//
//            Logger.i("resultData\n"+ resultData);
//            Logger.i("resultData.getDateStr()\n"+ resultData.getDateStr());
//            return;
//        }
        // resultData.getDateStr();
//        RolesRateOneDay dayRolesData = resultData.getRolesRateOneDay();
//
//        switch (bundle.getInt(RateByRoleBiz.EXTRA_DATA_DAYS)){
//            case RateByRoleBiz.DATA_DAYS_30:
//                mRolesDataNurse30days = dayRolesData.getNurseRate();
//                mRolesDataWorker30days = dayRolesData.getSupportWorkerRate();
//                mRolesDataDoctor30days = dayRolesData.getDoctorRate();
//                mRolesDataPhysician30days = dayRolesData.getPhysicianRate();
//                mRolesDataAll30days = resultData.getDepartRate();
//                break;
//            case RateByRoleBiz.DATA_DAYS_1:
//                mRolesDataNurseOneday = dayRolesData.getNurseRate();
//                mRolesDataWorkerOneday = dayRolesData.getSupportWorkerRate();
//                mRolesDataDoctorOneday = dayRolesData.getDoctorRate();
//                mRolesDataPhysicianOneday = dayRolesData.getPhysicianRate();
//                mRolesDataAllOneday = resultData.getDepartRate();
//                break;
//        }

        // updateRadarUI();
    }

    /**
     * 更新雷达图的UI
     * 数据顺序
     */
    private void updateRadarUI() {
        if(mRbtnRecent30.isChecked()){//显示30天数据
            RadarChartUtil.getRadarChart_5(getActivity(), mRadarChart,
                    mRolesDataAll30days,mRolesDataNurse30days,mRolesDataDoctor30days,
                    mRolesDataPhysician30days,mRolesDataWorker30days);

        }else{
            RadarChartUtil.getRadarChart_5(getActivity(), mRadarChart,
                    mRolesDataAllOneday,mRolesDataNurseOneday,mRolesDataDoctorOneday,
                    mRolesDataPhysicianOneday,mRolesDataWorkerOneday);
        }
    }

    /**
     * 更新Pie饼状图的UI
     */
    private void updatePieUI() {
        if(mRbtnRecent30.isChecked()){//显示30天数据

            PieChartUtil.setPieChartUI_5(getActivity(),mPieChart,
                    mMoment0003_30days,//洗手次数 0003
                    mMoment0110_30days,//长时离开病床未洗手 0110
                    mMoment0103_30days,//接近病床未洗手 0103
                    mMoment0008_30days,//离开病房未洗手 0008
                    mMoment0007_30days);//进入病房未洗手 0007

        }else{

            PieChartUtil.setPieChartUI_5(getActivity(),mPieChart,
                    mMoment0003_onedays,//洗手次数 0003
                    mMoment0110_onedays,//长时离开病床未洗手 0110
                    mMoment0103_onedays,//接近病床未洗手 0103
                    mMoment0008_onedays,//离开病房未洗手 0008
                    mMoment0007_onedays);//进入病房未洗手 0007

        }
    }

    /**
     * 更新条形图 两前两后UI
     */
    private void updateProgBarUI() {

        setProgressValueAndAnimator(mPbBeforeContactRate,(int)mRateBeforeCloseNick,mTvBeforeContactRate,mRateBeforeCloseNick);
        setProgressValueAndAnimator(mPbBeforeAsepticRate,(int)mRateBeforeAsepticOperation,mTvBeforeAsepticRate,mRateBeforeAsepticOperation);
        setProgressValueAndAnimator(mPbAfterContactEvn,(int)mRateAfterCloseNickEnvri,mTvAfterContactEvn,mRateAfterCloseNickEnvri);
        //体液不做
        // setProgressValueAndAnimator(mPbAfterContactFluid,(),mTvAfterContactFluid,0f);
        setProgressValueAndAnimator(mPbAfterContactPatient,(int)mRateAfterCloseNick,mTvAfterContactPatient,mRateAfterCloseNick);
    }

}
