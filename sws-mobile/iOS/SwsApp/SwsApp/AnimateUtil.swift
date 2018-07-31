//
//  AnimateUtil.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/8.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  动画的工具类

import UIKit

class AnimateUtil{

    /**
     给一个view水平震动动画
     */
    static func springHorizental(toView view :UIView){
        let x = view.center.x
        let y = view.center.y
        let startX = x + 10
        view.center = CGPoint(x:startX,y: y )
        UIView.animate(withDuration: 1,
                       delay:0,
                       usingSpringWithDamping: 0.2,//阻尼系数
            initialSpringVelocity: 0,
            animations: {
                view.center = CGPoint(x:startX - 10,y: y )
                view.layoutIfNeeded()
        },
            completion:{ _ in //这是有一个参数的闭包
                view.center = CGPoint(x:x,y: y )
        })

    
    }
}
