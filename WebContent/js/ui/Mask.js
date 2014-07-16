/**
 * I.ui.Mask
 * <i>遮罩</i>
 */
I.regist('ui.Mask',function(W,D){
  var CFG = {
    skin:'Default',
    color:'#FFF',
    opacity:10,
    callback:function(){}
  };
  var _create = function(obj){
    var o = I.insert('div');
    I.cls(o,obj.className);
    I.opacity(o,obj.config.opacity);
    o.style.backgroundColor = obj.config.color;
    obj.layer = o;
    obj.suit = function(){
      try{
        var r = I.region();
        this.layer.style.left = r.x+'px';
        this.layer.style.top = r.y+'px';
        this.layer.style.width = r.width+'px';
        this.layer.style.height = r.height+'px';
      }catch(e){}
    };
    I.listen(W,'resize',function(m,e){
      obj.suit();
    });
    I.listen(W,'scroll',function(m,e){
      obj.suit();
    });
    obj.suit();
  };
  var _prepare = function(config){
    var obj = {layer:null,config:null,className:null,suit:function(){}};
    var cfg = I.ui.Component.initConfig(config,CFG);
    obj.config = cfg;
    obj.className = 'i-ui-Mask-'+cfg.skin;
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