I.regist('ui.FontSize',function(W,D){
var CFG={
 text:['一号','二号','三号','四号','五号','六号','七号'],
 size:['1','2','3','4','5','6','7'],
 onPreview:function(){},
 callback:function(){}
};
var TAG='data-i-fontsize-id';
var STYLE=function(){/*
.i-fontsize{position:absolute;z-index:994;margin:0;padding:0;left:-1000px;top:-1000px;width:120px;background-color:#FFF;border:1px solid #E1E1E1;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;box-sizing:border-box;overflow:hidden;}
.i-fontsize ul{list-style:none;margin:0;padding:0;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;box-sizing:border-box;}
.i-fontsize li{display:block;margin:2px;padding:0;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;box-sizing:border-box;border:1px solid #FFF;cursor:default;}
*/}+'';
var obj={
 inited:false,
 doms:[],
 current:null,
 config:{},
 color:'',
 layer:null
};
var _trim=function(os){
 var s=os;
 s=s.substr('function(){/*'.length+1);
 s=s.substr(0,s.length-'*/}'.length);
 return s;
};
var _init=function(){
 var o=I.$('class','i-fontsize');
 if(o&&o.length>0){
  o=o[0];
 }else{
  I.style(_trim(STYLE));
  o=I.insert('div');
  I.cls(o,'i-fontsize');
  var s='<ul>';
  for(var i=0,j=CFG.size.length;i<j;i++){
   s+='<li data-fontsize="'+CFG.size[i]+'" style="font-size:'+CFG.size[i]+'">'+CFG.text[i]+'</li>';
  }
  s+='</ul>';
  o.innerHTML=s;
  bindEvent();
 }
 obj.layer=o;
 obj.close=function(){
  this.layer.style.left='-1000px';
  this.current=null;
  this.fontsize='';
 };
 obj.moveTo=function(x,y){
  I.util.Boost.addStyle(obj.layer,'left:'+x+'px;top:'+y+'px;');
 };
 var li=I.$(o,'tag','li');
 I.listen(li,'click',function(m,e){
  if(!obj.current) return;
  var fontsize=m.getAttribute('data-fontsize');
  obj.fontsize=fontsize;
  obj.config[obj.current.getAttribute(TAG)].callback.call(obj,fontsize);
  obj.close();
 });
 I.listen(li,'mouseover',function(m,e){
   I.util.Boost.addStyle(m,'background-color:#D5E1F2;border:1px solid #A3BDE3;');
  if(!obj.current) return;
  obj.config[obj.current.getAttribute(TAG)].onPreview.call(obj,m.getAttribute('data-fontsize'));
 });
 I.listen(li,'mouseout',function(m,e){
  I.util.Boost.addStyle(m,'background-color:#FFF;border:1px solid #FFF;');
 });
};
var bindEvent=function(){
 I.listen(D,'click',function(o,e){
  try{
   o=e.srcElement||e.target;
   var v=false;
   for(var i=0;i<obj.doms.length;i++){
    if(o==obj.doms[i]){
     v=true;
     break;
    }
   }
   if(v) return;
   v=I.region();
   var x=e.clientX+v.x;
   var y=e.clientY+v.y;
   var m=I.region(obj.layer);
   if(x < m.x || y < m.y || x > m.x+m.width || y > m.y+m.height) obj.close();
  }catch(ee){}
  return true;
 });
};
var _bind=function(dom,config){
 if(!obj.inited) _init();
 dom=I.$(dom);
 var uuid=I.util.UUID.next();
 dom.setAttribute(TAG,uuid);
 obj.doms.push(dom);
 var cfg=I.ui.Component.initConfig(config,CFG);
 obj.config[uuid]=cfg;
 I.listen(dom,'click',function(m,e){
  var r=I.region(m);
  obj.moveTo(r.x,r.y+r.height);
  obj.current=m;
 });
 return obj;
};
return {
 bind:function(dom,cfg){return _bind(dom,cfg);}
};
}+'');