I.regist('ui.Color',function(W,D){
var CFG={
 onPreview:function(){},
 callback:function(){}
};
var TAG='data-i-color-id';
var STYLE=function(){/*
.i-color{position:absolute;z-index:994;margin:0;padding:0;left:-1000px;top:-1000px;width:207px;height:120px;background-color:#FFF;border:1px solid #E1E1E1;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;box-sizing:border-box;}
.i-color ul{list-style:none;margin:0;padding:0;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;box-sizing:border-box;}
.i-color ol{list-style:none;margin:0;padding:0;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;box-sizing:border-box;margin-left:5px;margin-top:5px;float:left;border:1px solid #E2E4E6;}
.i-color li{display:block;width:15px;height:15px;margin:0;padding:0;font-size:0;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;box-sizing:border-box;}
.i-color ul li{margin-left:5px;margin-top:5px;float:left;border:1px solid #E2E4E6;}
.i-color ol li{width:13px;height:13px;}
*/}+'';
var obj={
 inited:false,
 doms:[],
 current:null,
 config:{},
 color:'',
 layer:null
};
var COLOR=[
 ['#FFFFFF','#000000','#EEECE1','#1F497D','#4F81BD','#C0504D','#9BBB59','#8064A2','#4BACC6','#F79646'],
 ['#F2F2F2','#D8D8D8','#BFBFBF','#A5A5A5','#7F7F7F'],
 ['#7F7F7F','#595959','#3F3F3F','#262626','#0C0C0C'],
 ['#DDD9C3','#C4BD97','#938953','#494429','#1D1B10'],
 ['#C6D9F0','#8DB3E2','#548DD4','#17365D','#0F243E'],
 ['#DBE5F1','#B8CCE4','#95B3D7','#366092','#244061'],
 ['#F2DCDB','#E5B9B7','#D99694','#953734','#632423'],
 ['#EBF1DD','#D7E3BC','#C3D69B','#76923C','#4F6128'],
 ['#E5E0EC','#CCC1D9','#B2A1C7','#5F497A','#3F3151'],
 ['#DBEEF3','#B7DDE8','#92CDDC','#31859B','#205867'],
 ['#FDEADA','#FBD5B5','#FAC08F','#E36C09','#974806'],
 ['#C00000','#FF0000','#FFC000','#FFFF00','#92D050','#00B050','#00B0F0','#0070C0','#002060','#7030A0']
];
var _trim=function(os){
 var s=os;
 s=s.substr('function(){/*'.length+1);
 s=s.substr(0,s.length-'*/}'.length);
 return s;
};
var _init=function(){
 I.style(_trim(STYLE));
 var o=I.insert('div');
 I.cls(o,'i-color');
 var s='<ul>';
 for(var i=0;i<10;i++){
  s+='<li style="background-color:'+COLOR[0][i]+'" data-color="'+COLOR[0][i]+'"></li>';
 }
 s+='</ul>';
 for(var i=0;i<10;i++){
  s+='<ol>';
  for(var j=0;j<5;j++){
    s+='<li style="background-color:'+COLOR[i+1][j]+'" data-color="'+COLOR[i+1][j]+'"></li>';
  }
  s+='</ol>';
 }
 s+='<ul>';
 for(var i=0;i<10;i++){
  s+='<li style="background-color:'+COLOR[11][i]+'" data-color="'+COLOR[11][i]+'"></li>';
 }
 s+='</ul>';
 o.innerHTML=s;
 obj.layer=o;
 obj.close=function(){
  this.layer.style.left='-1000px';
  this.current=null;
  this.color='';
 };
 obj.moveTo=function(x,y){
  I.util.Boost.addStyle(obj.layer,'left:'+x+'px;top:'+y+'px;');
 };
 var li=I.$(o,'tag','li');
 I.listen(li,'click',function(m,e){
  if(!obj.current) return;
  obj.color=m.getAttribute('data-color');
  obj.config[obj.current.getAttribute(TAG)].callback.call(obj);
  obj.close();
 });
 I.listen(li,'mouseover',function(m,e){
  m.style.border='1px solid #F29436';
  if(!obj.current) return;
  obj.config[obj.current.getAttribute(TAG)].onPreview(m.getAttribute('data-color'));
 });
 I.listen(li,'mouseout',function(m,e){
  m.style.border='';
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
 return obj;
};
return {
 bind:function(dom,cfg){return _bind(dom,cfg);}
};
}+'');