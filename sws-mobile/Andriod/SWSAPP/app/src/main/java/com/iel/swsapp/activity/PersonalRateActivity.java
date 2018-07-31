package com.iel.swsapp.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.iel.swsapp.R;
import com.iel.swsapp.business.OneStaffRateBiz;
import com.iel.swsapp.business.StaffRateBiz;
import com.iel.swsapp.utils.StaticClass;
import com.iel.swsapp.utils.chartutil.BarChartUtil;
import com.iel.swsapp.utils.chartutil.PieChartUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Full Name  : com.iel.swsapp.activity.PersonalRateActivity
 * Author Name      : yxnne
 * Create Time      : 2017/3
 * Project Name     : SWSAPP
 * Descriptions     : 个人依从率信息
 */
public class PersonalRateActivity extends ActionbarBackBaseActivity {

    public static String EXTRA_NAME = "name";
    public static String EXTRA_JOB_TYPE = "job_type";
    public static String EXTRA_RATE = "rate";
    public static String EXTRA_DEPART = "depart";
    public static String EXTRA_RFID = "rfid";

    public static String EXTRA_0003 = "0003";
    public static String EXTRA_0007 = "0007";
    public static String EXTRA_0008 = "0008";
    public static String EXTRA_0110 = "0110";
    public static String EXTRA_0103 = "0103";

    public static String EXTRA_AFTER_CONTACT = "rate_after_contact";
    public static String EXTRA_BEFORE_CONTACT = "rate_before_contact";
    public static String EXTRA_BEFORE_ASEPT = "rate_before_asept";
    public static String EXTRA_AFTER_ENVI = "rate_after_envi";

    private OneStaffRateBiz mRateBiz;

    private ListView mLvPersonalInfo;
    private List<String> mListPersonInfoStr;

    private RadioButton mRbtnToday;
    private RadioButton mRbtnRecent;

    //广播
    private MyBroadCast mBroadcast;

    //图表
    private PieChart mPieChart;
    //private HorizontalBarChart mBarChart;
    private String mUserName;//名字
    private String mRate;//依从率
    private String mDeparment;//依从率
    private String mJobType;//职位
    private String mRfid;//胸卡号

    //洗手五时机
    private int mMoment0003;
    private int mMoment0007;
    private int mMoment0008;
    private int mMoment0110;
    private int mMoment0103;

    //条形图四个数据 double
    private float mRateAfterCloseNick = 0;
    private float mRateAfterCloseNickEnvri = 0;
    private float mRateBeforeAsepticOperation = 0;
    private float mRateBeforeCloseNick = 0;


    /**
     * 静态方法，获得Intent
     * @param name 人员姓名
     * @param jobType 职位类别
     * @param rate 依从率
     * @param department 部门
     * @return Intent
     */
    public static Intent getIntent(Activity from,String name , String jobType, String rate, String department){
        Intent intent = new Intent(from,PersonalRateActivity.class);
        intent.putExtra(EXTRA_NAME,name);
        intent.putExtra(EXTRA_JOB_TYPE,jobType);
        intent.putExtra(EXTRA_RATE,rate);
        intent.putExtra(EXTRA_DEPART,department);

        return intent;
    }

    /**
     * 静态方法，获得Intent
     * @param name 人员姓名
     * @param jobType 职位类别
     * @param rate 依从率
     * @param department 部门
     * @return Intent
     */
    public static Intent getIntent(Activity from,String name , String jobType, String rate, String department,String rfid,
        int m0003,int m0007,int m0008,int m0110,int m0103){
        Intent intent = new Intent(from,PersonalRateActivity.class);
        intent.putExtra(EXTRA_NAME,name);
        intent.putExtra(EXTRA_JOB_TYPE,jobType);
        intent.putExtra(EXTRA_RATE,rate);
        intent.putExtra(EXTRA_DEPART,department);
        intent.putExtra(EXTRA_RFID,rfid);

        intent.putExtra(EXTRA_0003,m0003);
        intent.putExtra(EXTRA_0007,m0007);
        intent.putExtra(EXTRA_0008,m0008);
        intent.putExtra(EXTRA_0103,m0103);
        intent.putExtra(EXTRA_0110,m0110);

        return intent;
    }

    public static Intent getIntent(Activity from,String name , String jobType,
                                   String rate, String department, String rfid,
                                   int m0003,int m0007,int m0008,int m0110,int m0103,
                                   float rateAfterCloseNick,
                                   float rateAfterCloseNickEnvri,
                                   float rateBeforeAsepticOperation,
                                   float rateBeforeCloseNick){
        Intent intent = new Intent(from,PersonalRateActivity.class);
        intent.putExtra(EXTRA_NAME,name);
        intent.putExtra(EXTRA_JOB_TYPE,jobType);
        intent.putExtra(EXTRA_RATE,rate);
        intent.putExtra(EXTRA_DEPART,department);
        intent.putExtra(EXTRA_RFID,rfid);

        intent.putExtra(EXTRA_0003,m0003);
        intent.putExtra(EXTRA_0007,m0007);
        intent.putExtra(EXTRA_0008,m0008);
        intent.putExtra(EXTRA_0103,m0103);
        intent.putExtra(EXTRA_0110,m0110);

        intent.putExtra(EXTRA_AFTER_CONTACT,rateAfterCloseNick);
        intent.putExtra(EXTRA_BEFORE_CONTACT,rateBeforeCloseNick);
        intent.putExtra(EXTRA_BEFORE_ASEPT,rateBeforeAsepticOperation);
        intent.putExtra(EXTRA_AFTER_ENVI,rateAfterCloseNickEnvri);

        return intent;
    }
    //用ProgressBar做的条形图
    private ProgressBar mPbBeforeContactRate,mPbBeforeAsepticRate,
                        mPbAfterContactEvn,mPbAfterContactFluid,mPbAfterContactPatient;

