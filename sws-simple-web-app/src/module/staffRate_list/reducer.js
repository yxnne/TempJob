import * as actionType from './actionType'

const initState = {
  list:[
    { name:'加载中', times:100, rank:1, role:'加载中', department:'加载中', detail:[{position:'加载中', times:30, name:'1234'}, {position:'加载中', times:17, name:'1234'}] },
    { name:'加载中', times:100, rank:2, role:'加载中', department:'加载中', detail:[{position:'加载中', times:30, name:'1234'}] },
    { name:'加载中', times:100, rank:3, role:'加载中' , department:'加载中', detail:[{position:'加载中', times:30, name:'1234'}, {position:'加载中', times:17,name:'1234'}] },

  ]
};

export default (state=initState, action) =>{
  switch(action.type){
    case actionType.GET_STAFF_LIST_STATISTIC:
      
      // 需要解析下数据
      let newList = action.payload.map(item=>{
        const oldDetail = item.detail;
        // let posAndName = item.detail.position.split('#');
        const detail = oldDetail.map(d => (
          {...d, position:d.position.split('#')[0], name:d.position.split('#')[1] }
        ));
        return {...item , detail}
      });
      return {...state, list:newList };
    default:
      return { ...state }

  }
};