/**
 * I.ui.Checkbox
 * <i>复选组件</i>
 */
I.regist('ui.Checkbox',function(W,D){
  var CFG = {
    skin:'Default',
    border:'0',
    border_hover:'0',
    background:'#FBFBFB',
    background_hover:'#E9E9E9',
    color:'#333',
    color_hover:'#333',
    label:null,
    icon_normal:'fa fa-square-o',
    icon_checked:'fa fa-check-square-o',
    value:'',
    id:'',
    checked:false,
    dom:D.body,
    _callback:function(){
      var dom = this.dom;
      var cfg = this.config;
      var ipt = I.$(dom,'tag','input')[0];
      if(ipt.checked){
        ipt.checked = '';
        I.cls(I.$(dom,'tag','b')[0],cfg.icon_normal);
        cfg.checked = false;
      }else{
        ipt.checked = 'checked';
        I.cls(I.$(dom,'tag','b')[0],cfg.icon_checked);
        cfg.checked = true;
      }
    },
    callback:function(){
      this.config._callback.call(this);
    }
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
    if(cfg.checked){
      I.cls(I.$(dom,'tag','b')[0],cfg.icon_checked);
    }else{
      I.cls(I.$(dom,'tag','b')[0],cfg.icon_normal);
    }
  };
  var _create = function(config){
    var dom = I.insert('a',config.dom?config.dom:CFG.dom);
    if(config.label){
      dom.innerHTML = config.label;
    }
    return _render(dom,config);
  };
  var _render = function(dom,config){
    dom = I.$(dom);
    var obj = {dom:dom,className:null,config:null};
    var cfg = I.ui.Component.initConfig(config,CFG);
    obj.config = cfg;
    var ipt = I.$(dom,'tag','input');
    if(!ipt||ipt.length<1){
      dom.innerHTML = '<b></b><i>'+dom.innerHTML+'</i><input id="'+cfg.id+'" type="checkbox" />';
      ipt = I.$(dom,'tag','input');
    }
    ipt[0].value = cfg.value;
    obj.className = 'i-ui-Checkbox-'+cfg.skin;
    I.util.Skin.init(cfg.skin);
    if('a'==dom.tagName.toLowerCase()){
      if(!dom.getAttribute('href')){
        dom.href = 'javascript:void(0);';
      }
    }
    I.cls(dom,obj.className);
    _bindEvent(obj);
    obj.checkOn = function(){
      this.config.checked = true;
      var ipt = I.$(this.dom,'tag','input')[0];
      ipt.checked = 'checked';
      I.cls(I.$(this.dom,'tag','b')[0],this.config.icon_checked);
    };
    obj.checkOff = function(){
      this.config.checked = false;
      var ipt = I.$(this.dom,'tag','input')[0];
      ipt.checked = '';
      I.cls(I.$(this.dom,'tag','b')[0],this.config.icon_normal);
    };
    return obj;
  };
  return {
    create:function(cfg){return _create(cfg);},
    render:function(dom,cfg){return _render(dom,cfg);}
  };
}+'');