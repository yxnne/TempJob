//
//  EventBusiness.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/23.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  事件的业务

import UIKit
import Alamofire
import SwiftyJSON

class EventBusiness{
    static let ALL_EVENT_SELECTED_ID = "all_event_selected"
    static let ALL_STAFF_SELECTED_ID = 23456


    /**
     查询事件
     */
    func doCheckEvent(vc viewController:UIViewController,
                      departIds department :Int ,
                      eventTypeId eventType:String,
                      staffTypeId staffType:Int,
                      startTimeStr startTime :String ,
                      endTimeStr endTime :String ,
                      pageNbr page:Int){
        //构造url
        var url = ""
        if staffType == EventBusiness.ALL_STAFF_SELECTED_ID && eventType == EventBusiness.ALL_EVENT_SELECTED_ID{
            //构造一个全部查询的：事件和人员
            url = InterfaceUtils.makeURL_eventCheck_allEventAllStaffType(startTimeStr: startTime, endTimeStr: endTime, departStr: "\(department)", pageNum: "\(page)")
            
        }
        if eventType != EventBusiness.ALL_EVENT_SELECTED_ID{
            //构造一个查询针对事件的
            url = InterfaceUtils.makeURL_eventCheck_allStaffType_ByEvent(startTimeStr: startTime, endTimeStr: endTime, departStr: "\(department)", pageNum: "\(page)", eventTypeId: eventType)
        }
        
        
        //let url = InterfaceUtils.makeURL_getEventTypesAndIdsInSys()
        Alamofire.request(url).responseJSON(completionHandler: {response in
            //debugPrint(response)
            if let json = response.result.value {
                
                let jsonJ = JSON(json)
                //print(jsonJ)
                
                let eventResults = jsonJ["page"]["result"].arrayValue
                var eventList:[EventItem] = []
                for item in eventResults{
                    
                    let event = EventItem()
                    event.name = item["docName"].string ?? "没有名字"
                    event.event = item["eventTypeName"].string ?? "没有事件"
                    event.time = item["updateTime"].string ?? "没有时间"
                    
                    eventList.append(event)
                }
                //判断下ViewController
                //var vc:UIViewController?
                if let vc = viewController as? EventHistoryViewController{//历史事件页
                    
                    vc.eventItems.items = eventList
                    vc.tableView.reloadData()
                    
                } else if let vc = viewController as? EventPageViewController{//今日事件页
                    if eventList.count == 0{//事件条数为0 提示下
                        vc.infoTodayNoEventByNow()
                    }
                    
                    vc.eventItems.items = eventList
                    vc.tableView.reloadData()

                }
            }else {
                
            }
            
 
        })
    }

}
