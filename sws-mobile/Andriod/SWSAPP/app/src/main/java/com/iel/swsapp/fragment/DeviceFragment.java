package com.iel.swsapp.fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iel.swsapp.R;
import com.iel.swsapp.activity.DeviceWarnningActivity;
import com.iel.swsapp.business.DeviceWarningBiz;
import com.iel.swsapp.utils.AnimatorUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class Full Name  : com.iel.swsapp.fragment.MainInfoFragment
 * Author Name      : yxnne
 * Create Time      : 2017/2/8
 * Project Name     : SWSAPP
 * Descriptions     : 设备信息 Fragment
 */
public class DeviceFragment extends Fragment implements View.OnClickListener {

    private CardView mCardViewAp,mCardViewCard,mCardViewBed,
            mCardViewDoor,mCardViewBottle;

    private TextView mTvNbrAp,mTvLowPowerAp,
            mTvNbrCard,mTvLowPowerCard,
            mTvNbrBed,mTvLowPowerBed,
            mTvNbrDoor,mTvLowPowerDoor,
            mTvNbrBottle,mTvLowPowerBottle;

    private ImageView mIvCardState;

    private DeviceWarningBiz mDevicesWarningBiz;

    private static final int HANDLER_SHAKE = 1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 1:

                    ObjectAnimator objectAnimator = AnimatorUtil.tada(mIvCardState,1.4f);
                    objectAnimator.start();
                    break;

                case DeviceWarningBiz.HANDLER_WARNNING_DEVICES:
                    List<DeviceWarningBiz.DeviceRunningStatus> runningStatus =
                            (List<DeviceWarningBiz.DeviceRunningStatus>)msg.obj;
                    for(DeviceWarningBiz.DeviceRunningStatus item: runningStatus){
                        setDevicesWarningInfos(item);
                    }
                    break;
            }
        }
    };

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            // 需要做的事:发送消息
            Message message = new Message();
            message.what = 1;
            mHandler.sendMessage(message);
        }
    };

    private void setDevicesWarningInfos(DeviceWarningBiz.DeviceRunningStatus state){
        switch (state.getDeviceType()){
            case "门外发射器":
                setDeviceState(mTvNbrDoor,mTvLowPowerDoor,
                        getString(R.string.text_device_nbr) + state.getOkNbr(),
                        getString(R.string.text_device_low_power_nbr) + state.getBadNbr());
                break;
            case "液瓶识别器":
                setDeviceState(mTvNbrBottle,mTvLowPowerBottle,
                        getString(R.string.text_device_nbr) + state.getOkNbr(),
                        getString(R.string.text_device_low_power_nbr) + state.getBadNbr());
                break;
            case "智能胸牌":
                setDeviceState(mTvNbrCard,mTvLowPowerCard,
                        getString(R.string.text_device_nbr) + state.getOkNbr(),
                        getString(R.string.text_device_low_power_nbr) + state.getBadNbr());
                break;
            case "门内发射器":
                break;
            case "无线接入点":
                setDeviceState(mTvNbrAp,mTvLowPowerAp,
                        getString(R.string.text_device_nbr) + state.getOkNbr(),
                        getString(R.string.text_device_low_power_nbr) + state.getBadNbr());
                break;
            case "床信号识别器":
                setDeviceState(mTvNbrBed,mTvLowPowerBed,
                        getString(R.string.text_device_nbr) + state.getOkNbr(),
                        getString(R.string.text_device_low_power_nbr) + state.getBadNbr());
                break;
        }
    }

    private void CheckDeviceStatus(){
        if (mDevicesWarningBiz == null) {
            mDevicesWarningBiz = new DeviceWarningBiz();
        }
        mDevicesWarningBiz.doCheckDevicesWarnning(mHandler);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device,null);

        mIvCardState = (ImageView) view.findViewById(R.id.iv_device_card_card);

        mCardViewAp = (CardView) view.findViewById(R.id.cardview_device_ap);
        mCardViewCard = (CardView) view.findViewById(R.id.cardview_device_card);
        mCardViewBed = (CardView) view.findViewById(R.id.cardview_bed_device);
        mCardViewDoor = (CardView) view.findViewById(R.id.cardview_door_device);
        mCardViewBottle = (CardView) view.findViewById(R.id.cardview_bottle_device);

        mCardViewAp.setOnClickListener(this);
        mCardViewCard.setOnClickListener(this);
        mCardViewBed.setOnClickListener(this);
        mCardViewDoor.setOnClickListener(this);
        mCardViewBottle.setOnClickListener(this);

        // Device Info
        mTvNbrAp = (TextView) view.findViewById(R.id.tv_device_ap_nbr);
        mTvNbrCard = (TextView) view.findViewById(R.id.tv_device_card_nbr);
        mTvNbrBed = (TextView) view.findViewById(R.id.tv_device_bed_nbr);
        mTvNbrDoor = (TextView) view.findViewById(R.id.tv_device_door_nbr);
        mTvNbrBottle = (TextView) view.findViewById(R.id.tv_device_bottle_nbr);

        mTvLowPowerAp =  (TextView) view.findViewById(R.id.tv_device_ap_low_power);
        mTvLowPowerCard =  (TextView) view.findViewById(R.id.tv_device_card_low_power);
        mTvLowPowerBed =  (TextView) view.findViewById(R.id.tv_device_bed_low_power);
        mTvLowPowerDoor =  (TextView) view.findViewById(R.id.tv_device_door_low_power);
        mTvLowPowerBottle =  (TextView) view.findViewById(R.id.tv_device_bottle_low_power);

        // get infos of devices
        CheckDeviceStatus();

        timer.schedule(task, 1000, 1500); // 1s后执行task,经过1s再次执行

        return view;
    }



    private void setDeviceState(TextView tvNbr, TextView tvLowPower,String strNbr,String strLowPower) {
        tvNbr.setText(strNbr);
        tvLowPower.setText(strLowPower);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardview_device_ap:

            case R.id.cardview_bed_device:

            case R.id.cardview_door_device:

            case R.id.cardview_bottle_device:
                Toast.makeText(getActivity(),R.string.info_device_ok,Toast.LENGTH_SHORT).show();
                break;
            case R.id.cardview_device_card:
                // startActivity(new Intent(getActivity(), DeviceWarnningActivity.class));
                break;
        }

    }
}
