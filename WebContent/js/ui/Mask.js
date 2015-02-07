/**
 * I.ui.Mask
 * <i>遮罩</i>
 */
I.regist('ui.Mask',function(W,D){
 var CFG = {
  skin:'Default',
  color:'#FFF',
  opacity:10,
  callback:function(){}
 };
 var _create = function(obj){
  var cfg = obj.config;
  var o = I.insert('div');
  I.cls(o,obj.className);
  I.opacity(o,cfg.opacity);
  o.style.background = cfg.color;
  obj.layer = o;
  obj.suit = function(){
   try{
    var r = I.region();
    I.util.Boost.addStyle(this.layer,'left:'+r.x+'px;top:'+r.y+'px;width:'+r.width+'px;height:'+r.height+'px;');
   }catch(e){}
  };
  I.listen(W,'resize',function(m,e){
   obj.suit();
  });
  I.listen(W,'scroll',function(m,e){
   obj.suit();
  });
  obj.suit();
 };
 var _prepare = function(config){
  var obj = {layer:null,config:null,className:null,suit:function(){}};
  var cfg = I.ui.Component.initConfig(config,CFG);
  obj.config = cfg;
  obj.className = 'i-ui-Mask-'+cfg.skin;
  I.util.Skin.init(cfg.skin);
  _create(obj);
  obj.close = function(){
   this.config.callback.call(this);
   this.layer.parentNode.removeChild(this.layer);
  };
  return obj;
 };
 return {
  create:function(cfg){return _prepare(cfg);}
 };
}+'');