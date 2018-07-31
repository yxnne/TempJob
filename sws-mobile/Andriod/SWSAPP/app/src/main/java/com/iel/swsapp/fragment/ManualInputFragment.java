package com.iel.swsapp.fragment;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.iel.swsapp.R;
import com.iel.swsapp.activity.ManualRecordListActivity;
import com.iel.swsapp.application.MyApplication;
import com.iel.swsapp.db.MyWatchStatisticDatabaseHelper;
import com.iel.swsapp.utils.DateUtils;
import com.iel.swsapp.utils.StaticClass;
import com.iel.swsapp.view.KeyRadioGroupV1;
import com.iel.swsapp.view.KeyRadioGroupV2;
import com.orhanobut.logger.Logger;

import java.util.Date;

/**
 * 人工观察
 * 输入表单页面
 */
public class ManualInputFragment extends Fragment {
    private EditText mEtName,mEtNbr;
    private Spinner mSpinnerRole,mSpinnerDepart;
    //private RadioGroup mRgOccasion,mRgWay,mRgCorrection;
    private KeyRadioGroupV2 mKrgOccasion;
    private KeyRadioGroupV1 mKrgWay,mKrgCorrection,mKrgObey;
    private LinearLayout mLlWayContainner,mLlCorrectionContainer;
    private Button mBtnSubmit;

    //数据库辅助类
    private MyWatchStatisticDatabaseHelper mDBHelper;

    //是否依从状态值
    private boolean isObey = true;
    private String mSelectDepart = "";
    private String mSelectRole = "";

