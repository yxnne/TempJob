//
//  MyBackgroudImageViewController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/7.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  有背景的Viewcontroller

import UIKit

class MyBackgroudImageViewController:ViewController{


    //添加背景的方法
    func  addBaseViewBackgroud(picName imgName:String ,isNeedBlur needBlur : Bool){
        
        let imgBg = UIImage(named:imgName)
        
        let imgViewBg = UIImageView(image: imgBg)
        imgViewBg.frame = CGRect(x: 0, y: 0, width: view.bounds.width, height: view.bounds.height)
        view.addSubview(imgViewBg)
        
        if(needBlur){
            //添加模糊视图
            //首先创建一个模糊效果
            let blurEffect = UIBlurEffect(style:.prominent)
            //接着创建一个承载模糊效果的视图
            let blurView = UIVisualEffectView(effect: blurEffect)
            //设置模糊视图的大小（全屏）
            blurView.frame.size = CGSize(width: view.frame.width, height: view.frame.height)
            //更改背景
            view.addSubview(imgViewBg)
            
            view.addSubview(blurView)
            view.sendSubview(toBack: blurView)
            
            view.sendSubview(toBack: imgViewBg)
        }else{
            //更改背景
            view.addSubview(imgViewBg)
            
            view.sendSubview(toBack: imgViewBg)
        }
        
    }



}
