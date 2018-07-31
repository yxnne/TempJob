//
//  StaffTabFragmentViewController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/7/27.
//  Copyright © 2017年 iel-mac. All rights reserved.
//

import UIKit
import XLPagerTabStrip

class StaffTabFragmentViewController : UIViewController,IndicatorInfoProvider{
    
    override func viewDidLoad() {
        super.viewDidLoad()
        print(#function)
        
        let viewSub = UIView(frame: CGRect(x: 50, y: 50, width: 100, height: 100))
        viewSub.backgroundColor = UIColor.red
        view.addSubview(viewSub)
    }
    
    //实现的是IndicatorInfoProvider协议方法
    func indicatorInfo(for pagerTabStripController: PagerTabStripViewController) -> IndicatorInfo {
        return IndicatorInfo(title: "部门人员依从率")
    }
    
    
}

