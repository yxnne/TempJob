//
//  MyBannerView.swift
//  IOS_Test1
//
//  Created by iel-mac on 2017/7/25.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  实现一个可监听点击事件带上指示器的轮播图控件

/**
 使用方法：banner-----------
 //整一个父UIView作为容器
 let cardView = UIView(frame : CGRect(x: 0, y: 0, width: view.bounds.width, height: 200))
 //构造Banner 指定大小
 let banner = MyBannerView(frame : CGRect(x: 0, y: 0, width: view.bounds.width, height: 200))
 //设置轮播图片
 banner.prepareMyBannerView(imageStrArraysNames: ["img_banner_1","img_banner_2","img_banner_3"])
 //构造指示器，指定指示器大小
 let indicator = UIPageControl(frame: CGRect(x: view.bounds.width/2 - 50, y: 160, width: 100, height: 30))
 //绑定指示器
 banner.bindPageIndicator(indicatorPageController: indicator)
 //轮播图循环起来
 banner.bannerLoop()
 //设置一个点击事件的监听吧
 banner.myViewClickDelegate = self
 //把指示器和banner添加到父布局里面
 cardView.addSubview(banner)
 cardView.addSubview(indicator)
 view.addSubview(cardView)
 */

import UIKit

class MyBannerView :UIScrollView ,UIScrollViewDelegate{
    
    var imageCount:Int = 0              //图片总数
    var pageIndicator:UIPageControl?    //指示器引用
    var currentPage = 0                 //当前页面索引
    var myViewClickDelegate :MyBannerImageClickedDelegate?  //图片点击代理回调
    
    //调用该方法，传入图片
    func prepareMyBannerView(imageStrArraysNames imageName:[String]) {
        
        imageCount = imageName.count
        //遍历image集合，构造布局
        for i in 0...(imageName.count - 1) {
            //建造 UIImageView
            let imageView = UIImageView(frame: CGRect(x:CGFloat(i) * self.bounds.width,y:0,width:self.bounds.width, height:self.bounds.height) )
            //添加点击监听 UITapGestureRecognizer
            imageView.isUserInteractionEnabled = true       //可交互开关打开
            let tapRecongnizer = UITapGestureRecognizer(
                target:self,action:#selector(clickImage(_:))
            )
            imageView.addGestureRecognizer(tapRecongnizer)
            
            self.addSubview(imageView)
            //设置轮播图图片
            imageView.image = UIImage(named: imageName[i])
            
        }
        
        //设置ScrollView的内在容量
        self.contentSize = CGSize(width:CGFloat(imageName.count ) * self.bounds.width, height :self.bounds.height)
        
        self.bounces = false                            //设置吸附属性
        self.isPagingEnabled = true                     //设置书页效果
        self.showsHorizontalScrollIndicator = false     //不要水平默认指示器
        
        //单独创建第n+1张轮播图，和第一张图片是同一张图
        let imageView = UIImageView(frame:CGRect(x:CGFloat(imageName.count) * self.bounds.width,y:0,
                                                 width : self.bounds.width,height : self.bounds.height))
        imageView.isUserInteractionEnabled = true       //可交互开关打开
        let tapRecongnizer = UITapGestureRecognizer(
            target:self,action:#selector(clickImage(_:))
        )
        imageView.addGestureRecognizer(tapRecongnizer)
        imageView.image = UIImage(named:imageName[0])
        
        self.addSubview(imageView)
    }
    
    /**
     和外部的Indicator绑定
     */
    func bindPageIndicator(indicatorPageController indicator :UIPageControl  ) {
        
        pageIndicator = indicator
        pageIndicator?.backgroundColor = UIColor.init(red: 0, green: 0, blue: 0, alpha: 0.25)
        pageIndicator?.numberOfPages = imageCount
        
        //设置代理
        self.delegate = self
        
    }
    
    /**
     创建轮播图定时器
     */
    func bannerLoop() {
        let  timer =  Timer.scheduledTimer(timeInterval: 3, target: self, selector: #selector(self.timerManager), userInfo: nil, repeats: true)
        
        //这句话实现多线程，如果你的ScrollView是作为TableView的headerView的话，在拖动tableView的时候让轮播图仍然能轮播就需要用到这句话
        RunLoop.current.add(timer, forMode: RunLoopMode.commonModes)
        
    }
    //定时器回调
    func timerManager() {
        //设置偏移量
        self.setContentOffset(CGPoint(x:self.contentOffset.x + self.bounds.width, y:0), animated: true)
        //当偏移量达到最后一张的时候，让偏移量置零
        if self.contentOffset.x == CGFloat(self.bounds.width) * CGFloat(imageCount) {
            self.contentOffset = CGPoint(x:0,y: 0)
        }
    }
    
    //手动翻页调用
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        let cPage = self.contentOffset.x / self.bounds.width
        if let indicator = pageIndicator{
            indicator.currentPage = Int(cPage)
            currentPage = Int(cPage)
        }
        currentPage %= imageCount
    }
    
    //自动播放时，调用该方法
    func scrollViewDidEndScrollingAnimation(_ scrollView: UIScrollView) {
        currentPage += 1
        if let indicator = pageIndicator{
            indicator.currentPage = currentPage % imageCount
            
        }
        
        currentPage %= imageCount
    }
    
    //图片的点击监听会调用
    func clickImage(_ sender :UITapGestureRecognizer){
        
        //print(#function)
        if let clickDelegate = self.myViewClickDelegate{
            clickDelegate.imageClicked(clickItem: currentPage)
            
        }
    }
    
    //各种位置归零
    func resetBannerPosition(){
        //当前页面索引
        currentPage  = 0
        //指示器
        pageIndicator?.currentPage = 0
        //重置偏移量
        self.contentOffset = CGPoint(x:0,y: 0)
    
    }
    
}

/**
 这个东西是图片点击的接口协议
 */
public protocol MyBannerImageClickedDelegate : NSObjectProtocol {
    
    func imageClicked(clickItem item :Int) -> Void
}

