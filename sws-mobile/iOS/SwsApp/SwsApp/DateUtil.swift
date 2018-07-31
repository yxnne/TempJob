//
//  DateUtil.swift
//  IOS_Test1
//
//  Created by iel-mac on 2017/7/24.
//  Copyright © 2017年 iel-mac. All rights reserved.
//

import UIKit

class DateUtil{
    
    //static let ONE_DAY_TIME_MS = 1000 * 60 * 60 * 24
    
    
    /**时间格式器 Date -> "4-25"*/
    static var DATE_FORMATTER_MD : DateFormatter {
        get{
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "MM-dd"
            return dateFormatter
        }
    }
    
    /**时间格式器 Date -> "2017-4-25"*/
    static var DATE_FORMATTER_YMD : DateFormatter {
        get{
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd"
            return dateFormatter
        }
    }
    
    /**
     得到今天所对应的日期，并且转换成字符串类似"2017-7-18"
     */
    static func getDateString_Now()->String {
        let now = Date()
        let strToday = DATE_FORMATTER_YMD.string(from: now)
        return strToday
    }
    /**
     得到一周前的一天的 YMD字符串
     */
    static func getDateString_OneWeekBefore()->String {
        let weekBeforeDate = getDateByAddingDays(baseDate:Date(),addingOffsets:-7)
        return DATE_FORMATTER_YMD.string(from: weekBeforeDate)
    }
    
    
    /**
     Date -> Date 得到x天前的时间
     */
    static func getDateByAddingDays(baseDate date :Date , addingOffsets offset:Int) ->Date{
        
        let calendar = Calendar.current
        var offSetComponents = DateComponents.init()
        offSetComponents.day = offset
        let dateBefore = calendar.date(byAdding: offSetComponents, to: date)
        
        return dateBefore!
    }
    
    
    /**
     Date -> Date 得到一个月前的时间
     */
    static func getDateBeforeOneMonthByNow() ->Date{
        return getDateByAddingDays(baseDate:Date(),addingOffsets:-30)
    }
    
    /**
     该方法是得到一周的时间标签并且把它转换成字符串数组
     例如：4-26，4-27，4-28，4-29 ...(一周时间的)
     */
    static func getOneWeekDateStringsEndInTody()-> [String]{
        
        //当前时间
        var now = Date()
        //当前日历
        let calendar = Calendar.current
        
        //格式化今天
        let strToday = DATE_FORMATTER_MD.string(from: now)
        var week :[String] = []
        
        week.append(strToday)
        
        //设置时间向前推的插补
        var offSetComponents = DateComponents.init()
        offSetComponents.day = -1
        
        //向前再推算六天
        for _ in 0..<6{
            //向前推算一天，添加到week里面
            let  dayBefore = calendar.date(byAdding: offSetComponents, to: now)
            now = dayBefore!
            week.insert(DATE_FORMATTER_MD.string(from: dayBefore!), at: 0)
            
        }
        
        return week
    }
    

    
    /**
     根据字符串（2017-7-18）
     得到30天对应的日期，并且转换成字符串类似"2017-6-18"
     */
    static func getDateString_DaysBefore(baseDateStr baseDateString :String ,beforeDays days :Int )->String {
        
        let baseDate = DATE_FORMATTER_YMD.date(from: baseDateString)
        let calendar = Calendar.current
        var offsetComponent = DateComponents.init()
        offsetComponent.day = -days
        
        if let base = baseDate{
            let beforeDate = calendar.date(byAdding: offsetComponent, to: base)
            
            let beforeDateString  = DATE_FORMATTER_YMD.string(from: beforeDate!)
            return beforeDateString
        }else{
            return ""
        }
    }
    
}
