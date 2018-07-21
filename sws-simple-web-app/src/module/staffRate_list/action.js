import * as actionType from './actionType';
import axios from 'axios';
import { getOneWeekStartAndEndTimeString, getTestDaysString } from '../../util/dateUtils';

// url
const URL_STAFFLIST_GET = '/iel-hhms/web/app/getStaffCounts.action';

export const testAction = ()=>({
  type: actionType.TEST_DEPARTMENT_LIST_STATISTIC
});

export const getAction = ( result ) => ({
  type: actionType.GET_DEPARTMENT_LIST_STATISTIC, payload:result
});

// 对外暴露方法来Get到数据
export const getStaffStatisticList = ( departmentIds ) => {

  return dispatch =>{

    const startTime = getTestDaysString().startTime;
    const endTime = getTestDaysString().endTime;

    axios.get(URL_STAFFLIST_GET, {
      params:{ startTime, endTime, departmentIds }
    }).then(res =>{
      if(res.status === 200 ) {
        console.log("staff -> ", res);
        
        dispatch(getAction(res.data.result));

      }

    })
  }
};