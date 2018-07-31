//
//  StaffsRateViewController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/7/28.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  员工依从率页面，有一个表(下拉刷新)
//  Table的代理写在文件后面extension

import UIKit
import DGElasticPullToRefresh
import Toaster
import PopupDialog

class StaffsRateViewController:MySecondaryPageBaseViewController{
    
    //Btn :选择数据科室
    var btnChooseDepart:UIButton!
    var chooseDepartProtocalImpl:MyChooseProtocal?
    //当前选择的科室
    var currentChoosenDepartmentName:String!
    var currentChoosenDepartmentIds:Int!
    //segmnetControl的值
    var isOneDay :Bool = false                      //false是近30天
    
    //因为会出现view没有在navigationbar下面的问题，所以把这个当做根view了
    @IBOutlet var pageMainView: UIView!
    
    var tableView :UITableView!
    
    var staffs :StaffRateLists!
    
    var clickedItem = 0
    //头View的位置
    var cardViewHeaderRect : CGRect{
        get{
            return CGRect(x:5,y:10,width:self.view.bounds.width - 10,height:120)
        }
    }
    
    //设置标题
    override func setTittle() -> String {
        return "员工依从率概览"
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        //pageMainView.backgroundColor = UIColor.red
        
        //1.假数据准备好
        prepareFakeStaffItems()
        //2.段选器和表头，以及部门选择按钮
        prepareHeaderView()
        addSelectDepartBtnAndJudgeIfEnable()
        //3.表--列表整好
        prepareTableView()
        
        //发送请求的方法—经测试OK
        //DepartmentStaffRateBusiness().doDepartmentStaffRateBusinessToday(viewController: self, checkDepartment: (AppGlobal.instance.currentLoginUser?.departIds)!)
        doCheckBusiness()
    }
    
    /*得到Table里面的假数据  */
    func prepareFakeStaffItems(){
        staffs = StaffRateLists(fakeCount:80)
    
    }
    /*准备列表顶上的View*/
    func prepareHeaderView(){
        //1.view
        let headerView = UIView(frame : cardViewHeaderRect)
        headerView.backgroundColor = UIColor.white
        //2.segmentControll
        //添加段选择器
        let segmentControl = UISegmentedControl(items:["当天","近30天"])
        segmentControl.backgroundColor = UIColor.white.withAlphaComponent(0.5)
        segmentControl.selectedSegmentIndex = 0
        let ySegCenterPoint = CGFloat(26)
        let xSegCenterPoint = headerView.bounds.width / 2
        segmentControl.center = CGPoint(x:xSegCenterPoint, y:ySegCenterPoint)
        segmentControl.selectedSegmentIndex = 1 // 默认设置成近30天
        //添加值变化监听
        segmentControl.addTarget(self, action: #selector(segmentValueChanged(_:)), for: .valueChanged)
        headerView.addSubview(segmentControl)
        
        //3.表头
        makeTableHeaderAndAddIt(parentViewToAdd:headerView)
        
        //添加头View
        pageMainView.addSubview(headerView)

    }
    
    /*添加弹窗选择的按钮，并且根据当前用户判断是否可用*/
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
        
        pageMainView.addSubview(btnChooseDepart)
    }
    
    /*表头的样式*/
    func makeTableHeaderAndAddIt(parentViewToAdd parentView :UIView){
        //表头的背景
        let bgHeaderView = UIView(frame :CGRect(x:10,y:85,width:parentView.bounds.width - 20,height:35))
        bgHeaderView.backgroundColor = UIColor.colorWithHexString(hex: "#00C0B9")
        //计算位置
        let everyWidth = parentView.bounds.width / 4
        let labelsCommenCenterY = bgHeaderView.bounds.height/2               //中心点y的位置
        //中心点x的位置
        let labelRoleCenterX = everyWidth / 2 - 10
        let labelNameCenterX = labelRoleCenterX + everyWidth
        let labelNoCenterX   = labelRoleCenterX + everyWidth * 2
        let labelRateCenterX = labelRoleCenterX + everyWidth * 3
        
        //列1 职务
        let labelRole = UILabel(frame:CGRect(x:0,y:0,width:40,height:20))
        labelRole.text = "职务"
        labelRole.font = UIFont.systemFont(ofSize:14)
        labelRole.textColor = UIColor.white
        labelRole.center = CGPoint(x:labelRoleCenterX ,y: labelsCommenCenterY)
        bgHeaderView.addSubview(labelRole)
        
        //列2 姓名
        let labelName = UILabel(frame:CGRect(x:0,y:0,width:40,height:20))
        labelName.text = "姓名"
        labelName.font = UIFont.systemFont(ofSize:14)
        labelName.textColor = UIColor.white
        labelName.center = CGPoint(x:labelNameCenterX ,y: labelsCommenCenterY)
        bgHeaderView.addSubview(labelName)
        
        //列3 胸卡号
        let labelNo = UILabel(frame:CGRect(x:0,y:0,width:50,height:20))
        labelNo.text = "胸卡号"
        labelNo.font = UIFont.systemFont(ofSize:14)
        labelNo.textColor = UIColor.white
        labelNo.center = CGPoint(x:labelNoCenterX ,y: labelsCommenCenterY)
        bgHeaderView.addSubview(labelNo)
        
        //列4 总依从率
        let labelRate = UILabel(frame:CGRect(x:0,y:0,width:60,height:20))
        labelRate.text = "总依从率"
        labelRate.font = UIFont.systemFont(ofSize:14)
        labelRate.textColor = UIColor.white
        labelRate.center = CGPoint(x:labelRateCenterX ,y: labelsCommenCenterY)
        bgHeaderView.addSubview(labelRate)
        
        parentView.addSubview(bgHeaderView)
    }
    
