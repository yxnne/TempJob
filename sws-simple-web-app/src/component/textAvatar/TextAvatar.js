import React from 'react'

/** 文字头像：最好是一个字 */
//{margin:'0px auto', color:'white', fontSize:36 , textAlign:'center' ,lineHeight:'60px', width:60, height:60, borderRadius:30, backgroundColor:'#33a4f4'}
const TextAvatar = ({bgColor='#33a4f4', radius=30, text='', textSize=36, textColor='white'})=> {
  const width = 2 * radius;
  const avatarStyle = {
    margin:'0px auto', 
    color:textColor, 
    fontSize:textSize , 
    textAlign:'center' ,
    lineHeight:`${width}px`, 
    width:width, 
    height:width, 
    borderRadius:radius, 
    backgroundColor:bgColor
  };
  return (
    <div style={avatarStyle}>
      {text}
    </div>
  )
};

export default TextAvatar;
