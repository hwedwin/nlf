I.regist('ui.Notify',function(W,D){
  var CFG = {
    skin:'Default',
    icon:'url(\'none\')',
    title:'通知',
    content:'',
    background:'#111',
    border:'1px solid #000',
    color:'#FFF',
    time:6000,
    callback:function(){}
  };
  var LIBS = [];
  var otherNotify = {
    loaded:false,
    instance:null,
    isSupport:function(){
      return true;
    },
    show:function(obj){
      var o = I.insert('div');
      I.cls(o,obj.className);
      obj.layer = o;
      obj.layer.style.color = obj.config.color;
      obj.layer.style.background = obj.config.background;
      obj.layer.style.border = obj.config.border;
      o.innerHTML = obj.config.content;
      var l = 0;
      obj.timer = W.setInterval(function(){
        if(l<obj.config.time){
          l+=20;
        }else{
          W.clearInterval(obj.timer);
          obj.close();
        }
      },20);
    }
  };
  var notify = {
    loaded:false,
    instance:null,
    isSupport:function(){
      if(!this.loaded){
        this.instance = W.Notifications;
        this.loaded = true;
      }
      if(!this.instance){
        return false;
      }
      var ps = this.instance.checkPermission();
      if(0==ps||1==ps){
        return true;
      }
      return false;
    },
    show:function(obj){
      var me = this;
      var inst = me.instance;
      var ps = inst.checkPermission();
      switch(ps){
        case 0:
          var m = inst.createNotification(obj.config.icon,obj.config.title,obj.config.content);
          m.show();
        break;
        case 1:
          var nobj = {layer:null,timer:null,config:null,className:null};
          var cfg = I.ui.Component.initConfig({content:'点击赋予通知权限'},CFG);
          nobj.config = cfg;
          nobj.className = obj.className;
          nobj.close = function(){
            try{
              if(this.layer){
                this.layer.parentNode.removeChild(this.layer);
              }
            }catch(e){
              return;
            }
            this.config.callback.call(this);
          };
          I.listen(nobj.layer,'click',function(m,e){
            nobj.close();
            inst.requestPermission(function(){
              var ns = inst.checkPermission();
              if(0==ns){
                var n = inst.createNotification(obj.config.icon,obj.config.title,obj.config.content);
                n.show();
              }
            });
          });
          otherNotify.show(nobj);
        break;
      }
    }
  };
  var webkitNotify = {
    loaded:false,
    instance:null,
    isSupport:function(){
      if(!this.loaded){
        this.instance = W.webkitNotifications;
        this.loaded = true;
      }
      if(!this.instance){
        return false;
      }
      var ps = this.instance.checkPermission();
      if(0==ps||1==ps){
        return true;
      }
      return false;
    },
    show:function(obj){
      var me = this;
      var inst = me.instance;
      var ps = inst.checkPermission();
      switch(ps){
        case 0:
          var m = inst.createNotification(obj.config.icon,obj.config.title,obj.config.content);
          m.show();
        break;
        case 1:
          var nobj = {layer:null,timer:null,config:null,className:null};
          var cfg = I.ui.Component.initConfig({content:'点击赋予通知权限'},CFG);
          nobj.config = cfg;
          nobj.className = obj.className;
          nobj.close = function(){
            try{
              if(this.layer){
                this.layer.parentNode.removeChild(this.layer);
              }
            }catch(e){
              return;
            }
            this.config.callback.call(this);
          };
          I.listen(nobj.layer,'click',function(m,e){
            nobj.close();
            inst.requestPermission(function(){
              var ns = inst.checkPermission();
              if(0==ns){
                var n = inst.createNotification(obj.config.icon,obj.config.title,obj.config.content);
                n.show();
              }
            });
          });
          otherNotify.show(nobj);
        break;
      }
    }
  };
  LIBS.push(notify);
  LIBS.push(webkitNotify);
  LIBS.push(otherNotify);
  var _create = function(obj){
    for(var i=0;i<LIBS.length;i++){
      var m = LIBS[i];
      if(m.isSupport()){
        m.show(obj);
        break;
      }
    }
  };
  var _prepare = function(config){
    var obj = {layer:null,timer:null,config:null,className:null};
    var cfg = I.ui.Component.initConfig(config,CFG);
    obj.config = cfg;
    obj.className = 'i-ui-Notify-'+cfg.skin;
    I.util.Skin.init(cfg.skin);
    _create(obj);
    obj.close = function(){
      try{
        if(this.layer){
          this.layer.parentNode.removeChild(this.layer);
        }
      }catch(e){
        return;
      }
      this.config.callback.call(this);
    };
    return obj;
  };
  return {
    create:function(cfg){_prepare(cfg);}
  };
}+'');