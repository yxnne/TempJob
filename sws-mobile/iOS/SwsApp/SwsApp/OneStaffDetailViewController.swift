//
//  OneStaffDetailViewController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/1.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  个人详情页面

import UIKit
import Charts

class OneStaffDetailViewController : MyWithGroupBarCardViewController ,UIScrollViewDelegate{
    
    //滚动视图
    var mainScrollView:UIScrollView!
    //Pie图
    var pieChartOneDetaile:PieChartView!
    
    //本页面的数据封装
    var staffInfoAndData : StaffRateItem?
    
    //头像
    var imageUserPortrait: UIImageView!
    //姓名
    var nameLabel:UILabel!
    //科室
    var departmentLabel:UILabel!
    //职务
    var roleLabel:UILabel!
    //总依从率
    var rateOverallLabel:UILabel!
    //卡片头 的位置
    var cardViewRect1 : CGRect{
        get{
            return CGRect(x:5,y:10,width:self.view.bounds.width - 10,height:250)
        }
    }
    
    //卡片2 的位置
    var cardViewRect2 : CGRect{
        get{
            return CGRect(x:5,y:270,width:self.view.bounds.width - 10,height:250)
        }
    }
    
    //卡片3 的位置
    var cardViewRect3 : CGRect{
        get{
            return CGRect(x:5,y:530,width:self.view.bounds.width - 10,height:240)
        }
    }

    override func setTittle() -> String {
        return "个人依从率"
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //
        print("test if passed "+"\(String(describing: staffInfoAndData?.cardNo))")
        
        //准备ScrollView
        mainScrollView = prepareMainScrollView()
        view.addSubview(mainScrollView)
        //card 1 :staff info 
        let cardStaffInfo = prepareCardView(cardRect:cardViewRect1)
        makeStaffInfoToCard(parentViewToAdd : cardStaffInfo)
        mainScrollView.addSubview(cardStaffInfo)
        
        //card 2 :次数分布
        let cardWashTimes = prepareCardView(cardRect:cardViewRect2)
        makePieToCard(parentViewToAdd : cardWashTimes)
        mainScrollView.addSubview(cardWashTimes)
        
        //card 3 :时机分布
        let cardWashOccasion = prepareCardView(cardRect:cardViewRect3)
        mainScrollView.addSubview(cardWashOccasion)
        makeBarToCard(parentViewToAdd: cardWashOccasion)
        
        //添加数据
        putOneInfoAndDatas()
        //设置时机值

        
    }
    
    //准备ScrollView
    func prepareMainScrollView() -> UIScrollView {
        let scrollview = UIScrollView()
        //全屏幕的frame尺寸
        scrollview.frame = view.bounds
        scrollview.delegate = self
        //设置contentSize，实际内容大小
        scrollview.contentSize = CGSize(width:view.bounds.width , height:780)
        return scrollview
        
    }
    
