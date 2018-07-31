//
//  ServerConfigViewController.swift
//  SwsApp
//
//  Created by iel-mac on 2017/7/17.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  配置服务器的参数的ViewControler

import UIKit
import Alamofire

class ServerConfigViewController : MyBackgroudImageViewController{
    
    
    @IBOutlet var tf_serverIP: UITextField!
    
    @IBOutlet var tf_serverPort: UITextField!
    
    @IBOutlet var stackViewPort: UIStackView!
    
    @IBOutlet var stackViewIp: UIStackView!
    //String 当前页面设置的IP地址和serverPort
    var ipAdress : String  = ""
    var portNo : String = ""
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        addBaseViewBackgroud(picName:"bk_login@2x",isNeedBlur:true)
        //从持久化层读数据

        tf_serverIP.text = AppGlobal.instance.appServerIPString
        tf_serverPort.text = AppGlobal.instance.appServerPortString

    }
    /**
     测试与服务器通信，看看测试是否可用，提示
     */
    @IBAction func testComunication2Server(_ sender: UIButton) {
        
        if let ip = tf_serverIP.text, let port = tf_serverPort.text,ip != "",port != ""{
            ipAdress = ip
            portNo = port
            
            //let urlAvailable = "http://\(ipAdress):\(portNo)/iel-hhms/web/app/serverConfigTest.action"
            let urlAvailable = InterfaceUtils.makeURL_testServerConnection(testIp: ipAdress, testPort: portNo)
            //使用AlamoFire发送请求：
            Alamofire.request(urlAvailable).responseJSON(completionHandler: {response in
                //debugPrint(response)
                if let json = response.result.value {
                    //print("\(json)")
                    //测试下toast
                    let jsonDic = json as! [String :Any ]
                    let res = jsonDic["result"] as! String
                    if "ok" == res {
                        //ok
                        ToastUtil.makeCommonToast("测试与服务器通信成功,当前配置可用!").show()
                    }else {
                        ToastUtil.makeCommonToast("测试与服务器通信失败!").show()
                    }
                    
                }else {
                    print("通信失败")
                    ToastUtil.makeCommonToast("测试与服务器通信失败!").show()
                }
            })
        }else{
            //配置信息没有完善
            ToastUtil.makeCommonToast("请完善配置信息").show()
            AnimateUtil.springHorizental(toView: stackViewPort)
            AnimateUtil.springHorizental(toView: stackViewIp)

        }
    }
    
    /**
     确认配置，写入持久化层，跳转
     */
    @IBAction func confirmServerConfig(_ sender: UIButton) {
        print(#function)
        if let ip = tf_serverIP.text, let port = tf_serverPort.text,ip != "",port != ""{
            ipAdress = ip
            portNo = port
//            PersistenceUtil.writeUserDefaultsKeyValueString(forKey: AppConst.KEY_CONFIG_SERVER_IP, valueStr: ipAdress)
//            PersistenceUtil.writeUserDefaultsKeyValueString(forKey: AppConst.KEY_CONFIG_SERVER_PORT, valueStr: port)
            AppGlobal.instance.setGlobalIpAndPort(ip: ipAdress, port: port)
            
        }else{
            //配置信息没有完善
            ToastUtil.makeCommonToast("请完善配置信息").show()
            AnimateUtil.springHorizental(toView: stackViewPort)
            AnimateUtil.springHorizental(toView: stackViewIp)
            
        }
        //跳转
        ToastUtil.makeCommonToast("您的配置已更新：\nIP地址:\(ipAdress)\n端口号:\(portNo)").show()
        self.dismiss(animated: true, completion: nil)
    }
    
    /**
     这里应该读取应用对于服务器数据的上一次设置了
     显示在两个UITextField：ServerIP 和 ServerPort 上面
     */
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        
//        不用在这一页集成 navigationController 了
//        所以这两句不需要了
//        self.navigationController?.navigationBar.isTranslucent = true
//        self.navigationItem.title = "服务器信息配置"
        
    }

    @IBAction func back(_ sender: UIStoryboardSegue) {
        
        self.dismiss(animated: true, completion: nil)
    }

    

}
