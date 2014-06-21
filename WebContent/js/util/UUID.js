/**
 * I.util.UUID
 * <i>UUID生成器</i>
 * <u>I.util.UUID.next();</u>
 * 
 */
I.regist('util.UUID',function(W,D){
  var _next = function(){
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g,
      function(c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
      });
  };
  return {
    next:function() {
      return _next();
    }
  };
}+'');