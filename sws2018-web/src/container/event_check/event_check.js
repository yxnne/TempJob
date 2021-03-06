import React from 'react';
import { Layout, Card, Divider } from 'antd';
import TableEventComponent from '../../component/table/table_event/table_event';
import FormEventComponent from '../../component/form/form_event/form_event';
import PersonalStatisticComponent from '../../component/personal_statistic/personal_statistic';

// 假数据生产器
import { getFakedata_event } from '../../util/fake/ui_fakedata_generator';

const { Content, Sider } = Layout;

/**
 * 事件查询页
 */
class PageEventCheck extends React.Component{

  constructor(){
    super();
    this.state = {
      infoPanelCollaped:true,
      tableData:getFakedata_event(50),
    };

    this.toggle = this.toggle.bind(this);
    this.onTableNameClickCallback = this.onTableNameClickCallback.bind(this);
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
              <FormEventComponent />

              <Divider />

              {/* 查询结果 -- Table */}
              <TableEventComponent  tableData={this.state.tableData} onNameClick={this.onTableNameClickCallback}/>
            </Card>

          </Content>
          <Sider collapsed={this.state.infoPanelCollaped} collapsedWidth={0} width={600} style={{backgroundColor:'#f0f2f5'}}>
            <PersonalStatisticComponent />
          </Sider>
        </Layout>

      </div>
    );
  }
}

export default PageEventCheck;
