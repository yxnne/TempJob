package com.iel.swsapp.business;

import com.iel.swsapp.R;
import com.iel.swsapp.application.MyApplication;
import com.iel.swsapp.utils.StaticClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Full Name  : com.iel.swsapp.business.NewInfoBiz
 * Author Name      : yxnne
 * Create Time      : 2017/3/16
 * Project Name     : SWSAPP
 * Descriptions     : 得到新闻信息
 */
public class NewInfoBiz {

    public static List<NewsInfo> getNewsList(){
        List<NewsInfo> listInfo = new ArrayList<>();

        if(MyApplication.MY_MODEL == StaticClass.MODEL_FAKE_DATA){
            listInfo.add(new NewsInfo("手卫生口诀","2015-10-15 杨晓帆 SIFIC感染官微",
                    "http://115.159.189.43:8080/test/yedl.html",
                    R.drawable.logo_news_1));
            listInfo.add(new NewsInfo("手卫生——外科手消毒培训要点","2016-10-28 利康消毒专家",
                    "http://mp.weixin.qq.com/s?src=3&timestamp=1489814076&ver=1&signature=WJe-xVQP1QUe0Yqe3fHfz28DNQ7icnvP91yAcc7y81hdjrrCzdEEHFIH*H8GaERFSrCL-LpeMHBFt6HhWVHehlpiNKhzTXsLa7co54a-JUKGo381i8*vVaHJWB5HNqamRctiKXxpZ1gUpRcDjE7vp6Zm2iIGNJzkXB*pJfPL1U4=",
                    R.drawable.logo_news_2));
            listInfo.add(new NewsInfo("手卫生监测内容与方法大汇总","2016-06-03 陈明谋 感控小蜘蛛",
                    "http://mp.weixin.qq.com/s?src=3&timestamp=1489814297&ver=1&signature=Pa8rTgh9eJTMpHa6yBkLN8SwD1p0BhQiIgmgKQ9YIYOdmOWX2DX8S06*M7cKMs-R-O2p9L*MWkueox*PofeHgcXAEzNvpDS2IcD40stO-z7BTRzZ35SyUaWNYDs5mfkgwb4rYFdXTFgKF9j43mW*i4l5Brj2cibkAz-VPvnEYZ8=",
                    R.drawable.logo_news_3));
            listInfo.add(new NewsInfo("手卫生与健康","2015-10-15  SIFIC感染官微",
                    "http://mp.weixin.qq.com/s?src=3&timestamp=1489814297&ver=1&signature=WJe-xVQP1QUe0Yqe3fHfz28DNQ7icnvP91yAcc7y81jg5H59-zsOz6UufbLQGdOOuVJBShYo2twVh9UKnpbyFP4Wnn8uw4i-QC-F6p26Qqgjscw*7mtRjqh0qX0KWzJkquS29PAGC*r1rw0vBuDvag*yUhavjd8fFNgc*fUJL2M=",
                    R.drawable.logo_news_4));
            listInfo.add(new NewsInfo("新生儿护理,“手卫生”要注意!","2016-07-09 好孕妈妈",
                    "http://mp.weixin.qq.com/s?src=3&timestamp=1489814297&ver=1&signature=WJe-xVQP1QUe0Yqe3fHfz28DNQ7icnvP91yAcc7y81iWMklYkdoR1A*nZ7NEPRHN6m*moA2IAqhuanyYVVxmm*ZXid-SZLeyDJTNoIcv6WFWPULonluU0-SYE2L29wHTz3Xu7F8bfNM6PUPuMwwQG2bSwSfULDy0BGBC-EgSYT8=",
                    R.drawable.logo_news_5));
            listInfo.add(new NewsInfo("手卫生的五个时刻(视频教学)","2016-04-07 CCN 中国危重症监护",
                    "http://mp.weixin.qq.com/s?src=3&timestamp=1489814076&ver=1&signature=huEiw0Bm1xWT3MtQDQxKbKJMPtH0TYcn9Ozr9u3YqFOcEfAUn7P3-H0esr2ccB3kANUd5arFqjJHn8QFCZT*G2qYykG3RFh-VoLv4suoofSypYhv3zKIoSB7Xg1KLCE0lQXmSBR9ME-OYq0fO9bp881cr9mb02eNJWWiEQ78ZdU=",
                    R.drawable.logo_news_6));
            listInfo.add(new NewsInfo("“手卫生”应该面向更广的人群","2016-05-05 陕西医院感染控制 感控plus",
                    "http://mp.weixin.qq.com/s?src=3&timestamp=1489814076&ver=1&signature=iAe4VUP-zPcq3fDpnA8osts3ZiCOnT9I5mD76q0DCIjgVIYFQiLu-Y01OPy91mu3sZKoGMOVZk-5fjnrW8BMTRk-HCLQvp-eOnsTLOtrWxT7wDddqEYBWCbPd4xm0dmpvuJ8jYaT4BeDPDhqLAw-5g==",
                    R.drawable.logo_news_7));
            listInfo.add(new NewsInfo("敬畏生命，唤醒感控！","2017-02-10 董宏亮 感控plus",
                    "http://mp.weixin.qq.com/s?src=3&timestamp=1489814076&ver=1&signature=JRZBY0rxZswJq3dasOzF-Wu*m1dHmCZh4xMfKLSJFueSb9BuGWg8-nFUzx5zgdsgUkmHtbbYQeSPFA7QJUrVXJUuu3Z15TC61HnD4o37fYkpfrwRR6K-Dqu6374O4batEZ2Q5BNiqF88PK0-XVcx0GpKIvoKoB6J11tNItKN9Jg=",
                    R.drawable.logo_news_8));
        }
        return listInfo;
    }


    public static  class NewsInfo{
        private String tittle;
        private String time;
        private String url;
        private int imgRes;

        public NewsInfo(String tittle, String time, String url, int imgRes) {
            this.tittle = tittle;
            this.time = time;
            this.url = url;
            this.imgRes = imgRes;
        }

        public void setTittle(String tittle) {
            this.tittle = tittle;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setImgRes(int imgRes) {
            this.imgRes = imgRes;
        }

        public String getTittle() {
            return tittle;
        }

        public String getTime() {
            return time;
        }

        public String getUrl() {
            return url;
        }

        public int getImgRes() {
            return imgRes;
        }
    }
}
