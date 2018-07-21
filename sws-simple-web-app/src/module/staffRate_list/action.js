import * as actionType from './actionType';
import axios from 'axios';
import { getOneWeekStartAndEndTimeString, getTestDaysString } from '../../util/dateUtils';

// url
const URL_STAFFLIST_GET = '/iel-hhms/web/app/getStaffCounts.action';

export const testAction = ()=>({
  type: actionType.TEST_STAFF_LIST_STATISTIC
});

export const getAction = ( result ) => ({
  type: actionType.GET_STAFF_LIST_STATISTIC, payload:result
});

// 对外暴露方法来Get到数据
export const getStaffStatisticList = ( departmentIds, startTime, endTime ) => {
  console.log('departmentIds', departmentIds)
  return dispatch =>{

    // 这里departmentIds是一个数组
    // 自己手动拼接URL
    let paramUrl_departs = "";
    for( const one of departmentIds ){
      paramUrl_departs += `departmentIds=${one}&`;
    }
    const url = URL_STAFFLIST_GET + '?' + paramUrl_departs + `startTime=${startTime}&endTime=${endTime}`;

    axios.get(url).then(res =>{
      if(res.status === 200 ) {
        console.log("staff -> ", res);
        
        dispatch(getAction(res.data.result));

      }

    })
  }
};