// 封装请求方法， 具体使用方法见README.md文档
// fetch不用引入

// response对象中的json提出来
function parseJSON(response) {
  return response.json();
}
// 检查状态码
function checkStatus(response) {
  //console.log("checkStatus",response.status)
  if (response.status >= 200 && response.status < 300) {
    return response;
  }
  const error = new Error(response.statusText);
  error.response = response;
  throw error;
}

const dealErr = (err) => {
  throw err;
}
/**
 * 基础请求方法
 *
 * @param  {string} url         请求URL地址
 * @param  {object} [options]   fetch方法使用的option
 * @return {object}             数据或错误
 */
function request(url, options) {
  return fetch(url, options )
    .then(checkStatus)
    .then(parseJSON)
    .catch(dealErr);
}

/**
 * 基础get请求方法,基于request()方法封装
 *
 * @param  {string} url         请求URL地址
 */
function get(url) {
  //实现跨域的请求头
  const opt ={
    method: 'GET',
  };
  return request(url, opt);
}

/**
 * 基础post请求方法,基于request()方法封装
 *
 * @param  {string} url         请求URL地址
 * @param  {string} formData    post数据
 */
function post(url, jsonData) {
  //实现跨域的请求头
  const opt ={
    method: 'POST',
    body:JSON.stringify(jsonData),
    mode: 'cors',
    headers:{
      'Content-type' : 'application/json; charset=UTF-8',
    }

  };
  return request(url, opt);
}

export {get, post};
