//
//  MCustomBarController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/7/19.
//  Copyright © 2017年 iel-mac. All rights reserved.
//

import UIKit

class MCustomBarController :UITabBarController{

    override func viewDidLoad() {
        super.viewDidLoad()
        createCustomTab()
    }
    
    //自定义TabBar
    func createCustomTab(){
        
        let mainPageViewController = MainPageViewController()
        let mainItem:UIBarItem = UIBarItem()
        mainItem.image = UIImage(named:"icon_tab_main_selected")
        mainItem.title = "首页"
        mainPageViewController.tabBarItem = mainItem as! UITabBarItem
        
        //装入
        let tabBarViewControllersArray = [mainPageViewController]
        
        self.viewControllers = tabBarViewControllersArray
        
    
    }

}
