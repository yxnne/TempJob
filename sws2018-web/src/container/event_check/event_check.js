import React from 'react';
import { Layout, Icon, Button, Card  } from 'antd';
import TableEventComponent from '../../component/table_event/table_event';

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
    console.log(clickedRecordRow);
  }

  render(){
    return (
      <div>123
        <Layout style={{backgroundColor:'transparent',margin:0, padding:0}}>
          <Content style={{margin:0, padding:0}}>
            PageEventCheck
            <Button type='ghost' onClick={this.toggle}>Test Call Panel</Button>


            {/* 查询条件 */}

            <Card style={{marginTop:20, marginRight:20, borderRadius:6}}>

            </Card>

            {/* 查询结果 -- Table */}
            <Card style={{marginTop:20, marginRight:20, borderRadius:6}}>
              <TableEventComponent  tableData={this.state.tableData} onNameClick={this.onTableNameClickCallback}/>
            </Card>


          </Content>
          <Sider collapsed={this.state.infoPanelCollaped} collapsedWidth={0}
            width={400}>

          </Sider>
        </Layout>

      </div>
    );
  }
}

export default PageEventCheck;
