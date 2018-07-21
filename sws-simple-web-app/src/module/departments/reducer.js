import * as actionType from "./actionType";
const initState = [];

export default  (state=initState, action) =>{
  switch(action.type){
    case actionType.GET_DEPARTMENTS:
      return [ ...action.payload];
    default:
      return [...state];
  }
};