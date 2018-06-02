import React from 'react';
import { Layout, Card, Divider } from 'antd';
import TableDeviceStatusCheckComponent from '../../component/table/table_device_status_check/table_device_status_check';
import FormDevicesStatusComponent from '../../component/form/form_device_status/form_device_status';

// 假数据生产器
import { getFakedata_deviceStatus } from '../../util/fake/ui_fakedata_generator';

const { Content, Sider } = Layout;

/**
 * 设备状态查询页
 */
class PageDeviceStatusCheck extends React.Component{

  constructor(){
    super();
    this.state = {
      infoPanelCollaped:true,
      tableData:getFakedata_deviceStatus(30),
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
              <FormDevicesStatusComponent />

              <Divider />

              {/* 查询结果 -- Table */}
              <TableDeviceStatusCheckComponent  tableData={this.state.tableData} onNameClick={this.onTableNameClickCallback}/>
            </Card>

          </Content>
          <Sider collapsed={this.state.infoPanelCollaped} collapsedWidth={0} width={400}>

          </Sider>
        </Layout>

      </div>
    );
  }
}

export default PageDeviceStatusCheck;
