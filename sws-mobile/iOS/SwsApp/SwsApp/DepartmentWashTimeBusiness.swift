//
//  DepartmentWashTimeBusiness.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/24.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  部门洗手时机：次数统计

import UIKit
import Alamofire
import SwiftyJSON

class DepartmentWashTimeBusiness{

    /**
     今天一天数据
     */
    func doDepartmentWashTimeBusinessToday(viewController vc:UIViewController,checkDepartment department :Int ) {
        
        let now = DateUtil.getDateString_Now()
        //let before = now
        doDepartmentWashTimeBusinessByStartTimeAndEndTime(vc,department,now,now)
    }
    
    /**
     近三十天数据
     */
    func doDepartmentWashTimeBusinessInNearly30Days(viewController vc:UIViewController,checkDepartment department :Int ) {
        
        let now = DateUtil.getDateString_Now()
        let before = DateUtil.getDateString_DaysBefore(baseDateStr: now, beforeDays: 30)
        doDepartmentWashTimeBusinessByStartTimeAndEndTime(vc,department,before,now)
    }

    /**
     返回样例
     {"treeId":"1",
     "0007":0,          //"0007":"进入病区",
     "staffCount":2,
     "0003":31,         //"0003":"手卫生完成",
     "0103":21,         //"0103":"接触患者前未手卫生",
     "0110":4,          //"0110":"离开患者后未手卫生
     "0008":0           //"0008":"离开病区",
     }
     //封装数据按照这个顺序:           0007         0003          0110             0103           0008
     //let datasLabelsArray = ["进入病房未洗手","洗手次数","长时间离开病床未洗手","接近病床未洗手","离开病房未洗手"]
     //let dataColorsArray  = [ "#223142",   "#52DDB6"，"#FE7276"           ,"#979899"      ,"#FAA755"]
     */
    func doDepartmentWashTimeBusinessByStartTimeAndEndTime(_ viewController:UIViewController,_ department :Int ,_ startTime :String , _ endTime :String ){
        print(#function)
        
        let url = InterfaceUtils.makeURL_getDepartWashTimes(startTimeStr: startTime, endTimeStr: endTime, departStr: "\(department)")
        print(url)
        
        Alamofire.request(url).responseJSON(completionHandler: {response in
            //debugPrint(response)
            if let json = response.result.value {
                
                print(json)
                let jsonJ = JSON(json)
                //得到viewController
                let vc = viewController as? DepartmentRateDetailViewController
                var washes:[WashTimeStruct] = []
                //0007
                let time0007 = jsonJ["0007"].intValue
                if time0007 != 0{
                    //不为0就给他封装个数据
                    var wash0007 = WashTimeStruct()
                    wash0007.colorStrHex = "#223142"
                    wash0007.occasionName = "进入病房未洗手"
                    wash0007.timesInt = time0007
                    //添加
                    washes.append(wash0007)
                
                }
                
                //0003
                let time0003 = jsonJ["0003"].intValue
                if time0003 != 0{
                    //不为0就给他封装个数据
                    var wash0003 = WashTimeStruct()
                    wash0003.colorStrHex = "#52DDB6"
                    wash0003.occasionName = "洗手次数"
                    wash0003.timesInt = time0003
                    //添加
                    washes.append(wash0003)
                    
                }
                
                //0110
                let time0110 = jsonJ["0110"].intValue
                if time0110 != 0{
                    //不为0就给他封装个数据
                    var wash0110 = WashTimeStruct()
                    wash0110.colorStrHex = "#FE7276"
                    wash0110.occasionName = "长时间离开病床未洗手"
                    wash0110.timesInt = time0110
                    //添加
                    washes.append(wash0110)
                    
                }
                
                //0103
                let time0103 = jsonJ["0103"].intValue
                if time0103 != 0{
                    //不为0就给他封装个数据
                    var wash0103 = WashTimeStruct()
                    wash0103.colorStrHex = "#979899"
                    wash0103.occasionName = "接近病床未洗手"
                    wash0103.timesInt = time0103
                    //添加
                    washes.append(wash0103)
                    
                }
                
                //0008
                let time0008 = jsonJ["0008"].intValue
                if time0008 != 0{
                    //不为0就给他封装个数据
                    var wash0008 = WashTimeStruct()
                    wash0008.colorStrHex = "#FAA755"
                    wash0008.occasionName = "离开病房未洗手"
                    wash0008.timesInt = time0008
                    //添加
                    washes.append(wash0008)
                    
                }
                
                vc?.washTimes = washes
                 }else {
                
            }
        })
    }
}
