/**
 * I.ui.Mobile
 * <i>移动页面</i>
 */
I.regist('ui.Mobile',function(W,D){
  var CFG = {
    skin:'MobileDefault'
  };

  var _init = function(obj){
    //总需要做点什么
  };
  var _render = function(config){
    var obj = {config:null};
    var cfg = I.ui.Component.initConfig(config,CFG);
    obj.config = cfg;
    I.util.Skin.init(cfg.skin);
    _init(obj);
    return obj;
  };
  return {
    render:function(cfg){return _render(cfg);}
  };
}+'');