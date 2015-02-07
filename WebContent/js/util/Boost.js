/**
 * I.util.Boost
 * <i>增强功能</i>
 */
I.regist('util.Boost',function(W,D){
 var _toCamelCase=function(){
  if(s.indexOf('-')<0&&s.indexOf('_')<0){
   return s;
  }
  return s.replace(/[-_][^-_]/g,function(m){
   return m.charAt(1).toUpperCase();
  });
 };
 var _addStyle=function(dom,style){
  var st=dom.style.cssText;
  if(';'!=st.substr(st.length-1)){
   st+=';';
  }
  st+=style;
  I.css(dom,st);
 };
 var _removeStyle=function(dom,styleName){
  if(dom.style.removeProperty){
   dom.style.removeProperty(styleName);
  }else if(dom.style.removeAttribute){
   dom.style.removeAttribute(_toCamelCase(styleName));
  }
 };
 var _round=function(dom,n){
  n=n+'';
  var dw='px';
  if(!/^[0-9]*$/.test(n.substr(n.length-1))){
   dw='';
  }
  _addStyle(dom,'-webkit-border-radius:'+n+dw+';-moz-border-radius:'+n+dw+';-ms-border-radius:'+n+dw+';-o-border-radius:'+n+dw+';border-radius:'+n+dw+';');
 };
 return{
  //转换为驼峰
  toCamelCase:function(s){return _toCamelCase(s);},
  //动态添加style代码
  addStyle:function(dom,style){_addStyle(dom,style);},
  //移除指定样式，样式名为非驼峰
  removeStyle:function(dom,styleName){_removeStyle(dom,styleName);},
  round:function(dom,n){_round(dom,n);}
 };
}+'');