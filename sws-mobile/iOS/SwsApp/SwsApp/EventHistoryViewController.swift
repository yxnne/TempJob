//
//  EventHistoryViewController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/3.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  历史事件页面

import UIKit
import DGElasticPullToRefresh

class EventHistoryViewController:MySecondaryPageBaseViewController{
    
    //@IBOutlet var btnShowCheckView: UIButton!//bgView上的显示 CheckView 的 按钮

    
    @IBOutlet var bgView: UIView!
    
    @IBOutlet var barItemCheck: UIBarButtonItem!
    
    @IBOutlet var checkView: CardView!                  //查询的类似对话框卡片的东西
    
    //查询事件的选择器
    @IBOutlet var eventTypePicker: UIPickerView!
    //人员的选择器
    @IBOutlet var roleTypePicker: UIPickerView!
    //开始时间选择器
    @IBOutlet var startTimeDataPicker: UIDatePicker!
    //结束时间选择器
    @IBOutlet var endTimeDataPicker: UIDatePicker!
    
    //事件类型数组
    var eventType :[EventType] = []
    //人员类别数组
    var staffType :[StaffType] = []
 
    //当前设置
    var currentEventTypeId = ""
    var currentStaffTypeId = 100
    var currentStartTime = ""
    var currentEndTime = ""
    
    var tableView:UITableView!
    
    var eventItems:EventList!
    
    //显示查询窗口
    @IBAction func clickCheckBtn(_ sender: UIButton) {
        //给个动画 CheckView 显示
        makeCheckViewOccurAnimate()
    }
    
    //点击取消的回调事件
    @IBAction func clickCloseCheckView(_ sender: UIButton) {
        // CheckView 消失
        makeCheckViewDissapearAnimate()
    }
    
    //点击"确定"(确定查询条件)的回调事件
    @IBAction func toCheckClick(_ sender: UIButton) {
        //假数据添加
        eventItems = EventList(fakeEventCount:100)
        //查询业务
        doCheckBiz()
        makeCheckViewDissapearAnimate()
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        makeEventAndStaffTypes()
        //假数据
        //eventItems = []
        eventItems = EventList(fakeEventCount:100)
        //添加表
        makeTableViewAndAddIt(parentViewToAdd:bgView)
        //添加表头
        makeTableHeaderAndAddIt(parentViewToAdd:bgView)

        //checkView在最上面一层
        bgView.bringSubview(toFront: checkView)
        
        makeCheckViewOccurAnimate()
        //准备选择器
        prepareCheckConditionPickers()
    }

