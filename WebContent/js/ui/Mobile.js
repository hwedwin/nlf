/**
 * I.ui.Mobile
 * <i>移动页面</i>
 */
I.regist('ui.Mobile',function(W,D){
  var CFG = {
    skin:'MobileDefault',
    scroll:false
  };
  var _init = function(obj){
    var i,j,k;
    if(obj.config.scroll){
      var ats = I.$('tag','article');
      if(ats&&ats.length>0){
        for(i=0,j=ats.length;i<j;i++){
          I.mobile.Scroll.create(ats[i]);
          break;
        }
      }
    }
    var gp = I.$('class','group');
    if(gp&&gp.length>0){
      for(i=0,j=gp.length;i<j;i++){
        var g = gp[i];
        var a = I.$(g,'*');
        I.listen(a,'click',function(m,e){
          var q = m.parentNode;
          var as = I.$(q,'*');
          I.each(as,function(n,k){
            I.cls(n,m==n?'active':'');
          });
        });
      }
    }
    var as = I.$('class','a');
    if(as&&as.length>0){
      I.listen(as,'click',function(m,e){
        var href = m.getAttribute('data-href');
        if(href){
          self.location = href;
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