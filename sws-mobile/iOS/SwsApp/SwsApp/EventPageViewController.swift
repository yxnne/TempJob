//
//  EventPageViewController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/3.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  实时事件(今日)

import UIKit
import DGElasticPullToRefresh

class EventPageViewController: MyTabViewController{

    
    @IBOutlet var bgView: UIView!
    
    var tableView:UITableView!
    
    var eventItems:EventList!
    
    var myTimer:Timer?
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //父类中配置导航的样式的方法
        configNavigateBar()
        //添加假数据
        eventItems = EventList(fakeEventCount: 40)
        //添加表头
        makeTableHeaderAndAddIt(parentViewToAdd:bgView)
        //添加表
        makeTableViewAndAddIt(parentViewToAdd:bgView)
        
        //
        
        //2.调用业务方法
        /*
        EventBusiness().doCheckEvent(vc: self,
                                     departIds: (AppGlobal.instance.currentLoginUser?.departIds)!,
                                     eventTypeId: EventBusiness.ALL_EVENT_SELECTED_ID,
                                     staffTypeId: EventBusiness.ALL_STAFF_SELECTED_ID,
                                     startTimeStr: DateUtil.getDateString_Now(),
                                     endTimeStr: DateUtil.getDateString_Now(),
                                     pageNbr: 1)
         */
  
    }
    //注册Timer
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        let intervalTime:Int = AppGlobal.instance.eventFreshInterval ?? AppConst.DEFAULT_FRESH_INTERVAL_EVENT   //系统中设置的时间，要是没有就默认
        myTimer =  Timer.scheduledTimer(timeInterval:TimeInterval(intervalTime),
                                        target:self,
                                        selector:#selector(doTimmerTask),
                                        userInfo:nil,repeats:true)
    }
    //注销Timer
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(true)
        myTimer?.invalidate()
        
    }
    //事件调度任务回调
    func doTimmerTask(){
        print(#function)
        EventBusiness().doCheckEvent(vc: self,
                                     departIds: (AppGlobal.instance.currentLoginUser?.departIds)!,
                                     eventTypeId: EventBusiness.ALL_EVENT_SELECTED_ID,
                                     staffTypeId: EventBusiness.ALL_STAFF_SELECTED_ID,
                                     startTimeStr: DateUtil.getDateString_Now(),
                                     endTimeStr: DateUtil.getDateString_Now(),
                                     pageNbr: 1)
    }
    
    /*表头的样式*/
    func makeTableHeaderAndAddIt(parentViewToAdd parentView :UIView){
        //表头的背景
        let bgHeaderView = UIView(frame :CGRect(x:20,y:26,width:view.bounds.width - 30 ,height:45))
        //bgHeaderView.backgroundColor = UIColor.colorWithHexString(hex: "#00C0B9")
        bgHeaderView.backgroundColor = UIColor.lightGray
        //计算位置
        let everyWidth = parentView.bounds.width / 3 + 10
        let labelsCommenCenterY = bgHeaderView.bounds.height/2               //中心点y的位置
        //中心点x的位置
        let offset = 15.0
        let labelNameCenterX = everyWidth / 2
        let labelEventCenterX = labelNameCenterX + everyWidth - CGFloat( offset)
        let labelTimeCenterX   = labelNameCenterX + everyWidth * 2 - CGFloat( offset)
        //列1 姓名
        let labelName = UILabel(frame:CGRect(x:0,y:0,width:40,height:20))
        labelName.text = "姓名"
        labelName.font = UIFont.systemFont(ofSize:15)
        labelName.textColor = UIColor.white
        labelName.center = CGPoint(x:labelNameCenterX ,y: labelsCommenCenterY)
        bgHeaderView.addSubview(labelName)
        
        //列2 事件
        let labelEvent = UILabel(frame:CGRect(x:0,y:0,width:40,height:20))
        labelEvent.text = "事件"
        labelEvent.font = UIFont.systemFont(ofSize:15)
        labelEvent.textColor = UIColor.white
        labelEvent.center = CGPoint(x:labelEventCenterX ,y: labelsCommenCenterY)
        bgHeaderView.addSubview(labelEvent)
        
        //列3 时间
        let labelTime = UILabel(frame:CGRect(x:0,y:0,width:50,height:20))
        labelTime.text = "时间"
        labelTime.font = UIFont.systemFont(ofSize:15)
        labelTime.textColor = UIColor.white
        labelTime.center = CGPoint(x:labelTimeCenterX ,y: labelsCommenCenterY)
        bgHeaderView.addSubview(labelTime)
        //实现圆角增加一个扩展方法中定义的层
        bgHeaderView.addCorner(roundingCorners: UIRectCorner.topLeft, cornerSize: CGSize(width:20, height:20))

        parentView.addSubview(bgHeaderView)
    }
    
    //添加表 以及下拉刷新控件
    func makeTableViewAndAddIt(parentViewToAdd parentView :UIView)  {
        //
        let tableFrame = CGRect(x:20,y:71,width:view.bounds.width - 30 ,height:parentView.bounds.height - 10)
        tableView = UITableView(frame:tableFrame,style:.plain)
        tableView.delegate = self
        tableView.dataSource = self
        
        tableView.register(MyEventsCell.self, forCellReuseIdentifier: "eventToadyCell")  // 注册Cell
        tableView.rowHeight = 40
        parentView.addSubview(tableView)
        
        //下拉刷新控件
        let loadingView = DGElasticPullToRefreshLoadingViewCircle()
        loadingView.tintColor = UIColor.white
        tableView.dg_addPullToRefreshWithActionHandler({ [weak self] () -> Void in
            DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + Double(Int64(1.5 * Double(NSEC_PER_SEC))) / Double(NSEC_PER_SEC), execute: {
                self?.tableView.dg_stopLoading()
                //执行
                print("下拉回调")
            })
            }, loadingView: loadingView)
        tableView.dg_setPullToRefreshFillColor( UIColor.lightGray)
        tableView.dg_setPullToRefreshBackgroundColor(tableView.backgroundColor!)

        
    }
    
    //今日暂时没有事件
    func infoTodayNoEventByNow(){
        ToastUtil.makeCommonToast("今日暂时没有事件").show()
    }
    
    //使用下拉刷新框架的时候，一定要带上这个析构
    deinit {
        tableView.dg_removePullToRefresh()
    }


}


//代理的Table的协议
extension EventPageViewController:UITableViewDataSource{
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    //返回数据项的个数方法
    func tableView(_ tableView: UITableView,
                   numberOfRowsInSection section: Int) -> Int {
        return eventItems.items.count
    }
    //放数据
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "eventToadyCell",for:indexPath) as! MyEventsCell
        let item = eventItems.items[indexPath.row]

        cell.labelName.text = item.name
        cell.labelEvent.text = item.event
        cell.labelTime.text = item.time
        //更改下背景色
        if indexPath.row % 2 == 0{
            cell.backgroundColor = UIColor.colorWithHexString(hex: "#98EAE2",alpha:0.2)
            
        }else{
            cell.backgroundColor = UIColor.white
        }
        
        return cell
        }
    
   }

//代理的Table的点击事件
extension EventPageViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        //暂时不响应店家事件
    }
    
}

