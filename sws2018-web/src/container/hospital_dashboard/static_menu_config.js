// 路由配置
export function getPagePaths(){
  return {
    hospitalOverall:'/dashboard/hospitalOverall', // X医院总览
    eventCheck:'/dashboard/eventCheck', // 事件查询
    deviceStatusCheck:'/dashboard/deviceStatusCheck', // 设备状态查询
    statisticDepartmentRate:'/dashboard/statisticDepartmentRate', // 部门依从率统计
    statisticLiquid:'/dashboard/statisticLiquid', // 洗手液统计
    bizManage:'/dashboard/bizManage', // 业务管理
    deviceQuickCheck:'/dashboard/deviceQuickCheck',// 设备查询
    apManage:'/dashboard/apManage', // AP管理
    userManage:'/dashboard/apManage', // 用户管理
    setting:'/dashboard/setting', // 系统设置
  };
}
const paths = getPagePaths();
// 定义一个页面和路由对应的对象
/*
当前的页面menu（静态的）
  X医院总览
  信息查询
    -- 事件查询
    -- 设备日志
  统计分析
    -- 依从率分析
    -- 洗手液统计
  系统业务
    -- 业务管理
  设备信息
    -- 设备查询
    -- AP管理
  系统管理
    -- 用户管理
    -- 系统设置
  帮助中心
*/
const staticMenuInfos = [
  {
    icon : 'dashboard',
    title : '医院总览',
    linkPath : paths.hospitalOverall,
  },{
    icon : 'search',
    title : '信息查询',
    subMenus : [
      {
        title : '事件查询',
        linkPath : paths.eventCheck
      }, {
        title : '设备日志',
        linkPath : paths.deviceStatusCheck
      },
    ],
  },{
    icon : 'line-chart',
    title : '统计分析',
    subMenus : [
      {
        title : '依从率分析',
        linkPath : paths.statisticDepartmentRate
      }, {
        title : '洗手液统计',
        linkPath : paths.statisticLiquid
      },
    ],
  },{
    icon : 'appstore-o',
    title : '系统业务',
    subMenus : [
      {
        title : '业务管理',
        linkPath : paths.bizManage
      }
    ],
  },{
    icon : 'hdd',
    title : '设备信息',
    subMenus : [
      {
        title : '设备查询',
        linkPath : paths.deviceQuickCheck
      }, {
        title : '物联网平台管理',
        linkPath : paths.apManage
      },
    ],
  },{
    icon : 'usergroup-add',
    title : '系统管理',
    subMenus : [
      {
        title : '用户管理',
        linkPath : paths.userManage
      }, {
        title : '系统设置',
        linkPath : paths.setting
      },
    ],
  },{
    icon : 'question-circle-o',
    title : '帮助中心',
    linkPath : '/dashboard',
  }
];

// 输出这个函数来得到默认的静态MenuInfo是
export function getStaticMenusInfos(){
  return staticMenuInfos;
}
