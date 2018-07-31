//
//  NewsViewController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/7/26.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  部门依从率页面详情

import UIKit

class DepartRateViewController:UIViewController{
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //设置 navigation 的 title
        navigationItem.title = "部门详情"
        
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
