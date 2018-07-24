// 这里产生测试UI时用的数据

/* staff or employee 员工 */
function generateFakeDate_staffs(){
  const data = [];
  for (let i = 0; i < 46; i++) {
    data.push({
      key:`staff_${i}`,
      serial: i,
      employeeId:`STAFF001_${i +1}${i}`,
      employeeName: "Jason Statham"+i,
      staff_role: "Doctor",
      rfid: `RFID_STAFF_NO11${i*10}${i}`,
      remark:'No Remarks',
    });
  }
  return data;
}

/* endoscope or gastroscope 内镜 */
function generateFakeDate_gastroscope(){
  const data = [];
  for (let i = 0; i < 46; i++) {
    data.push({
      key:`enteroscopy_${i}`,
      serial: i,
      endoscopeNumber:`enteroscopy_${i}`,
      endoscopeName: "Entero_"+i,
      endoscopeType: "Enteroscopy",
      rfid: `RFID_ENTRO_NO11${i*10}${i}`,
    });
  }
  return data;
}


//输出

export {
  generateFakeDate_gastroscope,
  generateFakeDate_staffs,
};
