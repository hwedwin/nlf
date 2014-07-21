/**
 * I.z.Win
 * <i>弹出窗</i>
 */
I.regist('z.Win',function(W,D){
  var CFG = {
    skin:'Default',
    mask:true,
    mask_opacity:10,
    mask_color:'#FFF',
    width:400,
    height:250,
    shadow_size:6,
    shadow_color:'#000',
    shadow_opacity:10,
    title:'窗口',
    title_height:30,
    title_background:'gray',
    title_color:'#FFF',
    title_border_color:'gray',
    close_background:'gray',
    close_color:'#FFF',
    close_border_color:'gray',
    content:'',
    content_border_color:'gray',
    content_background:'#FFF',
    callback:function(){}
  };
  var _create = function(obj){
    var cfg = obj.config;
    if(cfg.mask){
      obj.mask = I.ui.Mask.create({skin:cfg.skin,opacity:cfg.mask_opacity,color:cfg.mask_color});
    }
    var o = I.insert('div');
    o.innerHTML = '<i class="i-shadow"></i><i class="i-title"></i><a href="javascript:void(0);" class="i-close">×</a><i class="i-content"></i>';
    I.cls(o,obj.className);
    obj.layer = o;
    obj.closeButton = I.$(o,'class','i-close')[0];
    obj.titleBar = I.$(o,'class','i-title')[0];
    obj.contentPanel = I.$(o,'class','i-content')[0];
    obj.shadow = I.$(o,'class','i-shadow')[0];
    obj.shadow.style.backgroundColor = cfg.shadow_color;
    I.opacity(obj.shadow,cfg.shadow_opacity);
    obj.titleBar.innerHTML = cfg.title;
    obj.contentPanel.innerHTML = cfg.content;
    I.listen(obj.closeButton,'click',function(m,e){
      obj.close();
    });
    var r = I.region();
    var wd = cfg.width+cfg.shadow_size*2+2;
    var ht = cfg.height+cfg.shadow_size*2+cfg.title_height+2;
    obj.layer.style.left = (r.x+Math.floor((r.width-wd)/2))+'px';
    obj.layer.style.top = (r.y+Math.floor((r.height-ht)/2))+'px';
    obj.layer.style.width = wd+'px';
    obj.layer.style.height = ht+'px';
    obj.titleBar.style.backgroundColor = cfg.title_background;
    obj.titleBar.style.color = cfg.title_color;
    obj.titleBar.style.border = '1px solid '+cfg.title_border_color;
    obj.titleBar.style.left = cfg.shadow_size+'px';
    obj.titleBar.style.top = cfg.shadow_size+'px';
    obj.titleBar.style.width = cfg.width+'px';
    obj.titleBar.style.height = cfg.title_height+'px';
    obj.titleBar.style.lineHeight = cfg.title_height+'px';
    
    obj.closeButton.style.backgroundColor = cfg.title.close_background;
    obj.closeButton.style.color = cfg.close_color;
    obj.closeButton.style.right = (cfg.shadow_size+1)+'px';
    obj.closeButton.style.top = (cfg.shadow_size+1)+'px';
    obj.closeButton.style.width = (cfg.title_height-1)+'px';
    obj.closeButton.style.height = (cfg.title_height-1)+'px';
    obj.closeButton.style.lineHeight = (cfg.title_height-1)+'px';
    obj.contentPanel.style.left = cfg.shadow_size+'px';
    obj.contentPanel.style.top = (cfg.shadow_size+cfg.title_height+2)+'px';
    obj.contentPanel.style.width = cfg.width+'px';
    obj.contentPanel.style.height = cfg.height+'px';
    obj.contentPanel.style.border = '1px solid '+cfg.content_border_color;
    obj.contentPanel.style.borderTop = '0';
    obj.contentPanel.style.backgroundColor = cfg.content_background;
    I.util.Drager.drag(obj.titleBar,obj.layer);
  };

  var _prepare = function(config){
    var obj = {layer:null,mask:null,titleBar:null,closeButton:null,contentPanel:null,className:null,config:cfg};
    var cfg = I.ui.Component.initConfig(config,CFG);
    obj.config = cfg;
    obj.className = 'i-z-Win-'+cfg.skin;
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