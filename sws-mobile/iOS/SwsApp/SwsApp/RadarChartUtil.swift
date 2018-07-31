//
//  RadarChartUtil.swift
//  SwsApp
//
//  Created by iel-mac on 2017/7/28.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  雷达图的工具方法

import UIKit
import Charts

class RadarChartUtil{
    
    static func getRadarShowRolesRate(frame radarFrame : CGRect,
                                      pointValues values: Array<Double>,
                                      roleLabelString xStrings :[String],
                                      outerColorHexString lineColorString :String ,
                                      innerColorHexString innerColorString :String ) ->RadarChartView{
        
        let radarChart = getRadarChaertWithDatas(frame: radarFrame,
                                                 pointValues: values,
                                                 outerColorHexString: lineColorString ,
                                                 innerColorHexString: innerColorString  )
        //一些设置
        radarChart.legend.enabled = false               //不要图例
        radarChart.chartDescription?.text = ""          //不要描述
        ///radarChart.
        
        radarChart.yAxis.enabled = true
        radarChart.yAxis.forceLabelsEnabled = false
        radarChart.yAxis.labelTextColor = UIColor.clear
        //radarChart.xAxis.labelCount = 1
        radarChart.yAxis.labelCount = 5                 //五个刻度线
        //radarChart.yAxis.drawAxisLineEnabled = true   //不要刻度
        radarChart.yAxis.axisMinimum = 0
        
        let myXAxisFormatter = MyXAsixRadarValueFormatter()
        myXAxisFormatter.setLabelStrings(lableStrings: xStrings)
        radarChart.xAxis.valueFormatter = myXAxisFormatter
        radarChart.xAxis.labelPosition = .bottom
        radarChart.setExtraOffsets(left: 0, top: 20, right: 0, bottom: 0)
    
        
        //颜色设置
        /*
         mChart.setWebLineWidth(1f);
         mChart.setWebColor(Color.LTGRAY);
         mChart.setWebLineWidthInner(1f);
         mChart.setWebColorInner(Color.LTGRAY);
         mChart.setWebAlpha(100);
         */
        radarChart.webLineWidth = 1
        radarChart.webColor = UIColor.lightGray
        //radarChart.innerWebLineWidth = 2
        radarChart.innerWebColor = UIColor.lightGray
        radarChart.webAlpha = 1.0
        radarChart.tintColor = UIColor.red
        
        return radarChart
        
    }
    
    
    /**
     得到雷达图的基本方法
     */
    private static func getRadarChaertWithDatas(frame radarFrame : CGRect,
                                        pointValues values: Array<Double>,
                                        outerColorHexString lineColorString :String ,
                                        innerColorHexString innerColorString :String) -> RadarChartView{
        
        let radarChart = RadarChartView(frame : radarFrame)
        var dataEntries: [ChartDataEntry] = []
        for i in 0..<values.count{
            let dataEntry = ChartDataEntry(x: Double(i), y: values[i])
            dataEntries.append(dataEntry)
        }
        //根据键值对构造数据集
        let radarChartDataSet = RadarChartDataSet(values: dataEntries, label: "")
        radarChartDataSet.setColor(UIColor.colorWithHexString(hex: lineColorString))
        radarChartDataSet.fillColor = UIColor.colorWithHexString(hex: innerColorString)
        radarChartDataSet.fillAlpha = 0.9
        radarChartDataSet.drawFilledEnabled = true
        
        let radarChartData = RadarChartData()
        radarChartData.addDataSet(radarChartDataSet)
        
        //设置数据
        radarChart.data = radarChartData
        
        return radarChart
        
    }
    
}