    //顶头的卡片：呈现员工的基本信息
    func prepareCardView(cardRect cardFrame:CGRect) -> UIView{
        //构造一个cardView
        let myCardView = CardView(frame:cardFrame)
        myCardView.backgroundColor = UIColor.white
        return myCardView
    
    }
    //创造人员信息卡片上的内容
    func makeStaffInfoToCard(parentViewToAdd parentView:UIView) {
        let thisWholeWidth = parentView.bounds.width
        //背景一半儿灰
        let bgInHeighLightGray = UIView(frame:CGRect(x:0,y:0,width:thisWholeWidth,height:74))
        bgInHeighLightGray.backgroundColor = UIColor.lightGray
        parentView.addSubview(bgInHeighLightGray)
        //分割线
        let depaertLine1 = UIView(frame :CGRect(x:0,y:74,width:thisWholeWidth,height:1))
        depaertLine1.backgroundColor = UIColor.darkGray
        parentView.addSubview(depaertLine1)
        //构造UIImageView
        imageUserPortrait = UIImageView()
        imageUserPortrait.frame = CGRect(x:0,y:0,width:88,height:88)
        //设置图片
        let imgCirclePortrait = UIImage(named:"img_profile_tmp_doctor@2x")?.toCircle()
        imageUserPortrait.image = imgCirclePortrait
        imageUserPortrait.center = CGPoint(x:thisWholeWidth / 2,y:64)
        parentView.addSubview(imageUserPortrait)
        
        //构造 姓名Lable
        nameLabel = UILabel(frame:CGRect(x:30,y:85,width:100,height:30))
        nameLabel.font = UIFont.systemFont(ofSize: 20)
        nameLabel.textColor = UIColor.darkGray
        parentView.addSubview(nameLabel)
        let depaertLine2 = UIView(frame :CGRect(x:0,y:114,width:thisWholeWidth,height:1))
        depaertLine2.backgroundColor = UIColor.colorWithHexString(hex: "#D6D4D4")
        parentView.addSubview(depaertLine2)
        
        //构造 科室Label
        let departLabelTitle = UILabel(frame:CGRect(x:30,y:125,width:80,height:20))
        departLabelTitle.text = "科室："
        departLabelTitle.font = UIFont.systemFont(ofSize: 14)
        departLabelTitle.textColor = UIColor.lightGray
        parentView.addSubview(departLabelTitle)
        //部门值标签
        departmentLabel = UILabel(frame:CGRect(x:70,y:125,width:80,height:20))
        departmentLabel.font = UIFont.systemFont(ofSize: 14)
        departmentLabel.textColor = UIColor.lightGray
        parentView.addSubview(departmentLabel)
        //添加线
        let depaertLine3 = UIView(frame :CGRect(x:0,y:149,width:thisWholeWidth,height:1))
        depaertLine3.backgroundColor = UIColor.colorWithHexString(hex: "#D6D4D4")
        parentView.addSubview(depaertLine3)
        
        //构造 职务：
        let roleLabelTitle = UILabel(frame:CGRect(x:30,y:161,width:80,height:20))
        roleLabelTitle.text = "职务："
        roleLabelTitle.font = UIFont.systemFont(ofSize: 14)
        roleLabelTitle.textColor = UIColor.lightGray
        parentView.addSubview(roleLabelTitle)
        
        //职务值标签
        roleLabel = UILabel(frame:CGRect(x:70,y:161,width:80,height:20))
        roleLabel.font = UIFont.systemFont(ofSize: 14)
        roleLabel.textColor = UIColor.lightGray
        parentView.addSubview(roleLabel)
        //添加线
        let depaertLine4 = UIView(frame :CGRect(x:0,y:185,width:thisWholeWidth,height:1))
        depaertLine4.backgroundColor = UIColor.colorWithHexString(hex: "#D6D4D4")
        parentView.addSubview(depaertLine4)
        
        //构造 总依从率：
        let rateLabelTitle = UILabel(frame:CGRect(x:30,y:197,width:80,height:20))
        rateLabelTitle.text = "总依从率："
        rateLabelTitle.font = UIFont.systemFont(ofSize: 14)
        rateLabelTitle.textColor = UIColor.lightGray
        parentView.addSubview(rateLabelTitle)
        
        //职务值标签
        rateOverallLabel = UILabel(frame:CGRect(x:100,y:197,width:80,height:20))
        rateOverallLabel.font = UIFont.systemFont(ofSize: 14)
        rateOverallLabel.textColor = UIColor.lightGray
        parentView.addSubview(rateOverallLabel)
        //添加线
        let depaertLine5 = UIView(frame :CGRect(x:0,y:221,width:thisWholeWidth,height:1))
        depaertLine5.backgroundColor = UIColor.colorWithHexString(hex: "#D6D4D4")
        parentView.addSubview(depaertLine5)
    }
    
