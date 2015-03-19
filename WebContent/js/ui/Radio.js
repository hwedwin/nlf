I.regist('ui.Radio',function(W,D){
var CFG={
 skin:'Default',
 border:'0',
 border_hover:'0',
 background:'#FBFBFB',
 background_hover:'#E9E9E9',
 color:'#333',
 color_hover:'#333',
 label:null,
 icon_normal:'fa fa-circle-o',
 icon_checked:'fa fa-dot-circle-o',
 name:'radio',
 value:'',
 checked:false,
 dom:D.body,
 _callback:function(){
  var dom=this.dom;
  var cfg=this.config;
  var ipt=I.$(dom,'tag','input')[0];
  var name=ipt.getAttribute('name');
  var ipts=I.$('name',name);
  if(ipts&&ipts.length>0){
   for(var i=0;i<ipts.length;i++){
    var o=ipts[i];
    var p=o.parentNode;
    if(p==dom){
     o.checked='checked';
     I.cls(p,this.className+' '+cfg.icon_checked);
    }else{
     o.checked='';
     I.cls(p,this.className+' '+cfg.icon_normal);
    }
   }
  }
 },
 callback:function(){
  this.config._callback.call(this);
 }
};
var _bindEvent=function(obj){
 var cfg=obj.config;
 var dom=obj.dom;
 dom.style.border=cfg.border;
 dom.style.backgroundColor=cfg.background;
 dom.style.color=cfg.color;
 I.listen(dom,'click',function(m,e){
  cfg.callback.call(obj);
 });
 I.listen(dom,'mouseover',function(m,e){
  m.style.border=cfg.border_hover;
  m.style.backgroundColor=cfg.background_hover;
  m.style.color=cfg.color_hover;
 });
 I.listen(dom,'mouseout',function(m,e){
  m.style.border=cfg.border;
  m.style.backgroundColor=cfg.background;
  m.style.color=cfg.color;
 });
 var ipt=I.$(dom,'tag','input')[0];
 cfg.checked=(ipt.checked?true:false)||cfg.checked;
 I.cls(dom,obj.className+' '+(cfg.checked?cfg.icon_checked:cfg.icon_normal));
 if(cfg.checked){
  ipt.checked='checked';
 }
};
var _create=function(config){
 var dom=I.insert('a',config.dom?config.dom:CFG.dom);
 if(config.label){
  dom.innerHTML=config.label;
 }
 return _render(dom,config);
};
var _render=function(dom,config){
 dom=I.$(dom);
 var obj={dom:dom,className:null,config:null};
 var cfg=I.ui.Component.initConfig(config,CFG);
 obj.config=cfg;
 var ipt=I.$(dom,'tag','input');
 if(!ipt||ipt.length<1){
  dom.innerHTML=dom.innerHTML+'<input type="radio" name="'+cfg.name+'" value="'+cfg.value+'" />';
 }
 obj.className='i-ui-Radio-'+cfg.skin;
 I.util.Skin.init(cfg.skin);
 if('a'==dom.tagName.toLowerCase()){
  if(!dom.getAttribute('href')){
   dom.href='javascript:void(0);';
  }
 }
 I.cls(dom,obj.className);
 _bindEvent(obj);
 return obj;
};
return {
 create:function(cfg){return _create(cfg);},
 render:function(dom,cfg){return _render(dom,cfg);}
};
}+'');