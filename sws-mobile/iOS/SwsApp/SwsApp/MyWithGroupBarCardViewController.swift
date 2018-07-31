//
//  MyWithGroupBarCardViewController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/2.
//  Copyright © 2017年 iel-mac. All rights reserved.
//

import UIKit
import LinearProgressView

class MyWithGroupBarCardViewController:MySecondaryPageBaseViewController{
    
    //Bar：LinearProgressBar：四个条形时机图
    @IBOutlet var progressBarOccasionBeforeContactPatient: LinearProgressView!      //接触患者前
    @IBOutlet var progressBarOccasionBeforeAespsis: LinearProgressView!             //无菌操作前
    @IBOutlet var progressBarOccasionAfterEnvironment: LinearProgressView!          //接触环境后
    @IBOutlet var progressBarOccasionAfterContactPatient: LinearProgressView!       //接触患者后
    //时机值Label
    var LabelOccasionBeforeContactPatient:UILabel!
    var LabelOccasionBeforeAespsis:UILabel!
    var LabelOccasionAfterEnvironment:UILabel!
    var LabelOccasionAfterContactPatient:UILabel!


    //添加Bar
    func makeBarToCard(parentViewToAdd parentView:UIView){
        //添加进度条的Label
        let labelOccasionBeforeContactPatient = UILabel(frame:CGRect(x: 40, y: 30, width: 200, height: 20))
        labelOccasionBeforeContactPatient.text = "接触患者前"
        labelOccasionBeforeContactPatient.font = UIFont.systemFont(ofSize: 12)
        labelOccasionBeforeContactPatient.textColor = UIColor.darkGray
        
        let labelOccasionBeforeAespsis = UILabel(frame:CGRect(x: 40, y: 75, width: 200, height: 20))
        labelOccasionBeforeAespsis.text = "无菌操作前"
        labelOccasionBeforeAespsis.font = UIFont.systemFont(ofSize: 12)
        labelOccasionBeforeAespsis.textColor = UIColor.darkGray
        
        let labelOccasionAfterEnvironment = UILabel(frame:CGRect(x: 40, y: 120, width: 200, height: 20))
        labelOccasionAfterEnvironment.text = "接触环境后"
        labelOccasionAfterEnvironment.font = UIFont.systemFont(ofSize: 12)
        labelOccasionAfterEnvironment.textColor = UIColor.darkGray
        
        let labelOccasionAfterContactPatient = UILabel(frame:CGRect(x: 40, y: 165, width: 200, height: 20))
        labelOccasionAfterContactPatient.text = "接触患者后"
        labelOccasionAfterContactPatient.font = UIFont.systemFont(ofSize: 12)
        labelOccasionAfterContactPatient.textColor = UIColor.darkGray
        //添加这些label
        parentView.addSubview(labelOccasionBeforeContactPatient)
        parentView.addSubview(labelOccasionBeforeAespsis)
        parentView.addSubview(labelOccasionAfterEnvironment)
        parentView.addSubview(labelOccasionAfterContactPatient)
        
        //进度条
        progressBarOccasionBeforeContactPatient.frame   = CGRect(x: 110, y: 30, width: 200, height: 20)
        
        progressBarOccasionBeforeAespsis.frame          = CGRect(x: 110, y: 75, width: 200, height: 20)
        
        progressBarOccasionAfterEnvironment.frame       = CGRect(x: 110, y: 120, width: 200, height: 20)
        
        progressBarOccasionAfterContactPatient.frame    = CGRect(x: 110, y: 165, width: 200, height: 20)
        //进度条样式
        progressBarOccasionBeforeContactPatient.trackColor = UIColor.colorWithHexString(hex: "#32D6C5")
        progressBarOccasionBeforeContactPatient.barInset = 3
        progressBarOccasionBeforeContactPatient.animationDuration = 2
        
        progressBarOccasionBeforeAespsis.trackColor = UIColor.colorWithHexString(hex: "#32D6C5")
        progressBarOccasionBeforeAespsis.barInset = 3
        progressBarOccasionBeforeAespsis.animationDuration = 2
        
        progressBarOccasionAfterEnvironment.trackColor = UIColor.colorWithHexString(hex: "#32D6C5")
        progressBarOccasionAfterEnvironment.barInset = 3
        progressBarOccasionAfterEnvironment.animationDuration = 2
        
        progressBarOccasionAfterContactPatient.trackColor = UIColor.colorWithHexString(hex: "#32D6C5")
        progressBarOccasionAfterContactPatient.barInset = 3
        progressBarOccasionAfterContactPatient.animationDuration = 2
        
        parentView.addSubview(progressBarOccasionBeforeContactPatient)
        parentView.addSubview(progressBarOccasionBeforeAespsis)
        parentView.addSubview(progressBarOccasionAfterEnvironment)
        parentView.addSubview(progressBarOccasionAfterContactPatient)
        //时机值标签
        LabelOccasionBeforeContactPatient  = UILabel(frame: CGRect(x: 330, y: 30, width: 60, height: 20))
        LabelOccasionBeforeContactPatient.font = UIFont.systemFont(ofSize: 12)
        LabelOccasionBeforeContactPatient.textColor = UIColor.darkGray
        
        LabelOccasionBeforeAespsis  = UILabel(frame: CGRect(x: 330, y: 75, width: 60, height: 20))
        LabelOccasionBeforeAespsis.font = UIFont.systemFont(ofSize: 12)
        LabelOccasionBeforeAespsis.textColor = UIColor.darkGray
        
        LabelOccasionAfterEnvironment  = UILabel(frame: CGRect(x: 330, y: 120, width: 60, height: 20))
        LabelOccasionAfterEnvironment.font = UIFont.systemFont(ofSize: 12)
        LabelOccasionAfterEnvironment.textColor = UIColor.darkGray
        
        LabelOccasionAfterContactPatient  = UILabel(frame: CGRect(x: 330, y: 165, width: 60, height: 20))
        LabelOccasionAfterContactPatient.font = UIFont.systemFont(ofSize: 12)
        LabelOccasionAfterContactPatient.textColor = UIColor.darkGray
        
        parentView.addSubview(LabelOccasionBeforeContactPatient)
        parentView.addSubview(LabelOccasionBeforeAespsis)
        parentView.addSubview(LabelOccasionAfterEnvironment)
        parentView.addSubview(LabelOccasionAfterContactPatient)
        
        
    }
    //设置时机值 ，各个progressBar继承自父类
    func setOccasionData(beforeContactPatient beforeContactPatientData:Double,
                         beforeAespsis beforeAespsisData : Double,
                         afterEnvironment afterEnvironmentData :Double,
                         afterContactPatient afterContactPatientData:Double){
        //设置bar
        progressBarOccasionBeforeContactPatient.setProgress(Float(beforeContactPatientData), animated: true)
        
        progressBarOccasionBeforeAespsis.setProgress(Float(beforeAespsisData), animated: true)
        
        progressBarOccasionAfterEnvironment.setProgress(Float(afterEnvironmentData), animated: true)
        
        progressBarOccasionAfterContactPatient.setProgress(Float(afterContactPatientData), animated: true)
        //设置bar的显示值
        //保留一位小数
        let beforeContactPatientDataString = NumberUtil.makeDoubleToPrecentString(beforeContactPatientData)
        let beforeAespsisDataString = NumberUtil.makeDoubleToPrecentString(beforeAespsisData)
        let afterEnvironmentDataString = NumberUtil.makeDoubleToPrecentString(afterEnvironmentData)
        let afterContactPatientDataString = NumberUtil.makeDoubleToPrecentString(afterContactPatientData)
        
        LabelOccasionBeforeContactPatient.text = beforeContactPatientDataString
        LabelOccasionBeforeAespsis.text = beforeAespsisDataString
        LabelOccasionAfterEnvironment.text = afterEnvironmentDataString
        LabelOccasionAfterContactPatient.text = afterContactPatientDataString
        
    }

}