    //添加Pie
    func makePieToCard(parentViewToAdd parentView:UIView) {
        //添加段选择器
        let segmentControl = UISegmentedControl(items:["当天","近30天"])
        segmentControl.backgroundColor = UIColor.white.withAlphaComponent(0.5)
        segmentControl.selectedSegmentIndex = 0
        /*
         segmentControl.translatesAutoresizingMaskIntoConstraints = false  //这个东西就不许我绝对定位了艹
         添加约束
         
         let topConstraint = segmentControl.topAnchor.constraint(equalTo: parentView.topAnchor, constant: 8)
         let leadingConstraint = segmentControl.leadingAnchor.constraint(equalTo: parentView.leadingAnchor)
         let trailingConstraint = segmentControl.trailingAnchor.constraint(equalTo: parentView.trailingAnchor)
         
         topConstraint.isActive = true
         leadingConstraint.isActive = true
         trailingConstraint.isActive = true
         */
        let ySegCenterPoint = CGFloat(26)
        let xSegCenterPoint = parentView.bounds.width / 2
        segmentControl.center = CGPoint(x:xSegCenterPoint, y:ySegCenterPoint)
        parentView.addSubview(segmentControl)
        //添加Pie
        let viewRect = CGRect(x: 10, y: 56, width: parentView.bounds.width - 10, height: parentView.bounds.height - 60)
        
        //let datasArray = [60.0,80,20,40,30]
        let times = staffInfoAndData?.washTimes
        
        var datasArray:[Double ] = []
        var datasLabelsArray:[String ] = []
        var dataColorsArray:[String ] = []
        //如果没有值就别显示了
        // "#223142",   "#52DDB6"，"#FE7276"           ,"#979899"      ,"#FAA755"
        if times?.timesNoWashAcessRoom != 0.0{
            datasArray.append((times?.timesNoWashAcessRoom)!)
            datasLabelsArray.append("进入病房未洗手")
            dataColorsArray.append("#223142")
        }
        if times?.timesWashCorretly != 0.0{
            datasArray.append((times?.timesWashCorretly)!)
            datasLabelsArray.append("洗手次数")
            dataColorsArray.append("#52DDB6")
        }
        if times?.timesNoWashLeaveRoomLong != 0.0{
            datasArray.append((times?.timesNoWashLeaveRoomLong)!)
            datasLabelsArray.append("长时间离开病床未洗手")
            dataColorsArray.append("#FE7276")
        }
        if times?.timesNoWashCloseBed != 0.0{
            datasArray.append((times?.timesNoWashCloseBed)!)
            datasLabelsArray.append("接近病床未洗手")
            dataColorsArray.append("#979899")
        }
        if times?.timesNoWashLeaveRoom != 0.0{
            datasArray.append((times?.timesNoWashLeaveRoom)!)
            datasLabelsArray.append("离开病房未洗手")
            dataColorsArray.append("#FAA755")
        }
        
        
        pieChartOneDetaile = PieChartsUtil.getPieChartMoreKindsDatas(frame: viewRect,
                                                                 sectionValues: datasArray ,
                                                                 sectionDescription: datasLabelsArray,
                                                                 sectionColorStrHex: dataColorsArray,
                                                                 isNeedValueLine: true)
        parentView.addSubview(pieChartOneDetaile)
    }

    //添加数据的在已存在的引用上
    func putOneInfoAndDatas(){
        //放one info
        nameLabel.text = staffInfoAndData?.name
        departmentLabel.text = staffInfoAndData?.department
        roleLabel.text = staffInfoAndData?.role
        rateOverallLabel.text = staffInfoAndData?.rateOverAll
        //放图表的值
        //pie
        
        //bar
        let occasions = staffInfoAndData?.rateOccasions
        setOccasionData( beforeContactPatient :  (occasions?.rateBeforePatientContact)!,
                         beforeAespsis:  (occasions?.rateBeforeAsepsisOperation)!,
                         afterEnvironment :  (occasions?.rateAfterEnvironmentContact)!,
                         afterContactPatient :   (occasions?.rateAfterPatientContact)!)
    
    }
    
}
