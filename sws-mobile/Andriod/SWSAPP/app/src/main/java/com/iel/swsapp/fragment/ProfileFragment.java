package com.iel.swsapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iel.swsapp.R;
import com.iel.swsapp.activity.PersonalRateActivity;
import com.iel.swsapp.business.ProfileBiz;
import com.iel.swsapp.entity.ProfileInfo;

import java.util.ArrayList;

/**
 * Class Full Name  : com.iel.swsapp.fragment.MainInfoFragment
 * Author Name      : yxnne
 * Create Time      : 2017/2/8
 * Project Name     : SWS APP
 * Descriptions     : 首页的第一页fragment
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView mTvUserName;
    private TextView mTvUserComplianceRate;
    private TextView mTvUserDepartmentRank;

    private ListView mLvInfo;

    private ArrayList<String> mListInfoItem;

    private ProfileInfo mProfileInfo;

    private LinearLayout mLlLeft,mLlRight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,null);
        //find views
        mLlLeft = (LinearLayout) view.findViewById(R.id.ll_profile_compliance_rate);
        mLlRight = (LinearLayout) view.findViewById(R.id.ll_profile_department_rank);

        //初始化列表项
        mListInfoItem = new ArrayList<>();
        mListInfoItem.add(getString(R.string.text_department));
        mListInfoItem.add(getString(R.string.text_role));
        mListInfoItem.add(getString(R.string.text_card));
        mListInfoItem.add(getString(R.string.text_phone));
        //得到数据
        mProfileInfo = new ProfileBiz().getCurrentUserProfile();
        if(! ProfileBiz.STR_NO_RFID.equals(mProfileInfo.getCardNo())){
            //这里不为空的时候说明，系统上该用户关联了rfid的
            //所以根据这里根据rfid再查下其他信息

        }

        mTvUserName = (TextView) view.findViewById(R.id.tv_profile_username);
        mTvUserComplianceRate = (TextView) view.findViewById(R.id.tv_profile_compliance_rate);
        mTvUserDepartmentRank = (TextView) view.findViewById(R.id.tv_profile_departmant_rank);
        //mTvUserCardNo = view.findViewById(R.)
        mTvUserName.setText(mProfileInfo.getUserName());
        mTvUserComplianceRate.setText(mProfileInfo.getComplianceRate());
        mTvUserDepartmentRank.setText(mProfileInfo.getDepartRank());

        mLvInfo = (ListView) view.findViewById(R.id.lv_profile_info);
        mLvInfo.setAdapter(new ProfileInfoAdapter());
        //如果该员工的卡片信息为空
        if("no_card".equals(mProfileInfo.getCardNo())){
            View.OnClickListener tempListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),
                            getString(R.string.info_user_config_no_card),
                            Toast.LENGTH_SHORT).show();
                }
            };
            mLlLeft.setOnClickListener(tempListener);
            mLlRight.setOnClickListener(tempListener);

        }else{
            mLlLeft.setOnClickListener(this);
            mLlRight.setOnClickListener(this);
        }


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_profile_department_rank:

            case R.id.ll_profile_compliance_rate:
                Intent intent = new Intent(getActivity(), PersonalRateActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 用于listView的适配器
     */
    class ProfileInfoAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mListInfoItem.size();
        }

        @Override
        public Object getItem(int position) {
            return mListInfoItem.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(getActivity(),R.layout.item_list_profile_info,null);
            ImageView ivItemIcon = (ImageView) convertView.findViewById(R.id.iv_item_profile_icon);
            TextView tvItemName = (TextView) convertView.findViewById(R.id.tv_item_profile_item_name);
            TextView tvItemValue = (TextView) convertView.findViewById(R.id.tv_item_profile_item_value);
            switch (position){
                case 0:
                    ivItemIcon.setImageResource(R.drawable.icon_profile_depart);
                    tvItemValue.setText(mProfileInfo.getDepartment());
                    break;
                case 1:
                    ivItemIcon.setImageResource(R.drawable.icon_profile_role);
                    tvItemValue.setText(mProfileInfo.getJob());
                    break;
                case 2:
                    ivItemIcon.setImageResource(R.drawable.icon_profile_card);
                    tvItemValue.setText(mProfileInfo.getCardNo());
                    break;
                case 3:
                    ivItemIcon.setImageResource(R.drawable.icon_profile_phone);
                    tvItemValue.setText(mProfileInfo.getPhone());
                    break;
            }
            tvItemName.setText(mListInfoItem.get(position));

            return convertView;
        }
    }

}
