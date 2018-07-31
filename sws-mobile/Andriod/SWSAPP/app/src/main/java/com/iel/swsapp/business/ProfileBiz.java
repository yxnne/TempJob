package com.iel.swsapp.business;

import com.iel.swsapp.application.MyApplication;
import com.iel.swsapp.entity.ProfileInfo;
import com.iel.swsapp.entity.User;
import com.iel.swsapp.utils.StaticClass;

/**
 * Class Full Name  : com.iel.swsapp.business.ProfileBiz
 * Author Name      : yxnne
 * Create Time      : 2017/3/14
 * Project Name     : SWSAPP
 * Descriptions     : 用户基本信息的业务
 */
public class ProfileBiz {
    public static final String STR_NO_RFID = "no_card";
    public ProfileInfo getCurrentUserProfile(){
        ProfileInfo profileInfo ;

        /**假数据实现方案*/
        if(MyApplication.MY_MODEL == StaticClass.MODEL_FAKE_DATA) {

            profileInfo = new ProfileInfo("Xue Peak",
                    "消化科","医生","006688","13758274165",
                    "68%","6");

        }
        /**真数据实现方案*/
        else{
            User user = MyApplication.getCurrentUser();
            profileInfo = new ProfileInfo(user.getUserName(),
                    user.getDepartment(),"医生",user.getRfid(),user.getPhoneNbr(),
                    user.getRateInDepartment(),user.getRankIndepartment());
        }
        return profileInfo;
    }


}
