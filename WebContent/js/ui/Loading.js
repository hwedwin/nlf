I.regist('ui.Loading',function(W,D){
var CFG={
 skin:'Default',
 mask:true,
 mask_opacity:0,
 mask_color:'#FFF',
 color:'#0074D9',
 callback:function(){}
};
var _create=function(obj){
 var cfg=obj.config;
 if(cfg.mask){
  obj.mask=I.ui.Mask.create({skin:cfg.skin,opacity:cfg.mask_opacity,color:cfg.mask_color});
 }
 var o=I.insert('div');
 I.cls(o,obj.className);
 o.style.background=cfg.color;
 obj.layer=o;
 obj.layer.style.display='none';
 if(obj.mask){
  I.opacity(obj.mask.layer,0);
 }
 I.delay(1000,function(){
  try{I.opacity(obj.mask.layer,obj.config.mask_opacity);}catch(e){}
  try{obj.layer.style.display = 'block';}catch(e){}
  obj.timer=W.setInterval(function(){
   var inst=obj;
   if(inst.percent<100){
    inst.percent+=(100-inst.percent)/60;
   }else{
    inst.percent = 100;
   }
   var r = I.region();
   I.util.Boost.addStyle(inst.layer,'left:'+r.x+'px;top:'+r.y+';width:'+Math.floor(inst.percent/100*r.width)+'px;');
   if(inst.over){
    W.clearInterval(inst.timer);
    inst.timer=null;
    try{inst.close();}catch(e){}
   }
  },60);
 });
};
var _prepare=function(config){
 var obj={layer:null,mask:null,timer:null,percent:0,over:false,className:null,config:null};
 var cfg=I.ui.Component.initConfig(config,CFG);
 obj.config=cfg;
 obj.className='i-ui-Loading-'+cfg.skin;
 I.util.Skin.init(cfg.skin);
 _create(obj);
 obj.close=function(){
  var inst=this;
  inst.percent=100;
  inst.over=true;
  I.delay(400,function(){
   try{inst.mask.close();}catch(e){}
   try{inst.layer.parentNode.removeChild(inst.layer);}catch(e){return;}
   inst.config.callback.call(inst);
  });
 };
 return obj;
};
return{
 create:function(cfg){return _prepare(cfg);}
};
}+'');