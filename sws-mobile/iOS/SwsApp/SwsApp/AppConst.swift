//
//  AppConst.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/8.
//  Copyright © 2017年 iel-mac. All rights reserved.
//

import UIKit

class AppConst{
    /**项目工程名*/
    static let WEB_PROJECT_INTERFACE_NAME       = "iel-hhms/web/app/"       //服务器的项目名
    /**键索引-------------------*/
    static let KEY_CONFIG_SERVER_IP             = "config_server_ip"        //服务器IP地址
    static let KEY_CONFIG_SERVER_PORT           = "config_server_port"      //服务器端口号
    
    static let KEY_LOGIN_USER_NAME              = "login_user_name"         //用户名
    static let KEY_LOGIN_USER_PWD               = "login_user_pwd"          //密码
    
    static let KEY_FRESH_INTERVAL_EVENT         = "fresh_interval_event"    //事件的刷新时间设置
    static let KEY_FRESH_INTERVAL_DEVICE        = "fresh_interval_device"   //设备的刷新时间设置
    /**默认值-------------------*/
    static let DEFAULT_FRESH_INTERVAL_EVENT     = 3                         //事件的刷新时间设置：默认时间
    static let DEFAULT_FRESH_INTERVAL_DEVICE    = 60                        //设备的刷新时间设置：默认时间

}
