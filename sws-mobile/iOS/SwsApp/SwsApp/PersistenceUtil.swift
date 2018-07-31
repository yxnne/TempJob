//
//  PersistenceUtil.swift
//  SwsApp
//
//  Created by iel-mac on 2017/8/8.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  持久化工具类

import UIKit

class PersistenceUtil {
    /**得到存储路径*/
    static func getArchiveringURL(fileName name : String ) ->URL{
        let documentsDirectories = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
        let documentDirectory = documentsDirectories.first!
        return documentDirectory.appendingPathComponent(name)
    }
    
    /**NSKeyedArchiver写*/
    static func archiverSth(sthNeedArchivering sth : Any , toFile file: String){
        NSKeyedArchiver.archiveRootObject(sth, toFile: file)
    
    }
    /**NSKeyedArchiver读*/
    static func unArchiverString(toFile file: String) -> Any {
        return NSKeyedUnarchiver.unarchiveObject(withFile: file) as Any
        
    }
    /**UserDefaults 保存一个字符串*/
    static func writeUserDefaultsKeyValueString(forKey key :String ,valueStr string :String){
        UserDefaults.standard.set(string, forKey:key)
     
    }
    
    /**UserDefaults 保存一个字符串*/
    static func readUserDefaultsKeyValueString(forKey key :String ) -> String? {
        
        return UserDefaults.standard.value(forKey: key) as? String
    }

    
    /**UserDefaults 保存一个Int*/
    static func writeUserDefaultsKeyValueInt(forKey key :String ,valueStr string :Int){
        UserDefaults.standard.set(string, forKey:key)
        
    }
    
    /**UserDefaults 保存一个Int*/
    static func readUserDefaultsKeyValueInt(forKey key :String,defaultValue dValue:Int ) -> Int? {
        
        if let value = UserDefaults.standard.value(forKey: key){
            return (value as? Int) ?? dValue
        }else{
            return dValue
        }
        
    }
}

