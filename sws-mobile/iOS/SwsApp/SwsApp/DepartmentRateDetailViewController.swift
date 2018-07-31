//
//  DepartmentRateDetailViewController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/7/28.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  部门依从率详情

import UIKit
import Charts
import LinearProgressView
import Toaster
import PopupDialog

class DepartmentRateDetailViewController:MyWithGroupBarCardViewController,UIScrollViewDelegate{
    //Btn :选择数据科室
    var btnChooseDepart:UIButton!
    var chooseDepartProtocalImpl:MyChooseProtocal?
    //当前选择的科室
    var currentChoosenDepartmentName:String!
    var currentChoosenDepartmentIds:Int!
    //图1 模型
    //洗手时机次数,由Business设置
    var washTimes:[WashTimeStruct] = []{
        didSet{
            //移除子视图
            for view  in card1.subviews{
                view.removeFromSuperview()
            }
            
            //遍历这个数组，然后设置值
            var colorStrs:[String] = []
            var nameStrs:[String] = []
            var values:[Double] = []
            for one in washTimes{
                values.append(Double(one.timesInt))
                nameStrs.append(one.occasionName)
                colorStrs.append(one.colorStrHex)
                print("---------------------------")
                print("name is :\(one.occasionName)")
                print("value is :\(one.timesInt)")
                print("color is :\(one.colorStrHex)")
                
                print("---------------------------")
            }
            //更新视图
            makePieToCard(parentViewToAdd: card1, dataColorHexStrings: colorStrs, dataNameStrings: nameStrs, dataValues: values)
        }
    }
    //图2 模型
    //时机依从率值,由Business设置
    var occasionsRateArray:[Double] = [0.0,0.0,0.0,0.0]{
        didSet{
            //更新UI
            setOccasionData(beforeContactPatient    :occasionsRateArray[0],
                            beforeAespsis           :occasionsRateArray[1],
                            afterEnvironment        :occasionsRateArray[2],
                            afterContactPatient     :occasionsRateArray[3])
        }
    }
    //图3 模型,由Business设置
    var rolesNamesArray:[String ] = []
    
    var rolesRateArray:[Double] = []{
        didSet{
            radarCharDepart.removeFromSuperview()
            makeRadarToCard(parentViewToAdd:card3,roleLabelString :rolesNamesArray,pointValues : rolesRateArray)
            
        }
    }
    
    //segmnetControl的值
    var isOneDay :Bool = false                      //false是近30天
   
    //滚动视图
    var mainScrollView:UIScrollView!
    //Pie图
    var pieChartDepart:PieChartView!
    //Radar图
    var radarCharDepart:RadarChartView!
 
    //@IBOutlet var bgView: UIView!
    
    //卡片1 的位置
    var cardViewRect1 : CGRect{
        get{
            return CGRect(x:5,y:70,width:self.view.bounds.width - 10,height:230)
        }
    }
    //卡片2 的位置
    var cardViewRect2 : CGRect{
        get{
            return CGRect(x:5,y:310,width:self.view.bounds.width - 10,height:220)
        }
    }
    
    //卡片3 的位置
    var cardViewRect3 : CGRect{
        get{
            return CGRect(x:5,y:540,width:self.view.bounds.width - 10,height:300)
        }
    }
    
    var card3:UIView!
    var card1:UIView!

    
    //设置标题
    override func setTittle() -> String {
        
        return "部门依从率概览"
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //准备ScrollView
        mainScrollView = prepareMainScrollView()
        
        //添加段选择器
        addSegmentControlToScrollView()

        //添加弹窗选择的按钮，并且根据当前用户判断是否可用
        addSelectDepartBtnAndJudgeIfEnable()
        
        //卡片Card1：Pie
        card1 = prepareCardView(cardViewFrame: cardViewRect1)
        let datasArray = [60.0,80,20,40,30]
        let datasLabelsArray = ["进入病房未洗手","洗手次数","长时间离开病床未洗手","接近病床未洗手","离开病房未洗手"]
        let dataColorsArray = ["#52DDB6","#223142","#FE7276","#979899","#FAA755"]
        makePieToCard(parentViewToAdd: card1, dataColorHexStrings: dataColorsArray, dataNameStrings: datasLabelsArray, dataValues: datasArray)
        mainScrollView.addSubview(card1)
        
        //卡片Card2：Bar
        let card2 = prepareCardView(cardViewFrame: cardViewRect2)
        makeBarToCard(parentViewToAdd:card2)
        
        mainScrollView.addSubview(card2)
        
        //卡片Card3：Radar
        /*
         pointValues: [20,90,30,40,70],
         roleLabelString :["医生","护士","医师","护工","护师"],
         */
        card3 = prepareCardView(cardViewFrame: cardViewRect3)
        makeRadarToCard(parentViewToAdd:card3,roleLabelString :["医生","护士","医师","护工","护师"] ,pointValues : [20,90,30,40,70])
        mainScrollView.addSubview(card3)

        //把scrollview添加到bgView上
        view.addSubview(mainScrollView)
        
        //设置时机值
        setOccasionData( beforeContactPatient :  20,
                         beforeAespsis:  35,
                         afterEnvironment :  10,
                         afterContactPatient :   70)
        
        //得到部门
        print("\(String(describing: AppGlobal.instance.currentSysDepartments))")
        //currentChoosenDepartment = AppGlobal.instance.currentLoginUser?.departIds
        //初始查询
        doCheckBusiness()
        
    }

