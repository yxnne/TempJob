//
//  CheckSystemEventListBusiness.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/23.
//  Copyright © 2017年 iel-mac. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON

class CheckSystemEventListBusiness{

    /*
     {"rfidStatus":{
     "1":"清洁","10":"长时接触患者提醒","11":"接触患者未手卫生","12":"短时离开患者提醒","13":"离开患者未手卫生","2":"有限清洁","3":"待手卫生","4":"未执行","5":"短时进入病区","6":"短时离开病区","7":"进入病区未手卫生","8":"离开病区未手卫生","9":"短时接触患者提醒"
     },
     /*以下是需要用的*/
     "eventType":{
        "0003":"手卫生完成",
        "0004":"外门手卫生完成",
        "0007":"进入病区",
        "0008":"离开病区",
        "0018":"接近患者",
        "0045":"接近仪器",
        "0102":"离开病区未手卫生",
        "0103":"接触患者前未手卫生",
        "0110":"离开患者后未手卫生
        }
     }
    */
    func doCheckEventTypesInSystem(restoreDest dest :AppGlobal){
        let url = InterfaceUtils.makeURL_getEventTypesAndIdsInSys()
        Alamofire.request(url).responseJSON(completionHandler: {response in
            //debugPrint(response)
            if let json = response.result.value {
                
                let jsonJ = JSON(json)
                
                var eventTypeArrays:[EventType] = []
                let eventTypes = jsonJ["eventType"].dictionary
                for (id,name) in eventTypes!{
                    let type = EventType()
                    type.typeId = id
                    type.typeName = name.string
                    
                    eventTypeArrays.append(type)
                }
                //向"目的地"设置值
                dest.currentSysEventTypes = eventTypeArrays
                
            }else {
                
            }
        })
    
    }

}
