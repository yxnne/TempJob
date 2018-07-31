//
//  MyPieChartValueFormatter.swift
//  IOS_Test1
//
//  Created by iel-mac on 2017/7/22.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  y轴数值样式格式器

import UIKit
import Charts

class MyPieChartValueFormatter : NSObject,IValueFormatter{
    var callIndex:Int = 0
    
    var dataLabelStrings:[String] = []
    
    func setDataLabelStrings(dataLableStrings strs:[String])  {
        self.dataLabelStrings = strs
    }
    
    func stringForValue(_ value: Double, entry: Charts.ChartDataEntry, dataSetIndex: Int, viewPortHandler: Charts.ViewPortHandler?) -> String{
        
        callIndex = callIndex  % dataLabelStrings.count
        let dataLabel = dataLabelStrings[callIndex]
       
        let percentValue = NumberUtil.makeDoubleToPrecentString(value)
        let toReturnLable = "\(percentValue) \(dataLabel)"
        callIndex += 1
        return toReturnLable
    }
    
}
