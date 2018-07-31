//
//  MyDepartStaffRateCell.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/1.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  这里是部门员工依从率列表的Cell

import UIKit

class MyDepartStaffRateCell : UITableViewCell{
    @IBOutlet var labelRole:UILabel!
    @IBOutlet var labelName:UILabel!
    @IBOutlet var labelNo:UILabel!
    @IBOutlet var labelRate:UILabel!

    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }

    override init(style: UITableViewCellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        
        self.selectionStyle = .default
        
        labelRole = UILabel(frame:CGRect(x:10, y:10, width:80, height:30))
        labelName = UILabel(frame:CGRect(x:110, y:10, width:80, height:30))
        labelNo   = UILabel(frame:CGRect(x:210, y:10, width:80, height:30))
        labelRate = UILabel(frame:CGRect(x:330, y:10, width:80, height:30))
        
        self.addSubview(labelRole)
        self.addSubview(labelName)
        self.addSubview(labelNo)
        self.addSubview(labelRate)
        
        
    }


}
