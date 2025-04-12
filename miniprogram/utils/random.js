export default function randomString(len) {
  len = len || 32;
  let timestamp = new Date().getTime();
  /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
  let $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
  let maxPos = $chars.length;
  let randomStr = '';
  for (let i = 0; i < len; i++) {
    randomStr += $chars.charAt(Math.floor(Math.random() * maxPos));
  }
  //console.log('时间戳', timestamp)
  //console.log('随机数', randomStr)
  return randomStr + timestamp;
}
//console.log('时间戳+随机数', randomString(10))