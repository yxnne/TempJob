package com.iel.swsapp.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iel.swsapp.R;
import com.iel.swsapp.business.DeviceWarningBiz;

import java.util.List;
/**
 * Class Full Name  : com.iel.swsapp.activity.DeviceWarnningActivity
 * Author Name      : yxnne
 * Create Time      : 2017/3
 * Project Name     : SWSAPP
 * Descriptions     : 设备预警和信息
 */
public class DeviceWarnningActivity extends ActionbarBackBaseActivity {

    private ListView mLvWarnning;
    private List<DeviceWarningBiz.DeviceWarningInfo> mListWaringData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_warnning);

        //设置自己的ActionBar
        setMyActionBar(R.id.rl_myactionbar_back,
                R.id.iv_myactionbar_menu,
                R.id.tv_myactionbar_tittle,
                true,getString(R.string.text_device_warnnig_detail_info));


        //getDatas
        mListWaringData = new DeviceWarningBiz().getDeviceWainningList();
        //findView
        mLvWarnning = (ListView) findViewById(R.id.lv_devices_warning_detail);

        mLvWarnning.setAdapter(new MyWarnningInfoAdapter());
    }
    class MyWarnningInfoAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mListWaringData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListWaringData.get(position);
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
                convertView = View.inflate(DeviceWarnningActivity.this,
                        R.layout.item_list_device_warnning_info, null);

                TextView tvType = (TextView) convertView.findViewById(R.id.tv_device_warning_item_type);
                TextView tvNo= (TextView) convertView.findViewById(R.id.tv_device_warning_item_no);
                TextView tvStaff = (TextView) convertView.findViewById(R.id.tv_device_warning_item_staff);
                RelativeLayout rlSend = (RelativeLayout) convertView.findViewById(R.id.rl_send_msg);

                viewHolder.tv_type = tvType;
                viewHolder.tv_no = tvNo;
                viewHolder.tv_staff = tvStaff;
                viewHolder.rl_send_msg = rlSend;

                convertView.setTag(viewHolder);

            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_type.setText(mListWaringData.get(position).getDeviceType());
            viewHolder.tv_no.setText(mListWaringData.get(position).getDeviceNo());
            viewHolder.tv_staff.setText(mListWaringData.get(position).getDeviceStaff());
            viewHolder.rl_send_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DeviceWarnningActivity.this,
                            "send msg to  :"+mListWaringData.get(position).getDeviceStaff(),
                            Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }


        class ViewHolder{

            TextView tv_type;
            TextView tv_no;
            TextView tv_staff;
            RelativeLayout rl_send_msg;
        }



    }




}
