//
//  ProfilePageViewController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/7/20.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  用户信息展示页

import UIKit

class ProfilePageViewController: UITableViewController {
    
    //控件的OutLet引用
    //label
    @IBOutlet var labelUserName: UILabel!

    @IBOutlet var labelRate: UILabel!
    
    @IBOutlet var labelRankInDepart: UILabel!
    
    @IBOutlet var labelDepartment: UILabel!
    
    @IBOutlet var labelRole: UILabel!
    
    @IBOutlet var labelCardNo: UILabel!
    
    @IBOutlet var labelPhoteNo: UILabel!
    
    //image
    @IBOutlet var imageUserPortrait: UIImageView!
    
    //view （作为 button用）
    
    @IBOutlet var viewLeftAsBtn: UIView!
    
    @IBOutlet var viewRightAsBtn: UIView!
    
    //重写viewdidload 做一些准备工作和数据加载
    override func viewDidLoad() {
        super.viewDidLoad()
        
        configNavigateBar()
        
        addViewBtnTapEvent()
        
        putUserDatas2Views()
        
        
    }
    
    //配置下本页的navigationBar
    func configNavigateBar()  {
        //更改导航栏的背景色
        navigationController?.navigationBar.barTintColor = UIColor.colorWithHexString(hex: "00C0B9")
        //更改导航栏的文字颜色 赋值号右边创建了一个字典对象，NSForegroundColorAttributeName是一个String类型的
        navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        
    }
    
    
    
    //给viewBtn增加点击监听
    func addViewBtnTapEvent()  {
        let viewBtnTapRecognizerLeft = UITapGestureRecognizer(
            target:self,action:#selector(respondTap(_:))
        )
        
        let viewBtnTapRecognizerRight = UITapGestureRecognizer(
            target:self,action:#selector(respondTap(_:))
        )
        // 在两个view上添加手势识别器 两个view的点击事件是一样的
        viewLeftAsBtn.addGestureRecognizer(viewBtnTapRecognizerLeft)
        viewRightAsBtn.addGestureRecognizer(viewBtnTapRecognizerRight)
        
    }
    //tap点击后的回调
    func respondTap(_ sender:UITapGestureRecognizer){
        print(#function)
    }
    
    //向view里面填充数据
    func putUserDatas2Views(){
    
        //放数据
        //TODO 弄个CurrentUser 实体对象然后放数据
        labelUserName.text = AppGlobal.instance.currentLoginUser?.userName
        labelDepartment.text = AppGlobal.instance.currentLoginUser?.department
        
        //放图片，变成圆形
        let imgCirclePortrait = UIImage(named:"img_profile_tmp_doctor@2x")?.toCircle()
        imageUserPortrait.image = imgCirclePortrait
    
    }
    
    

    
    
}
