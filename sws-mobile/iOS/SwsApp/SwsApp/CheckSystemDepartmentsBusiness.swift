//
//  CheckSystemDepartmentsBusiness.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/18.
//  Copyright © 2017年 iel-mac. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON

class CheckSystemDepartmentsBusiness{
    
    /**
     查询所有的系统内的部门，把结果保存在AppGlobal中
     */
    func doCheckAllDepartmentsInSystem(restoreDest dest :AppGlobal){
        /*
         返回样例
         {"depaertLists":
            [
                {"departId":"52","departName":"麻醉科"},
                {"departId":"132","departName":"感控科"},
                ...
            ]
         }
         
         */
        let url = InterfaceUtils.makeURL_getDepartmentInSys()
        Alamofire.request(url).responseJSON(completionHandler: {response in
            //debugPrint(response)
            if let json = response.result.value {
                
                let jsonJ = JSON(json)

                var departmentResultArrays:[Department] = []
                
                let departLists = jsonJ["depaertLists"].arrayValue
                
                for departEntry in departLists{
                    let depart = Department()
                    depart.departIds = departEntry["departId"].intValue
                    depart.departName = departEntry["departName"].string
                    departmentResultArrays.append(depart)
                }
                dest.currentSysDepartments = departmentResultArrays
            }else {
                
            }
        })
    }
}