    //动画
    private TranslateAnimation mHiddenAction = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF,0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,0.0f);

    private TranslateAnimation mShowAction = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 1.0f,Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF,0.0f, Animation.RELATIVE_TO_SELF, 0.0f);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manual_form,null);
        setUIAnimations();

        findViews(view);

        mDBHelper = new MyWatchStatisticDatabaseHelper(getActivity(), "sws.db", null, 1);
        return view;
    }

    /**
     * 动画相关的设置
     */
    private void setUIAnimations() {
        //动画相关的设置
        //隐藏
        mHiddenAction.setDuration(300);
        mHiddenAction.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                mLlCorrectionContainer.setVisibility(View.GONE);
                mLlWayContainner.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //显示
        mShowAction.setDuration(350);
        mShowAction.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mBtnSubmit.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLlCorrectionContainer.setVisibility(View.VISIBLE);
                mLlWayContainner.setVisibility(View.VISIBLE);
                mBtnSubmit.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    /**
     * 找到控件
     */
    private void findViews(View view) {
        //名字和工号
        mEtName = (EditText) view.findViewById(R.id.et_manual_beObservedName);
        mEtNbr = (EditText) view.findViewById(R.id.et_manual_beObservedNumber);
        //角色和部门
        mSpinnerDepart = (Spinner) view.findViewById(R.id.spinner_manual_depart);
        mSpinnerRole = (Spinner) view.findViewById(R.id.spinner_manual_role);
        //依从情况
        mKrgObey = (KeyRadioGroupV1) view.findViewById(R.id.rg_manual_obey);

        //洗手时机
        mKrgOccasion = (KeyRadioGroupV2) view.findViewById(R.id.rg_manual_occasion);
        //洗手方式
        mKrgWay = (KeyRadioGroupV1) view.findViewById(R.id.rg_manual_way);
        mLlWayContainner = (LinearLayout) view.findViewById(R.id.ll_manual_input_way);
        //洗手正确性
        mKrgCorrection = (KeyRadioGroupV1) view.findViewById(R.id.rg_manual_correction);
        mLlCorrectionContainer = (LinearLayout) view.findViewById(R.id.ll_manual_input_correction);


        //对依从性的RadioGroup增加监听
        mKrgObey.setOnCheckedChangeListener(new KeyRadioGroupV1.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(KeyRadioGroupV1 group, @IdRes int checkedId) {
                judgeIfObey(checkedId);
            }
        });


        //提交表单 按钮
        mBtnSubmit = (Button) view.findViewById(R.id.btn_submit_manual_form);
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
            //  操作db
            mDBHelper.getWritableDatabase();

            saveAnRecord();

        }});

        //Spinner
        //选择科室Spinner
        mSpinnerDepart = (Spinner) view.findViewById(R.id.spinner_manual_depart);

        ArrayAdapter<String> departAdapter =
                new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, MyApplication.sDepartmentNames);
        mSpinnerDepart.setAdapter(departAdapter);
        mSpinnerDepart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),"selected is :"+ MyApplication.sDepartmentIds.get(position),Toast.LENGTH_SHORT).show();
                mSelectDepart = MyApplication.sDepartmentNames.get(position);
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

        //选择职工类型的
        mSpinnerRole = (Spinner) view.findViewById(R.id.spinner_manual_role);
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_spinner_item, MyApplication.sRoleTypeNames);
        mSpinnerRole.setAdapter(roleAdapter);
        mSpinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),"selected is :"+ MyApplication.sRoleTypeIds.get(position),Toast.LENGTH_SHORT).show();
                mSelectRole = MyApplication.sRoleTypeNames.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * 插入一条数据到数据库
     */
    private void saveAnRecord(){
        //得到输入的值
        String name =  mEtName.getText().toString();
        String nbr = mEtNbr.getText().toString();
        if (TextUtils.isEmpty(name)){
            mEtName.setError("被观察者姓名不要为空");
            return;
        }


        //得到RadioGroup中选择到的ID
        int occasionCheckedID = mKrgOccasion.getCheckedRadioButtonId();
        int obeyCheckedID = mKrgObey.getCheckedRadioButtonId();
        int wayCheckedID = mKrgWay.getCheckedRadioButtonId();
        int correctionCheckedID = mKrgCorrection.getCheckedRadioButtonId();


        String occasion = getOccasionValue(occasionCheckedID);
        String obey = getObeyValue(obeyCheckedID);
        String way = getWayValue(wayCheckedID);
        String correct = getCorrectionValue(correctionCheckedID);

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("depart",mSelectDepart);
        cv.put("role",mSelectRole);
        cv.put("name",name);
        cv.put("nbr",nbr);
        cv.put("occasion",occasion);
        cv.put("obey",obey);
        cv.put("way",way);
        cv.put("right",correct);
        cv.put("date",new Date(System.currentTimeMillis()).getTime());
        db.insert("Record", null , cv);
        Logger.i("new Date(System.currentTimeMillis()).getTime()"+new Date(System.currentTimeMillis()).getTime());
        Logger.i("date is :"+ DateUtils.getDateString(new Date(System.currentTimeMillis())));
        // Toast.makeText(getActivity(), "" + mSelectDepart + mSelectRole + name + nbr + occasion + correct + way + obey,Toast.LENGTH_LONG).show();
        Intent i = new Intent(getActivity(), ManualRecordListActivity.class);
        startActivity(i);
    }

    /**
     * 判断是否依从
     * @param checkedId 当前选择的RadioButton的ID
     */
    private void judgeIfObey(int checkedId) {
        switch (checkedId){
            case R.id.rb_manual_obey_ok://依从
                isObey = true;
                break;
            case R.id.rb_manual_obey_no_ok://不依从
                isObey = false;
                break;
        }

        changeUIAccordingIfObey();
    }

    /**
     * 根据选择的依从情况看看是否需要显示下面的
     */
    private void changeUIAccordingIfObey() {

        if(isObey){
            //mLlCorrectionContainer.setVisibility(View.VISIBLE);
            //mLlWayContainner.setVisibility(View.VISIBLE);
            mLlCorrectionContainer.startAnimation(mShowAction);
            mLlWayContainner.startAnimation(mShowAction);
        }else{
            mLlCorrectionContainer.startAnimation(mHiddenAction);
            mLlWayContainner.startAnimation(mHiddenAction);
        }
    }

    /**
     * 根据传过来的洗手时机控件ID判断选择的值
     * @param id radiobtn的ID值
     */
    private String getOccasionValue(int id){
        String vString = "";
        switch (id){
            case R.id.rb_manual_AfterContactPatient:
                vString = "接触患者后";
                break;
            case R.id.rb_manual_AfterContactPatientFluid:
                vString = "接触患者体液后";
                break;
            case R.id.rb_manual_beforeAtepicOpt:
                vString = "无菌操作前";
                break;
            case R.id.rb_manual_beforeContactPatient:
                vString = "接触患者前";
                break;
            case R.id.rb_manual_beforeContactPatientEnv:
                vString = "接触环境前";
                break;
        }
        //Toast.makeText(getActivity(),vString,Toast.LENGTH_SHORT).show();
        return vString;
    }
    /**
     * 根据传过来的洗手方式控件ID判断选择的值
     * @param id radiobtn的ID值
     */
    private String getWayValue(int id){
        String vString = "";
        switch (id){
            case R.id.rb_manual_way_water:
                vString = "流水洗手卫生";
                break;
            case R.id.rb_manual_way_noWater84:
                vString = "手消液手卫生";
                break;
        }
        //Toast.makeText(getActivity(),vString,Toast.LENGTH_SHORT).show();
        return vString;
    }
    /**
     * 根据传过来的洗手正确性控件ID判断选择的值
     * @param id radiobtn的ID值
     */
    private String  getCorrectionValue(int id){
        String vString = "";
        switch (id){
            case R.id.rb_manual_wash_right:
                vString = "完全正确";
                break;
            case R.id.rb_manual_wash_wrong:
                vString = "不合规范";
                break;
        }
        //Toast.makeText(getActivity(),vString,Toast.LENGTH_SHORT).show();
        return vString;
    }
    /**
     * 根据传过来的是否依从控件ID判断选择的值
     * @param id radiobtn的ID值
     */
    private String getObeyValue(int id){
        String vString = "";
        switch (id){
            case R.id.rb_manual_obey_ok:
                vString = "依从";
                break;
            case R.id.rb_manual_obey_no_ok:
                vString = "未依从";
                break;
        }
       // Toast.makeText(getActivity(),vString,Toast.LENGTH_SHORT).show();
        return vString;
    }
}
