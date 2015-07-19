I.regist('ui.Mobile',function(W,D){
var CFG={
 skin:'MobileDefault',
 mask_opacity:40,
 mask_color:'#000',
};
var _init=function(obj){
 var i,j,k,cfg=obj.config;
 var gp=I.$('class','group');
 if(gp&&gp.length>0){
  for(i=0,j=gp.length;i<j;i++){
   var g=gp[i];
   var a=I.$(g,'*');
   I.listen(a,'click',function(m,e){
    var q=m.parentNode;
    var as=I.$(q,'*');
    I.each(as,function(n,k){
     I.cls(n,m==n?'active':'');
    });
   });
  }
 }
 var as=I.$('class','a');
 if(as&&as.length>0){
  I.listen(as,'click',function(m,e){
   var aside=m.getAttribute('data-aside');
   if(aside){
    var ao=I.$(aside);
    if(ao){
     if('1'==ao.getAttribute('data-show')){
      ao.style.display='none';
      ao.setAttribute('data-show','0');
      try{obj.mask.close();}catch(e){}
     }else{
      obj.mask = I.mobile.Mask.create({skin:cfg.skin,opacity:cfg.mask_opacity,color:cfg.mask_color});
      I.listen(obj.mask.layer,'click',function(m,e){
       ao.style.display='none';
       ao.setAttribute('data-show','0');
       obj.mask.close();
      });
      var pos=ao.getAttribute('data-at');
      I.util.Boost.addStyle(ao,'display:block;left:'+('right'==pos?'auto':'0')+';right:'+('right'==pos?'0':'auto')+';');
      ao.setAttribute('data-show','1');
     }
    }
    return;
   }
   var href=m.getAttribute('data-href');
   if(href) self.location=href;
  });
 }
};
var _render=function(config){
 var obj={config:null};
 var cfg=I.ui.Component.initConfig(config,CFG);
 obj.config=cfg;
 I.util.Skin.init(cfg.skin);
 _init(obj);
 return obj;
};
return {
 render:function(cfg){return _render(cfg);}
};
}+'');