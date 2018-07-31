//
//  LoginViewOCntroller.swift
//  SwsApp
//
//  Created by iel-mac on 2017/7/17.
//  Copyright © 2017年 iel-mac. All rights reserved.
//

import UIKit
import Alamofire

class LoginViewController : MyBackgroudImageViewController{
    
    
    //OutLets
    @IBOutlet var tf_userName:UITextField!
    @IBOutlet var tf_passWord:UITextField!
    
    @IBOutlet var stackView_user: UIStackView!
    @IBOutlet var stackView_pwd: UIStackView!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //print("LoginViewController---print viewDidLoad")
        addBaseViewBackgroud(picName:"bk_login@2x",isNeedBlur:false)
        //从持久层
        setTextField();
        tf_passWord.isSecureTextEntry = true        //密码框yangshi
    }
    
    
    /**
     读取系统保存上一次登录用户名，默认显示在两个UITextField上面
     */
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        //1.初始化下本页的navigationItem
        self.navigationController?.navigationBar.isTranslucent = false
    }
    
    //Actions 
    @IBAction func toServerConfig(_ sender:UIButton){
        print(#function)
    }
    /**
     viewdidload的时候从持久层获取数据吧
     */
    func setTextField(){
        
        let name = PersistenceUtil.readUserDefaultsKeyValueString(forKey: AppConst.KEY_LOGIN_USER_NAME)
        let pwd = PersistenceUtil.readUserDefaultsKeyValueString(forKey: AppConst.KEY_LOGIN_USER_PWD)
        if name != nil,name != ""{
            tf_userName.text = name
        }
        if pwd != nil,pwd != ""{
            tf_passWord.text = pwd
        }
    }
       
    /**
     登录按钮的action响应方法
     */
    @IBAction func toLogin(_ sender: UIButton) {

        if let userName = tf_userName.text,let pwd = tf_passWord.text,
            userName != "",pwd != ""{//输入非空
//            if checkLogin(userName, pwd){//如果用户名称密码输入正确
//                print("username and password is correnct! and let do it one more step!")
//                
//                //这是呈现下一个页面的方法
//                self.performSegue(withIdentifier: "login", sender: self)
//            
//            } else {//用户名密码不正确，提示下
//                
//            
//            }
            
            let urlLogin = InterfaceUtils.makeURL_login(userName: userName, password: pwd)
            Alamofire.request(urlLogin).responseJSON(completionHandler: { response in
                if let json = response.result.value{
                    print(json)
                   
                    self.doAccrodingLoginResult(jsonDictionary:json as! [String :Any ] )
                }
            })
        }else {//存在空输入时，提示下
            ToastUtil.makeCommonToast("无法登录哦，用户名或密码信息缺失").show()
            AnimateUtil.springHorizental(toView: stackView_user)
            AnimateUtil.springHorizental(toView: stackView_pwd)
        
        }
    }
    //假数据的测试方法检查登录合法性的方法
    func checkLogin(_ userName:String ,_ password:String)->Bool{
        if userName == "admin",password == "12345"{
            return true
        }else{
            return false
        
        }
    }
    /**
     判断登录结果的方法
     */
    func doAccrodingLoginResult(jsonDictionary jsonDic:Dictionary<String, Any> ){
        let success = jsonDic["success"] as! Bool
        //登录成功
        if success == true{
            //弹窗
            ToastUtil.makeCommonToast("登录成功").show()
            //封装APP的 当前用户对象
            encapsulateCurrentUserByResult(jsonDictionary:jsonDic)
            //把当前的输入用户名和密码，写入持久层
            PersistenceUtil.writeUserDefaultsKeyValueString(forKey: AppConst.KEY_LOGIN_USER_NAME, valueStr: tf_userName.text!)
            PersistenceUtil.writeUserDefaultsKeyValueString(forKey: AppConst.KEY_LOGIN_USER_PWD, valueStr: tf_passWord.text!)
            //跳转页面
            self.performSegue(withIdentifier: "login", sender: self)
            //查询当前的系统内部门
            CheckSystemDepartmentsBusiness().doCheckAllDepartmentsInSystem(restoreDest: AppGlobal.instance)
            //查询当前的系统内员工类型
            CheckSystemStaffTypesBusiness().doCheckAllStaffTypesInSystem(restoreDest: AppGlobal.instance)
            //查询当前的系统内事件类型
            CheckSystemEventListBusiness().doCheckEventTypesInSystem(restoreDest: AppGlobal.instance)
        }else{//登录失败
            ToastUtil.makeCommonToast("登录失败").show()
        
        }
    }
    /**
    登录成功后，根据结果，封装当前的用户对象，并且存在AppGlobal里面
     */
    func encapsulateCurrentUserByResult(jsonDictionary jsonDic:Dictionary<String, Any> ){
    
        let currentUser = User()
        //发现
        //currentUser.departIds = jsonDic["departIds"] as? Int
        //这样是不能把json里面的int解出来并赋值，用以下办法可以
        if let departIdInt = jsonDic["departIds"] as? String {
            print("departIdInt is : \(departIdInt)")
            currentUser.departIds = Int(departIdInt)
        }
        
        
        currentUser.department = jsonDic["department"] as? String
        currentUser.deviceName = jsonDic["deviceName"] as? String
        currentUser.rfid = jsonDic["rfid"] as? String
        currentUser.success = jsonDic["success"] as? Bool
        currentUser.userName = jsonDic["userName"] as? String
        AppGlobal.instance.currentLoginUser = currentUser
        
/*
        //输出赋值
        print("currentUser.departIds--->\(String(describing: currentUser.departIds))")
        print("currentUser.department--->\(String(describing: currentUser.department))")
        print("currentUser.rfid--->\(String(describing: currentUser.rfid))")
        print("currentUser.userName--->\(String(describing: currentUser.userName))")
        print("currentUser.deviceName--->\(String(describing: currentUser.deviceName))")
        print("currentUser.success--->\(String(describing: currentUser.success))")
 */
    
    }
    
    /**
     segue跳转时的准备工作
     */
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        switch segue.identifier {
        case "serverConfig"?:
            print("UIButton segue ---> serverConfig")
            break
        default:
            //
            break
        }
    }
}
