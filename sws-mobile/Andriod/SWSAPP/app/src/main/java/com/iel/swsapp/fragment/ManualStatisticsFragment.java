package com.iel.swsapp.fragment;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.iel.swsapp.R;
import com.iel.swsapp.activity.ManualRecordListActivity;
import com.iel.swsapp.application.MyApplication;
import com.iel.swsapp.db.MyWatchDBLogicUtils;
import com.iel.swsapp.utils.AnimatorUtil;
import com.iel.swsapp.utils.CommenUtil;
import com.iel.swsapp.utils.StaticClass;
import com.iel.swsapp.utils.chartutil.PieChartUtil;
import com.iel.swsapp.view.KeyRadioGroupV1;
import com.iel.swsapp.view.KeyRadioGroupV2;

import java.util.Calendar;
import java.util.Map;

/**
 * 人工观察
 * 统计结果页面
 */
public class ManualStatisticsFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    //部门的统计概要值
    private TextView mTvRateObey,mTvRateCorrection;
    //作为按钮用的跳转到下一层
    //private TextView mTvMoreStaffInfoBtn;
    private CardView mCVMoreStaffInfoBtn;
    //各类Spinner
    private Spinner mSpinnerDepart,mSpinnerWay,mSpinnerOccasionHow,mSpinnerRoleHow;
    //日期选择
    private Button mBtnChooseTime;

    //图表总体饼图
    private PieChart mPieChart_DepartOverAll;
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
    //雷达图角色
    private RadarChart mRadarChart_Role;

    //参数属性:时间范围
    private String mStrTimeStart;
    private String mStrTimeEnd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manual_statistics,null);
        findViews(view);

        setDefaultTimes();

        return view;
    }



    @Override
    public void onStart() {
        super.onStart();
        getStatisticsAndSetCharts();
    }

    private void getStatisticsAndSetCharts() {

        // 得到数据
        Map<String , Double> results = MyWatchDBLogicUtils.getRatesFromAll(getActivity());

        Double rateAll = results.get(MyWatchDBLogicUtils.RESULT_RATE_ALL_OBEY);
        Double rateCorrect = results.get(MyWatchDBLogicUtils.RESULT_RATE_ALL_CORRECT);
        mTvRateObey.setText( String.format("%.1f", rateAll)+"%");
        mTvRateCorrection.setText( String.format("%.1f", rateCorrect)+"%");

        PieChartUtil.showTestPieDepartOverall(mPieChart_DepartOverAll,
                getActivity(),
                rateAll.floatValue(),
                String.format("%.1f", rateAll)+"%",
                12,
                R.color.color_Do_BeforeAspeticOpt, R.color.color_Do_BeforeAspeticOpt,R.color.color_Do_BeforeAspeticOpt_bg);

        Double rateOcaBC = results.get(MyWatchDBLogicUtils.RESULT_RATE_OCCASION_BEFORE_CONTACT);
        PieChartUtil.showTestPieDepartOverall(mPieChart_BeforeContact,
                getActivity(),
                rateOcaBC.floatValue(),
                String.format("%.1f", rateOcaBC)+"%",
                12,
                R.color.color_Do_BeforeContact, R.color.color_Do_BeforeContact,R.color.color_Do_BeforeContact_bg);

        Double rateOcAC = results.get(MyWatchDBLogicUtils.RESULT_RATE_OCCASION_AFTER_CONTACT);
        PieChartUtil.showTestPieDepartOverall(mPieChart_AfterContactPatient,
                getActivity(),
                rateOcAC.floatValue(),
                String.format("%.1f", rateOcAC)+"%",
                12,
                R.color.color_Do_BeforeAspeticOpt, R.color.color_Do_BeforeAspeticOpt,R.color.color_Do_BeforeAspeticOpt_bg);


        Double rateOcBA = results.get(MyWatchDBLogicUtils.RESULT_RATE_OCCASION_BEFORE_ASPETIC);
        PieChartUtil.showTestPieDepartOverall(mPieChart_BeforeAsepticOpt,
                getActivity(),
                rateOcBA.floatValue(),
                String.format("%.1f", rateOcBA)+"%",
                12,
                R.color.color_Do_AfterContactEnv, R.color.color_Do_AfterContactEnv,R.color.color_Do_AfterContactEnv_bg);

        Double rateOcAL = results.get(MyWatchDBLogicUtils.RESULT_RATE_OCCASION_AFTER_LIQUID);
        PieChartUtil.showTestPieDepartOverall(mPieChart_AfterContactFluids,
                getActivity(),
                rateOcAL.floatValue(),
                String.format("%.1f", rateOcAL)+"%",
                12,
                R.color.color_Do_AfterContactFluids, R.color.color_Do_AfterContactFluids,R.color.color_Do_AfterContactFluids_bg);

        Double rateOcAE = results.get(MyWatchDBLogicUtils.RESULT_RATE_OCCASION_AFTER_ENV);
        PieChartUtil.showTestPieDepartOverall(mPieChart_AfterContactEnv,
                getActivity(),
                rateOcAE.floatValue(),
                String.format("%.1f", rateOcAE)+"%",
                12,
                R.color.color_Do_AfterContactPatient, R.color.color_Do_AfterContactPatient,R.color.color_Do_AfterContactPatient_bg);

    }

    /**
     * 设置默认时间
     */
    private void setDefaultTimes() {
        //默认选择时间
        Calendar now = Calendar.getInstance();
        setStartAndEndTime(now.get(Calendar.YEAR),
                now.get(Calendar.MONTH) ,
                now.get(Calendar.DAY_OF_MONTH),
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH) + 1,
                now.get(Calendar.DAY_OF_MONTH));
        //设置时间到按钮上
        changeChooseBtnText();

    }

    /**
     * 找到控件
     */
    private void findViews(View view) {

        //findView
        //button
        mBtnChooseTime = (Button) view.findViewById(R.id.btn_choose_time_manual_statistic);
        //tvs
        mTvRateObey = (TextView) view.findViewById(R.id.tv_way_manual_statistics_rate_obey);
        mTvRateCorrection = (TextView) view.findViewById(R.id.tv_way_manual_statistics_rate_correction);
        mCVMoreStaffInfoBtn = (CardView) view.findViewById(R.id.cv_more_staff);

        //spinners
        mSpinnerDepart = (Spinner) view.findViewById(R.id.spinner_manual_statistic_depart);
        mSpinnerWay = (Spinner) view.findViewById(R.id.spinner_manual_statistic_way);
        mSpinnerOccasionHow = (Spinner) view.findViewById(R.id.spinner_manual_statistic_occassion);
        mSpinnerRoleHow = (Spinner) view.findViewById(R.id.spinner_manual_statistic_role);
        //charts
        mPieChart_DepartOverAll = (PieChart) view.findViewById(R.id.piechart_manual_statistic_overall);
        //2 before 3 after
        mPieChart_BeforeContact = (PieChart) view.findViewById(R.id.piechart_manual_statistic_before_contact_patient);
        mPieChart_BeforeAsepticOpt = (PieChart) view.findViewById(R.id.piechart_manual_statistic_before_aspetic);
        mPieChart_AfterContactEnv = (PieChart) view.findViewById(R.id.piechart_manual_statistic_after_contact_patient_env);
        mPieChart_AfterContactFluids = (PieChart) view.findViewById(R.id.piechart_manual_statistic_after_contact_fluid);
        mPieChart_AfterContactPatient = (PieChart) view.findViewById(R.id.piechart_manual_statistic_after_contact_patient);
        //radar
        mRadarChart_Role = (RadarChart) view.findViewById(R.id.radarchart_manual_statistics_role);

        //事件监听
        mCVMoreStaffInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(),"to be jump",Toast.LENGTH_SHORT).show();
                // Jump to record
                Intent i = new Intent(getActivity(), ManualRecordListActivity.class);
                startActivity(i);

            }
        });
        //时间选择按钮
        mBtnChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDateRange();
            }
        });

        //Spinner设置
        //部门的Spinner
        ArrayAdapter<String> departAdapter =
                new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, MyApplication.sDepartmentNames);
        mSpinnerDepart.setAdapter(departAdapter);
        mSpinnerDepart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),"selected is :"+ MyApplication.sDepartmentIds.get(position),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //选择科室Spinner :admin 和 感控科的用户登录后可以使用Spinner
        String cUserDepartIds = MyApplication.getCurrentUser().getDepartIds();
        if(StaticClass.SYS_DEPART_ID_ADIMN.equals(cUserDepartIds) ||
                StaticClass.SYS_DEPART_ID_GANKONG.equals(cUserDepartIds) ){
            //admin 和 感控科的用户登录后可以使用Spinner
            mSpinnerDepart.setEnabled(true);
        }else{
            //其他科室的不能用Spinner
            //找到需要显示的默认值
            int defaultPosition =
                    departAdapter.getPosition(MyApplication.getCurrentUser().getDepartment());
            mSpinnerDepart.setSelection(defaultPosition);
            mSpinnerDepart.setEnabled(false);
        }
        //洗手方式spinner监听
        mSpinnerWay .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),position+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //时机统计结果类型spinner监听
        mSpinnerOccasionHow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),position+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //人员统计类型spinner监听
        mSpinnerRoleHow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),position+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 暂时将一些spinner设置为不可用
        mSpinnerWay.setEnabled(false);
        mSpinnerOccasionHow.setEnabled(false);
        mSpinnerRoleHow.setEnabled(false);

    }


    /**
     * 处理时间选择,弹出对话框DatePickerDialog
     * 对话框的回调是本Fragment实现的接口
     */
    private void chooseDateRange() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                ManualStatisticsFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAutoHighlight(false);
        dpd.setStartTitle("开始时间");
        dpd.setEndTitle("截止时间");
        dpd.setAccentColor(ContextCompat.getColor( getActivity(),R.color.colorPrimaryDark));
        dpd.setMaxDate(now);
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    /**
     * 本Fragment实现了选择时间对话框的回调接口
     */
    @Override
    public void onDateSet(DatePickerDialog view,
                          int year,int monthOfYear,int dayOfMonth,
                          int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {


        // 不合法选择
        if(year > yearEnd // 起始年份 大于 结束年份
                || year == yearEnd && monthOfYear > monthOfYearEnd
                || year == yearEnd && monthOfYear == monthOfYearEnd && dayOfMonth > dayOfMonthEnd
                ){
            Toast.makeText(getActivity(), R.string.info_illegal_pick_time,Toast.LENGTH_SHORT).show();
            //动画
            ObjectAnimator objectAnimator = AnimatorUtil.nope(mBtnChooseTime );
            objectAnimator.start();
            return;
        }
        //因为canlender返回的月份是从0开始的
        monthOfYear++;
        monthOfYearEnd++;

        setStartAndEndTime(year,monthOfYear,dayOfMonth,
                yearEnd,monthOfYearEnd,dayOfMonthEnd);

        changeChooseBtnText();
    }
    /**
     * 设置时间选择按钮的值
     */
    private void changeChooseBtnText() {
        StringBuilder sb = new StringBuilder("");
        sb.append(mStrTimeStart);
        sb.append(" ");
        sb.append(getString(R.string.text_to));
        sb.append(" ");
        sb.append(mStrTimeEnd);
        mBtnChooseTime.setText(sb.toString());
    }
    /**
     * 设置起始时间和终止时间，时间成员变量值
     */
    private void setStartAndEndTime(int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        StringBuilder strStart = new StringBuilder("");
        StringBuilder strEnd = new StringBuilder("");
        strStart.append(year).append("-")
                .append(monthOfYear).append("-")
                .append(dayOfMonth);
        strEnd.append(yearEnd).append("-")
                .append(monthOfYearEnd).append("-")
                .append(dayOfMonthEnd);
        mStrTimeStart = strStart.toString();
        mStrTimeEnd = strEnd.toString();
    }
}
