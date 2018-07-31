package com.iel.swsapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.iel.swsapp.R;
import com.iel.swsapp.activity.DepartRateActivity;
import com.iel.swsapp.activity.PersonalRateActivity;
import com.iel.swsapp.business.StaffRateBiz;
import com.iel.swsapp.utils.StaticClass;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Class Full Name  : com.iel.swsapp.fragment.MainInfoFragment
 * Author Name      : yxnne
 * Create Time      : 2017/2/8
 * Project Name     : SWSAPP
 * Descriptions     : 部门人员依从率  Fragment
 */

/**
 * {"page":{"first":0,"pageNo":1,"pageSize":30,"result":[{"departName":"演示间","docName":"11","docType":"医生","errorNum":5,"normalNum":7,"rate":"58.3","rfid":"000001"},{"departName":"演示间","docName":"小二","docType":"护士","errorNum":8,"normalNum":26,"rate":"76.5","rfid":"000003"},{"departName":"演示间","docName":"4号胸牌","docType":"医生","errorNum":88,"normalNum":35,"rate":"28.5","rfid":"000004"},{"departName":"演示间","docName":"5号胸牌","docType":"医生","errorNum":8,"normalNum":5,"rate":"38.5","rfid":"000005"}],"total":4,"totalPage":1}}
 */
public class StaffRateFragment extends Fragment implements DepartRateActivity.onChooseSelectedListner{

    private static final String KEY_LISTNER_STAFF = "StaffRateFragment" ;
    private RadioButton mRbtnToday,mRbtnRecent30;

    private PtrFrameLayout mPtr;
    private ListView mLvRateStaff;
    private StaffRateAdapter mAdapter;

    private List<StaffRateBiz.StaffRateInfoItem> mListData = new ArrayList<>();

    private ProgressBar mPbLoadingStaff;

    private StaffRateBiz mBiz;

    private MyBroadCast mMyBroadCast;


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            mPbLoadingStaff.setVisibility(View.GONE);
            mLvRateStaff.setVisibility(View.VISIBLE);
            super.handleMessage(msg);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mMyBroadCast = new MyBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(StaticClass.ACTION_GET_STAFF_RATE);
        getActivity().registerReceiver(mMyBroadCast,filter);

        ((DepartRateActivity)getActivity()).setOnChooseSelectedListner(KEY_LISTNER_STAFF,this);
        getStaffData(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mMyBroadCast);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_rate,null);
        mPtr = (PtrFrameLayout) view.findViewById(R.id.ptframe_staff_rate);
        mLvRateStaff = (ListView) view.findViewById(R.id.lv_rate_staff);
        mPbLoadingStaff = (ProgressBar) view.findViewById(R.id.pb_loading_staff_rate);
        mPbLoadingStaff.setVisibility(View.GONE);

        mRbtnToday = (RadioButton) view.findViewById(R.id.rbtn_staff_today);
        mRbtnRecent30 = (RadioButton) view.findViewById(R.id.rbtn_staff_rencent30);
        mRbtnRecent30.setChecked(true);

        mRbtnToday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPbLoadingStaff.setVisibility(View.VISIBLE);
                mLvRateStaff.setVisibility(View.GONE);
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(1);

                    }
                }.start();

                if(isChecked){
                    getStaffData(true);
                }else{
                    getStaffData(false);
                }
            }
        });

        mAdapter = new StaffRateAdapter();
        mLvRateStaff.setDividerHeight(0);
        mLvRateStaff.setAdapter(mAdapter);


        //取数据
        //getStaffData(false);

        //添加ptr
        // 设置刷新完成
        mPtr.disableWhenHorizontalMove(true);

        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtr.refreshComplete();
                        getStaffData(false);
                        if(getActivity()!= null){
                            Toast.makeText(getActivity(), "update", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, 1800);
            }

        });


        return view;
    }



    /**
     * 获得数据的方法
     */
    private void getStaffData(boolean isOneDay) {
        if (mBiz == null){
            mBiz = new StaffRateBiz();
        }

        mBiz.getStaffRateInfoList(getActivity(),isOneDay);
    }

    @Override
    public void onChooseCheck() {
        if(mRbtnToday.isChecked()){
            getStaffData(true);
        }else{
            getStaffData(false);
        }

    }

    class StaffRateAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;


            if(convertView == null ){
                viewHolder = new ViewHolder();
                convertView = View.inflate(getActivity(),
                        R.layout.item_list_rate_staff, null);

                //tvNo用来写职位吧
                TextView tvNo = (TextView) convertView.findViewById(R.id.tv_item_rate_staff_no);
                TextView tvName = (TextView) convertView.findViewById(R.id.tv_item_rate_staff_name);
                TextView tvCardNo = (TextView) convertView.findViewById(R.id.tv_item_rate_staff_card_no);
                TextView tvStaff = (TextView) convertView.findViewById(R.id.tv_item_rate_staff_rate);
                LinearLayout llItem = (LinearLayout) convertView.findViewById(R.id.ll_item_staff);

                viewHolder.tv_no = tvNo;
                viewHolder.tv_name = tvName;
                viewHolder.tv_cardno = tvCardNo;
                viewHolder.tv_rate = tvStaff;
                viewHolder.ll_item = llItem;

                convertView.setTag(viewHolder);

            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_no.setText(mListData.get(position).getJobType());
            viewHolder.tv_name.setText(mListData.get(position).getName());
            viewHolder.tv_cardno.setText(mListData.get(position).getCardno());
            viewHolder.tv_rate.setText(mListData.get(position).getRate()+"%");
            viewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = PersonalRateActivity.getIntent(getActivity(),
                            mListData.get(position).getName(),
                            mListData.get(position).getJobType(),
                            mListData.get(position).getRate(),
                            mListData.get(position).getDepartment(),
                            mListData.get(position).getRfid(),
                            Integer.valueOf(mListData.get(position).getMoment0003()),
                            Integer.valueOf(mListData.get(position).getMoment0007()),
                            Integer.valueOf(mListData.get(position).getMoment0008()),
                            Integer.valueOf(mListData.get(position).getMoment0110()),
                            Integer.valueOf(mListData.get(position).getMoment0103()),
                            (float)mListData.get(position).getRateAfterCloseNick(),
                            (float)mListData.get(position).getRateBeforeCloseNick(),
                            (float)mListData.get(position).getRateBeforeAsepticOperation(),
                            (float)mListData.get(position).getRateAfterCloseNickEnvri()

                    );

                    startActivity(i);
                }
            });

            return convertView;
        }


        class ViewHolder{

            TextView tv_no;
            TextView tv_name;
            TextView tv_cardno;
            TextView tv_rate;
            LinearLayout ll_item;

        }
    }

    /**
     * 该Fragment的广播接受器
     */
    private class MyBroadCast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(mListData != null){
                mListData.clear();
            }else{
                mListData = new ArrayList<>();
            }
            Bundle bundle = intent.getExtras();
            StaffRateBiz.StaffRateData data = (StaffRateBiz.StaffRateData) bundle.getSerializable(StaffRateBiz.RESULT_STAFF);
            mListData.addAll(data.getDatas());
//
//            for(StaffRateBiz.StaffRateInfoItem item :data.getDatas()){
//                mListData.add(item);
//            }

            mAdapter.notifyDataSetChanged();

        }
    }

}