    /*准备Table*/
    func prepareTableView(){
        //创建Table
        let tabelRect = CGRect(x:15,y:130,width:cardViewHeaderRect.width - 20,height:pageMainView.bounds.height - 30 )
        tableView = UITableView(frame:tabelRect,style: .plain)
        tableView.dataSource = self     // 数据的代理
        tableView.delegate = self       // 点击的代理
        tableView.register(MyDepartStaffRateCell.self, forCellReuseIdentifier: "staffRateCell")  // 注册Cell
        tableView.rowHeight = 40
        pageMainView.addSubview(tableView)
        
        //下拉刷新控件
        let loadingView = DGElasticPullToRefreshLoadingViewCircle()
        loadingView.tintColor = UIColor.white
        tableView.dg_addPullToRefreshWithActionHandler({ [weak self] () -> Void in
            DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + Double(Int64(1.5 * Double(NSEC_PER_SEC))) / Double(NSEC_PER_SEC), execute: {
                self?.tableView.dg_stopLoading()
                //执行
                //print("下拉回调")
                
                self?.doCheckBusiness()
                ToastUtil.makeCommonToast("下拉刷新").show()
                
            })
            }, loadingView: loadingView)
        tableView.dg_setPullToRefreshFillColor( UIColor.colorWithHexString(hex: "#00C0B9"))
        tableView.dg_setPullToRefreshBackgroundColor(tableView.backgroundColor!)

    }
    
    //使用下拉刷新框架的时候，一定要带上这个析构
    deinit {
        tableView.dg_removePullToRefresh()
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

    //发送请求
    func doCheckBusiness(){
        if isOneDay{//一天
            DepartmentStaffRateBusiness().doDepartmentStaffRateBusinessToday(viewController: self, checkDepartment: currentChoosenDepartmentIds)
        }else{//近30天
            DepartmentStaffRateBusiness().doDepartmentStaffRateBusinessInNearly30Days(viewController: self, checkDepartment: currentChoosenDepartmentIds)
        }
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

    
}

//代理的Table的协议
extension StaffsRateViewController:UITableViewDataSource{
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    //返回数据项的个数方法
    func tableView(_ tableView: UITableView,
                   numberOfRowsInSection section: Int) -> Int {
        return staffs.items.count
    }
    //放数据
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "staffRateCell",for:indexPath) as! MyDepartStaffRateCell
        
        let item = staffs.items[indexPath.row]
        
        cell.labelRole.text = item.role
        cell.labelName.text = item.name
        cell.labelNo.text = item.cardNo
        cell.labelRate.text = item.rateOverAll
        return cell
    }
    
    //跳转逻辑
    //segue就是类似Intent
    //一个segue触发后会调用这个方法
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        //segue携带了一个重要的标识信息
        switch segue.identifier {
        case "showSomeoneDetail"?:
            
            print("click row is \(clickedItem)")
            let item = staffs.items[clickedItem]
            let detailViewController = segue.destination as! OneStaffDetailViewController
            detailViewController.staffInfoAndData = item
            
        default:
            preconditionFailure("Unexception segue identifier")
        }
    }
}

//代理的Table的点击事件
extension StaffsRateViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        //设置点击索引属性
        clickedItem = indexPath.row
        //呈现下一个页面，在prepare中传递参数
        self.performSegue(withIdentifier: "showSomeoneDetail", sender: self)
        
    }
    
}


