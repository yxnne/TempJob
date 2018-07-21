import * as actionType from './actionType';
import axios from 'axios';

const URL_LOGIN_GET = '/iel-hhms/web/app/appLogin.action'; 
// action creator 
const loginAction = (data) =>(
  { type:actionType.OK_LOGIN_ACTION, payload: data }
);

const networkErrorAction = () =>(
  { type:actionType.ERROR_NETWORK_ACTION }
);

const failedErrorAction = () =>(
  { type:actionType.ERROR_FAILED_ACTION }
);

export const login = ( userName, password )=>{
  return dispatch => {
    axios.get(URL_LOGIN_GET, {
      params:{userName, passWord:password}
    }).then(res =>{
      if(res.status === 200){
        console.log(res);
        dispatch(loginAction(res.data))
      }
    })
  }
}