import * as actionType from './actionType'

const initState = {
  list:[
    { name:'谢贤', times:47, rank:1, role:'护士', department:'消化科', detail:[{position:'A处', times:30}, {position:'B处', times:17}] },
    { name:'马化腾', times:30, rank:2, role:'护士', department:'牙科', detail:[{position:'A处', times:30}] },
    { name:'王祖蓝', times:28, rank:3, role:'医生' , department:'泌尿科', detail:[{position:'A处', times:30}, {position:'B处', times:17}] },
    { name:'关谷神奇', times:27, rank:4, role:'医生', department:'牙科', detail:[{position:'A处', times:30}, {position:'B处', times:17}] },
    { name:'郭芙蓉', times:27, rank:5, role:'护士', department:'泌尿科', detail:[{position:'A处', times:30}, {position:'B处', times:17}] },
    { name:'佘太君', times:27, rank:6, role:'护师', department:'泌尿科', detail:[{position:'A处', times:30}, {position:'B处', times:17}] },
    { name:'邓九公', times:26, rank:7, role:'医生', department:'泌尿科', detail:[{position:'A处', times:30}, {position:'B处', times:17}] },
    { name:'土行孙', times:21, rank:8, role:'医生', department:'泌尿科', detail:[{position:'A处', times:30}, {position:'B处', times:17}] },
    { name:'武松', times:20, rank:9, role:'护士', department:'妇科', detail:[{position:'A处', times:30}, {position:'B处', times:17}] },
    { name:'宝钗', times:19, rank:10, role:'护士', department:'牙科', detail:[{position:'A处', times:30}, {position:'B处', times:17}] },
  ]
};

export default (state=initState, action) =>{
  switch(action.type){
    case actionType.GET_DEPARTMENT_LIST_STATISTIC:
      let newList = action.payload;
      return {...state, list:newList };
    default:
      return { ...state }

  }
};