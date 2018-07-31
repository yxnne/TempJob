package com.iel.swsapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iel.swsapp.R;
import com.iel.swsapp.business.EventCheckBiz;
import com.iel.swsapp.entity.EventItem;
import com.iel.swsapp.utils.StaticClass;
import com.iel.swsapp.utils.testdata.LoginFakeDataGenerator;
import com.iel.swsapp.view.MyLoadMoreListView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Class Full Name  : com.iel.swsapp.fragment.MainInfoFragment
 * Author Name      : yxnne
 * Create Time      : 2017/2/8
 * Project Name     : SWSAPP
 * Descriptions     : 实时事件的列表
 */

public class RealtimeEventFragment extends Fragment {
    private RelativeLayout mRlNoData;

    //下拉刷新和上拉加载 PtrLayout
    private PtrClassicFrameLayout mPtr;
    private MyLoadMoreListView mLvEventNow;

    //private ProgressBar mPbLoading;
    private RealEventListAdapter mAdapter;

    private List<EventItem> mEventList;
    private EventCheckBiz mBiz;

    //Handler
    private Handler mainHandler= new Handler();
    private Runnable mTimerTask = new Runnable() {
        @Override
        public void run() {
            //定时查询的任务

            getDatas();

            mainHandler.postDelayed(this, 2000);
        }
    };

    private void getDatas() {
        if(mBiz == null){
            mBiz = new EventCheckBiz();

        }
        //mBiz.getHistoryList()

        mBiz.getTodayList(getActivity(),"1");
    }

    private MyBroadCast mBroadcastReciever;

    @Override
    public void onResume() {

        //开启定时任务
        mainHandler.postDelayed(mTimerTask, 2000);//每两秒执行一次runnable.

        super.onResume();
        mBroadcastReciever = new MyBroadCast();
        IntentFilter intentFilter = new IntentFilter();
        // add Actions to Filter
        intentFilter.addAction(StaticClass.ACTION_GET_REAL_EVENT);
        getActivity().registerReceiver(mBroadcastReciever,intentFilter);

    }



    @Override
    public void onStop() {
        super.onStop();

        //关闭定时任务
        mainHandler.removeCallbacks(mTimerTask);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_realtime_event, null);

        //
        mRlNoData = (RelativeLayout) view.findViewById(R.id.rl_real_event_nodata_info);
        //得到数据
        mEventList = new ArrayList<>();

        //new EventCheckBiz().getRealList();

        mLvEventNow = (MyLoadMoreListView) view.findViewById(R.id.lv_event_now);
        mAdapter = new RealEventListAdapter();
        mLvEventNow.setAdapter(mAdapter);

        mPtr = (PtrClassicFrameLayout) view.findViewById(R.id.ptframe_history_event);

        mPtr.disableWhenHorizontalMove(true);
        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtr.refreshComplete();
                        //getListViewData();
                        getDatas();
                        Toast.makeText(getActivity(), "update", Toast.LENGTH_SHORT).show();
                    }
                }, 500);
            }
        });
        return view;
    }

    /**
     * ListView 的适配器
     */
    class RealEventListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mEventList.size();
        }

        @Override
        public Object getItem(int position) {
            return mEventList.get(position);
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
                convertView = View.inflate(getActivity(), R.layout.item_list_event_body, null);

                //获得holder
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name_item_list_event);
                viewHolder.tv_event = (TextView) convertView.findViewById(R.id.tv_event_item_list_event);
                viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time_item_list_event);

                viewHolder.rl1 = (RelativeLayout) convertView.findViewById(R.id.rl_item_event_1);
                viewHolder.rl2 = (RelativeLayout) convertView.findViewById(R.id.rl_item_event_2);
                viewHolder.rl3 = (RelativeLayout) convertView.findViewById(R.id.rl_item_event_3);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_name.setText(mEventList.get(position).getRoleName());
            viewHolder.tv_event.setText(mEventList.get(position).getEvent());
            viewHolder.tv_time.setText(mEventList.get(position).getTime());

            if ((position % 2) == 0) {
                viewHolder.rl1.setBackgroundColor(ContextCompat.getColor(
                        getActivity(), (R.color.color_bk_table_even)));

                viewHolder.rl2.setBackgroundColor(ContextCompat.getColor(
                        getActivity(), (R.color.color_bk_table_even)));

                viewHolder.rl3.setBackgroundColor(ContextCompat.getColor(
                        getActivity(), (R.color.color_bk_table_even)));
            }else{
                viewHolder.rl1.setBackgroundColor(ContextCompat.getColor(
                        getActivity(), (R.color.color_bk_table_odd)));

                viewHolder.rl2.setBackgroundColor(ContextCompat.getColor(
                        getActivity(), (R.color.color_bk_table_odd)));

                viewHolder.rl3.setBackgroundColor(ContextCompat.getColor(
                        getActivity(), (R.color.color_bk_table_odd)));
            }

            return convertView;
        }

        class ViewHolder {
            TextView tv_name;
            TextView tv_event;
            TextView tv_time;

            RelativeLayout rl1;
            RelativeLayout rl2;
            RelativeLayout rl3;

        }
    }

    /**
     * 今日实时事件的广播接受器
     */
    private class MyBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getExtras();
            EventCheckBiz.HisDatasResponse response = (EventCheckBiz.HisDatasResponse) bundle.getSerializable(EventCheckBiz.RESULT_REAL);

            //calcNowPage(response.getNowPage());
            if(response.getDatas().size() == 0){
                //没有数据
                //Toast.
                mRlNoData.setVisibility(View.VISIBLE);

            }else{
                mRlNoData.setVisibility(View.GONE);
                List<EventItem> lists = response.getDatas();
                mEventList = new ArrayList<>();
                for(EventItem item:lists){
                    mEventList.add(item);

                }

                updateListView();
            }



        }
    }

    private void updateListView() {
        mAdapter.notifyDataSetChanged();
        mLvEventNow.setLoadCompleted();

    }


}
