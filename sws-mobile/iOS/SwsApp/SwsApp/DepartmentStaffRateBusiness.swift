//
//  DepartmentStaffRateBusiness.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/22.
//  Copyright © 2017年 iel-mac. All rights reserved.
//


import UIKit
import Alamofire
import SwiftyJSON

class DepartmentStaffRateBusiness {
    
    /**
     今天一天数据
     */
    func doDepartmentStaffRateBusinessToday(viewController vc:UIViewController,checkDepartment department :Int ) {
        
        let now = DateUtil.getDateString_Now()
        //let before = now
        doDepartmentStaffRateBusinessByStartTimeAndEndTime(vc,department,now,now)
    }
    
    /**
     近三十天数据
     */
    func doDepartmentStaffRateBusinessInNearly30Days(viewController vc:UIViewController,checkDepartment department :Int ) {
        
        let now = DateUtil.getDateString_Now()
        let before = DateUtil.getDateString_DaysBefore(baseDateStr: now, beforeDays: 30)
        doDepartmentStaffRateBusinessByStartTimeAndEndTime(vc,department,before,now)
    }
    
    /**
     通过StartTiime and EndTime查询后台
     返回样例:
     {
     "page" : {
        "result" : [
            {
                "docType" : "医生",
                "num0007" : 0,
                "rateAfterCloseNick" : 0,
                "docName" : "小九",
                "num0103" : 0,
                "errorNum" : 0,
                "rateAfterCloseNickEnvri" : 0,
                "departName" : "麻醉科",
                "rank" : 1,
                "num0003" : 0,
                "num0008" : 0,
                "rateBeforeAsepticOperation" : 0,
                "normalNum" : 0,
                "rateBeforeCloseNick" : 0,
                "rate" : "0.0",
                "rfid" : "000990",
                "num0110" : 0
            },
            {
                "docType" : "医生",
                "num0007" : 0,
                "rateAfterCloseNick" : 0,
                "docName" : "五号测试员",
                "num0103" : 21,
                "errorNum" : 33,
                "rateAfterCloseNickEnvri" : 80,
                "departName" : "麻醉科",
                "rank" : 2,
                "num0003" : 31,
                "num0008" : 0,
                "rateBeforeAsepticOperation" : 0,
                "normalNum" : 6,
                "rateBeforeCloseNick" : 0,
                "rate" : "15.4",
                "rfid" : "000005",
                "num0110" : 4
            }
        ],
        "totalPage" : 1,
        "first" : 0,
        "pageSize" : 30,
        "total" : 2,
        "pageNo" : 1
        }
     }
     
     mMoment0003,//洗手次数 0003
     mMoment0110,//长时离开病床未洗手 0110
     mMoment0103,//接近病床未洗手 0103
     mMoment0008,//离开病房未洗手 0008
     mMoment0007);//进入病房未洗手 0007
     */
    func doDepartmentStaffRateBusinessByStartTimeAndEndTime(_ viewController:UIViewController,_ department :Int ,_ startTime :String , _ endTime :String ){
        print(#function)
        //print("startTime is : \(startTime)")
        //print("endTime is : \(endTime)")
        //let urlx = InterfaceUtils.makeURL_getDepartOverall(startTimeStr: startTime, endTimeStr: endTime, departStr: "\(department)")
        
        
        let url = InterfaceUtils.makeURL_getAllTypesStaffRateListByDepartments(startTimeStr: startTime, endTimeStr: endTime, departStr: "\(department)")
        print(url)
        
        Alamofire.request(url).responseJSON(completionHandler: {response in
            //debugPrint(response)
            if let json = response.result.value {
                
                let jsonJ = JSON(json)
                //得到viewController
                let vc = viewController as? StaffsRateViewController
                var staffRatesArray:[StaffRateItem ] = []
                
                let staffRatesListsJ = jsonJ["page"]["result"].arrayValue
                
                for one in staffRatesListsJ{
                    let staff = StaffRateItem()
                    staff.cardNo = one["rfid"].string               ?? "没有胸卡"
                    staff.department = one["departName"].string     ?? "没有部门"
                    staff.name = one["docName"].string              ?? "没有名字"
                    staff.role = one["docType"].string              ?? "没有角色"
                    staff.rateOverAll = one["rate"].string          ?? "? %"
                    //四大时机
                    staff.rateOccasions.rateAfterPatientContact     = one["rateAfterCloseNick"].doubleValue
                    staff.rateOccasions.rateAfterEnvironmentContact = one["rateAfterCloseNickEnvri"].doubleValue
                    staff.rateOccasions.rateBeforePatientContact    = one["rateBeforeCloseNick"].doubleValue
                    staff.rateOccasions.rateBeforeAsepsisOperation  = one["rateBeforeAsepticOperation"].doubleValue
                    //洗手次数统计
                    staff.washTimes.timesNoWashAcessRoom            = one["num0007"].doubleValue
                    staff.washTimes.timesNoWashCloseBed             = one["num0103"].doubleValue
                    staff.washTimes.timesNoWashLeaveRoom            = one["num0008"].doubleValue
                    staff.washTimes.timesNoWashLeaveRoomLong        = one["num0110"].doubleValue
                    staff.washTimes.timesWashCorretly               = one["num0003"].doubleValue
                    
                    //不要让空值出现
                    //发现可能在rfid出现空值
                    if staff.cardNo == ""{
                        staff.cardNo = "没有胸卡"
                    }
                    
                    //添加
                    staffRatesArray.append(staff)
                }

                vc?.staffs.items = staffRatesArray
                vc?.tableView.reloadData()
                
            }else {
                
            }
        })
        
    }
    
    
}
