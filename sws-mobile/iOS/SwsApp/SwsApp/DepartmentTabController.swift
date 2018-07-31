//
//  DepartmentTabController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/7/27.
//  Copyright © 2017年 iel-mac. All rights reserved.
//

import UIKit
import XLPagerTabStrip

class DepartmentTabController : ButtonBarPagerTabStripViewController{


    
    
    override func viewDidLoad() {
        
        navigationItem.title = "部门详情"
        
        
        self.settings.style.buttonBarItemFont = .systemFont(ofSize: 14)
        self.settings.style.buttonBarItemTitleColor = UIColor.red
        self.settings.style.buttonBarHeight = 20
        self.settings.style.buttonBarBackgroundColor = UIColor.white
        self.settings.style.buttonBarItemBackgroundColor = UIColor.white
        self.settings.style.selectedBarHeight = 2
        self.settings.style.selectedBarBackgroundColor = UIColor.yellow
        
        changeCurrentIndexProgressive = { (oldCell: ButtonBarViewCell?, newCell: ButtonBarViewCell?, progressPercentage: CGFloat, changeCurrentIndex: Bool, animated: Bool) -> Void in
            guard changeCurrentIndex == true else { return }
            oldCell?.label.textColor = .black
            newCell?.label.textColor = UIColor.blue
        }
        
        super.viewDidLoad()
        

        
    }
    
    override public func viewControllers(for pagerTabStripController: PagerTabStripViewController) -> [UIViewController] {
        /*
        let myTabDepart  = UIStoryboard(name: "Main", bundle: nil)
            .instantiateViewController(withIdentifier: "tabDepart") as! DepartmentTabFragmentViewController
        
        let myTabStaff  = UIStoryboard(name: "Main", bundle: nil)
            .instantiateViewController(withIdentifier: "tabStaff") as! StaffTabFragmentViewController
        */
        return [/*myTabDepart, myTabStaff*/]
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

