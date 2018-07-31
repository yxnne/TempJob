package com.iel.swsapp.activity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.iel.swsapp.R;
import com.iel.swsapp.db.MyWatchDBLogicUtils;
import com.iel.swsapp.db.MyWatchStatisticDatabaseHelper;
import com.iel.swsapp.db.OneRecord;
import com.iel.swsapp.utils.DateUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ManualRecordListActivity extends AppCompatActivity {

    private ListView mLvRocordList ;
    private MyWatchStatisticDatabaseHelper mDBHelper;
    private List<OneRecord> mRecordData = new ArrayList<>();
    private ManualRecordListAdapter mAdapter;
    private TextView mTvActionBar;
    private RelativeLayout mRlBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_record_list);

        // find db records
        getRecordsFromDb();
        // findviews
        mRlBack = (RelativeLayout) findViewById(R.id.rl_myactionbar_back);
        mRlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTvActionBar = (TextView) findViewById(R.id.tv_myactionbar_tittle);
        mTvActionBar.setText("人工记录结果");

        mLvRocordList = (ListView) findViewById(R.id.lv_manual_record_list);
        mAdapter = new ManualRecordListAdapter();
        mLvRocordList.setAdapter(mAdapter);


        mAdapter.notifyDataSetChanged();


    }

    public void getRecordsFromDb() {
        mRecordData = MyWatchDBLogicUtils.getAllRecords(this);
    }


    /**
     * inner adapter class for listview
     */
    class ManualRecordListAdapter extends BaseAdapter{

        class ViewHolder {
            TextView tvName ;
            TextView tvDepartRole ;
            TextView tvTime ;
            TextView tvWay;
            TextView tvOccasion ;
            TextView tvCorrect ;
        }

        @Override
        public int getCount() {
            return mRecordData.size();
        }

        @Override
        public Object getItem(int position) {
            return mRecordData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if (convertView == null ){
                viewHolder = new ViewHolder();
                convertView = View.inflate(ManualRecordListActivity.this, R.layout.item_list_manual_record, null);
                viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_item_manual_record_name);
                viewHolder.tvDepartRole = (TextView) convertView.findViewById(R.id.tv_item_manual_record_depart_role);
                viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_item_manual_record_time);
                viewHolder.tvWay = (TextView) convertView.findViewById(R.id.tv_item_manual_record_way);
                viewHolder.tvOccasion = (TextView) convertView.findViewById(R.id.tv_item_manual_record_occasion);
                viewHolder.tvCorrect = (TextView) convertView.findViewById(R.id.tv_item_manual_record_correct);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tvName.setText(mRecordData.get(position).name);
            viewHolder.tvDepartRole.setText(mRecordData.get(position).depart + ", " + mRecordData.get(position).role );
            Date time = new Date( mRecordData.get(position).time);

            viewHolder.tvTime.setText(DateUtils.getDateString(time));
            viewHolder.tvWay.setText(mRecordData.get(position).way);
            viewHolder.tvOccasion.setText(mRecordData.get(position).occasion);
            viewHolder.tvCorrect.setText(mRecordData.get(position).correct);

            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final int id = mRecordData.get(position).id;
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ManualRecordListActivity.this);
                    builder.setTitle("确认消息")
                    .setMessage("您确定要删除此条记录吗？")
                    .setCancelable(true)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (MyWatchDBLogicUtils.deleteRecordById(ManualRecordListActivity.this, id)) {
                                Toast.makeText(ManualRecordListActivity.this, "记录已被删除", Toast.LENGTH_SHORT).show();
                                getRecordsFromDb();
                                mAdapter.notifyDataSetChanged();

                            }
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
                    return true;
                }
            });

            return convertView;
        }
    }

}


