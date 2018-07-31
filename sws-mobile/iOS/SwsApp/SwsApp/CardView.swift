//
//  CardView.swift
//  SwsApp
//
//  Created by iel-mac on 2017/7/21.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  一个类似Android的CardView

import UIKit

@IBDesignable
class CardView: UIView {
    
    @IBInspectable var cornerRadius: CGFloat = 2
    
    @IBInspectable var shadowOffsetWidth: Int = 2
    @IBInspectable var shadowOffsetHeight: Int = 2
    @IBInspectable var shadowColor: UIColor? = UIColor.black
    @IBInspectable var shadowOpacity: Float = 0.5
    
    override func layoutSubviews() {
        //圆角
        layer.cornerRadius = cornerRadius
        layer.masksToBounds = false
        //颜色
        layer.shadowColor = shadowColor?.cgColor
        //阴影偏移量
        layer.shadowOffset = CGSize(width: shadowOffsetWidth, height: shadowOffsetHeight);
        //不透明度
        layer.shadowOpacity = shadowOpacity
        //路径
        let shadowPath = UIBezierPath(roundedRect: bounds, cornerRadius: cornerRadius)
        layer.shadowPath = shadowPath.cgPath
    }
    
}
