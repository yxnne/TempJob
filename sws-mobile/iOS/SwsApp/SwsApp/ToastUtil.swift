//
//  ToastUtil.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/8.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  APP的通用Toast样式设置

import UIKit
import Toaster

class ToastUtil{
    /**
     APP的通用Toast样式设置
     */
    static func makeCommonToast(_ text : String ) -> Toast{
        ToastView.appearance().cornerRadius = 10.0
        ToastView.appearance().bottomOffsetPortrait = 80.0
        return Toast(text: text, duration: Delay.long)
        
    }

}
