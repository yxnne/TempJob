//
//  DevicePageViewController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/2.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  设备信息页面

import UIKit

class DevicePageViewController:MyTabViewController{
    
    //设备数量的  Label  outlet
    @IBOutlet var labelApTotalCount: UILabel!       //AP
    @IBOutlet var labelApTerribleCount: UILabel!
    
    @IBOutlet var labelCardTotalCount: UILabel!     //Card
    @IBOutlet var labelCardTerribleCount: UILabel!
    
    @IBOutlet var labelBedTotalCount: UILabel!      //Bed
    @IBOutlet var labelBedTerribleCount: UILabel!
    
    @IBOutlet var labelAccessTotalCount: UILabel!   //Access
    @IBOutlet var labelAccessTerribleCount: UILabel!
    
    @IBOutlet var labelBottomTotalCount: UILabel!   //Bottom
    @IBOutlet var labelBottomTerribleCount: UILabel!
    
    var checkDeviceOverAllBiz:DeviceBusiness?
    //数据模型
    //AP
    var apTotalCount:Int = 0{
        didSet{
           labelApTotalCount.text = "\(apTotalCount)"
        }
    }
    var apTerribleCount:Int = 0{
        didSet{
            labelApTerribleCount.text = "\(apTerribleCount)"
            if apTerribleCount != 0{
                labelApTerribleCount.textColor = UIColor.red
            }
        }
    }
    
    //Card
    var cardTotalCount:Int = 0{
        didSet{
            labelCardTotalCount.text = "\(cardTotalCount)"
        }
    }
    var cardTerribleCount:Int = 0{
        didSet{
            labelCardTerribleCount.text = "\(cardTerribleCount)"
            if cardTerribleCount != 0{
                labelCardTerribleCount.textColor = UIColor.red
            }
        }
    }
    //Bed
    var bedTotalCount:Int = 0{
        didSet{
            labelBedTotalCount.text = "\(bedTotalCount)"
        }
    }
    var bedTerribleCount:Int = 0{
        didSet{
            labelBedTerribleCount.text = "\(bedTerribleCount)"
            if bedTerribleCount != 0{
                labelBedTerribleCount.textColor = UIColor.red
            }
        }
    }
    //Door
    var doorTotalCount:Int = 0{
        didSet{
            labelAccessTotalCount.text = "\(doorTotalCount)"
        }
    }
    var doorTerribleCount:Int = 0{
        didSet{
            labelAccessTerribleCount.text = "\(doorTerribleCount)"
            if doorTerribleCount != 0{
                labelAccessTerribleCount.textColor = UIColor.red
            }
        }
    }
    //Bottle
    var bottleTotalCount:Int = 0{
        didSet{
            labelBottomTotalCount.text = "\(bottleTotalCount)"
        }
    }
    var bottleTerribleCount:Int = 0{
        didSet{
            labelBedTerribleCount.text = "\(bottleTerribleCount)"
            if bottleTerribleCount != 0{
                labelBedTerribleCount.textColor = UIColor.red
            }
        }
    }
    
    var myTimer:Timer?
    
    //设备状态的提示 image  outlet
    @IBOutlet var imgApState: UIImageView!
    @IBOutlet var imgCardState: UIImageView!
    @IBOutlet var imgBedState: UIImageView!
    @IBOutlet var imgAcessState: UIImageView!
    @IBOutlet var imgBottomState: UIImageView!
    

    override func viewDidLoad() {
        super.viewDidLoad()
        configNavigateBar()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        if checkDeviceOverAllBiz == nil{
            checkDeviceOverAllBiz = DeviceBusiness()
            checkDeviceOverAllBiz?.doCheckDeviceState(vc: self, departIds: (AppGlobal.instance.currentLoginUser?.departIds)!)
        }
        
        //开启定时查询了
        let intervalTime:Int = AppGlobal.instance.deviceFreshInterval ?? AppConst.DEFAULT_FRESH_INTERVAL_DEVICE
        myTimer =  Timer.scheduledTimer(timeInterval:TimeInterval(intervalTime),
                                        target:self,
                                        selector:#selector(doTimmerTask),
                                        userInfo:nil,repeats:true)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewDidDisappear(true)
        //关闭定时查询
        myTimer?.invalidate()
    }
    
    //定时的执行体
    func doTimmerTask(){
        checkDeviceOverAllBiz?.doCheckDeviceState(vc: self, departIds: (AppGlobal.instance.currentLoginUser?.departIds)!)
    
    }
    // btn  action
    // AP
    @IBAction func apCardBtnClicked(_ sender: UIButton) {
        print(#function)
        ToastUtil.makeCommonToast("功能还在完善中，敬请期待").show()
    }
    
    // Card
    @IBAction func cardCardBtnClicked(_ sender: UIButton) {
        print(#function)
        ToastUtil.makeCommonToast("功能还在完善中，敬请期待").show()
    }
    
    // Bed
    @IBAction func bedCardBtnClicked(_ sender: UIButton) {
        print(#function)
        ToastUtil.makeCommonToast("功能还在完善中，敬请期待").show()
    }
    
    //Access
    @IBAction func accessCardBtnClicked(_ sender: UIButton) {
        print(#function)
        ToastUtil.makeCommonToast("功能还在完善中，敬请期待").show()
    }
    
    //Bottom
    @IBAction func bottomCardBtnClicked(_ sender: UIButton) {
        print(#function)
        ToastUtil.makeCommonToast("功能还在完善中，敬请期待").show()
    }
   
    
}
