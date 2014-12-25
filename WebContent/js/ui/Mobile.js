/**
 * I.ui.Mobile
 * <i>移动页面</i>
 */
I.regist('ui.Mobile',function(W,D){
  var CFG = {
    skin:'MobileDefault'
  };

  var _init = function(obj){
    var group = I.$('class','group');
    for(var i=0;i<group.length;i++){
      var g = group[i];
      var a = I.$(g,'*');
      I.listen(a,'click',function(m,e){
        var q = m.parentNode;
        var as = I.$(q,'*');
        for(var j=0;j<as.length;j++){
          I.cls(as[j],m==as[j]?'active':'');
        }
      });
    }
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