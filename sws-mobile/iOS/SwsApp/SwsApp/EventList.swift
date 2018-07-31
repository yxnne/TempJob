//
//  EventList.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/3.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  事件的集合

import UIKit

class EventList :NSObject{

    var items :[EventItem] = []
    
    init(fakeEventCount fakeCount:Int) {
        //
        for _ in 0..<fakeCount{
            let item = EventItem()
            item.name = "测试数据"
            item.time = "08-02 13:30:30"
            item.event = "接近病患未洗手"
            items.append(item)
        }
        
        
    }
    
    init(fakeHistoryCount fakeCount:Int) {
        //
        for _ in 0..<fakeCount{
            let item = EventItem()
            item.name = "Gamdio"
            item.time = "07-20 15:15:15"
            item.event = "接近病患未洗手"
            items.append(item)
        }
        
    }
    
}
