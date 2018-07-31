//
//  DepartmentOverAllBusiness.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/16.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  处理部门整体依从率业务逻辑类
//  处理后台数据库交互的逻辑

import UIKit
import Alamofire
import SwiftyJSON

class DepartmentOverAllBusiness {
    
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
        //print(url)
        
        Alamofire.request(url).responseJSON(completionHandler: {response in
            //debugPrint(response)
            if let json = response.result.value {

                let jsonJ = JSON(json)
                //得到viewController
                let vc = viewController as? MainPageViewController
                //1.计算总体依从率           ---------------------------------------------
                let rightTimes = jsonJ["page"]["result"][0]["rightCount"].intValue
                let wrongTimes = jsonJ["page"]["result"][0]["wrongCount"].intValue
                let rateOverall = Double(rightTimes) / Double(rightTimes + wrongTimes)
                //向viewController设置值
                vc?.depaertRateOverAll = rateOverall * 100
                
                //2.计算四大洗手时机 :接触患者前，无菌操作前，接触环境后，接触患者后--------------
                let rateBeforeCloseNick         = jsonJ["page"]["result"][0]["rateBeforeCloseNick"].doubleValue         //接触患者前
                let rateBeforeAsepticOperation  = jsonJ["page"]["result"][0]["rateBeforeAsepticOperation"].doubleValue  //无菌操作前
                let rateAfterCloseNickEnvri     = jsonJ["page"]["result"][0]["rateAfterCloseNickEnvri"].doubleValue     //接触环境后
                let rateAfterCloseNick          = jsonJ["page"]["result"][0]["rateAfterCloseNick"].doubleValue          //接触患者后
                //设置时机
                let occasionArrays = [rateBeforeCloseNick,rateBeforeAsepticOperation,rateAfterCloseNickEnvri,rateAfterCloseNick]
                vc?.occasionRateArrays = occasionArrays
                
            }else {
               
            }
        })
        
    }


}
