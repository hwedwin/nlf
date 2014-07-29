/**
 * I.ui.Form
 * <i>表单组件</i>
 */
I.regist('ui.Form',function(W,D){
  var CFG = {
    skin:'Default',
    border:'1px solid #DDD',
    border_hover:'1px solid #BBB',
    background:'#FFF',
    label_width:20,
    background_hover:'#FBFBFB',
    dom:D.body
  };
  var _renderForm = function(obj){
    var cfg = obj.config;
    var dom = obj.dom;
    dom.style.border = cfg.border;
    dom.style.background = cfg.background;
    I.listen(dom,'mouseover',function(m,e){
      dom.style.border = cfg.border_hover;
      dom.style.backgroundColor = cfg.background_hover;
    });
    I.listen(dom,'mouseout',function(m,e){
      dom.style.border = cfg.border;
      dom.style.backgroundColor = cfg.background;
    });
    var ul = I.$(dom,'*');
    if(!ul){
      return;
    }
    if(ul.length<1){
      return;
    }
    for(var i=0;i<ul.length;i++){
      if('ul'!=ul[i].tagName.toLowerCase()){
        continue;
      }
      var li = I.$(ul[i],'*');
      if(!li){
        continue;
      }
      if(li.length<1){
        continue;
      }
      var width = 100;
      var auto = 0;
      for(var j=0;j<li.length;j++){
        var m = li[j];
        var w = m.getAttribute('data-width');
        if(w){
          width -= parseInt(w,10);
        }else{
          auto++;
        }
      }
      if(auto>0){
        width /= auto;
      }
      for(var j=0;j<li.length;j++){
        var m = li[j];
        var w = m.getAttribute('data-width');
        if(w){
          m.style.width = parseInt(w,10)+'%';
        }else{
          m.style.width = width+'%';
        }
        var chd = I.$(m,'*');
        if(chd.length<1){
          m.style.textAlign = 'right';
          if(''==I.trim(m.innerHTML)){
            m.innerHTML = '&nbsp;';
          }
        }else if(chd.length==1){
          chd[0].style.width = '100%';
        }
      }
    }
  };
  var _render = function(dom,config){
    dom = I.$(dom);
    var obj = {dom:dom,className:null,config:null};
    var cfg = I.ui.Component.initConfig(config,CFG);
    obj.config = cfg;
    obj.className = 'i-ui-Form-'+cfg.skin;
    I.util.Skin.init(cfg.skin);
    I.cls(dom,obj.className);
    _renderForm(obj);
    return obj;
  };
  return {
    render:function(dom,cfg){return _render(dom,cfg);}
  };
}+'');