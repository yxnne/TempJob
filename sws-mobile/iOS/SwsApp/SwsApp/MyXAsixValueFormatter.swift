//
//  MyXAsixValueFormatter.swift
//  SwsApp
//
//  Created by iel-mac on 2017/7/29.
//  Copyright © 2017年 iel-mac. All rights reserved.
//

import UIKit
import Charts

class MyXAsixRadarValueFormatter:NSObject,IAxisValueFormatter{

    var myLabelStrings:[String ] = []
    
    func setLabelStrings(lableStrings strings :[String ]){
        myLabelStrings = strings
    }
    
    func stringForValue(_ value: Double,
                        axis: AxisBase?) -> String{
        let index = Int(value) % myLabelStrings.count
        return myLabelStrings[index]
    }

}
