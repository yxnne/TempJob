import * as actionTypes from './actiontype';
import axios from 'axios';
import { getOneWeekStartAndEndTimeString, getTestDaysString } from '../../util/dateUtils';

const URL_ORGANIZATION_GET = '/iel-hhms/web/app/getHospitalPerformanceThisWeek.action';

export const testAction = () =>({
  type:actionTypes.TEST_ORGANIZATION_STATISTIC,
});

export const getAction = (result) => ({
  type: actionTypes.GET_ORGANIZATION_STATISTIC, payload:result 
});

// 对外暴露的方法
// 得到数据
export const getOrganizationInfosAction = (departmentId)=>{

  return (dispatch)=>{
    const startTime = getTestDaysString().startTime;
    const endTime = getTestDaysString().endTime;
    axios.get(URL_ORGANIZATION_GET, {
      params:{
        startTime: startTime, 
        endTime: endTime,
        departmentId
      }
    }).then((res) => {
      if( res.status === 200 ){
        console.log('hospital data is :',res);
        const result = res.data.result;
        dispatch(getAction(result));
      }
    });
  }
  
}