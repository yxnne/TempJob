import React from 'react';
import { Table, Button } from 'antd';
import PropTypes from 'prop-types';

 /**
  * AP查询的Table组件
  */
class TableApComponent extends React.Component{

  render(){
    // 表头定义
    const columns = [{
        title: '设备名',
        dataIndex: 'name',
        key: 'name',
        sorter: (a, b) => a.name - b.name,
        render: (text, record)  => <a onClick={()=>this.props.onNameClick(record)}>{text}</a>,
      }, {
        title: 'IMEI码',
        dataIndex: 'imei',
        key: 'imei',
        sorter: (a, b) => a.imei - b.imei,
      }, {
        title: '状态',
        dataIndex: 'status',
        key: 'status',
        filters: [{
          text: '全部',
          value: '全部',
        }, {
          text: '链接',
          value: '断开',
        }],
        sorter: (a, b) => a.status - b.status,
      }, {
        title: '最后通信时间',
        dataIndex: 'last_com_time',
        key: 'last_com_time',
        sorter: (a, b) => a.last_com_time - b.last_com_time,
      }, {
        title: '备注',
        dataIndex: 'remark',
        key: 'remark',
      } , {
        title: '操作',
        key: 'action',
        width: 360,
        render: (text, record) => (
          <span>
            <Button size="small" type="primary" style={{marginRight:12}} onClick={()=>this.props.onStatisticClick(record)}>统计流量</Button>
            <Button size="small" type="danger" style={{marginRight:12}} onClick={()=>this.props.onCutClick(record)}>断开链接</Button>
            <Button size="small" type="ghost" style={{marginRight:12}} onClick={()=>this.props.onDetailClick(record)}>详细记录</Button>
          </span>
        ),
      }];

    return (
      <div>

        <Table columns={columns} dataSource={this.props.tableData} />
      </div>
    );
  }

}

// 组件属性检查设置
TableApComponent.propTypes = {
  onNameClick:PropTypes.func.isRequired,
  tableData:PropTypes.array.isRequired,
};

export default TableApComponent;
