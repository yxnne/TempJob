import React from 'react';
import { Table,  Input, Button, Icon } from 'antd';
import PropTypes from 'prop-types';
import './table_manage_user.css';
 /**
  * 设备管理，职工表
  */
class TableManageUserComponent extends React.Component{

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
        title: '关联职工',
        dataIndex: 'r_staff',
        key: 'r_staff',
        sorter: (a, b) => a.r_staff - b.r_staff,
      },  {
        title: '关联微信',
        dataIndex: 'r_wx',
        key: 'r_wx',
        sorter: (a, b) => a.r_wx - b.r_wx,
        onFilter: (value, record) => record.status.indexOf(value) === 0,
      }, {
        title: '手机',
        dataIndex: 'phone',
        key: 'phone',
        sorter: (a, b) => a.phone - b.phone,
      }, {
        title: '权限',
        dataIndex: 'auth',
        key: 'auth',
        sorter: (a, b) => a.auth - b.auth,
        render: (text, record)  => <a onClick={()=>this.props.onAuthClick(record)}>{text}</a>,
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
TableManageUserComponent.propTypes = {
  onNameClick:PropTypes.func.isRequired,
  tableData:PropTypes.array.isRequired,
};

export default TableManageUserComponent;
