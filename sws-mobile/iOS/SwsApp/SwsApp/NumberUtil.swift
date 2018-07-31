//
//  NumberUtil.swift
//  SwsApp
//
//  Created by iel-mac on 2017/7/28.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  处理些数值相关的工具

import UIKit

class NumberUtil{
    /**
     把double类型的转换成保留一位小数的百分之多少的字符串
     */
    static func makeDoubleToPrecentString(_ nbr:Double) -> String {
        
        var strNbr = String(format: "%.1f", nbr)
        strNbr.append("%")
        return strNbr
    }


}
