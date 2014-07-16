/**
 * I.ui.Button
 * <i>按钮组件</i>
 */
I.regist('ui.Button',function(W,D){
  var CFG = {
    skin:'Default',
    border:'1px solid #DDD',
    border_hover:'1px solid #999',
    background:'#E9E9E9',
    background_hover:'#FBFBFB',
    color:'#333',
    color_hover:'#333',
    label:null,
    icon:null,
    dom:D.body,
    callback:function(){}
  };
  var _bindEvent = function(obj){
    var cfg = obj.config;
    var dom = obj.dom;
    dom.style.border = cfg.border;
    dom.style.backgroundColor = cfg.background;
    dom.style.color = cfg.color;
    I.listen(dom,'click',function(m,e){
      cfg.callback.call(obj);
    });
    I.listen(dom,'mouseover',function(m,e){
      m.style.border = cfg.border_hover;
      m.style.backgroundColor = cfg.background_hover;
      m.style.color = cfg.color_hover;
    });
    I.listen(dom,'mouseout',function(m,e){
      m.style.border = cfg.border;
      m.style.backgroundColor = cfg.background;
      m.style.color = cfg.color;
    });
  };
  var _create = function(config){
    var dom = I.insert('a',config.dom?config.dom:CFG.dom);
    return _render(dom,config);
  };
  _render = function(dom,config){
    dom = I.$(dom);
    var obj = {dom:dom,className:null,config:null};
    var cfg = I.ui.Component.initConfig(config,CFG);
    obj.config = cfg;
    if(cfg.label){
      dom.innerHTML = cfg.label;
    }
    obj.className = 'i-ui-Button-'+cfg.skin;
    if(cfg.icon){
      obj.className += ' '+cfg.icon;
    }
    I.util.Skin.init(cfg.skin);
    if('a'==dom.tagName.toLowerCase()){
      if(!dom.getAttribute('href')){
        dom.href = 'javascript:void(0);';
      }
    }
    I.cls(dom,obj.className);
    _bindEvent(obj);
    return obj;
  };
  return {
    create:function(cfg){return _create(cfg);},
    render:function(dom,cfg){return _render(dom,cfg);}
  };
}+'');