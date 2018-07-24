import React from 'react';
import {Tabs, Table, Icon, Pagination, Input, Button} from 'antd';

const TabPane = Tabs.TabPane;


//产生数据
const data = [];
for (let i = 0; i < 50; i++) {
  data.push({
    key:i,
    series: i,
    id:i,
    gastro_name: `Gastro_${i}`,
  });
}


export default class PCOneTabListComponent extends React.Component{

  constructor(){
    super();
    this.state = {
      filterDropdownVisible: false,
      data,
      searchText: '',
      filtered: false,
    }
  }
  //切换的回调
  onChangeCallback(key) {
    console.log(key);
  }

  onInputChange(e) {
    this.setState({ searchText: e.target.value });
  }

  render(){
    //设置列
    const columns = [{
        title: this.props.col1_name,
        dataIndex: 'id',
        width:80,
      },{
        title: this.props.col2_name,
        dataIndex: 'gastro_name',
        width:100,
        render: (text, record) => (
          <span>
            <a href="#">{record.gastro_name}  <Icon type="right" /></a>
          </span>
        ),
        filterDropdown: (
            <div className="custom-filter-dropdown">
              <Input
                ref={ele => this.searchInput = ele}
                placeholder="Search name"
                value={this.state.searchText}

                onPressEnter={this.onSearch}
              />
              <Button type="primary" onClick={this.onSearch}>Search</Button>
            </div>
        ),
        filterIcon: <Icon type="search" style={{ color: this.state.filtered ? '#108ee9' : '#aaa' }} />,
        filterDropdownVisible: this.state.filterDropdownVisible,
        onFilterDropdownVisibleChange: (visible) => {
          this.setState({
            filterDropdownVisible: visible,
          }, () => this.searchInput && this.searchInput.focus());
        },

      }];


    return (
      <div style={{textAlign:"center"}} >
        <Tabs defaultActiveKey="1" onChange={this.onChangeCallback.bind(this)} >
          <TabPane tab={this.props.titleName} key="1" className="select_gastro_tabpane_container">

            <Table  columns={columns} dataSource={this.props.listData}
              pagination={{simple:true, size:"small", pageSize:14}}/>
          </TabPane>
        </Tabs>
      </div>
    );
  }

}
