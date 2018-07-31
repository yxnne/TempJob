//
//  CheckSystemStaffTypesBusiness.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/22.
//  Copyright © 2017年 iel-mac. All rights reserved.
//


import UIKit
import Alamofire
import SwiftyJSON

class CheckSystemStaffTypesBusiness{
    
    /**
     查询所有的系统内的部门，把结果保存在AppGlobal中
     */
    func doCheckAllStaffTypesInSystem(restoreDest dest :AppGlobal){
        /*
         返回样例
         {"jobType" : {
            "7" : "轮转生",
            "3" : "护士",
            ...
            }
         }
         
         */
        let url = InterfaceUtils.makeURL_getAllStaffTypesAndIdsInSys()
        Alamofire.request(url).responseJSON(completionHandler: {response in
            //debugPrint(response)
            if let json = response.result.value {
                
                let jsonJ = JSON(json)
                
                var staffTypeArrays:[StaffType] = []
                let staffTypes = jsonJ["jobType"].dictionary
                for (id,name) in staffTypes!{
                    let type = StaffType()
                    type.typeId = Int(id)
                    type.typeName = name.string

                    staffTypeArrays.append(type)
                }
                //向"目的地"设置值
                dest.currentSysStaffTypes = staffTypeArrays
 
            }else {
                
            }
        })
    }
}
