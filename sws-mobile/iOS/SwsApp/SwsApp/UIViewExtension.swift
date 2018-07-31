//
//  UIViewExtension.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/3.
//  Copyright © 2017年 iel-mac. All rights reserved.
//

import UIKit

extension UIView {
    //增加View的扩展方法
    func addCorner(roundingCorners: UIRectCorner, cornerSize: CGSize) {
        let path = UIBezierPath(roundedRect: bounds, byRoundingCorners: roundingCorners, cornerRadii: cornerSize)
        let cornerLayer = CAShapeLayer()
        cornerLayer.frame = bounds
        cornerLayer.path = path.cgPath
        
        layer.mask = cornerLayer
    }
}
