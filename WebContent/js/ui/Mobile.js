/**
 * I.ui.Mobile
 * <i>移动页面</i>
 */
I.regist('ui.Mobile',function(W,D){
  var CFG = {
    skin:'MobileDefault'
  };

  var _init = function(obj){
    var a = I.$('tag','a');
    for(var i=0;i<a.length;i++){
      var o = a[i];
      I.listen(o,'click',function(m,e){
        var da = m.getAttribute('data-aside');
        if(!da){
          return;
        }
        var aside = I.$(da);
        if(!aside){
          return;
        }
        var cls = aside.getAttribute('class')||aside.className;
        if(cls.indexOf('active')>-1){
          I.cls(aside,I.trim(cls.replace('active','')));
        }else{
          I.cls(aside,'active '+cls);
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