    //准备选择器
    func prepareCheckConditionPickers(){
        //事件类型
        eventTypePicker.dataSource = self
        eventTypePicker.delegate   = self
        eventTypePicker.selectedRow(inComponent: 0)
        //人员类别
        roleTypePicker.dataSource = self
        roleTypePicker.delegate   = self
        roleTypePicker.selectedRow(inComponent: 0)
        //开始时间选择器
        startTimeDataPicker.locale = Locale(identifier: "zh_CN")
        startTimeDataPicker.addTarget(self, action: #selector(startDateChanged(_:)),for: .valueChanged)
        //开始时间设置默认时间
        let beforeOneMonthDate = DateUtil.getDateBeforeOneMonthByNow()
        startTimeDataPicker.setDate(beforeOneMonthDate, animated: true)
        
        //结束时间选择器
        endTimeDataPicker.locale = Locale(identifier: "zh_CN")
        endTimeDataPicker.addTarget(self, action: #selector(endDateChanged(_:)),for: .valueChanged)
        //根据时间选择器设置变量的初始值
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd"
        currentStartTime  = formatter.string(from: startTimeDataPicker.date)
        currentEndTime    = formatter.string(from: endTimeDataPicker.date)
        
    }
    //准备选择器数据
    func makeEventAndStaffTypes(){
 
        let allEventType = EventType()
        allEventType.typeId = EventBusiness.ALL_EVENT_SELECTED_ID
        allEventType.typeName = "全部事件类型"
        eventType = AppGlobal.instance.currentSysEventTypes!
        eventType.insert(allEventType, at: 0)
        
        //人员类别数组

        let allStaffType = StaffType()
        allStaffType.typeId = EventBusiness.ALL_STAFF_SELECTED_ID
        allStaffType.typeName = "全部人员类型"
        staffType = AppGlobal.instance.currentSysStaffTypes!
        staffType.insert(allStaffType, at: 0)
        
    }
    
    override func setTittle() -> String {
        return "历史事件"
    }
    //查询的窗口显示
    func makeCheckViewOccurAnimate() {
        //显示
        checkView.isHidden = false
        //self.btnShowCheckView.isHidden = true
        barItemCheck.isEnabled = false
        checkView.alpha = 0
        UIView.animate(withDuration: 1.0, animations: {
            self.checkView.alpha = 1
        })
    }
    //查询的窗口消失
    func makeCheckViewDissapearAnimate() {
        checkView.alpha = 1
        //self.btnShowCheckView.isHidden = false
        barItemCheck.isEnabled = true
        UIView.animate(withDuration: 0.5, animations: {
            self.checkView.alpha = 0
        }, completion: {(Bool) -> Void in
            //隐藏
            self.checkView.isHidden = true
            
        })
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
        parentView.sendSubview(toBack: bgHeaderView)
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
        parentView.sendSubview(toBack: tableView)
        //下拉刷新控件
        let loadingView = DGElasticPullToRefreshLoadingViewCircle()
        loadingView.tintColor = UIColor.white
        tableView.dg_addPullToRefreshWithActionHandler({ [weak self] () -> Void in
            DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + Double(Int64(1.5 * Double(NSEC_PER_SEC))) / Double(NSEC_PER_SEC), execute: {
                self?.tableView.dg_stopLoading()
                //执行
                print("下拉回调")
                self?.doCheckBiz()
            })
            }, loadingView: loadingView)
        tableView.dg_setPullToRefreshFillColor( UIColor.lightGray)
        tableView.dg_setPullToRefreshBackgroundColor(tableView.backgroundColor!)
        
        
    }
    //使用下拉刷新框架的时候，一定要带上这个析构
    deinit {
        tableView.dg_removePullToRefresh()
    }
    //开始时间选择器的回调
    func startDateChanged(_ datePicker : UIDatePicker){
        let formatter = DateFormatter()
        //日期样式
        formatter.dateFormat = "yyyy-MM-dd"
        //print(formatter.string(from: datePicker.date))
        currentStartTime = formatter.string(from: datePicker.date)
    }
    //结束时间选择器的回调
    func endDateChanged(_ datePicker : UIDatePicker){
        let formatter = DateFormatter()
        //日期样式
        formatter.dateFormat = "yyyy-MM-dd"
        //print(formatter.string(from: datePicker.date))
        currentEndTime = formatter.string(from: datePicker.date)
    }
    //业务执行
    func doCheckBiz(){
        //1.判断下选择条件
/*        //1.1事件类型
        print("current event type id ->\(currentEventTypeId)")
        
        //1.2人员类型
        print("current staff type id ->\(currentStaffTypeId)")
        //1.3开始时间
        print("current start time  ->\(currentStartTime)")
        //1.4结束时间
        print("current end time ->\(currentEndTime)")
*/
        //2.调用业务方法
        EventBusiness().doCheckEvent(vc: self,
                                     departIds: (AppGlobal.instance.currentLoginUser?.departIds)!,
                                     eventTypeId: currentEventTypeId,
                                     staffTypeId: currentStaffTypeId,
                                     startTimeStr: currentStartTime,
                                     endTimeStr: currentEndTime,
                                     pageNbr: 1)
        
    }
    
}

//代理的Table的协议
extension EventHistoryViewController:UITableViewDataSource{
    
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
extension EventHistoryViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        //暂时不响应店家事件
    }
    
}



//扩展实现UIPickerView
extension EventHistoryViewController:UIPickerViewDataSource,UIPickerViewDelegate{
    func numberOfComponents(in pickerView: UIPickerView) -> Int{
        return 1
    }
    
    
    // returns the # of rows in each component..
   func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int{
    
        var count = 0
    
        if pickerView == eventTypePicker{
            count = eventType.count
        }else if pickerView == roleTypePicker{
            count = staffType.count
        }
        return count
    }
    
    func pickerView(_ pickerView: UIPickerView, viewForRow row: Int, forComponent component: Int, reusing view: UIView?) -> UIView{
        let label = UILabel(frame:CGRect(x: 0, y: 0, width: 150, height: 20))
        label.font = UIFont.systemFont(ofSize: 15)
        /*
         var currentEventTypeId = ""
         var currentStaffTypeId = 100
         */
        if pickerView == eventTypePicker{
            label.text = eventType[row].typeName
            //print("strTypeEvent[row]  ----> \(strTypeEvent[row])")
            //设置当前值
            currentEventTypeId = eventType[row].typeId!
        }else if pickerView == roleTypePicker{
            label.text = staffType[row].typeName
            //设置当前值
            currentStaffTypeId = staffType[row].typeId!
            
        }
        
        return label
    }
}