    //准备ScrollView
    func prepareMainScrollView() -> UIScrollView {
        let scrollview = UIScrollView()
        //全屏幕的frame尺寸
        scrollview.frame = view.bounds
        scrollview.delegate = self
        //设置contentSize，实际内容大小
        scrollview.contentSize = CGSize(width:view.bounds.width , height:850)
        return scrollview
        
    }
    
    //添加段选择器
    func addSegmentControlToScrollView(){
        let segmentControl = UISegmentedControl(items:["当天","近30天"])
        segmentControl.backgroundColor = UIColor.white.withAlphaComponent(0.5)
        segmentControl.selectedSegmentIndex = 0
        
        let ySegCenterPoint = CGFloat(36)
        let xSegCenterPoint = mainScrollView.bounds.width / 2
        segmentControl.center = CGPoint(x:xSegCenterPoint, y:ySegCenterPoint)
        segmentControl.selectedSegmentIndex = 1 // 默认设置成近30天
        
        //添加值变化监听
        segmentControl.addTarget(self, action: #selector(segmentValueChanged(_:)), for: .valueChanged)
        mainScrollView.addSubview(segmentControl)
    }
    
    //添加弹窗选择的按钮，并且根据当前用户判断是否可用
    func addSelectDepartBtnAndJudgeIfEnable(){
        //添加UIButton
        btnChooseDepart = UIButton(type: .system)                               //系统样式
        btnChooseDepart.frame = CGRect(x:12, y:20, width:100, height:30)        //frame
        btnChooseDepart.titleLabel?.lineBreakMode = .byWordWrapping             //可以换行
        btnChooseDepart.titleLabel?.font = UIFont.systemFont(ofSize: 12)        //文字大小
        btnChooseDepart.setTitleColor(UIColor.darkText, for: .disabled)
        
        var btnText = ""
        //根据权限个性化设置
        //先判断用户的是不是admin
        if AppGlobal.instance.currentLoginUser?.departIds == 1{
            //admin
            //默认给全院
            btnText = "全部科室"
            //点击监听
            btnChooseDepart.addTarget(self, action:#selector(btnChooseDepartTapped_dialogShow(_:)), for:.touchUpInside)

        }else{
            //不是admin
            //btnChooseDepart.isEnabled = true
            btnText = (AppGlobal.instance.currentLoginUser?.department)!

            btnChooseDepart.addTarget(self, action:#selector(btnChooseDepartTapped_toast(_:)), for:.touchUpInside)
            
        }
        
        //设置按钮，设置初始值
        updateValuesWhenDepartmentChoosen(btnText :btnText,currentDepartIds:(AppGlobal.instance.currentLoginUser?.departIds)! ,currentDepartName:(AppGlobal.instance.currentLoginUser?.department)!)

        mainScrollView.addSubview(btnChooseDepart)
    }
    
    //准备CardView to Chart
    func prepareCardView(cardViewFrame cFrame :CGRect ) -> UIView {
        //构造一个cardView
        let myCardView = CardView(frame:cFrame)
        myCardView.backgroundColor = UIColor.white
        
        return myCardView
        
    }

    //添加Pie
    func makePieToCard(parentViewToAdd parentView:UIView,dataColorHexStrings dataColorsArray:[String] ,dataNameStrings datasLabelsArray:[String] ,dataValues datasArray:[Double]) {

        //添加Pie
        let viewRect = CGRect(x: 10, y: 32, width: parentView.bounds.width - 10, height: parentView.bounds.height - 32)

//        let datasArray = [60.0,80,20,40,30]
//        let datasLabelsArray = ["进入病房未洗手","洗手次数","长时间离开病床未洗手","接近病床未洗手","离开病房未洗手"]
//        let dataColorsArray = ["#52DDB6","#223142","#FE7276","#979899","#FAA755"]
        
        pieChartDepart = PieChartsUtil.getPieChartMoreKindsDatas(frame: viewRect,
                                                                 sectionValues: datasArray,
                                                                 sectionDescription: datasLabelsArray,
                                                                 sectionColorStrHex: dataColorsArray,
                                                                 isNeedValueLine: true)
        parentView.addSubview(pieChartDepart)
    }
    
    //添加Radar
    func makeRadarToCard(parentViewToAdd parentView:UIView,roleLabelString roleLabels:[String] ,pointValues doubles:[Double]) {
        
        let viewRect = CGRect(x: 10, y: 30, width: parentView.bounds.width - 10, height: parentView.bounds.height - 40)
        radarCharDepart = RadarChartUtil.getRadarShowRolesRate(frame: viewRect,
                                                                pointValues: doubles,
                                                                roleLabelString :roleLabels,
                                                                outerColorHexString :"#007D78",
                                                                innerColorHexString :"#35C0bb")
        parentView.addSubview(radarCharDepart)
    }
    
    //segmentontrol选择回调
    func segmentValueChanged(_ segControl :UISegmentedControl){
        //设定值
        switch segControl.selectedSegmentIndex {
        case 0:
            isOneDay = true
            
        case 1:
            isOneDay = false
        default: break
        }
        //业务请求
        doCheckBusiness()
    }
    
    //选择科室的点击事件，弹窗
    func btnChooseDepartTapped_dialogShow(_ sender:UIButton){
        
        //创建ViewController显示Xib的Alert
        let chooseDepartVC = MyXibSelectDepartmentViewController(nibName: "MySelectDepartmentViewController", bundle: nil)
        
        if let departsInSys = AppGlobal.instance.currentSysDepartments{
            chooseDepartVC.departments = departsInSys
        }
        
        chooseDepartVC.departments = genDepartmentsFromAppGlobalAndMore()
        //chooseDepartVC.departStrings = ["12345","45678","dfdf","34de3dff","dfdfdfd","XXOO"]
        chooseDepartProtocalImpl = chooseDepartVC
        
        //创建弹出对话框
        let popup = PopupDialog(viewController: chooseDepartVC, buttonAlignment: .horizontal, transitionStyle: .bounceDown, gestureDismissal: true)
        
        //构造取消按钮
        let buttonOne = CancelButton(title: "取消", height: 60) {
            // self.label.text = "You canceled the rating dialog"
        }
        
        //构造确定按钮
        let buttonTwo = DefaultButton(title: "确定", height: 60) {
            //更改些变量值
            self.updateValuesWhenDepartmentChoosen(btnText :(self.chooseDepartProtocalImpl?.choosenString())!,
                                              currentDepartIds:(self.chooseDepartProtocalImpl?.choosenDepartIds())!,
                                              currentDepartName:(self.chooseDepartProtocalImpl?.choosenString())!)
            
            print("Test Choosen currentChoosenDepartmentIds  :\(self.currentChoosenDepartmentIds)")
            print("Test Choosen currentChoosenDepartmentName  :\(self.currentChoosenDepartmentName)")
            //业务请求
            self.doCheckBusiness()
        }
        
        //添加按钮
        popup.addButtons([buttonOne, buttonTwo])
        //呈现
        present(popup, animated: true, completion: nil)
    }
    
    //选择科室的点击事件，Toast提示
    func btnChooseDepartTapped_toast(_ sender:UIButton){
        ToastUtil.makeCommonToast("您无权限查看其它科室").show()
    }
    
    //弄一个Department数组用于给对话框vc传递
    //原来的没有全院这一项，现在天上
    func genDepartmentsFromAppGlobalAndMore()->[Department]{
       
        var departs :[Department] = []
        if let departsInSys = AppGlobal.instance.currentSysDepartments{
            //构造一个全院的数据
            let departWholeHospital = Department()
            departWholeHospital.departIds = 1
            departWholeHospital.departName = "全部科室"
            //组装这个数组
            departs = departsInSys
            departs.insert(departWholeHospital, at: 0)
        }
        return departs
    }
    //更新文字：选择depart的按钮
    func updateValuesWhenDepartmentChoosen(btnText text :String,currentDepartIds id :Int ,currentDepartName name :String){
        currentChoosenDepartmentIds = id
        currentChoosenDepartmentName = name
        btnChooseDepart.setTitle(text, for: .normal)
    
    }
    //发送请求
    func doCheckBusiness(){
        if isOneDay{//一天
            DepartmentDetailBusiness().doDepartmentOverAllBusinessToday(viewController: self, checkDepartment: currentChoosenDepartmentIds)
            
            DepartmentWashTimeBusiness().doDepartmentWashTimeBusinessToday(viewController: self, checkDepartment: currentChoosenDepartmentIds)
        }else{//近30天
            DepartmentDetailBusiness().doDepartmentOverAllBusinessInNearly30Days(viewController: self, checkDepartment: currentChoosenDepartmentIds)
            
            DepartmentWashTimeBusiness().doDepartmentWashTimeBusinessInNearly30Days(viewController: self, checkDepartment: currentChoosenDepartmentIds)
        }
    }
    
}
/**
 定义的接口协议用于在VC之间传数据，这里用作dialog返回协议
 */
protocol MyChooseProtocal {
    /**返回被选择的String*/
    func choosenString() ->String
    /**返回被选择的String在[]中的Index*/
    func choosenDepartIds() ->Int
}

