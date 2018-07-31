//
//  InterfaceUtils.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/12.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  InterfaceUtils接口 的工具类

import UIKit

class InterfaceUtils{
    
    private static let projName = AppConst.WEB_PROJECT_INTERFACE_NAME
    /**
     系统参数
     得到系统内事件类型
     http://192.168.1.7:8080/iel-hhms/web/app/eventList.action
     */
    static func makeURL_getEventTypesAndIdsInSys() ->String{
        let domin = AppGlobal.instance.appBaseDomin
        let funcName = "eventList.action"
        return domin + projName + funcName
    }
    /**
     系统参数
     得到系统内所有的部门：这个方法应该常用
     */
    static func makeURL_getDepartmentInSys() ->String {
        let domin = AppGlobal.instance.appBaseDomin
        let funcName = "getDepartments.action"
        
        return domin + projName + funcName + "?departIds=1"     //这里的参数传成departIds=1就是查询所有部门
    }
    /**
     系统参数
     得到部门根据部门Id:估计这个方法用不到
     */
    static func makeURL_getDepartmentInSys(forDepartIds departIDs :Int) ->String {
        let domin = AppGlobal.instance.appBaseDomin
        let funcName = "getDepartments.action"
        
        return domin + projName + funcName + "?departIds=\(departIDs)"
    }
    /**
     系统参数
     得到所有员工的类型和对应的id
     */
    static func makeURL_getAllStaffTypesAndIdsInSys() ->String {
        let domin = AppGlobal.instance.appBaseDomin
        let funcName = "rateByStaffList.action"
        
        return domin + projName + funcName
    }
    
    /**
     配置测试
     测试服务器配置信息
     */
    static func makeURL_testServerConnection(testIp ip:String ,testPort port :String )->String{
        //测试配置可用这里不应该是全局的domin，而是这里拼装domin
        //let domin = AppGlobal.instance.appBaseDomin
        let testDomin = "http://\(ip):\(port)/"
        let funcName = "serverConfigTest.action"
        return testDomin + projName + funcName
    }
    
    /**
     登录接口
     */
    static func makeURL_login(userName name:String , password pwd:String )->String {
        let domin = AppGlobal.instance.appBaseDomin
        let funcName = "appLogin.action"
        return domin + projName + funcName + "?userName=\(name)&passWord=\(pwd)"
    }

    /**
     部门情况
     四大时机，整体依从率，人员类别统计
     */
    static func makeURL_getDepartOverall(startTimeStr startTime:String ,endTimeStr endTime :String ,departStr depart:String )->String{
        let domin = AppGlobal.instance.appBaseDomin
        let funcName = "rateByDepartListSelect.action"

        return domin + projName + funcName + "?startTime=\(startTime)&endTime=\(endTime)&depart=\(depart)"
    }

    /**
     部门情况
     洗手时机次数
     http://192.168.1.7:8080/iel-hhms/web/app/rateMomentDepart.action?startTime=2017-7-20&endTime=2017-8-20&treeId=1&departIds=52&staffType=1,2,3,4,5
     */
    static func makeURL_getDepartWashTimes(startTimeStr startTime:String ,endTimeStr endTime :String ,departStr depart:String )->String {
        //全部人员
        var jobTypesStrings = ""
        let sysStaffTypes = AppGlobal.instance.currentSysStaffTypes
        for type in sysStaffTypes!{
            jobTypesStrings += "\(type.typeId!),"
        }
        let domin = AppGlobal.instance.appBaseDomin
        let funcName = "rateMomentDepart.action"
        
        return domin + projName + funcName + "?treeId=1&startTime=\(startTime)&endTime=\(endTime)&departIds=\(depart)&jobType=" + jobTypesStrings
    }

