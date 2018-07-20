const ONEDAY_MILLS = 24 * 60 * 60 * 1000;

export const getTestDaysString = () => {
  return {
    startTime:'2018-06-20 09:20:34',
    endTime:'2018-06-27 09:20:34',
  }
}

/** 
 * 得到从今天开始向前一周的日期字符串
 * 形式 2018-06-19 10:19:34
 */
export const getOneWeekStartAndEndTimeString = () =>{
  let nowDate = new Date();
  let weekBeforeDate = new Date(nowDate.getTime() - ONEDAY_MILLS * 7);
  return {
    startTime:_formatDateTime(nowDate),
    endTime:_formatDateTime(weekBeforeDate),
  }
};

/** 
 * 得到从今天开始向前的日期字符串,向前多少天按照天数参数
 * 形式 2018-06-19 10:19:34
 */
export const getStartAndEndTimeString = (beforeDays) =>{
  let nowDate = new Date();
  let weekBeforeDate = new Date(nowDate.getTime() - ONEDAY_MILLS * beforeDays);
  return {
    startTime:_formatDateTime(nowDate),
    endTime:_formatDateTime(weekBeforeDate),
  }
};

/** 
 * 从Date对象中得到String字符串
 */
export const getStringFormDate = (date) =>{
 
  return  _formatDateTime(date) ;
};

// 将Date对象转换成字符串yyyy-MM-dd hh:mm:ss
function _formatDateTime(theDate) {
  var _hour = theDate.getHours();
  var _minute = theDate.getMinutes();
  var _second = theDate.getSeconds();
  var _year = theDate.getFullYear()
  var _month = theDate.getMonth();
  var _date = theDate.getDate();

  if(_hour < 10){
    _hour = "0" + _hour
  }
  if(_minute < 10){
    _minute = "0" + _minute  
  }
  if(_second < 10){
    _second = "0" + _second  
  }
  
  _month = _month + 1;
  if(_month < 10){
    _month = "0" + _month;
  }
  if(_date < 10){
    _date = "0" + _date  
  }
  return  _year + "-" + _month + "-" + _date + " " + _hour + ":" + _minute + ":" + _second ;
}