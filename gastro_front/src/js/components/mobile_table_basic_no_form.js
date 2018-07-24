import React from 'react';
import {Table, Button, Row, Col, Popover, Affix, TreeSelect, Modal} from 'antd';

export default class MobileTableBasicNoFormComponent extends React.Component{

  constructor(){
    super();
    this.state = {
      selectedRowKeys: [], // Check here to configure the default column
      addLoading: false,
      modifyLoading: false,
      deleteLoading: false,
      controlPanelVisible: false,
      settingModalVisible:false,
      treeValue:undefined
    };
  }

  addClickHanlde(){
    this.setState({ addLoading: true });
    // ajax request after empty completing
    // 调用属性传过来的方法
    if (this.props.onAddClick) {
      this.props.onAddClick();
    }
    setTimeout(() => {
      this.setState({
        selectedRowKeys: [],
        addLoading: false,
        controlPanelVisible:false
      });

    }, this.props.addloadingTime);
  }

  modifyClickHanlde(){
    this.setState({ modifyLoading: true });
    // ajax request after empty completing
    //
    if (this.props.onModifyClick) {
      const rows = this.state.selectedRowKeys;
      this.props.onModifyClick(rows[rows.length - 1]);
    }

    setTimeout(() => {
      this.setState({
        selectedRowKeys: [],
        modifyLoading: false,
        controlPanelVisible:false
      });
    }, 500);
  }

  deleteClickHanlde(){
    this.setState({ deleteLoading: true });
    // ajax request after empty completing
    // 调用属性传过来的方法
    if(this.props.onDeleteClick){
      this.state.selectedRowKeys.map((item) => {
        this.props.onDeleteClick(item);
      });
    }
    setTimeout(() => {
      this.setState({
        selectedRowKeys: [],
        deleteLoading: false,
        controlPanelVisible:false
      });
    }, 1000);
  }

  onSelectChange(selectedRowKeys){
    console.log('selectedRowKeys changed: ', selectedRowKeys);
    this.setState({ selectedRowKeys });
  }

  //功能按钮按下调用
  handlecontrolPanelVisibleChange(controlPanelVisible){
    this.setState({
      controlPanelVisible:controlPanelVisible
    });

  }
  //设置树按钮按下调用
  handleSettingTreeClick(){
    this.showModal();
  }

  onTreeSelectChange(value){
    console.log(arguments);
    this.setState({ value });
  }

  onTreeSelectClickHandle(){
    console.log("click");
  }

  //显示对话框
  showModal(){
    this.setState({
      settingModalVisible:true
    });
  }
  //关闭对话框
  closeModal(){
    this.setState({
      settingModalVisible:false
    });
  }


  render(){
    const { addLoading, modifyLoading, deleteLoading, selectedRowKeys } = this.state;
    const rowSelection = {
     selectedRowKeys,
     onChange: this.onSelectChange.bind(this),
    };

    const hasSelected = selectedRowKeys.length > 0;
    //弹出的内容
    const popContent = <div id="btn_container">

                        <Button
                          type="primary"
                          onClick={this.addClickHanlde.bind(this)}
                          loading={addLoading}>
                          添加
                        </Button>

                        <Button
                          type="primary"
                          onClick={this.modifyClickHanlde.bind(this)}
                          disabled={!hasSelected}
                          loading={modifyLoading}>
                          更改
                        </Button>

                        <Button
                          type="danger"
                          onClick={this.deleteClickHanlde.bind(this)}
                          disabled={!hasSelected}
                          loading={deleteLoading}>
                          删除
                        </Button>

                      </div>;

    //树形选择框是否需要:

    const treeData = [{
      label: '父节点',
      value: '0-0',
      key: '0-0',
      children: [{
          label: '子节点 1',
          value: '0-0-1',
          key: '0-0-1',
        }, {
          label: '子节点 2',
          value: '0-0-2',
          key: '0-0-2',
          children: [{
              label: '孙子节点 1',
              value: '0-0-1-0',
              key: '0-0-1',
            }, {
                label: '孙子节点 3',
                value: '0-0-1-1',
                key: '0-0-2',
            }, {
                label: '孙子节点 3',
                value: '0-0-1-2',
                key: '0-0-3',
            }]
        }],
      }];


    //选择树控件 是否需要判断
    const selectedTree = this.props.needTree?
      <TreeSelect style={{ width: 200 }}
        value={this.state.treeValue}
        dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
        treeData={treeData}
        placeholder="Please select"
        treeDefaultExpandAll
        onChange={this.onTreeSelectChange.bind(this)}
        onExpand={this.onTreeSelectClickHandle.bind(this)}
      />
      :
      <span></span>
      ;

      //设置树按钮 是否需要的判断
    const settingTreeStyle = {marginTop:0};
    const settingTree = (this.props.needTreeSetting && this.props.needTree)?
      <Button type="ghost"  icon="setting" style={settingTreeStyle}
        onClick={this.showModal.bind(this)}>
      </Button>
      :
      <span></span>
      ;
      //Table 的TotalWith
      const totalWidth = this.props.totalWidth?
      {x:this.props.totalWidth}
      :
      { x: 1300 }
      ;
    return (
        <div id="basic_table_and_form_container">
          <div id="btn_container">
            <Row>
              <Col span={21} >
                <div id="m_table_tools_container">
                  {selectedTree}
                  {settingTree}
                </div>
              </Col>
              <Col span={3}>
              {/*固定的内容*/}
                <Affix target={() => window}>
                  <Popover content={popContent} trigger="click"
                    placement="left" visible={this.state.controlPanelVisible}
                    onVisibleChange={this.handlecontrolPanelVisibleChange.bind(this)}>

                      <Button type="primary" size="large" shape="circle" icon={this.props.iconBtnType} />

                  </Popover>
                </Affix>
              </Col>
            </Row>
          </div>
          <Table rowSelection={rowSelection} columns={this.props.columns}
            dataSource={this.props.data} scroll={totalWidth} pagination={{ size:"small"}}/>

          {/*模态框*/}
          <Modal title="修改组织树" visible={this.state.settingModalVisible}
            onCancel={()=>{this.closeModal()}} footer={null} >
            <div style={{height:"300px", textAlign:"center"}}>这里是树</div>
            <div id="btn_container">
              <Button type="primary"  icon="plus" >
                添加
              </Button>
              <Button type="ghost"  icon="edit" >
                修改
              </Button>
              <Button type="danger"  icon="minus" >
                删除
              </Button>
            </div>
          </Modal>
        </div>
    )
  }
}
