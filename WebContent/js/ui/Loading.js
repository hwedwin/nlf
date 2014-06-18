I.regist('ui.Loading',function(W,D){
  var _cssState = {};
  var CSS = function(){/*
.${skin}{position:absolute;left:0;top:0;width:0;height:${height}px;margin:0;padding:0;overflow:hidden;background-color:${bgcolor};font-size:0;border:0;}
  */}+'';
  var CFG = {
    mask:true,
    mask_color:'#FFF',
    mask_opacity:5,
    bgcolor:'#29D',
    height:1
  };
  var _css = function(cssName,cfg){
    var s = CSS;
    s = s.substr('function(){/*'.length+1);
    s = s.substr(0,s.length-'*/}'.length);
    s = s.replace(/\${skin}/g,cssName);
    s = s.replace(/\${bgcolor}/g,cfg.bgcolor);
    s = s.replace(/\${height}/g,cfg.height);
    return s;
  };
  var _create = function(config){
    var cfg = {};
    var tcfg = config;
    if(!tcfg){
      tcfg = {};
    }
    for(var i in CFG){
      cfg[i] = (undefined!=tcfg[i])?tcfg[i]:CFG[i];
    }
    var cssName = ['i-ui-Loading',cfg.bgcolor.replace(/#/g,'')].join('-');
    if(!_cssState[cssName]){
      I.style(_css(cssName,cfg));
      _cssState[cssName] = true;
    }   
    var obj = {};
    var instance = {'layer':null,'mask':null,timer:null,percent:0,over:false,config:cfg};
    if(cfg.mask){
      var r = I.region();
      var o = I.insert('div');
      I.css(o,'position:absolute;left:'+r.x+'px;top:'+r.y+'px;width:'+r.width+'px;height:'+r.height+'px;margin:0;padding:0;font-size:0;background-color:'+cfg.mask_color);
      o.innerHTML = '<iframe style="margin:0;padding:0" width="100%" height="100%" border="0" frameborder="0" scrolling="no"></iframe>';
      instance.mask = o;
      I.opacity(o,cfg.mask_opacity);
    }
    
    var q = I.insert('div');
    I.cls(q,cssName);
    instance.layer = q;
    var close = function(){
      instance.percent = 100;
      instance.over = true;
    };
    obj['close'] = function(){close();};
    obj['getInstance'] = function(){return instance;};
    obj['instance'] = instance;
    I.listen(W,'resize',function(m,e){
      try{
        if(obj.instance.mask){
          var tr = I.region();
          obj.instance.mask.style.left = tr.x+'px';
          obj.instance.mask.style.top = tr.y+'px';
          obj.instance.mask.style.width = tr.width+'px';
          obj.instance.mask.style.height = tr.height+'px';
        }
      }catch(ex){}
    });
    I.listen(W,'scroll',function(m,e){
      try{
        if(obj.instance.mask){
          var tr = I.region();
          obj.instance.mask.style.left = tr.x+'px';
          obj.instance.mask.style.top = tr.y+'px';
          obj.instance.mask.style.width = tr.width+'px';
          obj.instance.mask.style.height = tr.height+'px';
        }
      }catch(ex){}
    });
    instance.timer = W.setInterval(function(){
      if(instance.percent<100){
        instance.percent+=(100-instance.percent)/60;
      }else{
        instance.percent = 100;
      }
      var tr = I.region();
      instance.layer.style.left = tr.x+'px';
      instance.layer.style.top = tr.y+'px';
      instance.layer.style.width = Math.floor(instance.percent/100*tr.width)+'px';
      if(instance.over){
        W.clearInterval(instance.timer);
        instance.timer = null;
        W.setTimeout(function(){
          instance.layer.parentNode.removeChild(instance.layer);
          instance.mask.parentNode.removeChild(instance.mask);
        },400);
      }
    },60);
    return obj;
  };
  return {
    create:function(cfg){return _create(cfg);}
  };
}+'');