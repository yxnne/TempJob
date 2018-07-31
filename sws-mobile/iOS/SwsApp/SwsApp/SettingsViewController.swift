//
//  SettingsViewController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/7/20.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  设置页面的

import UIKit
import Toaster
import PopupDialog

class SettingsViewController : UITableViewController{
    
    //实时事件刷新时间
    @IBOutlet var settingRealTimeUpdateTime: UIView!
    
    //设备信息刷新时间
    @IBOutlet var settingDeviceUpdateTime: UIView!
    
    //设置折线图人员类别
    @IBOutlet var settingRolesTypes: UIView!
    
    //退出登录的
    @IBOutlet var logoutButtonCrad: CardView!

    
    @IBOutlet var labelEventInterval: UILabel!
    
    @IBOutlet var labelDeviceInterval: UILabel!
    
    //代理协议对象
    var selectIntervalImpl : MySettingIntervalProtocal?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        navigationItem.title = "设置"
        configNavigateBar()
        
        prepareSettings()
        
        //设置刷新时间显示基于当前系统内
        labelEventInterval.text = "\(AppGlobal.instance.eventFreshInterval ?? AppConst.DEFAULT_FRESH_INTERVAL_EVENT) s"
        labelDeviceInterval.text = "\(AppGlobal.instance.deviceFreshInterval ?? AppConst.DEFAULT_FRESH_INTERVAL_DEVICE) s"
    }
    
    //准备设置
    func prepareSettings(){
    
        
        //事件刷新时间
        let event_settingGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(doSetEventUpdateClick(_:)))
        settingRealTimeUpdateTime.addGestureRecognizer(event_settingGestureRecognizer)
        
        //设备刷新时间
        let device_settingGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(doSetDeviceUpdateClick(_:)))
        settingDeviceUpdateTime.addGestureRecognizer(device_settingGestureRecognizer)
        
        //折线人员类别
        let role_settingGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(doSetRoleChartUpdateClick(_:)))
        settingRolesTypes.addGestureRecognizer(role_settingGestureRecognizer)
        
        //登出按钮
        let logout_settingGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(doLogoutClick(_:)))
        logoutButtonCrad.addGestureRecognizer(logout_settingGestureRecognizer)
    
    }
    
    //事件刷新时间
    func doSetEventUpdateClick(_ sender :UITapGestureRecognizer){
        //
        let vc = MyXibSettingIntervalTimeViewController(nibName: "TimeIntervalsSetting", bundle: nil)
        vc.intervals = [1,2,3,5,10,20]
        selectIntervalImpl = vc
        //创建弹出对话框
        let popup = PopupDialog(viewController: vc, buttonAlignment: .horizontal, transitionStyle: .bounceDown, gestureDismissal: true)
        //构造取消按钮
        let buttonOne = CancelButton(title: "取消", height: 60) {
            // self.label.text = "You canceled the rating dialog"
        }
        
        //构造确定按钮
        let buttonTwo = DefaultButton(title: "确定", height: 60) {
            //更改些变量值
            let value = self.selectIntervalImpl?.settingValueInt() ?? 3
            
            self.labelEventInterval.text = "\(value) s"
            //设置持久化
            PersistenceUtil.writeUserDefaultsKeyValueInt(forKey: AppConst.KEY_FRESH_INTERVAL_EVENT,
                                                         valueStr: value)
            //更新系统值
            AppGlobal.instance.eventFreshInterval = value
            
        }
        
        //添加按钮
        popup.addButtons([buttonOne, buttonTwo])
        //呈现
        present(popup, animated: true, completion: nil)
    }
    
    //设备刷新时间
    func doSetDeviceUpdateClick(_ sender :UITapGestureRecognizer){
        
        //
        let vc = MyXibSettingIntervalTimeViewController(nibName: "TimeIntervalsSetting", bundle: nil)
        vc.intervals = [15,30,60,90,120,180]
        selectIntervalImpl = vc
        //创建弹出对话框
        let popup = PopupDialog(viewController: vc, buttonAlignment: .horizontal, transitionStyle: .bounceDown, gestureDismissal: true)
        //构造取消按钮
        let buttonOne = CancelButton(title: "取消", height: 60) {
            // self.label.text = "You canceled the rating dialog"
        }
        
        //构造确定按钮
        let buttonTwo = DefaultButton(title: "确定", height: 60) {
            //更改些变量值
            let value = self.selectIntervalImpl?.settingValueInt() ?? 60
            
            self.labelDeviceInterval.text = "\(value) s"
            //持久化
            PersistenceUtil.writeUserDefaultsKeyValueInt(forKey: AppConst.KEY_FRESH_INTERVAL_DEVICE,
                                                         valueStr: value)
            //系统值
            AppGlobal.instance.deviceFreshInterval = value
        }
        
        //添加按钮
        popup.addButtons([buttonOne, buttonTwo])
        //呈现
        present(popup, animated: true, completion: nil)
        
    }
    
    //折线人员类别
    func doSetRoleChartUpdateClick(_ sender :UITapGestureRecognizer){
        
        ToastUtil.makeCommonToast("功能将在下一个版本中完善，敬请期待").show()
        //TODO :设置首页的折线图的人员查询人员类别
        //performSegue(withIdentifier: "settingRoles", sender: self)
    }
    
    //登出action
    func doLogoutClick(_ sender :UITapGestureRecognizer){

        //弹出对话框
        let logoutAlertDialog = UIAlertController(title: "退出登录", message: "您确定要退出登录吗？", preferredStyle: .alert)
        
        let cancelAction = UIAlertAction(title: "取消", style: .cancel, handler: nil)
        logoutAlertDialog.addAction(cancelAction)
        let deleteAction = UIAlertAction(title: "确定", style: .destructive, handler: { (action) -> Void in
            
            self.toLoginPage()
            
        })
        logoutAlertDialog.addAction(deleteAction)
        present(logoutAlertDialog, animated: true, completion: nil)
        
        
    }
    
    //跳转到登录页面
    func toLoginPage(){
        self.dismiss(animated: true, completion: nil)
        
        ToastUtil.makeCommonToast("准备登出").show()
        self.performSegue(withIdentifier: "logout", sender: self)
        //当前用户舍掉
        AppGlobal.instance.currentLoginUser = nil
        
        
    
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
    
    func setTittle() ->String{
        return ""
        
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
/**
 协议：设置间隔时间的协议
 */
protocol MySettingIntervalProtocal {
    func settingValueString()->String
    func settingValueInt() ->Int
}
