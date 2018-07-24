// 后台系统根目录
let backend_server_domin = 'http://192.168.125.102:8090';

/* URL: 实时监控 */
// 实时监控数据
function url_findDecontaminationRealTime(){
  return backend_server_domin + '/endoscope/decontamination/findDecontaminationRealTime';
}
/* URL: 用户管理 相关接口 */
// 查找所有用户
function url_findAllUsers(){
  return backend_server_domin + '/endoscope/user/findAll';
}

// 添加用户
function url_addNewUser(){
  return backend_server_domin + '/endoscope/user/add';
}

function url_deleteByUserId(){
  return backend_server_domin + '/endoscope/user/deleteById';
}

function url_updateUser(){
  return backend_server_domin + '/endoscope/user/updateById';
}


/* URL: 设备管理 相关接口 */
// 查找所有设备
function url_findAllDevices(){
  return backend_server_domin + '/endoscope/device/findDevicesByStationIdByPage';
}

// 添加一台设备
function url_addNewDevice(){
  return backend_server_domin + '/endoscope/device/findDevicesByStationIdByPage';
}

/* URL: 流程管理 相关接口 */
// 查找所有流程
function url_findAllProcess(){
  return backend_server_domin + '/endoscope/process/findAll';
}
// 修改某一条流程
function url_updateProcess(){
  return backend_server_domin + '/endoscope/process/update';
}

/* URL: 内镜管理 相关接口 */
// 查找所有内镜信息并分页
function url_findAllEndoscopesByPage(){
  return backend_server_domin + '/endoscope/endoscope/findAllByPage';
}

// 添加一条内镜
function url_addNewEndoscope(){
  return backend_server_domin + '/endoscope/endoscope/add';
}

// 添加一条内镜
function url_deleteOneEndoscope(){
  return backend_server_domin + '/endoscope/endoscope/deleteByEndoscopeId';
}

// 添加一条内镜
function url_updateEndoscope(){
  return backend_server_domin + '/endoscope/endoscope/update';
}

/* URL: 员工管理 相关接口 */
// 查找所有员工号
function url_findAllEmloyees(){
  return backend_server_domin + '/endoscope/employee/findAllEmployees';
}

// 通过员工号查找员工
function url_findEmloyeeById(){
  return backend_server_domin + '/endoscope/employee/findByEmployeeId';
}

// 添加新员工
function url_addNewEmployee(){
  return backend_server_domin + '/endoscope/employee/add';
}

// 删除一个员工
function url_deleteOneEmployee(){
  return backend_server_domin + '/endoscope/employee/deleteByEmployeeId';
}

// 更改员工信息
function url_updateEmployee(){
  return backend_server_domin + '/endoscope/employee/update';
}

/* 输出接口 */
export {
  url_findDecontaminationRealTime,

  // 输出:用户管理相关
  url_findAllUsers,
  url_addNewUser,
  url_deleteByUserId,
  url_updateUser,

  // 输出:设备管理相关
  url_findAllDevices,


  // 输出:流程管理相关接口
  url_findAllProcess,
  url_updateProcess,

  // 输出: 内镜管理相关接口
  url_findAllEndoscopesByPage,
  url_addNewEndoscope,
  url_deleteOneEndoscope,
  url_updateEndoscope,

  // 输出: 员工管理相关接口
  url_findAllEmloyees,
  url_findEmloyeeById,
  url_addNewEmployee,
  url_deleteOneEmployee,
  url_updateEmployee,

};
