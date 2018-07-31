package com.iel.swsapp.activity;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.iel.swsapp.R;
import com.iel.swsapp.view.KeyRadioGroupV1;
import com.iel.swsapp.view.KeyRadioGroupV2;

/**
 * 10-17 2017 by yxnne
 * 去了一趟郑州 响应薛总要求，增加手动统计模块
 * 要求检查人在APP中填写手卫生人的姓名/工号/角色（从后台获得）
 * 然后选择洗手类型/洗手时机/正确性并提交数据到后台
 * PS:后台阿贵在做
 */
public class ManualFormActivity extends AppCompatActivity {

    private EditText mEtName,mEtNbr;
    private Spinner mSpinnerRole,mSpinnerDepart;
    //private RadioGroup mRgOccasion,mRgWay,mRgCorrection;
    private KeyRadioGroupV2  mKrgOccasion;
    private KeyRadioGroupV1  mKrgWay,mKrgCorrection;
    private Button mBtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_form);
        //TODO
        //1.得到当前系统的人员角色信息，并且显示在Spinner上

        //2.得到系统的部门信息，如果当前用户是admin，显示到spinner上
        //如果不是admin，显示当前部门到Spinner中，Spinner设置为不可变并且消息提示
        findViews();

    }

    /**
     * 找到控件
     */
    private void findViews() {
        //名字和工号
        mEtName = (EditText) findViewById(R.id.et_manual_beObservedName);
        mEtNbr = (EditText) findViewById(R.id.et_manual_beObservedNumber);
        //角色和部门
        mSpinnerDepart = (Spinner) findViewById(R.id.spinner_manual_depart);
        mSpinnerRole = (Spinner) findViewById(R.id.spinner_manual_role);
        //洗手时机
        mKrgOccasion = (KeyRadioGroupV2) findViewById(R.id.rg_manual_occasion);
        //洗手方式
        mKrgWay = (KeyRadioGroupV1) findViewById(R.id.rg_manual_way);
        //洗手正确性
        mKrgCorrection = (KeyRadioGroupV1) findViewById(R.id.rg_manual_correction);

//        mKrgOccasion.setOnCheckedChangeListener(new KeyRadioGroupV2.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(KeyRadioGroupV2 group, @IdRes int checkedId) {
//                Toast.makeText(ManualFormActivity.this,checkedId + "",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        mKrgOccasion.getCheckedRadioButtonId();

        //提交表单 按钮
        mBtnSubmit = (Button) findViewById(R.id.btn_submit_manual_form);
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
            //得到RadioGroup中选择到的ID
            int occasionCheckedID = mKrgOccasion.getCheckedRadioButtonId();
            int wayCheckedID = mKrgWay.getCheckedRadioButtonId();
            int correctionCheckedID = mKrgCorrection.getCheckedRadioButtonId();
            getOccasionValue(occasionCheckedID);
            getWayValue(wayCheckedID);
            getCorrectionValue(correctionCheckedID);

        }});
    }

    /**
     * 根据传过来的洗手时机控件ID判断选择的值
     * @param id radiobtn的ID值
     */
    private void getOccasionValue(int id){
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
        Toast.makeText(this,vString,Toast.LENGTH_SHORT).show();
    }
    /**
     * 根据传过来的洗手方式控件ID判断选择的值
     * @param id radiobtn的ID值
     */
    private void getWayValue(int id){
        String vString = "";
        switch (id){
            case R.id.rb_manual_way_water:
                vString = "流水洗手卫生";
                break;
            case R.id.rb_manual_way_noWater84:
                vString = "手消液手卫生";
                break;
        }
        Toast.makeText(this,vString,Toast.LENGTH_SHORT).show();
    }
    /**
     * 根据传过来的洗手正确性控件ID判断选择的值
     * @param id radiobtn的ID值
     */
    private void getCorrectionValue(int id){
        String vString = "";
        switch (id){
            case R.id.rb_manual_wash_right:
                vString = "完全正确";
                break;
            case R.id.rb_manual_wash_wrong:
                vString = "不合规范";
                break;
        }
        Toast.makeText(this,vString,Toast.LENGTH_SHORT).show();
    }
}
