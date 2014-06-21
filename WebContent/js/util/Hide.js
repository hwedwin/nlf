/**
 * I.util.Hide
 * <i>隐藏显示字符串</i>
 * <u>I.util.Hide.show(s);</u>
 * <u>I.util.Hide.hide(s);</u>
 */
I.regist('util.Hide',function(W,D){
  var _show = function(s){
    if(s.indexOf('\u200d')<0&&s.indexOf('\u200c')<0){
      return s;
    }
    var n = [];
    var ns = s.split('');
    for(var i=0;i<ns.length;i++){
      if(ns[i]=='\u200d'){
        n.push('1');
      }else if(ns[i]=='\u200c'){
        n.push('0');
      }
    }
    var rs = [];
    var sb = [];
    for(var i=0;i<n.length;i++){
      if(sb.length<16){
        sb.push(n[i]);
      }else{
        rs.push(String.fromCharCode(parseInt(sb.join(''),2)));
        sb = [];
        sb.push(n[i]);
      }
    }
    if(sb.length>=16){
      rs.push(String.fromCharCode(parseInt(sb.join(''),2)));
    }
    return rs.join('');
  };
    
  var _hide = function(s){
    throw 'util.Hide.hide(s) not implemented';
  };
    
  return{
    show:function(s) {
      return _show(s);
    },
    hide:function(s) {
      return _hide(s);
    }
  };
}+'');