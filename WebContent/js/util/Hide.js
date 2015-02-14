I.regist('util.Hide',function(W,D){
var _show=function(s){
 if(s.indexOf('\u200d')<0&&s.indexOf('\u200c')<0) return s;
 var n=[],rs=[],sb=[],ns=s.split('');
 for(var i=0,j=ns.length;i<j;i++){
  if(ns[i]=='\u200d') n.push('1');
  else if(ns[i]=='\u200c') n.push('0');
 }
 for(var i=0,j=n.length;i<j;i++){
  if(sb.length<16) sb.push(n[i]);
  else{
   rs.push(String.fromCharCode(parseInt(sb.join(''),2)));
   sb=[];
   sb.push(n[i]);
  }
 }
 if(sb.length>=16) rs.push(String.fromCharCode(parseInt(sb.join(''),2)));
 return rs.join('');
};
var _hide=function(s){throw 'util.Hide.hide(s) not implemented';};
return {
 show:function(s){return _show(s);},
 hide:function(s){return _hide(s);}
};
}+'');