import React from 'react';
import {Table, Button, Row, Col, Popover, Affix, TreeSelect, Modal} from 'antd';

export default class MobileTableNormalInForm extends React.Component{

  constructor(){
    super();
    this.state = {
      selectedRowKeys: [], // Check here to configure the default column

      modalVisible: false,
    };
  }

  //Table 第一列的复选
  onSelectChange(selectedRowKeys){
    console.log('selectedRowKeys changed: ', selectedRowKeys);
    this.setState({ selectedRowKeys });
  }
  //对话框上的OK按钮
  handleOk(){

  }

  render(){
    const { addLoading, modifyLoading, deleteLoading, selectedRowKeys } = this.state;

    //需要列前复选的设置
    const rowSelectionNeed = {
     selectedRowKeys,
     onChange: this.onSelectChange.bind(this),
    };

    //需要列前复选的设置
    const rowSelection = this.props.needRowSelection?
    rowSelectionNeed
    :
    undefined
    ;

    //弹出的内容

    //宽度和scroll的设置
    const totalWidth = this.props.totalWidth?
    {x:this.props.totalWidth}
    :
    { x: 1300 }
    ;
    //附加工具
    const tableTool = this.props.tableTool?
    this.props.tableTool
    :
    <span></span>
    ;

    return (
        <div id="basic_table_and_form_container">
          <div id="btn_container">
            <Row>
              <Col span={21} >
                <div id="m_table_tools_container">
                  {tableTool}
                </div>
              </Col>
              <Col span={3}>
              {/*固定的内容*/}
                <Affix target={() => window}>
                  <Button type="primary" size="large" shape="circle"
                  icon={this.props.iconBtnType} onClick={this.props.searchClickHanlde}/>
                 </Affix>
              </Col>
            </Row>
          </div>
          <Table rowSelection={rowSelection} columns={this.props.columns} dataSource={this.props.data} scroll={totalWidth} pagination={{ size:"small"}}/>

        </div>
    )
  }
}
