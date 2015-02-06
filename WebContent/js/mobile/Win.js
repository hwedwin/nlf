/**
 * I.mobile.Win
 * <i>移动端弹出窗</i>
 */
I.regist('mobile.Win',function(W,D){
  var CFG = {
    skin:'MobileDefault',
    mask:true,
    mask_opacity:60,
    mask_color:'#000',
    mask_close:true,
    title:'窗口',
    content:'',
    content_background:'#FFF',
    callback:function(){}
  };
  var _create = function(obj){
    var cfg = obj.config;
    if(cfg.mask){
      obj.mask = I.mobile.Mask.create({skin:cfg.skin,opacity:cfg.mask_opacity,color:cfg.mask_color});
    }
    var o = I.insert('section');
    o.innerHTML = '<header></header><article></article><footer></footer>';
    I.cls(o,obj.className);
    obj.layer = o;
    var chd = I.$(o,'*');
    obj.header = chd[0];
    obj.article = chd[1];
    obj.footer = chd[2];
    obj.header.innerHTML = cfg.title;
    obj.article.innerHTML = cfg.content;
    obj.article.style.backgroundColor = cfg.content_background;
    if(cfg.mask){
      if(cfg.mask_close){
        I.listen(obj.mask.layer,'click',function(m,e){
          obj.close();
        });
      }
    }
    obj.goTop = function(){
      obj.layer.style.top = '1em';
      obj.layer.style.bottom = 'auto';
    };
    obj.goCenter = function(){
      var r = I.region();
      var or = I.region(obj.layer);
      var y = Math.floor((r.height-or.height)/2);
      obj.layer.style.top = y+'px';
      obj.layer.style.bottom = 'auto';
    };
    obj.goBottom = function(){
      obj.layer.style.top = 'auto';
      obj.layer.style.bottom = '1em';
    };
  };

  var _prepare = function(config){
    var obj = {layer:null,mask:null,titleBar:null,closeButton:null,contentPanel:null,className:null,config:cfg};
    var cfg = I.ui.Component.initConfig(config,CFG);
    obj.config = cfg;
    obj.className = 'i-mobile-Win-'+cfg.skin;
    I.util.Skin.init(cfg.skin);
    _create(obj);
    obj.close = function(){
      this.config.callback.call(this);
      try{
        this.mask.close();
      }catch(e){}
      this.layer.parentNode.removeChild(this.layer);
    };
    return obj;
  };
  return {
    create:function(cfg){return _prepare(cfg);}
  };
}+'');