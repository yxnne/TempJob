import * as actionType from './actionType'

const initState = {
  success:false,
  msg:""
};

export default (state=initState, action) =>{
  switch( action.type ){
    case actionType.OK_LOGIN_ACTION:
      return { ...state ,...action.payload }
    default:
      return { ...state }
  }
}