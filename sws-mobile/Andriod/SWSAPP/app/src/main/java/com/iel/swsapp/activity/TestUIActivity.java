package com.iel.swsapp.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.github.mikephil.charting.charts.LineChart;

import com.iel.swsapp.R;
import com.iel.swsapp.view.MyLoadMoreListView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
/**
 * Class Full Name  : com.iel.swsapp.activity.SplashActivity
 * Author Name      : yxnne
 * Create Time      : 2017/2
 * Project Name     : SWSAPP
 * Descriptions     : 测试一些 UI效果 ，实际不参与代码逻辑
 */
public class TestUIActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private DatePicker mDatePicker;
    private LineChart mLineChart;
    private MyLoadMoreListView mMyLoadMoreListView;
    private MyAdap adpter;
    private List<String > mListData = new ArrayList<>(Arrays.asList(
            "987654","123456","而热热 f","想抽你想不抽你","dfs","56trgfs",
            "演的","地方","个 地方我 f","地方","d不错fs","的",
            "，吗VB","想从v","一个法国 f","666666","婆婆来了","324的反对党",
            "自主学习","查询","成都市的","去去去","的","从"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ui);

        mMyLoadMoreListView = (MyLoadMoreListView) findViewById(R.id.myLoaderListView);
        adpter = new MyAdap();
        mMyLoadMoreListView.setAdapter(adpter);
        mMyLoadMoreListView.setOnLoadMoreListener(new MyLoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onloadMore() {
                loadMore();
            }
        });
        // 下拉刷新框架的引用
        final PtrClassicFrameLayout ptr =
                (PtrClassicFrameLayout) findViewById(R.id.ptframe_test);

        //ptr.setHeaderView();
        // 设置刷新完成
        ptr.disableWhenHorizontalMove(true);

        ptr.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptr.refreshComplete();
                        Calendar now = Calendar.getInstance();
                        DatePickerDialog dpd = DatePickerDialog.newInstance(
                                TestUIActivity.this,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH)
                        );
                        dpd.setAutoHighlight(false);
                        dpd.setStartTitle("开始时间");
                        dpd.setEndTitle("截止时间");
                        dpd.setAccentColor(ContextCompat.getColor( TestUIActivity.this,R.color.colorPrimaryDark));
                        dpd.setMaxDate(now);
                        dpd.show(getFragmentManager(), "Datepickerdialog");
                    }
                }, 1800);
            }

        });

    }

    private void loadMore() {

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mListData.add("+++---***");
                mListData.add("////---");
                mListData.add("66666666");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adpter.notifyDataSetChanged();
                        mMyLoadMoreListView.setLoadCompleted();
                    }
                });
            }
        }.start();

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth,
                          int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {

    }

    class MyAdap extends BaseAdapter{

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
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(TestUIActivity.this,R.layout.item_list_history_event_body, null);
            TextView tv = (TextView) convertView.findViewById(R.id.tv_name_item_list_event);
            tv.setText(mListData.get(position));

            return convertView;
        }
    }

}
