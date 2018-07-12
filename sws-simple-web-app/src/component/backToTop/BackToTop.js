import React, { Component } from 'react'
import { withRouter } from 'react-router-dom';


// commenly styles
const commenStyle = {
  height:32,
  lineHeight:'32px',
  color:'#33A3F4',
  fontSize:16,
  paddingBottom:4
}

/** 返回上一级 */
@withRouter
export default class BackToTop extends Component {
  constructor(props){
    super(props);

    this.onBackClick = this.onBackClick.bind(this);
  }

  onBackClick(){
    if(this.props.backPath){
      this.props.history.push(this.props.backPath)
    }

  }

  render() {
    return (
      <div style={{...commenStyle, marginTop:10, display:'table', width:'100%', borderBottom: '1px solid #ddd'}}>
        <div style={{...commenStyle, display:'table-row', width:'100%'}}>
          <div style={{...commenStyle, display:'table-cell', width:'22%', textAlign:'center'}}
            onClick={this.onBackClick}>
           &lt;&nbsp;返回
          </div>
          <div style={{...commenStyle, display:'table-cell', width:'56%', textAlign:'center'}}>
            {this.props.title}
          </div>
          <div style={{...commenStyle, display:'table-cell', width:'22%', textAlign:'center'}}>
            {this.props.extra}
          </div>
        </div>
      </div>
    )
  }
}
