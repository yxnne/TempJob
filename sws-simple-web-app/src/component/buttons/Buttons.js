import React  from 'react';
import { Button } from 'antd-mobile'

const CheckButton = ({style, onClick, size, type, children}) => {
  
  return (
    <Button style={style} icon={<img src={require('./image/check.svg')} alt="" />}
      type={type} size={size} onClick={onClick}>{children}</Button>
  )
}

export  { CheckButton };