    private TextView mTvBeforeContactRate,mTvBeforeAsepticRate,
            mTvAfterContactEvn,mTvAfterContactFluid,mTvAfterContactPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_rate);
        //设置自己的ActionBar
        setMyActionBar(R.id.rl_myactionbar_back,
                R.id.iv_myactionbar_menu,
                R.id.tv_myactionbar_tittle,
                true,getString(R.string.text_personal_rate));
        //获得数据
        mUserName = getIntent().getStringExtra(EXTRA_NAME);
        mRate = getIntent().getStringExtra(EXTRA_RATE);
        mJobType = getIntent().getStringExtra(EXTRA_JOB_TYPE);
        mDeparment = getIntent().getStringExtra(EXTRA_DEPART);
        mRfid = getIntent().getStringExtra(EXTRA_RFID);

        mMoment0003 = getIntent().getIntExtra(EXTRA_0003,0);
        mMoment0110 = getIntent().getIntExtra(EXTRA_0110,0);
        mMoment0103 = getIntent().getIntExtra(EXTRA_0103,0);
        mMoment0008 = getIntent().getIntExtra(EXTRA_0008,0);
        mMoment0007 = getIntent().getIntExtra(EXTRA_0007,0);

        mRateAfterCloseNick = getIntent().getFloatExtra(EXTRA_AFTER_CONTACT,0);
        mRateBeforeCloseNick = getIntent().getFloatExtra(EXTRA_BEFORE_CONTACT,0);
        mRateBeforeAsepticOperation = getIntent().getFloatExtra(EXTRA_BEFORE_ASEPT,0);
        mRateAfterCloseNickEnvri = getIntent().getFloatExtra(EXTRA_AFTER_ENVI,0);

        //RadioButton
        mRbtnToday = (RadioButton) findViewById(R.id.rbtn_personal_today);
        mRbtnRecent = (RadioButton) findViewById(R.id.rbtn_personal_rencent30);
        mRbtnRecent.setChecked(true);

        mRbtnToday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    PieChartUtil.setPieChartUI_5(PersonalRateActivity.this,mPieChart,20,40,60,10,30);
