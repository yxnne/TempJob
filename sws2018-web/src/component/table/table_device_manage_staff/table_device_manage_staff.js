import React from 'react';
import { Table,  Input, Button, Icon } from 'antd';
import PropTypes from 'prop-types';
import './table_device_manage_staff.css';
 /**
  * 设备管理，职工表
  */
class TableDeviceManageStaffComponent extends React.Component{

  constructor(){
    super();
    this.state = {
      filterDropdownVisible: false,
      data:[],
      searchText: "",
      filtered: false
    };
    this.onInputChange = this.onInputChange.bind(this);
    this.onSearch = this.onSearch.bind(this);
  }


  onInputChange(e) {
    this.setState({ searchText: e.target.value });
  };
  onSearch() {
    const { searchText } = this.state;
    const reg = new RegExp(searchText, "gi");
    this.setState({
      filterDropdownVisible: false,
      filtered: !!searchText,
      data: this.state.data
        .map(record => {
          const match = record.name.match(reg);
          if (!match) {
            return null;
          }
          return {
            ...record,
            name: (
              <span>
                {record.name
                  .split(
                    new RegExp(`(?<=${searchText})|(?=${searchText})`, "i")
                  )
                  .map(
                    (text, i) =>
                      text.toLowerCase() === searchText.toLowerCase() ? (
                        <span key={i} className="highlight">
                          {text}
                        </span>
                      ) : (
                        text
                      ) // eslint-disable-line
                  )}
              </span>
            )
          };
        })
        .filter(record => !!record)
    });
  };

  componentWillReceiveProps(){
    const props_data = this.props.tableData
    this.setState({
      data:props_data
    });
  }

  render(){
    // 表头定义
    const columns = [{
        title: '姓名',
        dataIndex: 'name',
        key: 'name',
        sorter: (a, b) => a.name - b.name,
        render: (text, record)  => <a onClick={()=>this.props.onNameClick(record)}>{text}</a>,
        filterDropdown: (
          <div className="custom-filter-dropdown">
            <Input ref={ele => (this.searchInput = ele)}
              placeholder="Search name" value={this.state.searchText}
              onChange={this.onInputChange}
              onPressEnter={this.onSearch} />
            <Button type="primary" onClick={this.onSearch}>
              Search
            </Button>
          </div>
        ),
        filterIcon: (
          <Icon type="search" style={{ color: this.state.filtered ? "#108ee9" : "#aaa" }} />
        ),
        filterDropdownVisible: this.state.filterDropdownVisible,
        onFilterDropdownVisibleChange: visible => {
          this.setState(
            {
              filterDropdownVisible: visible
            },
            () => this.searchInput && this.searchInput.focus()
          );
        }
      }, {
        title: '工号',
        dataIndex: 'no',
        key: 'no',
        sorter: (a, b) => a.no - b.no,
      },  {
        title: '角色',
        dataIndex: 'role',
        key: 'role',
        sorter: (a, b) => a.role - b.role,
        filters: [{
          text: '医生',
          value: '医生',
        }, {
          text: '护士',
          value: '护士',
        }, {
          text: '其他',
          value: '其他',
        }],
        onFilter: (value, record) => record.status.indexOf(value) === 0,
      }, {
        title: '关联胸牌',
        dataIndex: 'card',
        key: 'card',
        sorter: (a, b) => a.card - b.card,
      }, {
        title: '关联用户',
        dataIndex: 'user',
        key: 'user',
        sorter: (a, b) => a.user - b.user,
      },{
        title: '备注',
        dataIndex: 'remark',
        key: 'remark',
      }];

    //表的尾部
    const footer = (info)=>{
      return (<span>本次查询结果有&nbsp;<a href="#">{info}</a>&nbsp;条</span>);
    }

    return (
      <div>

        <Table columns={columns} dataSource={this.state.data} footer={() => footer(this.state.data.length)}/>
      </div>
    );
  }

}

// 组件属性检查设置
TableDeviceManageStaffComponent.propTypes = {
  onNameClick:PropTypes.func.isRequired,
  tableData:PropTypes.array.isRequired,
};

export default TableDeviceManageStaffComponent;
