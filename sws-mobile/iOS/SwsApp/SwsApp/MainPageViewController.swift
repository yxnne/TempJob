//
//  MainPageViewController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/7/18.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  登录后第一个Tab所需的页面
//  除去navigationbar 和 tabbar之外
//  整体是一个ScrollView
//  最上面计划是一个banner轮播图
//  下面有三个卡片
//  卡片1：饼图 卡片2：饼图*5 卡片3：折线图

import UIKit
import Charts

class MainPageViewController: MyTabViewController , UIScrollViewDelegate,MyBannerImageClickedDelegate{
    
    //滚动视图
    var mainScrollView:UIScrollView!
    //卡片1，2，3
    var cardChartView1 :UIView?
    var cardChartView2 :UIView?
    var cardChartView3 :UIView?
    //卡片 Banner 的位置
    var cardViewBannerRect : CGRect{
        get{
            return CGRect(x:0,y:10,width:self.view.bounds.width,height:190)
        }
    }
    
    //卡片1 的位置
    var cardViewRect1 : CGRect{
        get{
            return CGRect(x:5,y:210,width:self.view.bounds.width - 10,height:190)
        }
    }

    //卡片2 的位置
    var cardViewRect2 : CGRect{
        get{
            return CGRect(x:5,y:410,width:self.view.bounds.width - 10,height:140)
        }
    }
    
    //卡片3 的位置
    var cardViewRect3 : CGRect{
        get{
            return CGRect(x:5,y:560,width:self.view.bounds.width - 10,height:240)
        }
    }
    
    //Banner控件
    var banner: MyBannerView?
    
    //图表控件
    //PieChart 整体
    var pieChartOverall:PieChartView?
    //PieChart 时机1
    var pieChartBeforePatientContact:PieChartView?
    //PieChart 时机2
    var pieChartBeforeAsepsisOperation:PieChartView?
    //PieChart 时机3
    var pieChartAfterEnvironmentContact:PieChartView?
    //PieChart 时机4
    var pieChartAfterPatientContact:PieChartView?
    
    //LineChart 角色
    var linrChartRoles:LineChartView?
    
    
    //业务对象
    var departmentOverallBiz:DepartmentOverAllBusiness?
    
