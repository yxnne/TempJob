//
//  DepartmentDetailBusiness.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/22.
//  Copyright © 2017年 iel-mac. All rights reserved.
//


import UIKit
import Alamofire
import SwiftyJSON

class DepartmentDetailBusiness {
    
    /**
     今天一天数据
     */
    func doDepartmentOverAllBusinessToday(viewController vc:UIViewController,checkDepartment department :Int ) {
        
        let now = DateUtil.getDateString_Now()
        //let before = now
        doDepartmentOverAllBusinessByStartTimeAndEndTime(vc,department,now,now)
    }
    
    /**
     近三十天数据
     */
    func doDepartmentOverAllBusinessInNearly30Days(viewController vc:UIViewController,checkDepartment department :Int ) {
        
        let now = DateUtil.getDateString_Now()
        let before = DateUtil.getDateString_DaysBefore(baseDateStr: now, beforeDays: 30)
        doDepartmentOverAllBusinessByStartTimeAndEndTime(vc,department,before,now)
    }
    
    /**
     通过StartTiime and EndTime查询后台
     */
    func doDepartmentOverAllBusinessByStartTimeAndEndTime(_ viewController:UIViewController,_ department :Int ,_ startTime :String , _ endTime :String ){
        print(#function)
        //print("startTime is : \(startTime)")
        //print("endTime is : \(endTime)")
        let url = InterfaceUtils.makeURL_getDepartOverall(startTimeStr: startTime, endTimeStr: endTime, departStr: "\(department)")
        print(url)
        
        Alamofire.request(url).responseJSON(completionHandler: {response in
            //debugPrint(response)
            if let json = response.result.value {
                
                let jsonJ = JSON(json)
                print(jsonJ)
                
                //得到viewController
                let vc = viewController as? DepartmentRateDetailViewController
                //计算四大洗手时机 :接触患者前，无菌操作前，接触环境后，接触患者后--------------
                let rateBeforeCloseNick         = jsonJ["page"]["result"][0]["rateBeforeCloseNick"].doubleValue         //接触患者前
                let rateBeforeAsepticOperation  = jsonJ["page"]["result"][0]["rateBeforeAsepticOperation"].doubleValue  //无菌操作前
                let rateAfterCloseNickEnvri     = jsonJ["page"]["result"][0]["rateAfterCloseNickEnvri"].doubleValue     //接触环境后
                let rateAfterCloseNick          = jsonJ["page"]["result"][0]["rateAfterCloseNick"].doubleValue          //接触患者后
                var occasionsRates:[Double] = []
                occasionsRates.append(rateBeforeCloseNick)
                occasionsRates.append(rateBeforeAsepticOperation)
                occasionsRates.append(rateAfterCloseNickEnvri)
                occasionsRates.append(rateAfterCloseNick)
                //信息返回给vc
                vc?.occasionsRateArray = occasionsRates
                
                
                /*
                 var rolesNamesArray:[String ] = []
                 var rolesRateArray:[Double]
                */
                //计算按角色依从率
                let nameList = jsonJ["page"]["result"][0]["nameList"].arrayValue                                       //角色类型数组
                let rightCounts = jsonJ["page"]["result"][0]["rightCountList"].arrayValue
                let wrongCounts = jsonJ["page"]["result"][0]["wrongCountList"].arrayValue
                for name in nameList{
                    print(name.string ?? "fuck")
                }
                //计算角色依从率
                var roleRatesDoubleCounts :[Double] = []
                var roleNames:[String ] = []
                for  i in 0 ..< nameList.count{
                    let right = rightCounts[i].intValue
                    let wrong = wrongCounts[i].intValue
                    let total = right + wrong
                    let rate = Double(right)/Double(total) * 100                                                         //转换成百分之数
                    let name = nameList[i].string
                    //设置名字
                    roleNames.append(name!)
                    roleRatesDoubleCounts.append(rate )
                    
                    print("the role name is \(String(describing: name)) \n and the rate is \(rate)")
                    
                }
                //信息返回给vc
                vc?.rolesNamesArray = roleNames
                vc?.rolesRateArray = roleRatesDoubleCounts
 
        
            }else {
                
            }
        })
        
    }
    
    
}
