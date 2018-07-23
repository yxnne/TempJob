import * as actions from './actiontype'

const initState = {
  name:'hospital',
  totalTimes:'加载中',
  roleTimes:[
    { role: '医生', times: 40 },
    { role: '护士', times: 40 },
    { role: '保洁', times: 40 },
    { role: '医师', times: 40 },
    { role: '护师', times: 40 }
  ],
  weekTimes:[
    { day: '加载中', times: 150},
    { day: '加载中', times: 150 },
    { day: '加载中', times: 150 },
    { day: '加载中', times: 150 },
    { day: '加载中', times: 150 },
    { day: '加载中', times: 150 },
    { day: '加载中', times: 150 }
  ]
};

export default (state=initState, action) => {
  switch( action.type ){
    case actions.GET_ORGANIZATION_STATISTIC:
      //return { ...action.payload }
      // 这里的数据,需要处理下，是因为后台接口这里数据不一致
      const daysDatas = action.payload.roleTimes;
      let realTotal = 0;
      for(let one of daysDatas){
        realTotal += one.times
      }
      return { ...state, ...action.payload, totalTimes:realTotal }
    case actions.TEST_ORGANIZATION_STATISTIC:
      //TODO: 
      return { ...state, weekTimes:[
        { day: '07-04', times: 275},
        { day: '07-03', times: 115 },
        { day: '07-02', times: 120 },
        { day: '07-01', times: 350 },
        { day: '06-28', times: 150 }
      ]}
    default:
      return state;
  }
};
