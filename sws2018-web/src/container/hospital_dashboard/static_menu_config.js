// 定义一个页面和路由对应的对象
/*
当前的页面menu（静态的）
  X医院总览
  信息查询
    -- 数据记录
    -- 设备日志
  统计分析
    -- 依从率分析
    -- 洗手液统计
  系统业务
    -- 业务管理
  系统管理
    -- 用户管理
    -- 系统设置
  帮助中心
*/
const staticMenuInfos = [
  {
    icon : 'dashboard',
    title : '医院总览',
    linkPath : '/dashboard',
  },{
    icon : 'search',
    title : '信息查询',
    subMenus : [
      {
        title : '数据记录',
        linkPath : '/dashboard'
      }, {
        title : '设备日志',
        linkPath : '/dashboard'
      },
    ],
  },{
    icon : 'line-chart',
    title : '统计分析',
    subMenus : [
      {
        title : '依从率分析',
        linkPath : '/dashboard'
      }, {
        title : '洗手液统计',
        linkPath : '/dashboard'
      },
    ],
  },{
    icon : 'appstore-o',
    title : '系统业务',
    subMenus : [
      {
        title : '业务管理',
        linkPath : '/dashboard'
      }
    ],
  },{
    icon : 'usergroup-add',
    title : '系统管理',
    subMenus : [
      {
        title : '用户管理',
        linkPath : '/dashboard'
      }, {
        title : '系统设置',
        linkPath : '/dashboard'
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
