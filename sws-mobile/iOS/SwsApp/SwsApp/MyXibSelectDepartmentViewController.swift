//
//  MyXibSelectDepartmentViewController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/18.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  和Xib相关联的vc，用于选择科室

import UIKit

class MyXibSelectDepartmentViewController:UIViewController{
    
    //var departStrings :[String] = []
    
    var departments:[Department] = []

    @IBOutlet var departPicker: UIPickerView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        departPicker.delegate = self
        departPicker.dataSource = self
        
    }
    
}
/**实现Picker的协议*/
extension MyXibSelectDepartmentViewController:UIPickerViewDelegate, UIPickerViewDataSource{

    //设置选择框的列数为3列,继承于UIPickerViewDataSource协议
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    //设置选择框的行数为9行，继承于UIPickerViewDataSource协议
    func pickerView(_ pickerView: UIPickerView,
                    numberOfRowsInComponent component: Int) -> Int {
        return departments.count
    }
    
    //设置选择框各选项的内容，继承于UIPickerViewDelegate协议
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int,
                    forComponent component: Int) -> String? {
        return departments[row].departName
    }
    

}

/**实现MyChooseProtocal：和宿主通信*/
extension MyXibSelectDepartmentViewController:MyChooseProtocal {
    func choosenString() ->String{
        let index = departPicker.selectedRow(inComponent: 0)
        return departments[index].departName!
    }
    func choosenDepartIds() ->Int{
        let index = departPicker.selectedRow(inComponent: 0)
        return departments[index].departIds!
    }
}
