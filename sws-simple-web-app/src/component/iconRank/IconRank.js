import React  from 'react'

/** 圆形头像 */
export default (props)=>{
  const textColor = props.textColor;

  const height = props.height;
  const radius = height / 2 ;
  // 排名1,2,3的颜色不一样
  const rank = props.rank;
  let bgColor = "#23a7fc";
  switch(rank){
    case 1: bgColor = '#ff5757'; break;
    case 2: bgColor = '#ff8873'; break;
    case 3: bgColor = '#feb671'; break;
    default:bgColor = '#23a7fc';
  }

  // style define
  const myStyle = {
    width:height,
    height,
    lineHeight:`${height}px`,
    borderRadius:radius,
    background:bgColor,
    textAlign:'center',
    color:textColor,
  };
  return (
    <div style={myStyle}>
      {props.children}
    </div>
  );
};