    //业务数据
    //这里的逻辑是，在Biz类请求成功后中修改属性值，然后通过属性的didSet{}监听器改变UI
    //总体依从率的值
    var depaertRateOverAll:Double = 0.0{
        didSet{
            makeOverallPieChart(chartPercent:depaertRateOverAll,parentViewToAdd:cardChartView1! );
        }
    }
    //四大洗手时机 :接触患者前，无菌操作前，接触环境后，接触患者后
    var occasionRateArrays:[Double]=[0.0]{
        didSet{
            makeOccasionsPieChart(occasionDoubleArrays:occasionRateArrays,parentViewToAdd:cardChartView2! )
        }
    }
    //角色的数据 :依从率
    var rolesRolesDataArrays:[LineChartRoleDataWrapper] = []{
        didSet{
            //移除子视图
            for view  in (cardChartView3?.subviews)!{
                view.removeFromSuperview()
            }
            makeRolesRatesLineChart(parentViewToAdd:cardChartView3! ,rolesDataArray: rolesRolesDataArrays)
            
        }
    }
    
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        //页面出现之前，重置banner位置
        banner?.resetBannerPosition()
    }
    
    override func viewDidLoad() {
        print(#function)
        //0.设置Navigationbar 和 tabbar的样式
        configNavigateBar()
        tabBarController?.tabBar.tintColor = UIColor.colorWithHexString(hex: "00C0B9")  //改掉渲染颜色
        
        //1.添加ScrollView
        mainScrollView = prepareMainScrollView()
        //2.添加轮播banner
        let cardBannerView = prepareBannerView(cardViewFrame: cardViewBannerRect)
        
        mainScrollView.addSubview(cardBannerView)
        
        //3.添加卡片在ScrollView上: 手卫生依从率统计:整体情况
        cardChartView1 = prepareCardView(cardViewFrame: cardViewRect1, cardTitleStr: "手卫生依从率统计", cardIconImageName: "icon_wash_little@2x")
        makeOverallPieChart(chartPercent:70,parentViewToAdd:cardChartView1! )
        mainScrollView.addSubview(cardChartView1!)
        
        //4.添加卡片在ScrollView上: 手卫生时机依从率分布
        cardChartView2 = prepareCardView(cardViewFrame: cardViewRect2, cardTitleStr: "手卫生时机依从率分布", cardIconImageName: "icon_wash_little@2x")
        makeOccasionsPieChart(occasionDoubleArrays:[20,30,40,90],parentViewToAdd:cardChartView2! )
        mainScrollView.addSubview(cardChartView2!)
        
        //5.添加卡片在ScrollView上: 人员类别分析
        //假数据
        let dataDoctor = LineChartRoleDataWrapper(roleName: "医生",
                                                  roleDatas: [0.65,0.82,0.78, 0.73,0.65,0.9 ,0.70],
                                                  roleColor: UIColor.colorWithHexString(hex: "#E8A3A7") )
        
        let dataNurse = LineChartRoleDataWrapper(roleName: "护士",
                                                 roleDatas: [0.52,0.66,0.88, 0.45,0.82,0.91 ,0.66],
                                                 roleColor: UIColor.colorWithHexString(hex: "#45D1CC") )
        
        let dataWorker = LineChartRoleDataWrapper(roleName: "护工",
                                                  roleDatas: [0.35,0.45,0.55, 0.35,0.45,0.52 ,0.21],
                                                  roleColor: UIColor.colorWithHexString(hex: "#5E6975") )
        
        let dataPhysician = LineChartRoleDataWrapper(roleName: "护工",
                                                     roleDatas: [0.58,0.46,0.50, 0.65,0.95,0.85 ,0.25],
                                                     roleColor: UIColor.colorWithHexString(hex: "#5EA8C5") )
        let lineDatas = [dataDoctor,dataNurse,dataWorker,dataPhysician]
        //卡片
        cardChartView3 = prepareCardView(cardViewFrame: cardViewRect3, cardTitleStr: "人员类别分析", cardIconImageName: "icon_role_little@2x")
        
        makeRolesRatesLineChart(parentViewToAdd:cardChartView3! ,rolesDataArray: lineDatas)
        mainScrollView.addSubview(cardChartView3!)
        
        //6.在view里面添加上
        view.addSubview(mainScrollView)
        
        //7.业务类初始化
        if let userCurrentUser = AppGlobal.instance.currentLoginUser {
            departmentOverallBiz = DepartmentOverAllBusiness()
            departmentOverallBiz?.doDepartmentOverAllBusinessInNearly30Days(viewController:self,checkDepartment:userCurrentUser.departIds!)
            //RolesRateWeeklyBusiness().doRolesRateWeeklyBusinessFromToday(self, userCurrentUser.departIds!)
            //let timer = Timer.init()
            Timer.scheduledTimer(withTimeInterval: 2, repeats: false, block: { (Timer) in
                RolesRateWeeklyBusiness().doRolesRateWeeklyBusinessFromToday(self, userCurrentUser.departIds!)
            })
        
        }
    }

    //准备ScrollView
    func prepareMainScrollView() -> UIScrollView {
        let scrollview = UIScrollView()
        //全屏幕的frame尺寸
        scrollview.frame = view.bounds
        scrollview.delegate = self
        //设置contentSize，实际内容大小
        scrollview.contentSize = CGSize(width:view.bounds.width , height:810)
        return scrollview
        
    }
    //准备CardView to banner
    func prepareBannerView(cardViewFrame cFrame :CGRect ) -> UIView{
        //构造一个cardView
        let myCardView = CardView(frame:cFrame)
        myCardView.backgroundColor = UIColor.white
        
        //构造bannerView
        //整一个父UIView作为容器
        //let cardView = UIView(frame : CGRect(x: 0, y: 0, width: view.bounds.width, height: 200))
        //构造Banner 指定大小
        banner = MyBannerView(frame : CGRect(x: 0, y: 0, width: myCardView.bounds.width, height: myCardView.bounds.height))
        //设置轮播图片
        banner?.prepareMyBannerView(imageStrArraysNames: ["img_banner_1","img_banner_2","img_banner_3"])
        //构造指示器，指定指示器大小
        let indicator = UIPageControl(frame: CGRect(x: 0, y: myCardView.bounds.height - 30, width: myCardView.bounds.width, height: 30))
        //绑定指示器
        banner?.bindPageIndicator(indicatorPageController: indicator)
        //轮播图循环起来
        banner?.bannerLoop()
        //设置一个点击事件的监听吧
        banner?.myViewClickDelegate = self
        //把指示器和banner添加到父布局里面
        myCardView.addSubview(banner!)
        myCardView.addSubview(indicator)
        view.addSubview(myCardView)
        
        return myCardView
    }
    
    
    //准备CardView to Chart
    func prepareCardView(cardViewFrame cFrame :CGRect ,
                                 cardTitleStr cTitle:String ,
                                 cardIconImageName iconName:String
        ) -> UIView {
        //构造一个cardView
        let myCardView = CardView(frame:cFrame)
        myCardView.backgroundColor = UIColor.white
        
        //添加小图标
        
        //添加文字:卡片标题
        let titleLabel = UILabel(frame:CGRect(x:30,y:10,width:150,height:10))
        titleLabel.text = cTitle
        titleLabel.textColor = UIColor.darkGray
        titleLabel.font = UIFont.systemFont(ofSize:11)
        myCardView.addSubview(titleLabel)
        
        //添加文字:更多
        let moreLabel = UILabel(frame:CGRect(x:cFrame.width - 60,y:10,width:100,height:10))
        moreLabel.text = "更多>>"
        moreLabel.font = UIFont.systemFont(ofSize:11)
        moreLabel.textColor = UIColor.darkGray
        myCardView.addSubview(moreLabel)
        
        //添加卡片小icon
        let cardLittleIcon = UIImageView(frame:CGRect(x:10,y:8,width:12,height:12))
        cardLittleIcon.image = UIImage(named:iconName)
        myCardView.addSubview(cardLittleIcon)
        
        //添加手势识别器
        let tapRecongnizeAsBtn = UITapGestureRecognizer(
            target:self,action:#selector(tapCardViewCallBackImpl(_:))
        )
        myCardView.addGestureRecognizer(tapRecongnizeAsBtn)

        return myCardView
        
    }
    
    //添加第一张卡片的整体情况饼图
    func makeOverallPieChart(chartPercent percent:Double,parentViewToAdd parentView:UIView){
        let viewRect = CGRect(x: 10, y: 40, width: parentView.bounds.width - 10, height: parentView.bounds.height - 60)
        pieChartOverall = PieChartsUtil.getPieChartWithPercent(frame: viewRect,
                                                               percent: percent,
                                                               colorMainStrHex :"#7CE8E1",
                                                               colorBgStrHex :"#C8FBF8",
                                                               colorMiddleRingStrHex:"#C8FBF8")
        parentView.addSubview(pieChartOverall!)
    
    }
    
    //添加第二张卡片的4个时机的饼图 和图表说明文字
    func makeOccasionsPieChart(occasionDoubleArrays dataDoubleArray:[Double],parentViewToAdd parentView:UIView){
        //1.添加四个PieChart
        //计算每一个图表的大小
        let everyChartWidth = (parentView.bounds.width - 20) / 4        //每个宽度
        let everyChartHeight = CGFloat(Float(70))                       //每个高度
        let yEveryChartStart = CGFloat(Float(30))                       //y的起始点
        //每个x的起始点不一样
        let xStartChart1 = CGFloat(Float(10))
        let xStartChart2 = CGFloat(Float(xStartChart1)) + everyChartWidth
        let xStartChart3 = CGFloat(Float(xStartChart1)) + everyChartWidth * 2
        let xStartChart4 = CGFloat(Float(xStartChart1)) + everyChartWidth * 3
        //每一个cgRect
        let viewChartRect1 = CGRect(x: xStartChart1, y: yEveryChartStart, width:everyChartWidth, height: everyChartHeight)
        let viewChartRect2 = CGRect(x: xStartChart2, y: yEveryChartStart, width:everyChartWidth, height: everyChartHeight)
        let viewChartRect3 = CGRect(x: xStartChart3, y: yEveryChartStart, width:everyChartWidth, height: everyChartHeight)
        let viewChartRect4 = CGRect(x: xStartChart4, y: yEveryChartStart, width:everyChartWidth, height: everyChartHeight)
        //构造ChartView
        pieChartBeforePatientContact = PieChartsUtil.getPieChartWithPercent(frame: viewChartRect1,percent: dataDoubleArray[0],
                                                                            colorMainStrHex :"#FFC55D",colorBgStrHex :"#FFE2AE",
                                                                            colorMiddleRingStrHex:"#FFE2AE")
        
        pieChartBeforeAsepsisOperation = PieChartsUtil.getPieChartWithPercent(frame: viewChartRect2, percent: dataDoubleArray[1],
                                                                              colorMainStrHex :"#32D6C5",colorBgStrHex :"#98EAE2",
                                                                              colorMiddleRingStrHex:"#98EAE2")
        
        pieChartAfterEnvironmentContact = PieChartsUtil.getPieChartWithPercent(frame: viewChartRect3,percent: dataDoubleArray[2],
                                                                               colorMainStrHex :"#AEA8FE",colorBgStrHex :"#D6D3FE",
                                                                               colorMiddleRingStrHex:"#D6D3FE")
        
        pieChartAfterPatientContact = PieChartsUtil.getPieChartWithPercent(frame: viewChartRect4, percent: dataDoubleArray[3],
                                                                           colorMainStrHex :"#68ACF0",colorBgStrHex :"#B3D5F7",
                                                                           colorMiddleRingStrHex:"#B3D5F7")
        //添加图表
        parentView.addSubview(pieChartBeforePatientContact!)
        parentView.addSubview(pieChartBeforeAsepsisOperation!)
        parentView.addSubview(pieChartAfterEnvironmentContact!)
        parentView.addSubview(pieChartAfterPatientContact!)
        
        //2.添加说明文字
        //构造四个标签
        let describLabel1 = UILabel(frame:CGRect(x: 0, y: 0, width: everyChartWidth, height: 20))
        let describLabel2 = UILabel(frame:CGRect(x: 0, y: 0, width: everyChartWidth, height: 20))
        let describLabel3 = UILabel(frame:CGRect(x: 0, y: 0, width: everyChartWidth, height: 20))
        let describLabel4 = UILabel(frame:CGRect(x: 0, y: 0, width: everyChartWidth, height: 20))
        
        describLabel1.text = "接触患者前"
        describLabel1.textAlignment = .center
        describLabel1.textColor = UIColor.darkGray
        describLabel1.font = UIFont.systemFont(ofSize:11)
        
        describLabel2.text = "无菌操作前"
        describLabel2.textAlignment = .center
        describLabel2.textColor = UIColor.darkGray
        describLabel2.font = UIFont.systemFont(ofSize:11)
        
        describLabel3.text = "接触环境后"
        describLabel3.textAlignment = .center
        describLabel3.textColor = UIColor.darkGray
        describLabel3.font = UIFont.systemFont(ofSize:11)
        
        describLabel4.text = "接触患者后"
        describLabel4.textAlignment = .center
        describLabel4.textColor = UIColor.darkGray
        describLabel4.font = UIFont.systemFont(ofSize:11)
        
        //定位
        describLabel1.center = CGPoint(x:(everyChartWidth/2) + xStartChart1 , y: yEveryChartStart+everyChartHeight + CGFloat(Float(15)))
        describLabel2.center = CGPoint(x:(everyChartWidth/2) + xStartChart2 , y: yEveryChartStart+everyChartHeight + CGFloat(Float(15)))
        describLabel3.center = CGPoint(x:(everyChartWidth/2) + xStartChart3 , y: yEveryChartStart+everyChartHeight + CGFloat(Float(15)))
        describLabel4.center = CGPoint(x:(everyChartWidth/2) + xStartChart4 , y: yEveryChartStart+everyChartHeight + CGFloat(Float(15)))
        //添加到父视图
        parentView.addSubview(describLabel1)
        parentView.addSubview(describLabel2)
        parentView.addSubview(describLabel3)
        parentView.addSubview(describLabel4)
        
    }
    //添加第三张卡片的折线图
    func makeRolesRatesLineChart(parentViewToAdd parentView:UIView,rolesDataArray lineDatas:[LineChartRoleDataWrapper]){
        //1.先构造数据
/*
        let dataDoctor = LineChartRoleDataWrapper(roleName: "医生",
                                               roleDatas: [0.65,0.82,0.78, 0.73,0.65,0.9 ,0.70],
                                               roleColor: UIColor.colorWithHexString(hex: "#E8A3A7") )
        
        let dataNurse = LineChartRoleDataWrapper(roleName: "护士",
                                                 roleDatas: [0.52,0.66,0.88, 0.45,0.82,0.91 ,0.66],
                                                 roleColor: UIColor.colorWithHexString(hex: "#45D1CC") )
        
        let dataWorker = LineChartRoleDataWrapper(roleName: "护工",
                                                  roleDatas: [0.35,0.45,0.55, 0.35,0.45,0.52 ,0.21],
                                                  roleColor: UIColor.colorWithHexString(hex: "#5E6975") )
        
        let dataPhysician = LineChartRoleDataWrapper(roleName: "护工",
                                                     roleDatas: [0.58,0.46,0.50, 0.65,0.95,0.85 ,0.25],
                                                     roleColor: UIColor.colorWithHexString(hex: "#5EA8C5") )
        let lineDatas = [dataDoctor,dataNurse,dataWorker,dataPhysician]
 */
        //2.完成图表构建
        let chartFrame = CGRect(x:20,y:30,width:parentView.bounds.width - 20,height:parentView.bounds.height - 40)
        let weekString = DateUtil.getOneWeekDateStringsEndInTody()
        let lineChart = LineChartUtil.getLineChartRolesWeeks(frame: chartFrame ,
                                                             roleDatas: lineDatas,
                                                             weekStrs: weekString)
        //3.添加到父视图
        parentView.addSubview(lineChart)
    
    }
    
    
    //响应CardViewChart 点击的回调方法
    func tapCardViewCallBackImpl(_ sender: UITapGestureRecognizer){
        print(#function )
        self.performSegue(withIdentifier: "showDepartDetail", sender: self)
    }
    //响应轮播banner的item的回调方法
    func imageClicked(clickItem item :Int){
        print("click is \(item)")
        self.performSegue(withIdentifier: "showNews", sender: self)
        
    }
    
    
}
