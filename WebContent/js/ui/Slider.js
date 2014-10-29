/**
 * I.ui.Slider
 * <i>滑块组件</i>
 */
I.regist('ui.Slider',function(W,D){
  var CFG = {
    skin:'Default',
    border:'1px solid #DDD',
    background:'#E9E9E9',
    width:-1,
    height:4,
    block_width:8,
    block_height:18,
    block_background:'#FFF',
    block_border:'1px solid #999',
    bar_background:'#0074D9',
    bar_border:'1px solid #0074D9',
    min:0,
    max:100,
    dom:D.body,
    callback:function(){}
  };
  var _C = null;
  var _U = null;
  var _POOL = {};
  var _bindEvent = function(obj){
    var uuid = I.util.UUID.next();
    _POOL[uuid] = obj;
    var cfg = obj.config;
    var dom = obj.dom;
    dom.style.border = cfg.border;
    dom.style.backgroundColor = cfg.background;
    if(cfg.width>0){
      dom.style.width = cfg.width+'px';
    }
    dom.style.height = cfg.height+'px';
    dom.ondragstart = function(){return false;};
    dom.onselectstart = function(){return false;};
    var u = I.$(dom,'tag','b')[0];
    u.style.height = cfg.height + 'px';
    u.style.backgroundColor = cfg.bar_background;
    u.style.border = cfg.bar_border;
    u.ondragstart = function(){return false;};
    u.onselectstart = function(){return false;};
    var i = I.$(dom,'tag','i')[0];
    i.setAttribute('data-uuid',uuid);
    i.style.width = cfg.block_width + 'px';
    i.style.height = cfg.block_height + 'px';
    i.style.top = Math.floor(0-(cfg.block_height-cfg.height)/2)+'px';
    i.style.backgroundColor = cfg.block_background;
    i.style.border = cfg.block_border;
    i.ondragstart = function(){return false;};
    i.onselectstart = function(){return false;};
    I.listen(i,'mousedown',function(m,e){
      _C = m;
      _U = I.$(m.parentNode,'tag','b')[0];
      if(D.all) _C.setCapture();
      var b = I.region(dom);
      var r = I.region(_C);
      _C.setAttribute('X',r.x-b.x-e.clientX);
      _C.style.left = (r.x-b.x) + 'px';
      if(e.stopPropagation) e.stopPropagation();
      else e.cancelBubble = true;
      if(e.preventDefault) e.preventDefault();
      else e.returnValue = false;
      I.listen(D,'mousemove',function(m,e1){
        if(!_C||!_U){
          return;
        }
        var x = e1.clientX + parseInt(i.getAttribute('X'),10);
        if(x<0){
          x = 0;
        }
        if(x>b.width-r.width){
          x = b.width-r.width;
        }
        _C.style.left = x + 'px';
        _U.style.width = x+Math.floor(r.width/2) + 'px';
        var ro = _POOL[_C.getAttribute('data-uuid')];
        if(ro){
          var c = ro.config;
          ro.value = Math.floor(c.min+(c.max-c.min)*x/(b.width-r.width));
          c.callback.call(ro);
        }
        if(e1.stopPropagation) e1.stopPropagation();
        else e1.cancelBubble = true;
        if(e1.preventDefault) e1.preventDefault();
        else e1.returnValue = false;
      });
      I.listen(D,'mouseup',function(m,e2){
        if(!_C||!_U){
          return;
        }
        if(D.all) _C.releaseCapture();
        _C = null;
        _U = null;
        if(e2.stopPropagation) e2.stopPropagation();
        else e2.cancelBubble = true;
        if(e2.preventDefault) e2.preventDefault();
        else e2.returnValue = false;
      });
    });
  };
  var _create = function(config){
    var div = I.insert('div',config.dom?config.dom:CFG.dom);
    div.innerHTML = '<b></b><i></i>';
    return _render(div,config);
  };
  var _render = function(dom,config){
    dom = I.$(dom);
    var obj = {dom:dom,className:null,config:null,value:0};
    var cfg = I.ui.Component.initConfig(config,CFG);
    obj.config = cfg;
    obj.className = 'i-ui-Slider-'+cfg.skin;
    I.util.Skin.init(cfg.skin);
    I.cls(dom,obj.className);
    _bindEvent(obj);
    obj['setValue'] = function(num){
      var c = this.config;
      if(num<c.min){
        num = c.min;
      }
      if(num>c.max){
        num = c.max;
      }
      this.value = num;
      var b = I.region(this.dom);
      var i = I.$(this.dom,'tag','i')[0];
      var u = I.$(this.dom,'tag','b')[0];
      var r = I.region(i);
      var x = ((num-c.min)/(c.max-c.min)*(b.width-r.width));
      i.style.left = x+'px';
      u.style.width = (x+Math.floor(r.width/2))+'px';
      c.callback.call(this);
    };
    if(cfg.value){
      obj.setValue(cfg.value);
    }
    return obj;
  };
  return {
    create:function(cfg){return _create(cfg);},
    render:function(dom,cfg){return _render(dom,cfg);}
  };
}+'');