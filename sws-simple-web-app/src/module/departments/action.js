import * as actionType from './actionType';
import axios  from "axios";
const URL_DEPARTMENTS_GET = '/iel-hhms/web/app/getDepartmentsForWebApp.action'; 

// action creator
const getDepartmentsAction = (result) => ({
  type: actionType.GET_DEPARTMENTS, payload:result
});

export const getDepartments = () => (
  (dispatch)=>{
    axios.get(URL_DEPARTMENTS_GET).then(res=>{
      if(res.status === 200){
        const list = res.data.result.map(item=>(
          {name:item.name, id:item.id}
        ));

        // console.log('list -> ', list)

        dispatch(getDepartmentsAction(list));

      }
    });
  }
);