//
//  AppGlobal.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/11.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  全局管理的类

import UIKit

class AppGlobal {
    
    //APP 的服务器信息
    var appServerIPString = ""
    var appServerPortString = ""
    static let instance = AppGlobal()
    
    //当前的登录的用户
    var currentLoginUser:User?
    //当前系统内的部门
    var currentSysDepartments:[Department]?
    //当前系统内的人员类别
    var currentSysStaffTypes:[StaffType]?
    //当前系统内的事件类型
    var currentSysEventTypes:[EventType]?
    //实时事件的刷新时间
    var eventFreshInterval:Int?
    //设备的刷新时间
    var deviceFreshInterval:Int?
    
    private init() {
        
        //updateGlobalIpAndPortFromPersistentLayer()
        print("init--> \(appBaseDomin)")
    }
    
    var appBaseDomin :String{
        get{
            return "http://\(appServerIPString):\(appServerPortString)/"
        }
    
    }
    /**设置全局的IP地址和端口号*/
    func setGlobalIpAndPort(ip ipAdress:String ,port portNo :String ){
        
        appServerIPString = ipAdress
        appServerPortString = portNo
        writeIpAndPortToPersistentLayer(ip:appServerIPString ,port  :appServerPortString )
        
        
    }
    /**从持久层更新下appServer*/
    func updateGlobalIpAndPortFromPersistentLayer(){
    
        //读取值
        //Server IP
        if let serverIP = PersistenceUtil.readUserDefaultsKeyValueString(forKey: AppConst.KEY_CONFIG_SERVER_IP){
            appServerIPString = serverIP
        }
        //ServerPort
        if let serverPort = PersistenceUtil.readUserDefaultsKeyValueString(forKey: AppConst.KEY_CONFIG_SERVER_PORT){
            appServerPortString = serverPort
        }
        //其他设置设置
        //事件刷新时间
        let eventInterval = PersistenceUtil.readUserDefaultsKeyValueInt(forKey: AppConst.KEY_FRESH_INTERVAL_EVENT, defaultValue: AppConst.DEFAULT_FRESH_INTERVAL_EVENT)
        eventFreshInterval = eventInterval
        
        let deviceInterval = PersistenceUtil.readUserDefaultsKeyValueInt(forKey: AppConst.KEY_FRESH_INTERVAL_DEVICE, defaultValue: AppConst.DEFAULT_FRESH_INTERVAL_DEVICE)
        deviceFreshInterval = deviceInterval
        
        print("updateGlobalIpAndPortFromPersistentLayer is called , the appBaseDomin is :\(appBaseDomin)")
        print("事件刷新时间是：\(String(describing: eventInterval))")
        print("设备刷新时间是：\(String(describing: deviceInterval))")
    }
    
    /**向持久层中写入*/
    private func writeIpAndPortToPersistentLayer(ip ipAdress:String ,port portNo :String ){
        PersistenceUtil.writeUserDefaultsKeyValueString(forKey: AppConst.KEY_CONFIG_SERVER_IP, valueStr: ipAdress)
        PersistenceUtil.writeUserDefaultsKeyValueString(forKey: AppConst.KEY_CONFIG_SERVER_PORT, valueStr: portNo)
        
    }

}
