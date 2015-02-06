I.regist('mobile.Loading',function(W,D){
  var CFG = {
    skin:'MobileDefault',
    text:'加载中...',
    callback:function(){}
  };
  var _create = function(obj){
    var cfg = obj.config;
    var o = I.insert('div');
    o.innerHTML = '<div><i></i><b>'+cfg.text+'</b></div>';
    I.cls(o,obj.className);
    obj.layer = o;
  };
  var _prepare = function(config){
    var obj = {layer:null,className:null,config:null};
    var cfg = I.ui.Component.initConfig(config,CFG);
    obj.config = cfg;
    obj.className = 'i-mobile-Loading-'+cfg.skin;
    I.util.Skin.init(cfg.skin);
    _create(obj);
    obj.close = function(){
      var inst = this;
      try{
        inst.layer.parentNode.removeChild(inst.layer);
      }catch(e){
        return;
      }
      inst.config.callback.call(inst);
    };
    return obj;
  };
  return {
    create:function(cfg){return _prepare(cfg);}
  };
}+'');