    /**
     员工依从率:
     得到部门或全院所有类别的员工的依从率List
     rateByStaffSelect.action
     http://192.168.1.7:8080/iel-hhms/web/app/rateByStaffSelect.action?treeId=1&departIds=1&staffId=-1&startTime=2017-7-20&endTime=2017-8-20&jobType=1,2,3,4,5
     */
    static func makeURL_getAllTypesStaffRateListByDepartments(startTimeStr startTime:String ,endTimeStr endTime :String ,departStr depart:String ) ->String {
        //得到系统全部人员
        var jobTypesStrings = ""
        let sysStaffTypes = AppGlobal.instance.currentSysStaffTypes
        for type in sysStaffTypes!{
            jobTypesStrings += "\(type.typeId!),"
        }
        
        let domin = AppGlobal.instance.appBaseDomin
        let funcName = "rateByStaffSelect.action"
        //treeId=1&staffId=-1这个是固定的
        return domin + projName + funcName + "?treeId=1&staffId=-1&startTime=\(startTime)&endTime=\(endTime)&departIds=\(depart)&jobType=" + jobTypesStrings
    }
    
    /**
     事件查询:按时间
     全部事件，全部人员类型
     //可用：
     http://192.168.1.7:8080/iel-hhms/web/app/eventSelect.action?treeId=1&departIds=1&staffId=-1&startTime=2017-7-20&endTime=2017-8-20
     
     */
    static func makeURL_eventCheck_allEventAllStaffType(startTimeStr startTime:String ,
                                                        endTimeStr endTime :String ,
                                                        departStr depart:String,
                                                        pageNum page:String) ->String {
        //所有事件类型//所有人员类别，默认不用专门穿参数
        let domin = AppGlobal.instance.appBaseDomin
        let funcName = "eventSelect.action"
        //treeId=1&staffId=-1这个是固定的
        return domin + projName + funcName + "?treeId=1&staffId=-1&startTime=\(startTime)&endTime=\(endTime)&departIds=\(depart)&pageNum=\(page)"
    }
    
    /**
     事件查询：通过人员类别查询
     这个接口后台没有实现暂时
     
     */
    static func makeURL_eventCheck_allEvent_ByStaffType(startTimeStr startTime:String ,
                                                        endTimeStr endTime :String ,
                                                        departStr depart:String,
                                                        pageNum page:String,
                                                        staffTypeId staffType:String ) ->String {
        return makeURL_eventCheck_allEventAllStaffType(startTimeStr :startTime ,endTimeStr :endTime ,departStr :depart,pageNum :page) +
            "&jobType=\(staffType)"
    }
    /**
     事件查询：通过事件类别查询
     */
    static func makeURL_eventCheck_allStaffType_ByEvent(startTimeStr startTime:String ,
                                                        endTimeStr endTime :String ,
                                                        departStr depart:String,
                                                        pageNum page:String,
                                                        eventTypeId eventType:String ) ->String {
        return makeURL_eventCheck_allEventAllStaffType(startTimeStr :startTime ,endTimeStr :endTime ,departStr :depart,pageNum :page) +
        "&eventType=\(eventType)"
    }
    
    /**
     七天数据：
     人员按类别依从率
     http://192.168.124.19/iel-hhms/web/app/rateByStaffTypeIOS.action?treeId=1&staffType=1,2,3,4,5,6,7,8,9&startTime=2017-8-11&endTime=2017-8-18&departIds=1
     */
    static func makeURL_rolesRateInAWeek(startTimeStr startTime:String ,
                                         endTimeStr endTime :String ,
                                          departStr depart:String) ->String {
        //得到系统全部人员
        var jobTypesStrings = ""
        let sysStaffTypes = AppGlobal.instance.currentSysStaffTypes
        for type in sysStaffTypes!{
            jobTypesStrings += "\(type.typeId!),"
        }
        
        let domin = AppGlobal.instance.appBaseDomin
        let funcName = "rateByStaffTypeIOS.action"
        //treeId=1&staffId=-1这个是固定的
        return domin + projName + funcName + "?treeId=1&startTime=\(startTime)&endTime=\(endTime)&departIds=\(depart)&staffType=" + jobTypesStrings
    }
    /**
     设备信息:部门内,当前状态
     http://localhost/iel-hhms/web/app/getDeviceStatusByDepartId.action?departIds=1
     */
    static func makeURL_deviceInfoOverAll(departStr depart:String)->String{
        //所有事件类型//所有人员类别，默认不用专门穿参数
        let domin = AppGlobal.instance.appBaseDomin
        let funcName = "getDeviceStatusByDepartId.action"
        //treeId=1&staffId=-1这个是固定的
        return domin + projName + funcName + "?departIds=\(depart)"
        
    }
    
}
