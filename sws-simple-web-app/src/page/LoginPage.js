import React from 'react';
import { List, InputItem, WhiteSpace } from 'antd-mobile';


/** 登录页面 */
class LoginPage extends React.Component {

  constructor(props){
    super(props);
    this.state = {
      isNeedServerSet:false,
      username:'',
      password:'',
      serverIP:'',
      serverPort:'',
    };

    this.onClickLogin = this.onClickLogin.bind(this);
    this.onInputChanged = this.onInputChanged.bind(this);
    this.onClickToggleServerSet = this.onClickToggleServerSet.bind(this);
    this.onClickBasicTitle = this.onClickBasicTitle.bind(this);
  }

  onClickLogin(){
    console.log(this.state)
  }
  
  onInputChanged(stateKey, value){
    this.setState({
      [stateKey]:value
    });
  }

  onClickToggleServerSet(){
    const isNeedServer = this.state.isNeedServerSet;
    this.setState({ isNeedServerSet:!isNeedServer});

  }

  onClickBasicTitle(){
    //点击标题的时候聚焦 username
    this.userInputRef.focus();
  }

  render(){
    // 服务器IP信息输入框
    const serverSetServerEle = this.state.isNeedServerSet?(
      <InputItem  clear placeholder="设置服务器IP地址" ref={r=>this.serverIPInputRef = r}
      onChange={(v)=>this.onInputChanged("serverIP", v )}>
        服务器
      </InputItem>
    ):null;

    // 服务器端口信息输入框
    const serverSetPortEle = this.state.isNeedServerSet?(
      <InputItem clear placeholder="设置服务器端口号" 
      onChange={(v)=>this.onInputChanged("serverPort", v )}>
        端口
      </InputItem>
    ):null;

    const toggleBtnStyle = this.state.isNeedServerSet?
    { color:'#999' }:{ color:'#5cbafd' };

    return (
      <div>
        <div className='login-app-v-info'>智控手卫生简化版 Web APP v1.0</div>

        {/* logo部分 */}
        <div className='login-applogo-container'>
          <img style={{width:100, heigth:100}} src={require('./image/app_logo.svg')} alt='log'/>

        </div>

        {/* 输入部分 */}
        <List renderHeader={() => ''}>
          {/* 基本登录信息标题, 点击聚焦 */}
          <List.Item >
            <div style={{ width: '100%', color: '#999', fontSize:14 }}  
              onClick={this.onClickBasicTitle}>
              基本登录信息
            </div>
          </List.Item>

          {/* 基本信息输入框 */}
          <InputItem clear placeholder="请输入用户名" 
            onChange={(v)=>this.onInputChanged("username", v )}
            ref={el => this.userInputRef = el}>
            用户名
          </InputItem>
          <InputItem clear placeholder="请输入对应密码" 
          onChange={(v)=>this.onInputChanged("password", v )}>
            密码
          </InputItem>

          {/* 服务器信息标题，点击可展开或收起服务器信息输入框 */}
          <List.Item >
            <div style={{ width: '100%', fontSize:14, ...toggleBtnStyle}}
              onClick={this.onClickToggleServerSet} >
              { this.state.isNeedServerSet?'收起服务器设置项':'需要设置或更改服务器？'}
            </div>
          </List.Item>

          {/* 两个服务器信息输入框 */}
          { serverSetServerEle }

          { serverSetPortEle }

          <List.Item>
            <div style={{ width: '100%', color: '#108ee9', textAlign: 'center', padding:'8px 0' }}
              onClick={this.onClickLogin} >
              登&nbsp;&nbsp;&nbsp;录
            </div>
          </List.Item>
        </List>
        <WhiteSpace size='xl'/>
        <WhiteSpace size='xl'/>
      </div>
    );
  }
}

export default LoginPage;