import React from 'react';
import {Card, Divider, Tree, Icon, Popover} from 'antd';

const TreeNode = Tree.TreeNode;

export default class PCOrganiseTreeComponent extends React.Component{

  constructor(){
    super();
    this.state = {
      treeData: [
        { title: 'Expand to load', key: '0' },
        { title: 'Expand to load', key: '1' },
        { title: 'Tree Node', key: '2', isLeaf: true },
      ],
      visible:false
    };

  }

  handleClickAdd(){
    console.log("I clicked the add ");
    this.handleVisibleChange(false);
  }
  handleClickModify(){
    console.log("I clicked the modify ");
    this.handleVisibleChange(false);
  }
  handleClickDelete(){
    console.log("I clicked the delete ");
    this.handleVisibleChange(false);
  }

  // onLoadData(treeNode) {
  //   return new Promise((resolve) => {
  //     if (treeNode.props.children) {
  //       resolve();
  //       return;
  //     }
  //     setTimeout(() => {
  //       treeNode.props.dataRef.children = [
  //         { title: 'Child Node', key: `${treeNode.props.eventKey}-0` },
  //         { title: 'Child Node', key: `${treeNode.props.eventKey}-1` },
  //       ];
  //       this.setState({
  //         treeData: [...this.state.treeData],
  //       });
  //       resolve();
  //     }, 1000);
  //   });
  // }

  // renderTreeNodes(data) {
  //   return data.map((item) => {
  //     if (item.children) {
  //       return (
  //         <TreeNode title={item.title} key={item.key} dataRef={item}>
  //           {this.renderTreeNodes(item.children)}
  //         </TreeNode>
  //       );
  //     }
  //     return <TreeNode {...item} dataRef={item} />;
  //   });
  // }

  handleVisibleChange(visible){
    this.setState({visible});
  }


  render(){
    const actions = <div>
                      <a onClick={this.handleClickAdd.bind(this)}>添加</a>
                      <Divider type="vertical" />
                      <a onClick={this.handleClickModify.bind(this)}>修改</a>
                      <Divider type="vertical" />
                      <a onClick={this.handleClickDelete.bind(this)}>删除</a>
                    </div>;

    const popoverAction = this.props.needActoion
    ?
    <Popover content={actions} trigger="click"
      placement="right" visible={this.state.visible}
      onVisibleChange={this.handleVisibleChange.bind(this)}>
        <Icon type="ellipsis"  style={{ fontSize: 22, color: '#46cd71' }}/>
    </Popover>
    :
    <div>
    </div>
    ;



    return (
      <Card title="组织树"  bordered={false} extra={popoverAction} >
        <Tree
          defaultExpandedKeys={['0-0-0', '0-0-1']}
          defaultSelectedKeys={['0-0', '0-0-0']}
          onSelect={this.onSelect}
          onCheck={this.onCheck}
          >
          <TreeNode title="内镜中心" key="0-0">
            <TreeNode title="诊疗室_1" key="0-0-0" >
              <TreeNode title="二级节点1" key="0-0-0-0"  />
              <TreeNode title="二级节点2" key="0-0-0-1" disabled/>
              <TreeNode title="二级节点3" key="0-0-0-2" disabled/>
            </TreeNode>
            <TreeNode title="诊疗室_2" key="0-0-1">
            </TreeNode>
            <TreeNode title="诊疗室_3" key="0-0-2">
            </TreeNode>
            <TreeNode title="诊疗室_2" key="0-0-3">
              {/*这里是修改样式的方法*/}
              <TreeNode title={<span style={{ color: '#1890ff' }}>sss</span>} key="0-0-1-0" />
            </TreeNode>
          </TreeNode>
        </Tree>
     </Card>
    );
  }

}
