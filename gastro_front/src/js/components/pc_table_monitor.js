
import React from 'react';
import { Table, Input, Popconfirm, Divider, Row, Steps, Icon, Pagination} from 'antd';


const Step = Steps.Step;
// 暂时耦合在组件里面的测试数据，
// 未来总会解耦合的
const data = [];
const gastroIds       = ["GASTRO_110", "GASTRO_10pro", "GASTRO_XP", "GASTRO_Vista", "GASTRO_x9play", "GASTRO_2000"];
const gastroNames     = ["内镜110", "胃镜200", "神域之肠镜", "圣镜", "Gastro", "CoolGastro牌内镜"];
const washStaffs      = ["蔡多芬", "黎暗", "黄圣二", "林肝如", "范热热", "仓木丝绸"];
const washStations    = ["Station1", "Station2", "野区_Station", "Station1", "Station2", "Station010"];

const stepStatusStart =  ["finish"  , "finish"  , "finish"  , "finish" , "finish"     ,"process"];
const stepStatusEnzyme = ["finish"  , "finish"  , "process" , "finish" , "finish"     , "wait" ];
const stepStatusSecond = ["process" , "finish"  , "wait"    , "finish" , "finish"     , "wait" ];
const stepStatusSoak   = ["wait"    , "finish"  , "wait"    , "process", "finish"     , "wait" ];
const stepStatusLast   = ["wait"    , "finish"  , "wait"    , "wait"   , "finish"     , "wait" ];
const stepStatusDry    = ["wait"    , "finish"  , "wait"    , "wait"   , "finish"     , "wait" ];

const usingTimeStart    = ["13:55:50","13:55:50","13:55:50","13:55:50","13:55:50","13:55:50"]
const usingTimeEnzyme   = ["14:25:50","14:25:50","14:25:50","14:25:50","14:25:50","14:25:50"]
const usingTimeSecond   = ["13:15:50","13:15:50","13:15:50","13:15:50","13:15:50","13:15:50"]
const usingTimeSoak     = ["16:15:50","16:15:50","16:15:50","16:15:50","16:15:50","16:15:50"]
const usingTimeLast     = ["17:50:50","16:15:50","16:15:50","16:15:50","16:15:50","16:15:50"]
const usingTimeDry      = ["18:25:50","16:15:50","16:15:50","16:15:50","16:15:50","16:15:50"]

const finishedTimeStart    = ["5min 20s","5min 20s","5min 20s","5min 20s","5min 20s","5min 20s"]
const finishedTimeEnzyme   = ["7min 20s","7min 20s","7min 20s","7min 20s","7min 20s","7min 20s"]
const finishedTimeSecond   = ["4min 30s","7min 20s","5min 30s","7min 20s","7min 20s","7min 20s"]
const finishedTimeSoak     = ["7min 20s","7min 20s","7min 20s","7min 20s","7min 20s","7min 20s"]
const finishedTimeLast     = ["8min 20s","5min 30s","7min 20s","7min 20s","10min 20s","7min 20s"]
const finishedTimeDry      = ["10min 20s","7min 20s","7min 20s","7min 20s","7min 20s","1min 2s"]
for (let i = 0; i < 20; i++) {
  data.push({
    key:`enteroscopy_${i}`,
    serial: i,
    gastro_id:`${gastroIds[i % gastroNames.length]}`,
    gastro_name:`${gastroNames[i % gastroNames.length]}`,
    wash_staff:`${washStaffs[i % gastroNames.length]}`,
    wash_station:`${washStations[i % gastroNames.length]}`,
    wash_stepstatus_start:`${stepStatusStart[i % gastroNames.length]}`,
    wash_stepstatus_enzyme:`${stepStatusEnzyme[i % gastroNames.length]}`,
    wash_stepstatus_second:`${stepStatusSecond[i % gastroNames.length]}`,
    wash_stepstatus_soak:`${stepStatusSoak[i % gastroNames.length]}`,
    wash_stepstatus_last:`${stepStatusLast[i % gastroNames.length]}`,
    wash_stepstatus_dry:`${stepStatusDry[i % gastroNames.length]}`,

    using_time_start_start:`${usingTimeStart[i % gastroNames.length]}`,
    using_time_start_enzyme:`${usingTimeEnzyme[i % gastroNames.length]}`,
    using_time_start_second:`${usingTimeSecond[i % gastroNames.length]}`,
    using_time_start_soak:`${usingTimeSoak[i % gastroNames.length]}`,
    using_time_start_last:`${usingTimeLast[i % gastroNames.length]}`,
    using_time_start_dry:`${usingTimeDry[i % gastroNames.length]}`,

    finished_time_start:`${finishedTimeStart[i % gastroNames.length]}`,
    finished_time_enzyme:`${finishedTimeEnzyme[i % gastroNames.length]}`,
    finished_time_second:`${finishedTimeSecond[i % gastroNames.length]}`,
    finished_time_soak:`${finishedTimeSoak[i % gastroNames.length]}`,
    finished_time_last:`${finishedTimeLast[i % gastroNames.length]}`,
    finished_time_dry:`${finishedTimeDry[i % gastroNames.length]}`,

    flow_time_normal: "30分60秒",
    flow_time_special: "60秒",
    flow_time_max: `300分60秒`,

  });
}
// 组件编辑状态的样式
const EditableCell = ({ editable, value, onChange }) => (
  <div>
    {editable
      ? <Input style={{ margin: '-5px 0', width:'60%' }} value={value} onChange={e => onChange(e.target.value)} />
      : value
    }
  </div>
);

