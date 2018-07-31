package com.iel.swsapp.business;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.iel.swsapp.application.MyApplication;
import com.iel.swsapp.entity.DepartsResponse;
import com.iel.swsapp.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Full Name  : com.iel.swsapp.business.DeviceWarningBiz
 * Author Name      : yxnne
 * Create Time      : 2017/3/15
 * Project Name     : SWSAPP
 * Descriptions     : 得到设备预警的业务类
 */
public class DeviceWarningBiz {

    private static final String URL_WARNNING_DEVICES_PATH = "getDeviceStatusByDepartId.action";
    public static final int HANDLER_WARNNING_DEVICES = 799;

    /*假数据测试*/
    public List<DeviceWarningInfo> getDeviceWainningList(){
        List<DeviceWarningInfo> listInfo = new ArrayList<>();
        String[] strNames = new String[]{
                "立春橙","笋丝艺","人情扩",
                "留校严", "养小囡","成鹰",
        };
        String[] strNo = new String[]{
                "001234","006234","001834",
                "001264", "009234","009527",
                "003364", "006634","009887",
                "002664"
        };

        //假数据
        if(MyApplication.MY_MODEL == StaticClass.MODEL_FAKE_DATA){
            for(int i = 0;i < 10;i++ ){
                listInfo.add(new DeviceWarningInfo("胸卡",strNo[i%strNo.length],strNames[i%strNames.length]));
            }
        }

        return listInfo;

    }


    /**
     * 内部类 对设备信息预警的简单封装, 未用到
     */
    public static  class DeviceWarningInfo{
        private String deviceType;
        private String deviceNo;
        private String deviceStaff;

        public DeviceWarningInfo(String deviceType, String deviceNo, String deviceStaff) {
            this.deviceType = deviceType;
            this.deviceNo = deviceNo;
            this.deviceStaff = deviceStaff;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public String getDeviceNo() {
            return deviceNo;
        }

        public String getDeviceStaff() {
            return deviceStaff;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public void setDeviceNo(String deviceNo) {
            this.deviceNo = deviceNo;
        }

        public void setDeviceStaff(String deviceStaff) {
            this.deviceStaff = deviceStaff;
        }
    }


    /**
     * 数据格式如下
     * {"mapStatus":{"门外发射器":[1,0],"液瓶识别器":[34,0],"智能胸牌":[82,15],"门内发射器":[1,0],"无线接入点":[2,0],"床信号识别器":[29,1]}}
     */
    public void doCheckDevicesWarnning(final Handler mHandler){
        StringBuilder url = new StringBuilder(MyApplication.sUrlNotChangePart);
        url.append(URL_WARNNING_DEVICES_PATH).append("?");
        url.append("departIds=1");

        RxVolley.get(url.toString(), new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);

                try {
                    JSONObject jsonObject = new JSONObject(t);
                    String mapStr = jsonObject.getString("mapStatus");

                    List<DeviceRunningStatus> statusLists = new ArrayList<>();
                    if(!mapStr.equals("")){
                        JSONArray jOutDoorDevicesStatus = new JSONObject(mapStr).getJSONArray("门外发射器");
                        JSONArray jBottleDevicesStatus = new JSONObject(mapStr).getJSONArray("液瓶识别器");
                        JSONArray jCardDevicesStatus = new JSONObject(mapStr).getJSONArray("智能胸牌");
                        JSONArray jInDoorDevicesStatus = new JSONObject(mapStr).getJSONArray("门内发射器");
                        JSONArray jAPDevicesStatus = new JSONObject(mapStr).getJSONArray("无线接入点");
                        JSONArray jBedDevicesStatus = new JSONObject(mapStr).getJSONArray("床信号识别器");

                        // 封装对象

                        statusLists.add(new DeviceRunningStatus(
                                "门外发射器",
                                jOutDoorDevicesStatus.getInt(0),
                                jOutDoorDevicesStatus.getInt(1)
                        ));
                        statusLists.add(new DeviceRunningStatus(
                                "液瓶识别器",
                                jBottleDevicesStatus.getInt(0),
                                jBottleDevicesStatus.getInt(1)
                        ));
                        statusLists.add(new DeviceRunningStatus(
                                "智能胸牌",
                                jCardDevicesStatus.getInt(0),
                                jCardDevicesStatus.getInt(1)
                        ));
                        statusLists.add(new DeviceRunningStatus(
                                "门内发射器",
                                jInDoorDevicesStatus.getInt(0),
                                jInDoorDevicesStatus.getInt(1)
                        ));
                        statusLists.add(new DeviceRunningStatus(
                                "无线接入点",
                                jAPDevicesStatus.getInt(0),
                                jAPDevicesStatus.getInt(1)
                        ));
                        statusLists.add(new DeviceRunningStatus(
                                "床信号识别器",
                                jBedDevicesStatus.getInt(0),
                                jBedDevicesStatus.getInt(1)
                        ));

                    }else{
                        statusLists.add(new DeviceRunningStatus(
                                "门外发射器",0,0
                        ));
                        statusLists.add(new DeviceRunningStatus(
                                "液瓶识别器",0,0
                        ));
                        statusLists.add(new DeviceRunningStatus(
                                "智能胸牌",0,0
                        ));
                        statusLists.add(new DeviceRunningStatus(
                                "门内发射器",0,0
                        ));
                        statusLists.add(new DeviceRunningStatus(
                                "无线接入点",0,0
                        ));
                        statusLists.add(new DeviceRunningStatus(
                                "床信号识别器",0,0
                        ));
                    }

                    Message msg = mHandler.obtainMessage();
                    msg.what = HANDLER_WARNNING_DEVICES;
                    msg.obj = statusLists;
                    mHandler.sendMessage(msg);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 内部类
     * 设备预警信息
     * 设备类别， 正常数量和异常数量
     */
    public static class DeviceRunningStatus{
        private String deviceType;
        private int okNbr;
        private int badNbr;

        public DeviceRunningStatus(String deviceType, int okNbr, int badNbr) {
            this.deviceType = deviceType;
            this.okNbr = okNbr;
            this.badNbr = badNbr;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public int getOkNbr() {
            return okNbr;
        }

        public void setOkNbr(int okNbr) {
            this.okNbr = okNbr;
        }

        public int getBadNbr() {
            return badNbr;
        }

        public void setBadNbr(int badNbr) {
            this.badNbr = badNbr;
        }

        @Override
        public String toString() {
            return "DeviceRunningStatus{" +
                    "deviceType='" + deviceType + '\'' +
                    ", okNbr='" + okNbr + '\'' +
                    ", badNbr='" + badNbr + '\'' +
                    '}';
        }
    }
}
