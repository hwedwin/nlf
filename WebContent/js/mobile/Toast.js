/**
 * I.mobile.Toast
 * <i>提示条</i>
 */
I.regist('mobile.Toast',function(W,D){
  var TIME = {
    '0':8000,
    '1':3000,
    '2':1000
  };
  var CFG = {
    skin:'MobileDefault',
    background:'#111',
    color:'#FFF',
    border:'0',
    msg:'',
    type:1,
    callback:function(){}
  };
  var _create = function(obj){
    var cfg = obj.config;
    var o = I.insert('div');
    I.cls(o,obj.className);
    obj.layer = o;
    o.innerHTML = '<span>'+cfg.msg+'</span>';
    obj.span = I.$(o,'*')[0];
    obj.span.style.backgroundColor = cfg.background;
    obj.span.style.color = cfg.color;
    obj.span.style.border = cfg.border;
    var op = 0;
    I.opacity(o,op);
    obj.timer = W.setInterval(function(){
      if(op<100){
        op+=2;
        I.opacity(o,op);
      }else{
        I.opacity(o,100);
        W.clearInterval(obj.timer);
        obj.timer = W.setTimeout(function(){
          obj.close();
        },TIME[cfg.type]);
      }
    },20);
  };
  var _prepare = function(config){
    var obj = {layer:null,timer:null,config:null,className:null};
    var cfg = I.ui.Component.initConfig(config,CFG);
    obj.config = cfg;
    obj.className = 'i-mobile-Toast-'+cfg.skin;
    I.util.Skin.init(cfg.skin);
    _create(obj);
    obj.close = function(){
      this.config.callback.call(this);
      this.layer.parentNode.removeChild(this.layer);
    };
    return obj;
  };
  return {
    create:function(cfg){return _prepare(cfg);}
  };
}+'');