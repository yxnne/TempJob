//
//  LineChartUtil.swift
//  IOS_Test1
//
//  Created by iel-mac on 2017/7/24.
//  Copyright © 2017年 iel-mac. All rights reserved.
//
//  折线图的工具方法
/**
 使用方法实例
 //1.先构造数据
 let dataDoc = LineChartRoleDataWrapper(roleName: "医生", roleDatas: [0.9,0.8,0.7, 0.8,0.6,0.9 ,0.7], roleColor: UIColor.blue)
 let dataNurse = LineChartRoleDataWrapper(roleName: "护士", roleDatas: [0.5,0.66,0.88, 0.90,0.90,0.91 ,0.85], roleColor: UIColor.yellow)
 
 let dataWorker = LineChartRoleDataWrapper(roleName: "护工", roleDatas: [0.35,0.45,0.55, 0.35,0.45,0.55 ,0.35], roleColor: UIColor.red)
 //2.完成图表构建
 let lineChart = LineChartUtil.getLineChartRolesWeeks(frame: CGRect(x:50,y:500,width:350,height:100),
 roleDatas: [dataDoc,dataNurse,dataWorker],
 weekStrs: DateUtil.getOneWeekDateStringsEndInTody())
 //3.添加到父视图
 view.addSubview(lineChart)
 
 
*/
import UIKit
import Charts

/**
 该类是本项目获得折线图的工具类
 */
class LineChartUtil {
    static func getLineChartRolesWeeks(frame lineFrame : CGRect,
                                       roleDatas roleDatasWrapers :[LineChartRoleDataWrapper] ,
                                       weekStrs xAxisStrings :[String] ) -> LineChartView{
        //构造ChartData
        let lineData = LineChartData()
        //根据参数拆解数据
        for dataWrapper in roleDatasWrapers{
            //每一次循环都得到一种角色的数据
            let dataChartSet = LineChartDataSet(values: dataWrapper.roleDataEntrys, label: dataWrapper.roleNameStr)
            dataChartSet.colors = [dataWrapper.roleColor]
            //对数据集设置
            dataChartSet.drawCirclesEnabled = true
            dataChartSet.highlightEnabled = false           //不显示点击坐标
            dataChartSet.mode = .cubicBezier                //光滑线
            dataChartSet.circleHoleRadius = 1               //内圆
            dataChartSet.circleRadius = 2                   //外圆
            //dataChartSet.circleHoleColor = dataWrapper.roleColor
            dataChartSet.circleColors = [UIColor.darkGray]  //外圆颜色
            
            //添加数据集
            lineData.addDataSet(dataChartSet)
        }
        
        let lineChart = LineChartView(frame:lineFrame)
        //添加数据
        lineChart.data = lineData
        
        //图表样式的一些调整
        //Y轴样式设置
        let rightY = lineChart.rightAxis
        rightY.enabled = true                    //不要右边轴线
        rightY.labelCount = 4
        rightY.gridLineDashLengths = [4 , 1.5]   //虚线样式
        rightY.drawAxisLineEnabled = false       //不画轴线
        rightY.labelTextColor = UIColor.clear
        
        let leftY = lineChart.leftAxis
        leftY.labelCount = 4                    //辅助线的数量
        leftY.gridLineDashLengths = [4 , 1.5]   //虚线样式
        leftY.drawAxisLineEnabled = false       //不画轴线
        leftY.labelTextColor = UIColor.clear    //刻度颜色 clear
        //leftY.xOffset = -100
        
        //X轴样式设置
        let bottomX = lineChart.xAxis
        bottomX.drawAxisLineEnabled = true
        bottomX.labelPosition = .bottom         //位置
        bottomX.gridColor = UIColor.clear
        //bottomX.xOffset = -20
        
        
        
        //x轴的刻度值得确定
        let xValueFormatter = XValueFoematter()
        xValueFormatter.xAxisValues = xAxisStrings
        bottomX.valueFormatter = xValueFormatter
        
        //显示图例
        let legend = lineChart.legend
        legend.horizontalAlignment = .right    //图例位置右上角
        legend.verticalAlignment = .top
        legend.form = .circle                  //样式，圆
        
        //不要描述
        lineChart.chartDescription?.text = ""
        //图表动画
        lineChart.animate(yAxisDuration: 0.8, easingOption: .easeInBounce)
        return lineChart
    }
    
    
    
}
/**
 结构体
 封装数据键值对，封装颜色，和 数据名字
 */
struct LineChartRoleDataWrapper {
    //图表的数据键值对
    public var roleDataEntrys:[ChartDataEntry]
    //颜色
    public var roleColor:UIColor
    //数据名称
    public var roleNameStr:String
    init() {
        roleDataEntrys = []
        roleColor = UIColor.clear
        roleNameStr = ""
    }
    
    init(roleName roleNameString :String,
         roleDatas roleDatasDouble:[Double],
         roleColor roleColorPresentation:UIColor){
        self.init()
        //填充数据
        self.roleNameStr = roleNameString
        self.roleColor = roleColorPresentation
        for i in 0..<roleDatasDouble.count{
            let chartEntry = ChartDataEntry(x: Double(i), y: roleDatasDouble[i])
            self.roleDataEntrys.append(chartEntry)
            
        }
        
    }
    
}
/**
 这个类实现了IAxisValueFormatter接口，主要是为了确定x轴上的刻度值
 */
class XValueFoematter:NSObject, IAxisValueFormatter{
    
    var xAxisValues : [String ]?
    //实现协议的方法
    public func stringForValue(_ value: Double,
                               axis: AxisBase?) -> String{
        if let strings  = xAxisValues{
            return strings[ Int(value) ]
        }
        return "待定"
    }
}
