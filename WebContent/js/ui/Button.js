I.regist('ui.Button',function(W,D){
var CFG={
 skin:'Default',
 background:'#FBFBFB',
 border:'1px solid #CCC',
 color:'#333',
 round:4,
 background_hover:'#E6E6E6',
 border_hover:'1px solid #ADADAD',
 color_hover:'#333',
 round_hover:4,
 label:null,
 icon:null,
 dom:D.body,
 callback:function(){}
};
var _bindEvent=function(obj){
 var cfg=obj.config,dom=obj.dom;
 I.util.Boost.addStyle(dom,'border:'+cfg.border+';background:'+cfg.background+';color:'+cfg.color);
 I.util.Boost.round(dom,cfg.round);
 I.listen(dom,'click',function(m,e){
  cfg.callback.call(obj);
 });
 I.listen(dom,'mouseover',function(m,e){
  I.util.Boost.addStyle(dom,'border:'+cfg.border_hover+';background:'+cfg.background_hover+';color:'+cfg.color_hover);
  I.util.Boost.round(dom,cfg.round_hover);
 });
 I.listen(dom,'mouseout',function(m,e){
  I.util.Boost.addStyle(dom,'border:'+cfg.border+';background:'+cfg.background+';color:'+cfg.color);
  I.util.Boost.round(dom,cfg.round);
 });
};
var _create=function(config){
 return _render(I.insert('a',config.dom?config.dom:CFG.dom),config);
};
var _renderOne=function(dom,config){
 dom=I.$(dom);
 var obj={dom:dom,className:null,config:null};
 var cfg=I.ui.Component.initConfig(config,CFG);
 obj.config=cfg;
 if(cfg.label) dom.innerHTML=cfg.label;
 obj.className='i-ui-Button-'+cfg.skin;
 if(cfg.icon) obj.className+=' '+cfg.icon;
 I.util.Skin.init(cfg.skin);
 if('a'==dom.tagName.toLowerCase()){
  if(!dom.getAttribute('href')) dom.href='javascript:void(0);';
 }
 I.cls(dom,obj.className);
 _bindEvent(obj);
 return obj;
};
var _render=function(dom,config){
 var who;
 I.each(dom,function(m,i){
  who=_renderOne(m,config);
 });
 return who;
}
return{
 create:function(cfg){return _create(cfg);},
 render:function(dom,cfg){return _render(dom,cfg);}
};
}+'');