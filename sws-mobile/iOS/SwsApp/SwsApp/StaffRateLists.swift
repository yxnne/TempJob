//
//  StaffRateLists.swift
//  SwsApp
//
//  Created by iel-mac on 2017/7/31.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  封装部门员工数据列表

import UIKit

class StaffRateLists{
    
    var items :[StaffRateItem] = []
    
    //假数据构造函数,参数假数据个数
    init(fakeCount count :Int){
        for _ in 0..<count{
            let staff = StaffRateItem()
            staff.name = "yxnne"
            staff.role = "doctor"
            staff.rateOverAll = "80%"
            staff.cardNo = "002244"
            staff.department = "神经内科"
            
            staff.rateOccasions.rateBeforeAsepsisOperation = 30
            staff.rateOccasions.rateAfterPatientContact = 40
            staff.rateOccasions.rateBeforeAsepsisOperation = 10
            staff.rateOccasions.rateBeforePatientContact = 25
            
            staff.washTimes.timesNoWashAcessRoom = 10
            staff.washTimes.timesNoWashCloseBed = 60
            staff.washTimes.timesNoWashLeaveRoom = 30
            staff.washTimes.timesNoWashLeaveRoomLong = 20
            staff.washTimes.timesWashCorretly = 50
            
            items.append(staff)
        
        }
    
    }


}
