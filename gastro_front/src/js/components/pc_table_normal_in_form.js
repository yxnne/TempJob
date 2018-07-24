import React from 'react';
import {Table, Button, Row, Col} from 'antd';

export default class PCTableNormalInFormComponent extends React.Component{

  constructor(){
    super();
    this.state = {
      selectedRowKeys: [], // Check here to configure the default column
      downloadLoading: false,
    };
  }

  addDownloadHanlde(){
    this.setState({ downloadLoading: true });
    // ajax request after empty completing
    setTimeout(() => {
      this.setState({
        selectedRowKeys: [],
        downloadLoading: false,
      });
    }, 1000);
  }

  onSelectChange(selectedRowKeys){
    console.log('selectedRowKeys changed: ', selectedRowKeys);
    this.setState({ selectedRowKeys });
  }

  render(){
    const { addLoading, modifyLoading, deleteLoading, selectedRowKeys } = this.state;
    const rowSelectionNeed = {
     selectedRowKeys,
     onChange: this.onSelectChange.bind(this),
    };

    const rowSelection = this.props.needRowSelection?
    rowSelectionNeed
    :
    undefined
    ;
    const hasSelected = selectedRowKeys.length > 0;

    const downloadSection = this.props.needDownloadTable ?
    (
      <Row>
        <Col span={4}>
          <div className="one_small_btn_container" >
            <Button type="primary"  icon="download"
            loading={this.state.downloadLoading} size="small" onClick={this.addDownloadHanlde.bind(this)} >
            Download
            </Button>
            </div>
        </Col>

        <Col span={20} />

      </Row>
    )
    :
    <div></div>
    ;

    return (
        <div className="basic_table_and_form_container">
          {/*下载按钮部分*/}
          {downloadSection}

          <Table rowSelection={rowSelection} columns={this.props.columns} 
          dataSource={this.props.data} scroll={{ x: this.props.totalWidth}}
          pagination={{simple:this.props.isPaginationSmiple, size:this.props.paginationSize, pageSize:this.props.pageNbr}}/>
        </div>
    )
  }
}
