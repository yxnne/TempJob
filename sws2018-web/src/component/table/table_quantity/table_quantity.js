import React from 'react';
import { Table } from 'antd';
import PropTypes from 'prop-types';

 /**
  * 洗手液统计（各个液瓶的）Table组件
  */
class TableQuantityComponent extends React.Component{

  render(){
    // 表头定义
    const columns = [{
        title: '设备名',
        dataIndex: 'name',
        key: 'name',
        sorter: (a, b) => a.name - b.name,
        render: (text, record)  => <a href="#">{text}</a>,
      }, {
        title: '类型',
        dataIndex: 'type',
        key: 'type',
        sorter: (a, b) => a.no - b.no,
      }, {
        title: '所属',
        dataIndex: 'belong',
        key: 'belong',
        sorter: (a, b) => a.rfid - b.rfid,
      }, {
        title: '次数',
        dataIndex: 'times',
        key: 'times',

        sorter: (a, b) => a.event - b.event,
      },{
        title: '使用量',
        dataIndex: 'quantity',
        key: 'quantity',

        sorter: (a, b) => a.event - b.event,
      }];

    return (
      <div>

        <Table columns={columns} dataSource={this.props.tableData} />
      </div>
    );
  }

}

// 组件属性检查设置
TableQuantityComponent.propTypes = {
  tableData:PropTypes.array.isRequired,
};

export default TableQuantityComponent;
