//
//  MyTabViewController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/3.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  Tab页面的ViewController基类

import UIKit

class MyTabViewController:ViewController{
    
    //设置导航栏样式
    func configNavigateBar()  {
        //更改导航栏的背景色
        navigationController?.navigationBar.barTintColor = UIColor.colorWithHexString(hex: "00C0B9")
        //更改导航栏的文字颜色 赋值号右边创建了一个字典对象，NSForegroundColorAttributeName是一个String类型的
        navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        //导航栏的文字的渲染颜色设置成白色
        navigationController?.navigationBar.tintColor = UIColor.white
    }

}
