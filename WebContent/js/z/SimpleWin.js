/**
 * I.z.SimpleWin
 * <i>自适应大小的简单弹出窗口</i>
 */
I.regist('z.SimpleWin',function(W,D){
  var _cssState = {};
  var CSS = function(){/*
.${skin}{position:absolute;left:0;top:0;margin:0;padding:0;overflow:hidden;border:0;}
.${skin} i{font-weight;normal;font-style:normal;}
.${skin} .i-shadow{position:absolute;left:0;top:0;margin:0;padding:0;overflow:hidden;border:0;width:100%;height:100%;background-color:${shadow_color};border-radius:6px;-webkit-border-radius:6px;-moz-border-radius:6px;}
.${skin} .i-title{position:absolute;left:${shadow_size}px;top:${shadow_size}px;margin:0;padding:0;height:${title_height}px;line-height:${title_height}px;overflow:hidden;border:1px solid ${border_color};background-color:#FFF;font-size:1em;text-indent:0.3em;}
.${skin} a.i-close{position:absolute;right:${shadow_size}px;top:${shadow_size}px;margin:0;padding:0;height:${title_height}px;width:${title_height}px;line-height:${title_height}px;text-decoration:none;border:0;border-top:1px solid ${border_color};border-right:1px solid ${border_color};font-size:${title_height}px;text-align:center;background-color:#FFF;color:${border_color};}
.${skin} a.i-close:hover{background-color:#C72327;color:#FFF}
.${skin} .i-content{position:absolute;left:${shadow_size}px;top:0;margin:0;padding:0;overflow:auto;border:1px solid ${border_color};border-top:0;background-color:#FFF}
  */}+'';
  var CFG = {
    mask:true,
    mask_color:'#999',
    mask_opacity:10,
    space:20,
    width:0,
    height:0,
    shadow_color:'#000',
    shadow_opacity:10,
    shadow_size:6,
    border_color:'gray',
    title:'窗口',
    title_height:30,
    content:'',
    tween:'quadraticOut',
    callback:function(){}
  };
  var _css = function(cssName,cfg){
    var s = CSS;
    s = s.substr('function(){/*'.length+1);
    s = s.substr(0,s.length-'*/}'.length);
    s = s.replace(/\${skin}/g,cssName);
    s = s.replace(/\${shadow_color}/g,cfg.shadow_color);
    s = s.replace(/\${shadow_size}/g,cfg.shadow_size);
    s = s.replace(/\${border_color}/g,cfg.border_color);
    s = s.replace(/\${title_height}/g,cfg.title_height);
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
    var cssName = ['i-z-SimpleWin',cfg.shadow_color.replace(/#/g,''),cfg.border_color.replace(/#/g,'')].join('-');
    if(!_cssState[cssName]){
      I.style(_css(cssName,cfg));
      _cssState[cssName] = true;
    }
    var obj = {};
    var instance = {layer:null,mask:null,titleBar:null,closeButton:null,contentPanel:null,config:cfg};
    if(cfg.mask){
      var r = I.region();
      var o = I.insert('div');
      I.css(o,'position:absolute;left:'+r.x+'px;top:'+r.y+'px;width:'+r.width+'px;height:'+r.height+'px;margin:0;padding:0;font-size:0;background-color:'+cfg.mask_color);
      o.innerHTML = '<iframe style="margin:0;padding:0" width="100%" height="100%" border="0" frameborder="0" scrolling="no"></iframe>';
      instance.mask = o;
      I.opacity(o,cfg.mask_opacity);
    }
    
    var o = I.insert('div');
    I.cls(o,cssName);
    instance.layer = o;
    o.innerHTML = '<i class="i-shadow"></i><i class="i-title"></i><a href="javascript:void(0);" class="i-close">×</a><i class="i-content"></i>';
    I.$(o,'class','i-title')[0].innerHTML = cfg.title;
    instance['closeButton'] = I.$(o,'class','i-close')[0];
    instance['titleBar'] = I.$(o,'class','i-title')[0];
    instance['contentPanel'] = I.$(o,'class','i-content')[0];
    instance['shadow'] = I.$(o,'class','i-shadow')[0];
    instance.contentPanel.innerHTML = cfg.content;
    I.opacity(instance.shadow,cfg.shadow_opacity);
    obj['instance'] = instance;
    obj['close'] = function(){
      this.instance.layer.parentNode.removeChild(this.instance.layer);
      try{
        if(this.instance.mask){
          this.instance.mask.parentNode.removeChild(this.instance.mask);
        }
      }catch(e){}
      this.instance.config.callback.call(this);
    };
    instance['closeButton'].onclick = function(){
      obj.close();
    };
    obj['suit'] = function(){
      var inst = this.instance;
      try{
        var cf = inst.config;
        var r = I.region();
        try{
          if(inst.mask){
            inst.mask.style.left = r.x+'px';
            inst.mask.style.top = r.y+'px';
            inst.mask.style.width = r.width+'px';
            inst.mask.style.height = r.height+'px';
          }
        }catch(ex){}
        var wd = cf.width>0?(cf.width+cf.shadow_size+2):(r.width-2*cf.space);
        var ht = cf.height>0?(cf.height+cf.shadow_size+cf.title_height+2):(r.height-2*cf.space);
        inst.layer.style.left = (cf.width>0?(r.x+Math.floor((r.width-wd)/2)):(r.x+cf.space))+'px';
        inst.layer.style.top = (r.y+cf.space)+'px';
        inst.layer.style.width = wd+'px';
        inst.layer.style.height = ht+'px';
        inst.titleBar.style.width = (wd-cf.shadow_size*2-2)+'px';
        inst.contentPanel.style.width = (wd-cf.shadow_size*2-2)+'px';
        inst.contentPanel.style.height = (ht-cf.shadow_size*2-cf.title_height-2)+'px';
      }catch(e){
      }
    };
    var r = I.region();
    var wd = cfg.width>0?(cfg.width+cfg.shadow_size+2):(r.width-2*cfg.space);
    var ht = cfg.height>0?(cfg.height+cfg.shadow_size+cfg.title_height+2):(r.height-2*cfg.space);
    instance.layer.style.left = (cfg.width>0?(r.x+Math.floor((r.width-wd)/2)):(r.x+cfg.space))+'px';
    instance.layer.style.width = wd+'px';
    instance.layer.style.height = ht+'px';
    instance.layer.style.top = (0-ht)+'px';
    instance.titleBar.style.width = (wd-cfg.shadow_size*2-2)+'px';
    instance.contentPanel.style.top = (cfg.shadow_size+cfg.title_height+2)+'px';
    instance.contentPanel.style.width = (wd-cfg.shadow_size*2-2)+'px';
    instance.contentPanel.style.height = (ht-cfg.shadow_size*2-cfg.title_height-2)+'px';
    I.util.Animator.create().change(cfg.tween,function(n){
      var inst = obj.instance;
      inst.layer.style.top = n+'px';
    },function(){
      I.listen(W,'resize',function(m,e){
        obj.suit();
      });
      I.listen(W,'scroll',function(m,e){
        obj.suit();
      });
      obj.suit();
    },10,0-ht,(r.y+cfg.space));
    return obj;
  };
    
  return {
    create:function(cfg){return _create(cfg);}
  };
}+'');