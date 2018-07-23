import * as actionType from './actionType'

const initState = {
  list:[
    { name:'加载中', times:200, rank:1 },
    { name:'加载中', times:390, rank:2 },
  ]
};

export default (state=initState, action) =>{
  switch(action.type){
    case actionType.GET_DEPARTMENT_LIST_STATISTIC:
      return {list: action.payload };
    default:
      return { ...state }

  }
};