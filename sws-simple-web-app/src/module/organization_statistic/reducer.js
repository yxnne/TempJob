import * as actions from './actiontype'

const initState = {
  name:'hospital',
  totalTimes:201,
  roleTimes:[
    { role: '医生', times: 40 },
    { role: '护士', times: 21 },
    { role: '保洁', times: 17 },
    { role: '医师', times: 13 },
    { role: '护师', times: 9 }
  ],
  weekTimes:[
    { date: '07-04', times: 275},
    { date: '07-03', times: 115 },
    { date: '07-02', times: 120 },
    { date: '07-01', times: 350 },
    { date: '06-30', times: 350 },
    { date: '06-29', times: 350 },
    { date: '06-28', times: 150 }
  ]
};

export default (state=initState, action) => {
  switch( action.type ){
    case actions.GET_ORGANIZATION_STATISTIC:
      //TODO: 
      return { ...state, ...action.payload }
    case actions.TEST_ORGANIZATION_STATISTIC:
      //TODO: 
      return { ...state, weekTimes:[
        { date: '07-04', times: 275},
        { date: '07-03', times: 115 },
        { date: '07-02', times: 120 },
        { date: '07-01', times: 350 },
        { date: '06-28', times: 150 }
      ] }
    default:
      return state;
  }
};
