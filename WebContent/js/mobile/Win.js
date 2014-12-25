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
    title_background:'#FFF',
    title_color:'#000',
    content:'',
    content_background:'#FFF',
    callback:function(){}
  };
  var _create = function(obj){
    var cfg = obj.config;
    if(cfg.mask){
      obj.mask = I.mobile.Mask.create({skin:cfg.skin,opacity:cfg.mask_opacity,color:cfg.mask_color});
    }
    var o = I.insert('div');
    o.innerHTML = '<i class="i-title"></i><i class="i-content"></i>';
    I.cls(o,obj.className);
    obj.layer = o;
    obj.titleBar = I.$(o,'class','i-title')[0];
    obj.line = I.$(o,'class','i-line')[0];
    obj.contentPanel = I.$(o,'class','i-content')[0];
    obj.titleBar.innerHTML = cfg.title+'<hr class="i-line" />';
    obj.contentPanel.innerHTML = cfg.content;
    
    obj.titleBar.style.backgroundColor = cfg.title_background;
    obj.titleBar.style.color = cfg.title_color;
    obj.contentPanel.style.backgroundColor = cfg.content_background;
    if(cfg.mask){
      if(cfg.mask_close){
        I.listen(obj.mask.layer,'click',function(m,e){
          obj.close();
        });
      }
    }
    obj.suit = function(){
      var r = I.region();
      var or = I.region(obj.layer);
      var y = Math.floor((r.height-or.height)/2);
      obj.layer.style.top = y+'px';
    };
    obj.suit();
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