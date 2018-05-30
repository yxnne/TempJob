import React from 'react';
import { Layout, Icon, Button } from 'antd';

const { Content, Sider } = Layout;

/**
 * 事件查询页
 */
class PageEventCheck extends React.Component{

  constructor(){
    super();
    this.state = {
      infoPanelCollaped:true,
    };

    this.toggle = this.toggle.bind(this);
  }

  toggle(){
    this.setState({
      infoPanelCollaped: !this.state.infoPanelCollaped,

   });
  }

  render(){
    return (
      <div>
        <Layout>
          <Content>
            PageEventCheck
            <Button type='ghost' onClick={this.toggle}>Test Call Panel</Button>
            <br /><br /><br /><br /><br /><br /><br />
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
