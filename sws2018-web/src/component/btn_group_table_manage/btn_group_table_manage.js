import React from 'react';
import { Button, Icon, Row, Col } from 'antd';
import PropTypes from 'prop-types';

/**
 * 管理表格的一组按键组件（ add， delete， modify， reload ）
 */
class BtnGroupTableManage extends React.Component{


  render(){
    return (
      <div style={{marginTop:16, marginBottom:16}}>
        <Row gutter={8}>
          <Col span={20}>
            <Button onClick={this.props.onAddClick} type='primary' size='small' icon="plus">添加</Button>
            <Button onClick={this.props.onModifyClick} style={{marginLeft:12}} type='ghost' size='small' icon="form">修改</Button>
            <Button onClick={this.props.onDeleteClick} style={{marginLeft:12}}  type='danger' size='small' icon="delete">删除</Button>

          </Col>
          <Col span={4}>
            <Button onClick={this.props.onReloadClick} style={{float:'right'}} type='ghost' size='small' icon="reload">重载</Button>
          </Col>

        </Row>
      </div>
    );
  }
}

// 组件属性检查设置
BtnGroupTableManage.propTypes = {
  onAddClick:PropTypes.func.isRequired,
  onModifyClick:PropTypes.func.isRequired,
  onDeleteClick:PropTypes.func.isRequired,
  onReloadClick:PropTypes.func.isRequired,
};

export default BtnGroupTableManage;
