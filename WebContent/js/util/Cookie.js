/**
 * I.util.Cookie
 * <i>Cookie操作类</i>
 */
I.regist('util.Cookie',function(W,D){
  var _set = function(key,value,day){
    var exp = -1;
    if(day) exp = day;
    if(exp>-1){
      var d = new Date();
      d.setTime(d.getTime()+exp*24*60*60*1000);
      D.cookie = key+'='+escape(value)+"; expires="+d.toGMTString();
    }else{
      D.cookie = key+'='+escape(value);
    }
  };
  var _get = function(key){
    try{
      var s = D.cookie.split('; ');
      for(var i=0;i<s.length;i++){
        var n = s[i].split('=');
        if(n[0] == key) return unescape(n[1]);
      }
    }catch(e){}
    return null;
  };
  var _remove = function(key){
    _set(key,'',-1);
  };
  
  return {
    /**
     * 设置Cookie
     * @param key 键
     * @param value 值
     * @param day 有效天数
     */
    set:function(key,value,day){_set(key,value,day);},
    /**
     * 获取Cookie，如果不存在键，返回null
     * @param key 键
     */
    get:function(key){return _get(key);},
    /**
     * 移除Cookie
     * @param key 键
     */
    remove:function(key){_remove(key);}
  };
}+'');