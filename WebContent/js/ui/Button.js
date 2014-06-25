/**
 * I.ui.Button
 * <i>按钮组件</i>
 */
I.regist('ui.Button',function(W,D){
  var CFG = {
    skin:'Default',
    label:'确定',
    icon:null,
    dom:D.body,
    callback:function(){}
  };
  var _create = function(obj){
    var cfg = obj.config;
    var o = I.insert('a',cfg.dom);
    o.innerHTML = cfg.label;
    o.href = 'javascript:void(0);';
    I.listen(o,'click',function(m,e){
      cfg.callback.call(obj);
    });
    I.cls(o,obj.className);
    obj.layer = o;
    return obj;
  };
    
  var _prepare = function(config){
    var obj = {layer:null,className:null,config:null};
    var cfg = I.ui.Component.initConfig(config,CFG);
    obj.config = cfg;
    if(cfg.icon){
      obj.className = 'i-ui-Button-'+cfg.skin+' fa fa-'+cfg.icon;
    }else{
      obj.className = 'i-ui-Button-'+cfg.skin;
    }
    I.util.Skin.init(cfg.skin);
    _create(obj);
    return obj;
  };
  return {
    create:function(cfg){return _prepare(cfg);}
  };
}+'');