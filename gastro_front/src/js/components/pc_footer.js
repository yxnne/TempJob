import React from 'react';
import {Row, Col} from 'antd';

export default class PCFooter extends React.Component{
  render(){
    return (
      <footer>
        <Row>
          <Col span={2} />
          <Col span={20} className="footer">
            &copy;&nbsp;2018 Hangzhou YiTongQuan Technology Co., Ltd.,All Rights Reserved
          </Col>
          <Col span={2} />
        </Row>
      </footer>
    );
  }

}
