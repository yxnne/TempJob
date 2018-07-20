import * as actionType from './actionType';
import axios from 'axios';

// url
const URL_STAFFLIST_GET = '/iel-hhms/web/app/getStaffCounts.action';

export const testAction = ()=>({
  type: actionType.TEST_DEPARTMENT_LIST_STATISTIC
});

export const getAction = ( result ) => ({
  type: actionType.GET_DEPARTMENT_LIST_STATISTIC, payload:result
});

// 对外暴露方法来Get到数据
export const getStaffList = ( startTime, endTime ) => {

  return dispatch =>{
    axios.get(URL_STAFFLIST_GET, {
      params:{ startTime, endTime }
    }).then(res =>{
      if(res.code === 200 ) {
        console.log(res);
      }
      // dispatch(getAction());

    })
  }
};