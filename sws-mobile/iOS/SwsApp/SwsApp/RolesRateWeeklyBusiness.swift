//
//  RolesRateWeeklyBusiness.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/24.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  所有人员类别一周数据业务类

import UIKit
import Alamofire
import SwiftyJSON

class RolesRateWeeklyBusiness{
    /**图表的颜色10种吧*/
    
/*
     let dataDoctor = LineChartRoleDataWrapper(roleName: "医生",
     roleDatas: [0.65,0.82,0.78, 0.73,0.65,0.9 ,0.70],
     roleColor: UIColor.colorWithHexString(hex: "#E8A3A7") )
     
     let dataNurse = LineChartRoleDataWrapper(roleName: "护士",
     roleDatas: [0.52,0.66,0.88, 0.45,0.82,0.91 ,0.66],
     roleColor: UIColor.colorWithHexString(hex: "#45D1CC") )
     
     let dataWorker = LineChartRoleDataWrapper(roleName: "护工",
     roleDatas: [0.35,0.45,0.55, 0.35,0.45,0.52 ,0.21],
     roleColor: UIColor.colorWithHexString(hex: "#5E6975") )
     
     let dataPhysician = LineChartRoleDataWrapper(roleName: "护工",
     roleDatas: [0.58,0.46,0.50, 0.65,0.95,0.85 ,0.25],
     roleColor: UIColor.colorWithHexString(hex: "#5EA8C5") )
     
     */
    static let colors = [
        "#E8A3A7","#45D1CC","#5E6975","#5EA8C5","#D0E9FF",
        "#EACF02","#199475","#495A80","#F17C67","#F2EFE6"
    
    ]
    /**
     */
    func doRolesRateWeeklyBusinessFromToday(_ viewController:UIViewController,_ department :Int){
        print(#function)
        
        let endTime = DateUtil.getDateString_Now()
        let startTime = DateUtil.getDateString_OneWeekBefore()
        
        let url = InterfaceUtils.makeURL_rolesRateInAWeek(startTimeStr: startTime, endTimeStr: endTime, departStr: "\(department)")
        print(url)
        
        Alamofire.request(url).responseJSON(completionHandler: {response in
            //debugPrint(response)
            if let json = response.result.value {
                print(json)
                
                let jsonJ = JSON(json)
                let dayMap = jsonJ["map"]
                
                //遍历系统的员工类别，和颜色,颜色大于10种就让他重复
                var rolesDic:Dictionary<String,[Double]> = [:] // = [String,LineChartRoleDataWrapper]()
                var colorsDic:Dictionary<String,UIColor> = [:]
                for i in 0..<AppGlobal.instance.currentSysStaffTypes!.count{
                    let staffType = AppGlobal.instance.currentSysStaffTypes![i]
                    let name = staffType.typeName
                    rolesDic[name!] = []

                    let colorIndex = i % RolesRateWeeklyBusiness.colors.count
                    colorsDic[name!] = UIColor.colorWithHexString(hex: RolesRateWeeklyBusiness.colors[colorIndex])

                }
                
                //根据JSON,之前的Dictionary构造[Double]
                for i in 1..<8{//本结构是从1开始
                    let oneDay = dayMap["\(i)"].arrayValue

                    for role in oneDay{
                        let name = role["name"].string
                        let rate = Double(role["rate"].string!)
   
                        rolesDic[name!]?.append(rate!)
           
                    }
                }
                
                //根据Dictionary 构造 Wrapper Array
                var wrapperDataArray:[LineChartRoleDataWrapper] = []
                for(name,rateArray) in rolesDic{
                    let dataWrapper = LineChartRoleDataWrapper.init(roleName: name, roleDatas: rateArray, roleColor: colorsDic[name]!)
                    wrapperDataArray.append(dataWrapper)
                    print("============================")
                    print("name is ----->\(name)")
                    print("rateArray is ----->\(rateArray)")
                    print("============================")
                }
                
                let vc = viewController as? MainPageViewController
                vc?.rolesRolesDataArrays = wrapperDataArray
                
            }else {
                
            }
        })
    }

    
}
