//
//  MyEventsCell.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/4.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  事件表用的Cell

import UIKit

class MyEventsCell: UITableViewCell{
    @IBOutlet var labelName:UILabel!
    @IBOutlet var labelEvent:UILabel!
    @IBOutlet var labelTime:UILabel!
    
    //var isColorSetted = false
    
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    override init(style: UITableViewCellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        
        self.selectionStyle = .default
        
        labelName = UILabel(frame:CGRect(x:40, y:10, width:80, height:30))
        labelEvent = UILabel(frame:CGRect(x:130, y:10, width:150, height:30))
        labelTime   = UILabel(frame:CGRect(x:260, y:10, width:150, height:30))
        
        labelName.font = UIFont.systemFont(ofSize: 14)
        labelEvent.font = UIFont.systemFont(ofSize: 14)
        labelTime.font = UIFont.systemFont(ofSize: 14)
        
        self.addSubview(labelName)
        self.addSubview(labelEvent)
        self.addSubview(labelTime)
        
    }
    
    
}
