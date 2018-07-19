import * as actionType from './actionType';
import axios from 'axios'

// url
const URL_DEPARTMENT_GET = '/iel-hhms/web/app/getDepartmentCounts.action';

export const testAction = ()=>({
  type: actionType.TEST_DEPARTMENT_LIST_STATISTIC
});

export const errorAction = (msg)=>({
  type: actionType.TEST_DEPARTMENT_LIST_STATISTIC,
  msg
});

const getListAction = (payload)=>({
  type: actionType.GET_DEPARTMENT_LIST_STATISTIC,
  payload
});

// 得到部门信息列表
// 发送请求
export const getDepartListAction = (startTime, endTime)=>{
  return (dispatch )=>{
    if(!startTime && !endTime ){
      dispatch(errorAction('没有起止时间参数'));
      return;
    }

    // axios请求
    axios.get(URL_DEPARTMENT_GET,{
      params:{
        startTime, endTime 
      }
    }).then(res =>{
      if( res.status === 200 ){
        console.log('res->', res);
        dispatch(getListAction(res.data.result));
      }
    });

  };
};