//
//  MyXibSettingIntervalTimeViewController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/29.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  forSeting ：时间选择的对话框

import UIKit

class MyXibSettingIntervalTimeViewController:UIViewController{
    
    @IBOutlet var dialogTitle: UILabel!

    @IBOutlet var intervalPicker: UIPickerView!
    
    var intervals:[Int] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        intervalPicker.dataSource = self
        intervalPicker.delegate = self
    }

}
/**实现Pick而得协议*/
extension MyXibSettingIntervalTimeViewController:UIPickerViewDelegate, UIPickerViewDataSource{
    
    //设置选择框的列数为3列,继承于UIPickerViewDataSource协议
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    //设置选择框的行数为9行，继承于UIPickerViewDataSource协议
    func pickerView(_ pickerView: UIPickerView,
                    numberOfRowsInComponent component: Int) -> Int {
        return intervals.count
    }
    
    //设置选择框各选项的内容，继承于UIPickerViewDelegate协议
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int,
                    forComponent component: Int) -> String? {
        return "\(intervals[row]) s"
    }
}


/**实现MyChooseProtocal：和宿主通信
 protocol MySettingIntervalProtocal {
 func settingValueString()
 }
 */
extension MyXibSettingIntervalTimeViewController:MySettingIntervalProtocal {
    func settingValueString() ->String{
        let index = intervalPicker.selectedRow(inComponent: 0)
        return "\(intervals[index])"
    }

    func settingValueInt() ->Int{
        let index = intervalPicker.selectedRow(inComponent: 0)
        return intervals[index]
    }

}