// 判断step中的图标
function judgeStepIconType(state){
  let iconString = "";
  if (state == "finish"){
    iconString = "check-circle";
  }else if (state == "process"){
    iconString = "loading";
  }else if (state == "wait") {
    iconString = "pause";
  }
  return iconString ;
}

/*
表格中描述的小组件
*/
function ProcessInfoDescription(props){


  let usingString = "";
  if (props.usingState == "finish"){
    usingString = "共花费:"  + props.finishedTime
  }else if (props.usingState == "process"){
    usingString = "已用时:" + props.usingTime
  }else if (props.usingState == "wait") {
    usingString = "等待开始";
  }

  let startString = "";
  if (props.usingState == "wait") {
    startString = "";
  }else {
    startString = "开始于:" + props.startTime;
  }

  return (
    <div style={{width:"100px"}}>
      <span>
        {startString}
      </span>
      <br />
      <span>
        { usingString }
      </span>

    </div>
  );
}

/*
组件:用于实时监控的表
*/
export default class PCTableMonitor extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      data ,
      costTime:0
    };

  }
  componentDidMount() {
    // timerID是为了撤销用
    this.timerID = setInterval(
      () => this.tick(),
      1000
    );
  }
  componentWillUnmount() {
      clearInterval(this.timerID);
  }

  tick() {
    this.setState((prevState, props) => ({
      costTime: prevState.costTime + 1
    }));
  }

  render() {
    this.columns = [{
        title: '序号',
        dataIndex: 'serial',
        width:'80px',
        fixed: 'left',
      },  {
        title: '内镜名称',
        dataIndex: 'gastro_name',
        width:'150px',
        fixed: 'left',
      }, {
        title: '内镜编号',
        dataIndex: 'gastro_id',
        width:'150px',
      },{
        title: '清洗过程进度监控',
        dataIndex: 'monitor',
        width:1000,
        render: (text, record) =>{
          return (
            <Steps>
              <Step status={record.wash_stepstatus_start} title="初洗"
                icon={<Icon type={judgeStepIconType(record.wash_stepstatus_start)} />}
                description={<ProcessInfoDescription
                  usingState={record.wash_stepstatus_start} startTime={record.using_time_start_start}
                  usingTime={this.state.costTime + " s"} finishedTime={record.finished_time_start}/>}/>

              <Step status={record.wash_stepstatus_enzyme} title="酶洗"
                icon={<Icon type={judgeStepIconType(record.wash_stepstatus_enzyme)} />}
                description={<ProcessInfoDescription
                  usingState={record.wash_stepstatus_enzyme} startTime={record.using_time_start_enzyme}
                  usingTime={this.state.costTime + " s"} finishedTime={record.finished_time_enzyme}/>}/>

              <Step status={record.wash_stepstatus_second} title="次洗"
                icon={<Icon type={judgeStepIconType(record.wash_stepstatus_second)} />}
                description={<ProcessInfoDescription
                  usingState={record.wash_stepstatus_second} startTime={record.using_time_start_second}
                  usingTime={this.state.costTime + " s"} finishedTime={record.finished_time_second}/>}/>

              <Step status={record.wash_stepstatus_soak} title="浸泡"
                icon={<Icon type={judgeStepIconType(record.wash_stepstatus_soak)} />}
                description={<ProcessInfoDescription
                  usingState={record.wash_stepstatus_soak} startTime={record.using_time_start_soak}
                  usingTime={this.state.costTime + " s"} finishedTime={record.finished_time_soak}/>}/>

              <Step status={record.wash_stepstatus_last} title="末洗"
                icon={<Icon type={judgeStepIconType(record.wash_stepstatus_last)} />}
                description={<ProcessInfoDescription
                  usingState={record.wash_stepstatus_last} startTime={record.using_time_start_last}
                  usingTime={this.state.costTime + " s"} finishedTime={record.finished_time_last}/>}/>

              <Step status={record.wash_stepstatus_dry} title="干燥"
                icon={<Icon type={judgeStepIconType(record.wash_stepstatus_dry)} />}
                description={<ProcessInfoDescription
                  usingState={record.wash_stepstatus_dry} startTime={record.using_time_start_dry}
                  usingTime={this.state.costTime + " s"} finishedTime={record.finished_time_dry}/>}/>
            </Steps>
          );
        },
      }, {
        title: '',
        dataIndex: '',
        width:'50px',
      }, {
        title: '洗消人员',
        dataIndex: 'wash_staff',
        width:'150px',
      },{
        title: '工作站',
        dataIndex: 'wash_station',
        width:'150px',
      }];

    return(
      <div className="basic_table_and_form_container" style={{paddingTop:"12px"}}>
        <Table dataSource={this.state.data} columns={this.columns} scroll={{x:1730}} pagination={{ pageSize:7}}/>
      </div>
    );
  }
}
