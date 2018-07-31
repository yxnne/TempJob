package com.iel.swsapp.fragment;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.iel.swsapp.R;
import com.iel.swsapp.business.EventCheckBiz;
import com.iel.swsapp.entity.EventItem;
import com.iel.swsapp.utils.AnimatorUtil;
import com.iel.swsapp.utils.StaticClass;
import com.iel.swsapp.view.MyLoadMoreListView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class HistoryEventFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    /**控件*/
    //Spinner
    private Spinner mSpinnerEventType;
    private Spinner mSpinnerRoleType;
    //Button
    private Button mBtnCheck;
    private Button mBtnChooseTime;
    //FloatingActionButton
    private FloatingActionButton mFabToCheck;
    //PtrLayout
    private PtrClassicFrameLayout mPtr;
    //ListView
    private MyLoadMoreListView mLvHistoryEvent;
    private HistoryEventAdapter mAdpter;
    //Layout
    private LinearLayout mLlTableHeader;
    private LinearLayout mLlEventCondition;
    //private RelativeLayout mRlSearchSection;
    //ProgressBar
    private ProgressBar mPbLoadding;

    //事件查询的集合
    private List<EventItem> mItemList = new ArrayList<>();

    private static final int WHAT_NO_DATA_TO_UPDATE = 5001;

    //Handler
    private Handler mainHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case WHAT_NO_DATA_TO_UPDATE://提示，当前可更新的没有数据了
                    Toast.makeText(getActivity(),
                            getString(R.string.info_no_new_data),Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    //参数属性:时间范围
    private String mStrTimeStart;
    private String mStrTimeEnd;
    private String mStrEventType = "all";
    private int mPageNbr = 1;
    private boolean needLoad = true;

    //广播
    private BroadcastReceiver mBroadcastReciever;

    @Override
    public void onResume() {
        super.onResume();

        mBroadcastReciever = new MyBroadCast();
        IntentFilter intentFilter = new IntentFilter();
        // add Actions to Filter
        intentFilter.addAction(StaticClass.ACTION_GET_HIS_EVENT);
        getActivity().registerReceiver(mBroadcastReciever,intentFilter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mBroadcastReciever != null){
            getActivity().unregisterReceiver(mBroadcastReciever);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_event,null);

        mPbLoadding = (ProgressBar) view.findViewById(R.id.pb_loading_history_event);

        mFabToCheck = (FloatingActionButton) view.findViewById(R.id.fab_to_check_condotion);
        mFabToCheck.setOnClickListener(this);

        mSpinnerEventType = (Spinner) view.findViewById(R.id.spinner_event_type_his);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.envent_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerEventType.setAdapter(adapter);
        mSpinnerEventType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                checkEventType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerRoleType = (Spinner) view.findViewById(R.id.spinner_role_type);

        mBtnCheck = (Button) view.findViewById(R.id.btn_check_his_event);
        mBtnCheck.setOnClickListener(this);

        mBtnChooseTime = (Button) view.findViewById(R.id.btn_choose_his_event_time);
        mBtnChooseTime.setOnClickListener(this);

        mLvHistoryEvent = (MyLoadMoreListView) view.findViewById(R.id.mlv_history_event);
        mLvHistoryEvent.setOnLoadMoreListener(new MyLoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onloadMore() {
                mainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EventCheckBiz biz = new EventCheckBiz();
                        //如果需要加载的话，就进行数据请求
                        if(needLoad){
                            biz.getHisList(getActivity(),mStrTimeStart,
                                    mStrTimeEnd,mStrEventType,mPageNbr+"");
                        }else{
                            //提示，没有新数据了
                            mainHandler.sendEmptyMessage(WHAT_NO_DATA_TO_UPDATE);
                        }

                    }
                },1000);

            }
        });


        mPtr = (PtrClassicFrameLayout) view.findViewById(R.id.ptframe_history_event);

        mLlTableHeader = (LinearLayout) view.findViewById(R.id.ll_history_event_table_head);
        mLlEventCondition = (LinearLayout) view.findViewById(R.id.ll_event_check_condition);
        //mRlSearchSection = (RelativeLayout) view.findViewById(R.id.rl_check_section);
        //mRlSearchSection.setOnClickListener(this);

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

        // 设置刷新完成
        mPtr.disableWhenHorizontalMove(true);

        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtr.refreshComplete();
                        getListViewData();
                        Toast.makeText(getActivity(), "update", Toast.LENGTH_SHORT).show();
                    }
                }, 1800);
            }

        });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_check_his_event://点击查看按钮后
                changeUI(true);
                getListViewData();
                break;
            case R.id.fab_to_check_condotion://点击浮动按钮后
                changeUI(false);
                break;
            case R.id.btn_choose_his_event_time://时间选择
                chooseDateRange();
                break;
        }
    }



    /**
     * 处理时间选择
     */
    private void chooseDateRange() {
        //Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                HistoryEventFragment.this,
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
     * 得到listview的数据
     */
    private void getListViewData() {

        if(mItemList!= null){
            mItemList.clear();
        }
        if(mAdpter == null){
            mAdpter = new HistoryEventAdapter();
            mLvHistoryEvent.setAdapter(mAdpter);

        }

        EventCheckBiz biz = new EventCheckBiz();
        biz.getHisList(getActivity(),mStrTimeStart,
                      mStrTimeEnd,mStrEventType, "1");//从第一页开始查

        //mItemList.removeAll();
        //mItemList = biz.getHisList(mStrTimeStart,
         //       mStrTimeEnd,mStrEventType,mPageNbr);


    }

    private void checkEventType(int position) {
        switch (position){
            case 0:
                mStrEventType = "all";
                break;
            case 1:
                mStrEventType = "0003";
                break;
            case 2:
                mStrEventType = "0007";
                break;
            case 3:
                mStrEventType = "0008";
                break;
            case 4:
                mStrEventType = "0018";
                break;
            case 5:
                mStrEventType = "0103";
                break;
            case 6:
                mStrEventType = "0110";
                break;


        }

    }

    private void changeUI(boolean pressChecked) {
        if(pressChecked){
            //显示表头
            mLlTableHeader.setVisibility(View.VISIBLE);
            //隐藏具体条件框
            mLlEventCondition.setVisibility(View.GONE);
            //显示浮动小按钮
            mFabToCheck.setVisibility(View.VISIBLE);
            //显示进度条
            mPbLoadding.setVisibility(View.VISIBLE);
            //暂时隐藏ListView
            mLvHistoryEvent.setVisibility(View.GONE);
            //模拟耗时操作，延时一下
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mPbLoadding.setVisibility(View.GONE);
                            mLvHistoryEvent.setVisibility(View.VISIBLE);
                            //

                        }
                    });
                }
            }
            ).start();


        }else{

            //隐藏具体条件框
            mLlEventCondition.setVisibility(View.VISIBLE);
            //显示小查询触发键
            mFabToCheck.setVisibility(View.GONE);
        }
    }

    /**
     * 本Fragment实现了选择时间对话框的回调接口
     * @param view 时间选择对话框引用
     * @param year 开始:年
     * @param monthOfYear 开始:月
     * @param dayOfMonth 开始:日
     * @param yearEnd 结束:年
     * @param monthOfYearEnd 结束:月
     * @param dayOfMonthEnd 结束:日
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
        sb.append("     ");
        sb.append(getString(R.string.text_to));
        sb.append("     ");
        sb.append(mStrTimeEnd);
        mBtnChooseTime.setText(sb.toString());
    }

    /**
     * 设置成员变量值
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


    /**
     * ListView的适配器
     */
    class HistoryEventAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mItemList.size();
        }

        @Override
        public Object getItem(int position) {
            return mItemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if (convertView == null) {

                viewHolder = new ViewHolder();
                convertView = View.inflate(getActivity(), R.layout.item_list_history_event_body, null);

                //获得holder
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name_item_list_event);
                viewHolder.tv_event = (TextView) convertView.findViewById(R.id.tv_event_item_list_event);
                viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time_item_list_event);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tv_name.setText(mItemList.get(position).getRoleName());
            viewHolder.tv_event.setText(mItemList.get(position).getEvent());
            viewHolder.tv_time.setText(mItemList.get(position).getTime());

            return convertView;
        }


        class ViewHolder {
            TextView tv_name;
            TextView tv_event;
            TextView tv_time;

        }
    }

    /**
     * 历史事件的广播接受器
     */
    private class MyBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getExtras();
            EventCheckBiz.HisDatasResponse response = (EventCheckBiz.HisDatasResponse) bundle.getSerializable(EventCheckBiz.RESULT_HIS);

            if(response.getDatas().size() == 0){//所查没有数据
                Toast.makeText(getActivity(),
                        getString(R.string.info_no_check_data),Toast.LENGTH_SHORT).show();
                return;
            }

            calcNowPage(response.getNowPage(),response.getTotalPage());

            if(mItemList.size() == 0){
                mItemList = response.getDatas();
            }else{
                mItemList.addAll(response.getDatas());
            }

            updateListView();


        }
    }

    /**
     * 数据集发生了变化 ，更新listview了
     */
    private void updateListView() {
        mAdpter.notifyDataSetChanged();
        mLvHistoryEvent.setLoadCompleted();
    }

    /**
     * 计算下下次请求时怎么弄页数
     * @param nowPage
     */
    private void calcNowPage(int nowPage,int totalPage) {
        if(nowPage == totalPage){
            needLoad = false;
        }else{
            mPageNbr = nowPage;
            mPageNbr ++ ;
        }

    }
}
