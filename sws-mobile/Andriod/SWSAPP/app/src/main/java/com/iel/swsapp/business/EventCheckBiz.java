package com.iel.swsapp.business;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.iel.swsapp.application.MyApplication;
import com.iel.swsapp.entity.EventItem;
import com.iel.swsapp.entity.response.EventSelectResponse;
import com.iel.swsapp.entity.response.LoginResponse;
import com.iel.swsapp.entity.response.Result;
import com.iel.swsapp.utils.DateUtils;
import com.iel.swsapp.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Class Full Name  : com.iel.swsapp.business.EventCheckBiz
 * Author Name      : yxnne
 * Create Time      : 2017/3/13
 * Project Name     : SWSAPP
 * Descriptions     : 时间查询
 */
public class EventCheckBiz {
    private static final String URL_EVEVT_HISTORY_PATH = "eventSelect.action";

    public static final String RESULT_HIS = "event_his_result";
    public static final String RESULT_REAL = "event_real_result";

    public static class HisDatasResponse implements Serializable{
        int nowPage;
        int totalPage;
        private List<EventItem> datas;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getNowPage() {
            return nowPage;
        }

        public void setNowPage(int nowPage) {
            this.nowPage = nowPage;
        }

        public List<EventItem> getDatas() {
            return datas;
        }

        public void setDatas(List<EventItem> datas) {
            this.datas = datas;
        }

        @Override
        public String toString() {
            return "HisDatasResponse{" +
                    "nowPage=" + nowPage +
                    ", totalPage=" + totalPage +
                    ", datas=" + datas +
                    '}';
        }
    }


    /**
     * 查询今日实时数据
     * @param context 上下文
     * @param pageNbr 页
     */
    public void getTodayList(final Context context, String pageNbr){

        String strToday = DateUtils.getNowDateString();
        //拼接URL
        StringBuilder url = new StringBuilder(MyApplication.sUrlNotChangePart);
        url.append(URL_EVEVT_HISTORY_PATH).append("?");
        url.append("staffId").append("=").append("-1").append("&");

        url.append("treeId").append("=")
                .append(MyApplication.getCurrentUser().getDepartIds()).append("&");
        url.append("departIds").append("=")
                .append(MyApplication.getCurrentUser().getDepartIds()).append("&");
        url.append("startTime").append("=").append(strToday).append("&");
        url.append("endTime").append("=").append(strToday);
        //url.append("pageNum").append("=").append(pageNbr);

       // RxVolley.Builder builder = RxVolley.
        HttpParams params = new HttpParams();
        params.put("Cache-Control", "private, max-age=0, no-cache");


        HttpCallback callback = new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);

                //根据JSON封装对象 使用谷歌狗剩去解析
                Gson gson = new Gson();
                EventSelectResponse response = gson.fromJson(t,
                        EventSelectResponse.class);
                HisDatasResponse res = parseObjData(response);

                //发送 广播
                Intent intent = new Intent(StaticClass.ACTION_GET_REAL_EVENT);

                //intent.putExtra(RESULT_HIS,res);
                Bundle bundle = new Bundle();

                bundle.putSerializable(RESULT_REAL, res);
                intent.putExtras(bundle);

                context.sendBroadcast(intent);

            }
        };
        new RxVolley.Builder()
                .url(url.toString()) //接口地址
                //请求类型，如果不加，默认为 GET 可选项：
                //POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
                .httpMethod(RxVolley.Method.GET)
                //设置缓存时间: 默认是 get 请求 5 分钟, post 请求不缓存
                .cacheTime(0)
                //内容参数传递形式，如果不加，默认为 FORM 表单提交，可选项 JSON 内容
                .contentType(RxVolley.ContentType.FORM)
                .params(params) //上文创建的HttpParams请求参数集
                //是否缓存，默认是 get 请求 5 缓存分钟, post 请求不缓存
                .shouldCache(false)
                //.progressListener(callback) //上传进度
                .callback(callback) //响应回调
                .encoding("UTF-8") //编码格式，默认为utf-8
                .doTask();  //执行请求操作

