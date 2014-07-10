/**
 * I.z.NoTitleWin
 * <i>自适应大小的简单弹出窗口</i>
 */
I.regist('z.NoTitleWin',function(W,D){
  var CFG = {
    skin:'Default',
    mask:true,
    space:20,
    width:0,
    height:0,
    shadow_size:6,
    content:'',
    tween:'quadraticOut',
    callback:function(){}
  };
  var _create = function(obj){
    var cfg = obj.config;
    if(cfg.mask){
      obj.mask = I.ui.Mask.create({skin:cfg.skin});
      obj.mask.layer.style.backgroundColor = '#000';
      I.opacity(obj.mask.layer,40);
    }
    var o = I.insert('div');
    o.innerHTML = '<i class="i-shadow"></i><i class="i-content"></i>';
    I.cls(o,obj.className);
    obj.layer = o;
    obj.contentPanel = I.$(o,'class','i-content')[0];
    obj.shadow = I.$(o,'class','i-shadow')[0];
    obj.contentPanel.innerHTML = cfg.content;
    obj.contentPanel.style.borderTop = '1px solid gray';
    I.opacity(obj.shadow,cfg.shadow_opacity);
    obj.suit = function(){
      var inst = this;
      try{
        var cf = inst.config;
        var r = I.region();
        var wd = cf.width>0?(cf.width+cf.shadow_size+2):(r.width-2*cf.space);
        var ht = cf.height>0?(cf.height+cf.shadow_size+2):(r.height-2*cf.space);
        inst.layer.style.left = (cf.width>0?(r.x+Math.floor((r.width-wd)/2)):(r.x+cf.space))+'px';
        inst.layer.style.top = (cf.height>0?(r.y+Math.floor((r.height-ht)/2)):(r.y+cf.space))+'px';
        inst.layer.style.width = wd+'px';
        inst.layer.style.height = ht+'px';
        inst.contentPanel.style.width = (wd-cf.shadow_size*2-2)+'px';
        inst.contentPanel.style.height = (ht-cf.shadow_size*2-2)+'px';
      }catch(e){
      }
    };
    var r = I.region();
    var wd = cfg.width>0?(cfg.width+cfg.shadow_size*2+2):(r.width-2*cfg.space);
    var ht = cfg.height>0?(cfg.height+cfg.shadow_size*2+2):(r.height-2*cfg.space);
    obj.layer.style.left = (cfg.width>0?(r.x+Math.floor((r.width-wd)/2)):(r.x+cfg.space))+'px';
    obj.layer.style.width = wd+'px';
    obj.layer.style.height = ht+'px';
    obj.layer.style.top = (0-ht)+'px';
    obj.contentPanel.style.top = (cfg.shadow_size+1)+'px';
    obj.contentPanel.style.left = cfg.shadow_size+'px';
    obj.contentPanel.style.width = (wd-cfg.shadow_size*2-2)+'px';
    obj.contentPanel.style.height = (ht-cfg.shadow_size*2-2)+'px';
    I.util.Animator.create().change(cfg.tween,function(n){
      obj.layer.style.top = n+'px';
    },function(){
      I.listen(W,'resize',function(m,e){
        obj.suit();
      });
      I.listen(W,'scroll',function(m,e){
        obj.suit();
      });
      obj.suit();
    },10,0-ht,(r.y+Math.floor((r.height-ht)/2)));
  };

  var _prepare = function(config){
    var obj = {layer:null,mask:null,contentPanel:null,className:null,config:cfg};
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