//                }else{
//                    PieChartUtil.setPieChartUI_5(PersonalRateActivity.this,mPieChart,100,128,88,99,100);
//                }
                doGetPersonalData();


            }
        });
        //柱状图初始化
        findBars();
        setBarsUI();

        getPersonalBasicInfo();
        //ListView 个人基本数据
        mLvPersonalInfo = (ListView) findViewById(R.id.lv_prsonal_info);
        mLvPersonalInfo.setAdapter(new MyPersonalInfoAdapter());

        //饼图 和 柱状图
        mPieChart = (PieChart) findViewById(R.id.pieChart_personal_rate_activity);
        //mBarChart = (HorizontalBarChart) findViewById(R.id.barchart_personal_rate_activity);

        PieChartUtil.setPieChartUI_5(this,mPieChart,
                mMoment0003,//洗手次数 0003
                mMoment0110,//长时离开病床未洗手 0110
                mMoment0103,//接近病床未洗手 0103
                mMoment0008,//离开病房未洗手 0008
                mMoment0007);//进入病房未洗手 0007

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mBroadcast == null){
            mBroadcast = new MyBroadCast();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(StaticClass.ACTION_GET_ONE_STAFF_RATE);
        registerReceiver(mBroadcast,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mBroadcast);
    }

    /**
     * 请求数据
     */
    private void doGetPersonalData() {
        if(mRateBiz == null){
            mRateBiz = new OneStaffRateBiz();
        }

        if(mRbtnToday.isChecked()){//请求今天数据
            mRateBiz.getOneStaffRateInfo(this,mRfid,true);

        }else{
            mRateBiz.getOneStaffRateInfo(this,mRfid,false);

        }

    }

    private void setBarsUI() {
//        private float mRateAfterCloseNick;
//        private float mRateAfterCloseNickEnvri;
//        private float mRateBeforeAsepticOperation;
//        private float mRateBeforeCloseNick;
        setProgressValueAndAnimator(mPbBeforeContactRate,(int)mRateBeforeCloseNick,mTvBeforeContactRate,mRateBeforeCloseNick);
        setProgressValueAndAnimator(mPbBeforeAsepticRate,(int)mRateBeforeAsepticOperation,mTvBeforeAsepticRate,mRateBeforeAsepticOperation);
        setProgressValueAndAnimator(mPbAfterContactEvn,(int)mRateAfterCloseNickEnvri,mTvAfterContactEvn,mRateAfterCloseNickEnvri);
        //setProgressValueAndAnimator(mPbAfterContactFluid,46,mTvAfterContactFluid,46.5f);
        setProgressValueAndAnimator(mPbAfterContactPatient,(int)mRateAfterCloseNick,mTvAfterContactPatient,mRateAfterCloseNick);

    }
    /**
     * 柱状图出初始化
     */
    private void findBars() {

        mPbBeforeContactRate = (ProgressBar) findViewById(R.id.pb_personal_bar_before_contact);
        mPbBeforeAsepticRate = (ProgressBar) findViewById(R.id.pb_personal_bar_before_aseptic);
        mPbAfterContactEvn = (ProgressBar) findViewById(R.id.pb_personal_bar_after_contact_envv);
        mPbAfterContactFluid = (ProgressBar) findViewById(R.id.pb_personal_bar_after_contact_body_fluids);
        mPbAfterContactPatient = (ProgressBar) findViewById(R.id.pb_personal_bar_after_contact_patients);

        mTvBeforeContactRate = (TextView) findViewById(R.id.tv_personal_value_before_contact);
        mTvBeforeAsepticRate = (TextView) findViewById(R.id.tv_personal_value_before_aseptic);
        mTvAfterContactEvn = (TextView) findViewById(R.id.tv_personal_value_bar_after_contact_env);
        mTvAfterContactFluid = (TextView) findViewById(R.id.tv_personal_value_bar_after_contact_body_fluids);
        mTvAfterContactPatient = (TextView) findViewById(R.id.tv_personal_value_bar_after_contact_patients);

    }

    /**
     *更新条形图
     */
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
        //animTv.setRepeatCount(1);
        animPb.start();
        animTv.start();

    }

    /**
     * 得到个人基本数据
     */
    private void getPersonalBasicInfo() {
        mListPersonInfoStr = new ArrayList<>();
        String strNameValue  ;
        String rate ;
        if(mUserName != null && !mUserName.equals("")){
            strNameValue = mUserName;
        }else{
            strNameValue = "User";
        }
        if(mRate != null && !mRate.equals("")){
            rate = mRate;
        }else{
            rate = "68%";
        }

        mListPersonInfoStr.add(strNameValue);
        mListPersonInfoStr.add(getString(R.string.text_department)+" : "+mDeparment);
        mListPersonInfoStr.add(getString(R.string.text_role)+" : "+mJobType);
        mListPersonInfoStr.add(getString(R.string.text_rate_total)+" : "+mRate+"%");

    }

    class MyPersonalInfoAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mListPersonInfoStr.size();
        }

        @Override
        public Object getItem(int position) {
            return mListPersonInfoStr.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.item_list_personal_rate_info,null);

            TextView tvInfo = (TextView) convertView.findViewById(R.id.tv_item_personal_info);
            if(position == 0){
                tvInfo.setTextSize(16);
                tvInfo.setTextColor(ContextCompat.getColor(PersonalRateActivity.this,
                        R.color.textNormalColor));
            }
            tvInfo.setText(mListPersonInfoStr.get(position));


            return convertView;
        }
    }

    /**
     * 本活动的广播
     */
    private class MyBroadCast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case StaticClass.ACTION_GET_ONE_STAFF_RATE:

                    Bundle bundle = intent.getExtras();
                    OneStaffRateBiz.StaffRateData data = (OneStaffRateBiz.StaffRateData) bundle.getSerializable(OneStaffRateBiz.RESULT_ONE_STAFF);

                    //两千两后
                    mRateAfterCloseNick = (float) data.getDatas().get(0).getRateAfterCloseNick();
                    mRateAfterCloseNickEnvri = (float) data.getDatas().get(0).getRateAfterCloseNickEnvri();
                    mRateBeforeAsepticOperation = (float) data.getDatas().get(0).getRateBeforeAsepticOperation();
                    mRateBeforeCloseNick = (float) data.getDatas().get(0).getRateBeforeCloseNick();
                    //洗手时机
                    mMoment0003 = data.getDatas().get(0).getMoment0003();
                    mMoment0007 = data.getDatas().get(0).getMoment0007();
                    mMoment0008 = data.getDatas().get(0).getMoment0008();
                    mMoment0103 = data.getDatas().get(0).getMoment0103();
                    mMoment0110 = data.getDatas().get(0).getMoment0110();

                    updateChars();
                    break;
            }
        }
    }

    private void updateChars() {
        //饼状图
        PieChartUtil.setPieChartUI_5(this,mPieChart,
                mMoment0003,//洗手次数 0003
                mMoment0110,//长时离开病床未洗手 0110
                mMoment0103,//接近病床未洗手 0103
                mMoment0008,//离开病房未洗手 0008
                mMoment0007);//进入病房未洗手 0007
        //条形图
        setBarsUI();
    }

}
