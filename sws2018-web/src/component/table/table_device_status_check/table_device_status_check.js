import React from 'react';
import { Table } from 'antd';
import PropTypes from 'prop-types';

 /**
  * 设备状态查询的Table组件
  */
class TableDeviceStatusCheckComponent extends React.Component{

  render(){
    // 表头定义
    const columns = [{
        title: '设备名',
        dataIndex: 'name',
        key: 'name',
        sorter: (a, b) => a.name - b.name,
        render: (text, record)  => <a onClick={()=>this.props.onNameClick(record)}>{text}</a>,
      }, {
        title: '编号',
        dataIndex: 'no',
        key: 'no',
        sorter: (a, b) => a.no - b.no,
      },  {
        title: '状态',
        dataIndex: 'status',
        key: 'status',
        sorter: (a, b) => a.status - b.status,
        filters: [{
          text: '欠压',
          value: '欠压',
        }, {
          text: '正常',
          value: '正常',
        }, {
          text: '疑故障',
          value: '疑故障',
        }],
        onFilter: (value, record) => record.status.indexOf(value) === 0,
      }, {
        title: '上报时间',
        dataIndex: 'time',
        key: 'time',
        sorter: (a, b) => a.time - b.time,
      }, {
        title: '从属',
        dataIndex: 'belong',
        key: 'belong',
        filters: [{
          text: 'ICU',
          value: 'ICU',
        }, {
          text: '等等',
          value: '等等',
        }],
        sorter: (a, b) => a.event - b.event,
      },{
        title: '备注',
        dataIndex: 'remark',
        key: 'remark',
      }];

    return (
      <div>

        <Table columns={columns} dataSource={this.props.tableData} />
      </div>
    );
  }

}

// 组件属性检查设置
TableDeviceStatusCheckComponent.propTypes = {
  onNameClick:PropTypes.func.isRequired,
  tableData:PropTypes.array.isRequired,
};

export default TableDeviceStatusCheckComponent;