//        RxVolley.get(url.toString(),params, new HttpCallback() {
//            @Override
//            public void onSuccess(String t) {
//                super.onSuccess(t);
//                //根据JSON封装对象 使用谷歌狗剩去解析
//                Gson gson = new Gson();
//                EventSelectResponse response = gson.fromJson(t,
//                        EventSelectResponse.class);
//                HisDatasResponse res = parseObjData(response);
//
//                //发送 广播
//                Intent intent = new Intent(StaticClass.ACTION_GET_REAL_EVENT);
//
//                //intent.putExtra(RESULT_HIS,res);
//                Bundle bundle = new Bundle();
//
//                bundle.putSerializable(RESULT_REAL,res );
//                intent.putExtras(bundle);
//
//                context.sendBroadcast(intent);
//
//            }
//        });

    }

    /**
     * 查询历史数据
     * @param context 上下文
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param eventType 时间类型
     * @param pageNbr 页
     */
    public void getHisList(final Context context,String startTime, String endTime, String eventType, String pageNbr){

        //拼接URL
        StringBuilder url = new StringBuilder(MyApplication.sUrlNotChangePart);
        url.append(URL_EVEVT_HISTORY_PATH).append("?");
        url.append("staffId").append("=").append("-1").append("&");
        url.append("startTime").append("=").append(startTime).append("&");
        url.append("endTime").append("=").append(endTime).append("&");
        url.append("departIds").append("=")
                .append(MyApplication.getCurrentUser().getDepartIds()).append("&");
        url.append("treeId").append("=")
                .append(MyApplication.getCurrentUser().getDepartIds()).append("&");

        if(eventType.equals("all")){
            url.append("pageNum").append("=").append(pageNbr);
        }else{
            url.append("pageNum").append("=").append(pageNbr).append("&");
            url.append("eventType").append("=").append(eventType);
        }

        RxVolley.get(url.toString(), new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                //根据JSON封装对象 使用狗剩
                Gson gson = new Gson();
                EventSelectResponse response = gson.fromJson(t,
                        EventSelectResponse.class);
                HisDatasResponse res = parseObjData(response);

                //发送 广播
                Intent intent = new Intent(StaticClass.ACTION_GET_HIS_EVENT);

                //intent.putExtra(RESULT_HIS,res);
                Bundle bundle = new Bundle();

                bundle.putSerializable(RESULT_HIS,res );
                intent.putExtras(bundle);

                context.sendBroadcast(intent);
            }
        });

    }
    /**
     * 根据实体对象，封装数据成我们需要的
     * @param response 根据JSON解析的实体
     * @return HisDatasResponse
     */
    private HisDatasResponse parseObjData(EventSelectResponse response) {
        HisDatasResponse res = new HisDatasResponse();
        List<EventItem> datas = new ArrayList<>();
        //设置当前页号码
//        if(response.getPage().getPageNo() == 1000){
//            res.setNowPage(response.getPage().getTotalPage());
//        }else{
//            res.setNowPage(response.getPage().getPageNo());
//    }
        res.setNowPage(response.getPage().getPageNo());
        res.setTotalPage(response.getPage().getTotalPage());

        List<Result> rawLists = response.getPage().getResult();


        //无关的东西太多 ，封装自己想要的数据
        for(Result r:rawLists){
            EventItem item = new EventItem();
            item.setRoleName(r.getDocName());
            item.setEvent(r.getEventTypeName());
            item.setTime(DateUtils.getMonthAndDayAndCMS(r.getCreateTime()));
            datas.add(item);
        }
        //按时间排下
        Collections.sort(datas);

        res.setDatas(datas);
        return res;


    }


    /**
     *
     * @return 列表   List<EventItem>
     */
    public List<EventItem> getRealList(){
        List<EventItem> listItem = new ArrayList<>();
        //添加表头
        //listItem.add(new EventItem("姓名","事件","时间"));
        //假数据
        if(MyApplication.MY_MODEL == StaticClass.MODEL_FAKE_DATA){
            String[] eventType = new String[]{
                    "进入病房未洗手","离开病房","正确洗手","进入病房","靠近病床",
            };
            String[] names = new String[]{
                    "立春橙","笋丝艺","人情扩",
                    "肤红亮","人用抄","留校严",
                    "养小囡","成鹰","掌事怦","望牙杯"
            };
            //假时间
            Long time = System.currentTimeMillis();

            for(int i = 0; i < 50 ; i++){
                String strTime = DateUtils.getDateString(new Date(time - 1000 * i));

                listItem.add(
                        new EventItem(
                            names[i%(names.length)],
                            eventType[i%(eventType.length)],
                            strTime));

            }

        }

        //非假数据待处理

        return listItem;
    }

    /**
     * 查询条件待处理
     * @return
     */
    public List<EventItem> getHistoryList(){
        List<EventItem> listItem = new ArrayList<>();
        if(MyApplication.MY_MODEL == StaticClass.MODEL_FAKE_DATA){
            String[] eventType = new String[]{
                    "离开病房","正确洗手","进入病房","靠近病床","进入病房未洗手"
            };
            String[] names = new String[]{

                    "肤红亮","人用抄","留校严",
                    "养小囡","乘鹰","掌事怦","望牙杯",
                    "立春橙","笋丝艺","人情扩",
            };
            //假时间
            Long time = System.currentTimeMillis();

            for(int i = 0; i < 50 ; i++){
                String strTime = DateUtils.getDateString(new Date(time - 1000 * i*60*60));

                listItem.add(
                        new EventItem(
                                names[i%(names.length)],
                                eventType[i%(eventType.length)],
                                strTime));

            }

        }

        //非假数据待处理
        return listItem;
    }
}
