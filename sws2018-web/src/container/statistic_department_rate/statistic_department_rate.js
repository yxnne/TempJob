import React from 'react';
import { Layout, Card, Divider, Select } from 'antd';
import TableEventComponent from '../../component/table/table_event/table_event';
import FormEventComponent from '../../component/form/form_event/form_event';

// 假数据生产器
import { getFakedata_event } from '../../util/fake/ui_fakedata_generator';

const { Content, Sider } = Layout;
const Option = Select.Option;


/**
 * 科室，依从率统计
 * 选择部门和查询时间后，可以Tab切换 概况 和 员工
 */
class PageStatisticDepartmentRate extends React.Component{
  constructor(){
    super();
    this.state = {
      infoPanelCollaped:true,
      tableData:getFakedata_event(50),
    };

    this.toggle = this.toggle.bind(this);
    this.onTableNameClickCallback = this.onTableNameClickCallback.bind(this);
  }

  handleChange(value) {
    console.log(`selected ${value}`);
  }
  // 右侧伸缩板子控制
  toggle(){
    this.setState({
      infoPanelCollaped: !this.state.infoPanelCollaped,

   });
  }

  // Table的名字点击时的回调函数
  onTableNameClickCallback(clickedRecordRow){
    // console.log(clickedRecordRow);
    // 拉开 右侧侧滑菜单
    this.toggle();
  }
  render(){

    return (
      <div>
        <Layout style={{backgroundColor:'transparent',margin:0, padding:0}}>
          <Content style={{margin:0, padding:0}}>

            <Card style={{marginTop:20, marginRight:20, borderRadius:6}}>

              {/* 查询条件 表单 */}
              <Select defaultValue="lucy" style={{ width: 120 }} onChange={this.handleChange.bind(this)}>
                <Option value="jack">Jack</Option>
                <Option value="lucy">Lucy</Option>
                <Option value="disabled" disabled>Disabled</Option>
                <Option value="Yiminghe">yiminghe</Option>
              </Select>

              <Divider />

              {/* 查询结果 -- Table */}
              <TableEventComponent  tableData={this.state.tableData} onNameClick={this.onTableNameClickCallback}/>
            </Card>

          </Content>
          <Sider collapsed={this.state.infoPanelCollaped} collapsedWidth={0} width={400}>

          </Sider>
        </Layout>
      </div>
    );
  }
}

export default PageStatisticDepartmentRate;
