import React from 'react';
import { Table } from 'antd';
import PropTypes from 'prop-types';

 /**
  * 事件查询的Table组件
  */
class TableEventComponent extends React.Component{

  render(){
    // 表头定义
    const columns = [{
        title: '姓名',
        dataIndex: 'name',
        key: 'name',
        sorter: (a, b) => a.name - b.name,
        render: (text, record)  => <a onClick={()=>this.props.onNameClick(record)}>{text}</a>,
      }, {
        title: '工号',
        dataIndex: 'no',
        key: 'no',
        sorter: (a, b) => a.no - b.no,
      }, {
        title: 'RFID',
        dataIndex: 'rfid',
        key: 'rfid',
        sorter: (a, b) => a.rfid - b.rfid,
      }, {
        title: '事件',
        dataIndex: 'event',
        key: 'event',
        filters: [{
          text: '执行手卫生',
          value: '执行手卫生',
        }, {
          text: '等等',
          value: '等等',
        }],
        sorter: (a, b) => a.event - b.event,
      }, {
        title: '状态',
        dataIndex: 'status',
        key: 'status',
        sorter: (a, b) => a.status - b.status,
        filters: [{
          text: '清洁',
          value: '清洁',
        }, {
          text: '不清洁',
          value: '不清洁',
        }],
        onFilter: (value, record) => record.status.indexOf(value) === 0,
      }, {
        title: '时间',
        dataIndex: 'time',
        key: 'time',
        sorter: (a, b) => a.time - b.time,
      }, {
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
TableEventComponent.propTypes = {
  onNameClick:PropTypes.func.isRequired,
  tableData:PropTypes.array.isRequired,
};

export default TableEventComponent;
