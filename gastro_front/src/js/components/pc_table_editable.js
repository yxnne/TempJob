
import React from 'react';
import { Table, Input, Popconfirm, Divider, Row} from 'antd';

// 暂时耦合在组件里面的测试数据，
// 未来总会解耦合的
const data = [];
const flowNames = ["初洗", "次洗", "酶洗", "浸泡", "末洗", "干燥"];
for (let i = 0; i < flowNames.length; i++) {
  data.push({
    key:`enteroscopy_${i}`,
    serial: i,
    flow_name:`${flowNames[i]}`,
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
/*
组件:可编辑的表
*/
export default class PCTableEditable extends React.Component {
  constructor(props) {
    super(props);
    this.columns = [{
      title: '序号',
      dataIndex: 'serial',

    }, {
      title: '流程名称',
      dataIndex: 'flow_name',

    }, {
      title: '正常流程时间',
      dataIndex: 'flow_time_normal',
      render: (text, record) => this.renderColumns(text, record, 'flow_time_normal'),
    }, {
      title: '最大流程时间',
      dataIndex: 'flow_time_max',
      render: (text, record) => this.renderColumns(text, record, 'flow_time_max'),
    },{
      title: '操作',
      dataIndex: 'operation',
      render: (text, record) => {
        const { editable } = record;
        return (
          <div className="editable-row-operations">
            {
              editable ?
                <span>
                  <Popconfirm title="确定这些修改？" onConfirm={() => this.save(record.key)}>
                    <a>保存</a>
                  </Popconfirm>
                  <Divider type="vertical"/>

                  <a onClick={() => this.cancel(record.key)}>放弃</a>

                </span>
                : <a onClick={() => this.edit(record.key)}>修改</a>
            }
          </div>
        );
      },
    }];

    //this.cacheData = data.map(item => ({ ...item }));

  }

  renderColumns(text, record, column) {
    return (
      <EditableCell
        editable={record.editable}
        value={text}
        onChange={value => this.handleChange(value, record.key, column)}
      />
    );
  }

  handleChange(value, key, column) {
    const newData = [...this.state.data];
    const target = newData.filter(item => key === item.key)[0];
    if (target) {
      target[column] = value;
      this.setState({ data: newData });
    }
  }

  edit(key) {
    const newData = [...this.state.data];
    const target = newData.filter(item => key === item.key)[0];
    if (target) {
      target.editable = true;
      this.setState({ data: newData });
    }
  }

  save(key) {
    const newData = [...this.state.data];
    const target = newData.filter(item => key === item.key)[0];
    if (target) {
      delete target.editable;
      this.setState({ data: newData });
      //this.cacheData = newData.map(item => ({ ...item }));
    }
    // 调用更改的逻辑
    this.props.onSaveChange(this.state.data);

  }

  cancel(key) {
    const newData = [...this.state.data];
    const target = newData.filter(item => key === item.key)[0];
    if (target) {
      //Object.assign(target, this.cacheData.filter(item => key === item.key)[0]);
      delete target.editable;
      this.setState({ data: newData });
    }
    this.props.onCancelChange();
  }

  render() {
    // 为什么写在这里是因为 父组传过来的props发声变化的时候会引发render()调用
    this.state = {
      data:this.props.data
    };

    return(
      <div className="basic_table_and_form_container" style={{paddingTop:"12px"}}>
        <Table dataSource={this.state.data} columns={this.columns} />
      </div>
    );
  }
}
