//
//  DeviceBusiness.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/24.
//  Copyright © 2017年 iel-mac. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON

class DeviceBusiness{
    
    static let DEVICE_DOOR_OUT          = "门外发射器"
    static let DEVICE_DOOR_IN           = "门内发射器"
    static let DEVICE_CARD              = "智能胸牌"
    static let DEVICE_WIRELESS_POINT    = "无线接入点"
    static let DEVICE_BED               = "床信号识别器"
    static let DEVICE_BOTTLE            = "液瓶识别器"
    
    /**
     查询设备状态
     */
    func doCheckDeviceState(vc viewController:UIViewController,
                            departIds department :Int ){
        //构造url
        let url = InterfaceUtils.makeURL_deviceInfoOverAll(departStr: "\(department)")
        
        
        /*
         {"mapStatus":
            {
                "门外发射器":[2,0],
                "液瓶识别器":[2,1],
                "智能胸牌":[65,7],
                "外门液瓶识别器":[1,0],
                "门内发射器":[2,0],
                "无线接入点":[3,0],
                "床信号识别器":[4,1]
            }
         }
         */
        Alamofire.request(url).responseJSON(completionHandler: {response in
            //debugPrint(response)
            if let json = response.result.value {
                
                let jsonJ = JSON(json)
                //print(jsonJ)
                //outDoor
                let doorOut_total   = jsonJ["mapStatus"][DeviceBusiness.DEVICE_DOOR_OUT][0].intValue
                let doorOut_low     = jsonJ["mapStatus"][DeviceBusiness.DEVICE_DOOR_OUT][1].intValue
                //innerDoor
                let doorIn_total    = jsonJ["mapStatus"][DeviceBusiness.DEVICE_DOOR_IN][0].intValue
                let doorIn_low      = jsonJ["mapStatus"][DeviceBusiness.DEVICE_DOOR_IN][1].intValue
                //bottle
                let bottle_total    = jsonJ["mapStatus"][DeviceBusiness.DEVICE_BOTTLE][0].intValue
                let bottle_low      = jsonJ["mapStatus"][DeviceBusiness.DEVICE_BOTTLE][1].intValue
                //card
                let card_total      = jsonJ["mapStatus"][DeviceBusiness.DEVICE_CARD][0].intValue
                let card_low        = jsonJ["mapStatus"][DeviceBusiness.DEVICE_CARD][1].intValue
                //bed
                let bed_total       = jsonJ["mapStatus"][DeviceBusiness.DEVICE_BED][0].intValue
                let bed_low         = jsonJ["mapStatus"][DeviceBusiness.DEVICE_BED][1].intValue
                //acess point
                let access_total    = jsonJ["mapStatus"][DeviceBusiness.DEVICE_WIRELESS_POINT][0].intValue
                let access_low      = jsonJ["mapStatus"][DeviceBusiness.DEVICE_WIRELESS_POINT][1].intValue
                
                //设置数据
                let vc = viewController as? DevicePageViewController
                vc?.doorTotalCount          = doorIn_total + doorOut_total
                vc?.doorTerribleCount       = doorIn_low + doorOut_low
                
                vc?.bottleTotalCount        = bottle_total
                vc?.bottleTerribleCount     = bottle_low
                
                vc?.cardTotalCount          = card_total
                vc?.cardTerribleCount       = card_low
                
                vc?.bedTotalCount           = bed_total
                vc?.bedTerribleCount        = bed_low
                
                vc?.apTotalCount            = access_total
                vc?.apTerribleCount         = access_low
                
            }else {
                
            }
        })
    }

    
}
