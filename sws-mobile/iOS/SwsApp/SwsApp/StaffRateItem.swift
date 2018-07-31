//
//  StaffRateItem.swift
//  SwsApp
//
//  Created by iel-mac on 2017/7/31.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  个人的信息实体


import UIKit

class StaffRateItem: NSObject {
    var name :String = ""                               //姓名
    var cardNo :String = ""                             //卡号
    var rateOverAll :String = ""                        //整体依从率
    var department :String = ""                         //部门
    var role :String = ""                               //职位
    var rateOccasions: RateOccasion = RateOccasion()    //各个时机依从率
    var washTimes:WashTime = WashTime()                 //时机洗手次数
    
}
/*时机依从率*/
struct RateOccasion {
   
    var rateBeforePatientContact:Double                 //接触患者前
    var rateBeforeAsepsisOperation:Double               //无菌操作前
    var rateAfterEnvironmentContact:Double              //接触患者环境前
    var rateAfterPatientContact:Double                  //解除患者后
    
    init() {
        //
        
        rateBeforePatientContact = 0
        rateBeforeAsepsisOperation = 0
        rateAfterEnvironmentContact = 0
        rateAfterPatientContact = 0
    }
    
}
/*洗手时机事件次数*/
struct WashTime{
    
    var timesNoWashAcessRoom:Double                     //进入病房未洗手
    var timesWashCorretly:Double                        //洗手次数
    var timesNoWashLeaveRoomLong:Double                 //长时间离开病床未洗手
    var timesNoWashCloseBed:Double                      //接近病床未洗手
    var timesNoWashLeaveRoom:Double                     //离开病房未洗手
    
    init() {
        //
        
        timesNoWashAcessRoom = 0
        timesWashCorretly = 0
        timesNoWashLeaveRoomLong = 0
        timesNoWashCloseBed = 0
        timesNoWashLeaveRoom  = 0
    }
}
