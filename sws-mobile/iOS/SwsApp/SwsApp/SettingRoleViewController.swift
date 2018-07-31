//
//  SettingRoleViewController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/29.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  设置首页的人员类别的页面

import UIKit

class SettingRoleViewController:UITableViewController{

    override func viewDidLoad() {
        super.viewDidLoad()
        navigationItem.title = "首页折线图人员类别"
        configNavigateBar()
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
