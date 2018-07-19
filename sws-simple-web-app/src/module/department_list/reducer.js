import * as actionType from './actionType'

const initState = {
  list:[
    { name:'骨科', times:417, rank:1 },
    { name:'消化科', times:390, rank:2 },
    { name:'神经内科', times:288, rank:3 },
    { name:'神经外科', times:251, rank:4 },
    { name:'内分泌科', times:249, rank:5 },
    { name:'泌尿科', times:230, rank:6 },
    { name:'妇科', times:226, rank:7 },
    { name:'皮肤科', times:217, rank:8 },
    { name:'牙科', times:202, rank:9 },
    { name:'耳鼻喉科', times:193, rank:10 },
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