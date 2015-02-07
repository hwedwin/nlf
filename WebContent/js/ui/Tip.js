I.regist('ui.Tip',function(W,D){
var TIME={
 '0':8000,
 '1':3000,
 '2':1000
};
var CFG={
 skin:'Default',
 background:'#111',
 color:'#FFF',
 border:'0',
 msg:'',
 type:1,
 round:6,
 callback:function(){}
};
var _create=function(obj){
 var cfg=obj.config;
 var o=I.insert('div');
 I.cls(o,obj.className);
 obj.layer=o;
 o.innerHTML='<span>'+cfg.msg+'</span>';
 I.util.Boost.addStyle(o,'border:'+cfg.border+';background:'+cfg.background+';color:'+cfg.color);
 I.util.Boost.round(o,cfg.round);
 var nr=I.region(I.$(o,'*')[0]);
 var pr=I.region();
 I.util.Boost.addStyle(o,'left:'+Math.max(Math.floor(pr.x+pr.width/2-nr.width/2),0)+'px;top:'+Math.floor(pr.y+10)+'px;');
 var op=0;
 I.opacity(o,op);
 obj.timer=W.setInterval(function(){
  if(op<100){
   op+=2;
   I.opacity(o,op);
  }else{
   I.opacity(o,100);
   W.clearInterval(obj.timer);
   I.delay(TIME[cfg.type],function(){
    obj.close();
   });
  }
 },20);
};
var _prepare=function(config){
 var obj={layer:null,timer:null,config:null,className:null};
 var cfg=I.ui.Component.initConfig(config,CFG);
 obj.config=cfg;
 obj.className='i-ui-Tip-'+cfg.skin;
 I.util.Skin.init(cfg.skin);
 _create(obj);
 obj.close=function(){
  this.config.callback.call(this);
  this.layer.parentNode.removeChild(this.layer);
 };
 return obj;
};
return{
 create:function(cfg){return _prepare(cfg);}
};
}+'');