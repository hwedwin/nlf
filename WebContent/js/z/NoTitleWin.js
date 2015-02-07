I.regist('z.NoTitleWin',function(W,D){
var CFG={
 skin:'Default',
 mask:true,
 mask_opacity:10,
 mask_color:'#FFF',
 width:400,
 height:250,
 shadow:'#333 0px 0px 8px',
 round:6,
 title_height:0,
 content:'',
 content_background:'#FFF',
 footer_height:0,
 callback:function(){}
};
var _create=function(obj){
 var cfg=obj.config;
 obj.contentPanel.innerHTML=cfg.content;
};
var _prepare=function(config){
 var cfg=I.ui.Component.initConfig(config,CFG);
 var obj=I.z.Win.create(cfg);
 _create(obj);
 return obj;
};
return{
 create:function(cfg){return _prepare(cfg);}
};
}+'');