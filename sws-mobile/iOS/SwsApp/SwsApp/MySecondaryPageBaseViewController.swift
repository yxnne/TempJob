//
//  MySecondaryPageBaseViewController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/7/28.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  二级页面的父类

import UIKit

class MySecondaryPageBaseViewController:UIViewController{
    
    override func viewDidLoad() {
        super.viewDidLoad()
        navigationItem.title = setTittle()
        configNavigateBar()
    }
    //设置导航栏样式
    func configNavigateBar()  {
        //更改导航栏的背景色
        navigationController?.navigationBar.barTintColor = UIColor.colorWithHexString(hex: "00C0B9")
        //更改导航栏的文字颜色 赋值号右边创建了一个字典对象，NSForegroundColorAttributeName是一个String类型的
        navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        //导航栏的文字的渲染颜色设置成白色
        navigationController?.navigationBar.tintColor = UIColor.white
        
    }
    
    func setTittle() ->String{
        return ""
    
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        //页面就要出现的时候，隐藏掉tabbar
        tabBarController?.tabBar.isHidden = true
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(true)
        //页面消失之前，恢复显示tabbar
        tabBarController?.tabBar.isHidden = false
    }
}
