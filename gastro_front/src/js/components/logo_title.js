import React from 'react';

export default class LogoTitleComponent extends React.Component{
  render(){

    return (
      <div className="logo" id="logo" hidden={this.props.toHidden}>

      </div>
    );
  }
}
