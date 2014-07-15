/**
 * I.ui.Loading
 * <i>加载进度条</i>
 */
I.regist('ui.Loading',function(W,D){
  var CFG = {
    skin:'Default',
    mask:true,
    color:null,
    callback:function(){}
  };
  var _create = function(obj){
    var cfg = obj.config;
    if(cfg.mask){
      obj.mask = I.ui.Mask.create({skin:cfg.skin});
    }
    var o = I.insert('div');
    I.cls(o,obj.className);
    obj.layer = o;
    if(obj.config.color){
      obj.layer.style.backgroundColor = obj.config.color;
    }
    obj.timer = W.setInterval(function(){
      var inst = obj;
      if(inst.percent<100){
        inst.percent+=(100-inst.percent)/60;
      }else{
        inst.percent = 100;
      }
      var r = I.region();
      inst.layer.style.left = r.x+'px';
      inst.layer.style.top = r.y+'px';
      inst.layer.style.width = Math.floor(inst.percent/100*r.width)+'px';
      if(inst.over){
        W.clearInterval(inst.timer);
        inst.timer = null;
        try{
          inst.close();
        }catch(e){}
      }
    },60);
  };
  var _prepare = function(config){
    var obj = {layer:null,mask:null,timer:null,percent:0,over:false,className:null,config:null};
    var cfg = I.ui.Component.initConfig(config,CFG);
    obj.config = cfg;
    obj.className = 'i-ui-Loading-'+cfg.skin;
    I.util.Skin.init(cfg.skin);
    _create(obj);
    obj.close = function(){
      var inst = this;
      inst.percent = 100;
      inst.over = true;
      I.delay(400,function(){
        try{
          inst.mask.close();
        }catch(e){}
        try{
          inst.layer.parentNode.removeChild(inst.layer);
        }catch(e){
          return;
        }
        inst.config.callback.call(inst);
      });
    };
    return obj;
  };
  return {
    create:function(cfg){return _prepare(cfg);}
  };
}+'');