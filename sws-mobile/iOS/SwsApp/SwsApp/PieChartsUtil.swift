//
//  PieChartUtil.swift
//  IOS_Test1
//
//  Created by iel-mac on 2017/7/22.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  饼图的工具方法

import UIKit
import Charts

class PieChartsUtil{
    
    /**
     方法使用说明：
        在ViewController中
     
        let piePercent = PieChartsUtil.getPieChartWithPercent(frame: CGRect(x:50,y:300,width:300,height:100),
                                                        percent: 70,
                                                        colorMainStrHex :"#7CE8E1",
                                                        colorBgStrHex :"#C8FBF8",
                                                        colorMiddleRingStrHex:"#C8FBF8")
     
        view.addSubview(piePercent)
     
     方法使用说明：
     pieChartDepart = PieChartsUtil.getPieChartMoreKindsDatas(frame: viewRect,
                                                        sectionValues: datasArray,
                                                        sectionDescription: datasLabelsArray,
                                                        sectionColorStrHex: dataColorsArray,
                                                        isNeedValueLine: true)
     view.addSubview(piePercent)
     
    */
    /**
     得到饼状图的静态方法
     这个方法用于呈现百分比
     */
    static func getPieChartWithPercent(frame pieFrame : CGRect,
                                       percent percentDouble:Double,
                                       colorMainStrHex colorMain:String,
                                       colorBgStrHex colorBg:String,
                                       colorMiddleRingStrHex colorRing:String) -> PieChartView{
        //准备数据
        var percentDatas :Array<Double> = []
        percentDatas.append(percentDouble)
        percentDatas.append(100.0 - percentDouble)
        //准备颜色集合
        var colors :[String] = []
        colors.append(colorMain)
        colors.append(colorBg)
        
        
        //根据数据得到图表,就是一个百分比,调用另一个方法
        let piePercnet = getPieChartWithDatas(frame :pieFrame,
                                              sectionValues : percentDatas,
                                              sectionDescription :["1","2"],
                                              sectionColorStrHex:colors,
                                              isNeedValueLine: false)
        //得到图表后
        //一些特殊设置
        //关于中心圆环
        piePercnet.holeRadiusPercent = 0.75
        piePercnet.transparentCircleColor = UIColor.colorWithHexString(hex: colorRing)
        piePercnet.transparentCircleRadiusPercent = 0.8
        //piePercnet.holeColor = UIColor.colorWithHexString(hex: "#00C0B9") //这个会设置整个圆心的颜色
        
        //关于中间文字
        piePercnet.drawCenterTextEnabled = true
        //makeDoubleToPrecentString
        
        piePercnet.centerText = "\(String(format: "%.1f", percentDouble)) %"
        //不要图例
        piePercnet.legend.enabled = false
        piePercnet.usePercentValuesEnabled = true
        
        //不要图表描述了
        piePercnet.chartDescription?.text = ""
        //图表的初始角度
        piePercnet.rotationAngle = -85
        piePercnet.animate(xAxisDuration: 1, easingOption: ChartEasingOption.easeOutSine)
        return piePercnet
    }
    
    /**
     多种类型的数据调用的工具方法
     */
    static func getPieChartMoreKindsDatas(frame pieFrame : CGRect,
                                          sectionValues values: Array<Double>,
                                          sectionDescription dataPoints :Array<String>,
                                          sectionColorStrHex colorStrHex:Array<String>,
                                          isNeedValueLine needVlueLine :Bool) -> PieChartView{
        //根据数据得到图表,就是一个百分比,调用另一个方法
        let pie = getPieChartWithDatas(frame :pieFrame,
                                              sectionValues : values,
                                              sectionDescription :dataPoints,
                                              sectionColorStrHex:colorStrHex,
                                              isNeedValueLine: needVlueLine)
        //一些特殊设置
        //关于中心圆环
        pie.holeRadiusPercent = 0.75
        //pie.transparentCircleColor = UIColor.colorWithHexString(hex: colorRing)
        pie.transparentCircleRadiusPercent = 0.8
        //piePercnet.holeColor = UIColor.colorWithHexString(hex: "#00C0B9") //这个会设置整个圆心的颜色
        
        //不要图例
        pie.legend.enabled = true
        pie.usePercentValuesEnabled = true
        
        //不要图表描述了
        pie.chartDescription?.text = ""
        //图表的初始角度
        pie.rotationAngle = -85
        pie.animate(xAxisDuration: 1, easingOption: ChartEasingOption.easeOutSine)

    
        return pie
    }
    
    
    /**
     得到饼状图的静态方法
     这个方法用于呈现百分比
     */
    private static func getPieChartWithDatas(frame pieFrame : CGRect,
                                     sectionValues values: Array<Double>,
                                     sectionDescription dataDescription :Array<String>,
                                     sectionColorStrHex colorStrHex:Array<String>,
                                     isNeedValueLine needVlueLine :Bool) -> PieChartView {
        print(#function)
        
        
        //按CGRect的大小新建一个饼状图
        let pie = PieChartView(frame : pieFrame)
        
        //初始化数据集合键值对
        var dataEntries: [ChartDataEntry] = []
        
        //遍历数据，添加到键值对集合里面
        for i in 0..<dataDescription.count {
            let dataEntry = ChartDataEntry(x: Double(i), y: values[i])
            dataEntries.append(dataEntry)
        }
        
        //根据键值对，构建图表使用的数据集合
        let pieChartDataSet = PieChartDataSet(values: dataEntries, label: "")
        
        //pieChartData.add
        
        //添加颜色
        var colors: [UIColor] = []
        for i in 0..<colorStrHex.count{
            colors.append(UIColor.colorWithHexString(hex: colorStrHex[i]) )
            
        }
        
        pieChartDataSet.colors = colors
        //文字说明不要
        pieChartDataSet.drawValuesEnabled = false
        //选中态的缩放比例
        pieChartDataSet.selectionShift = 3
        
        //把数据集合转换成Pie图的数据
        let pieChartData = PieChartData()
        pieChartData.addDataSet(pieChartDataSet)
        
        if(needVlueLine){//需要数值引线的样式
            //需要数值引线
            pieChartDataSet.drawValuesEnabled = true
            //引线的颜色
            pieChartDataSet.valueColors = [UIColor.black]
            //引线样式
            pieChartDataSet.valueLinePart1OffsetPercentage = 0.8;
            pieChartDataSet.valueLinePart1Length = 0.2;
            pieChartDataSet.valueLinePart2Length = 0.4;
            //使你的引线在外侧
            pieChartDataSet.yValuePosition = .outsideSlice
            
            //设置y值得样式
            let labelFormatter = MyPieChartValueFormatter()
            labelFormatter.setDataLabelStrings(dataLableStrings: dataDescription)
            pieChartData.setValueFormatter(labelFormatter)
            pieChartData.setValueFont(NSUIFont.systemFont(ofSize: 10))   //字体大小
        }else{
            pieChartDataSet.drawValuesEnabled = false
        }
        
        pie.data = pieChartData
        return pie
        
    }
    
    
}
