/**
 * I.mobile.Mask
 * <i>遮罩</i>
 */
I.regist('mobile.Mask',function(W,D){
  var CFG = {
    skin:'MobileDefault',
    color:'#000',
    opacity:60,
    callback:function(){}
  };
  var _create = function(obj){
    var o = I.insert('div');
    I.cls(o,obj.className);
    I.opacity(o,obj.config.opacity);
    o.style.backgroundColor = obj.config.color;
    obj.layer = o;
  };
  var _prepare = function(config){
    var obj = {layer:null,config:null,className:null,suit:function(){}};
    var cfg = I.ui.Component.initConfig(config,CFG);
    obj.config = cfg;
    obj.className = 'i-mobile-Mask-'+cfg.skin;
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