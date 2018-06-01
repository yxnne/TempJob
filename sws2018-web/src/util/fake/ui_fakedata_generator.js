// 专门产生假数据的文件
// 在写UI阶段使用

/**
 * 事件假数据,一般，事件查询页面会用到
 */
export function getFakedata_event(num){
  const fakeNames = ['Andrew', 'Me MMaYa', '撒由那拉', '赵智商', '咬津', '远不平', 'かわいい'];
  const fakeNo = ['000002', '000006', '000008', '110008', '000333', '000001', '006606'];
  const fakeEvent = ['进入病区', '手卫生完成', '解除患者', '未手卫生'];
  const fakeStatus = ['清洁', '不清洁'];

  let datas = [];
  for(let i = 0; i < num; i++ ){
    let r = Math.random();
    let r_name = Math.floor(r * fakeNames.length);
    let r_event = Math.floor(r * fakeEvent.length);
    let r_status = Math.floor(r * fakeStatus.length);
    datas.push({
      name:fakeNames[r_name],
      no:fakeNo[r_name],
      rfid:fakeNo[r_name],
      event:fakeEvent[r_event],
      status:fakeStatus[r_status],
      time:new Date().getTime(),
      remark:''
    });

  }
  return datas;
}

/**
 * 设备状态假数据,一般，设备状态查询页面会用到
 */
export function getFakedata_deviceStatus(num){
  const fakeNames = ['门识别器01', '红外液瓶2', '胸卡赵智商', '门识别器05', '胸卡咬津', '胸卡远不平', '胸卡かわいい'];
  const fakeNo = ['M0001', 'W00002', '000008', '110008', 'M0005', '000001', '006606'];
  const fakeBelong = ['骨科', 'ICU', 'ICU', 'ICU', '神经内科', 'ICU', 'ICU'];
  const fakeEvent = ['进入病区', '手卫生完成', '解除患者', '未手卫生'];
  const fakeStatus = ['欠压', '正常', '疑故障'];

  let datas = [];
  for(let i = 0; i < num; i++ ){
    let r = Math.random();
    let r_name = Math.floor(r * fakeNames.length);
    let r_event = Math.floor(r * fakeEvent.length);
    let r_status = Math.floor(r * fakeStatus.length);
    datas.push({
      name:fakeNames[r_name],
      no:fakeNo[r_name],
      rfid:fakeNo[r_name],
      belong:fakeBelong[r_name],
      status:fakeStatus[r_status],
      time:new Date().getTime(),
      remark:''
    });

  }
  return datas;
}
