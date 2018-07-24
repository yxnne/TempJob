import React from 'react';
import {Table, Button, Row, Col} from 'antd';

export default class PCTableBasicNoFormComponent extends React.Component{

  constructor(){
    super();
    this.state = {
      selectedRowKeys: [], // Check here to configure the default column
      addLoading: false,
      modifyLoading: false,
      deleteLoading: false
    };
  }

  addClickHanlde(){
    this.setState({ addLoading: true });
    // ajax request after empty completing
    setTimeout(() => {
      this.setState({
        selectedRowKeys: [],
        addLoading: false,
      });
    }, 1000);
  }

  modifyClickHanlde(){
    this.setState({ modifyLoading: true });
    // ajax request after empty completing
    setTimeout(() => {
      this.setState({
        selectedRowKeys: [],
        modifyLoading: false,
      });
    }, 1000);
  }

  deleteClickHanlde(){
    this.setState({ deleteLoading: true });
    // ajax request after empty completing
    setTimeout(() => {
      this.setState({
        selectedRowKeys: [],
        deleteLoading: false,
      });
    }, 1000);
  }

  onSelectChange(selectedRowKeys){
    console.log('selectedRowKeys changed: ', selectedRowKeys);
    this.setState({ selectedRowKeys });
  }

  render(){
    const { addLoading, modifyLoading, deleteLoading, selectedRowKeys } = this.state;
    const rowSelection = {
     selectedRowKeys,
     onChange: this.onSelectChange.bind(this),
    };
    const hasSelected = selectedRowKeys.length > 0;

    return (
        <div className="basic_table_and_form_container">
          <Row>
            <Col span={18} />
            <Col span={6}>
              <div className="three_btn_container" >
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

              </div>
            </Col>
          </Row>

          <Table rowSelection={rowSelection} columns={this.props.columns} dataSource={this.props.data} />
        </div>
    )
  }
}
