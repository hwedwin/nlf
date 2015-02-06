/**
 * I.mobile.Alert
 * <i>Alert提示框</i>
 */
I.regist('mobile.Alert',function(W,D){
  var CFG = {
    skin:'MobileDefault',
    title:'<i>提示</i>',
    content:'',
    button_label:'确定',
    button_border:'0',
    button_background:'#FFF',
    button_color:'#333',
    mask_close:false,
    callback:function(){}
  };
  var _create = function(obj){
    var cfg = obj.config;
    var html = obj.config.content;
    if(html.indexOf('<')<0){
      html = '<div style="padding:1em;text-align:center">'+html+'</div>';
    }
    obj.article.innerHTML = html;
    obj.footer.innerHTML = '<div class="grid" style="padding:.6em"></div>';
    var buttonBar = I.$(obj.footer,'*')[0];
    var btnOK = I.mobile.Button.create({
      dom:buttonBar,
      skin:cfg.skin,
      label:cfg.button_label,
      border:cfg.button_border,
      background:cfg.button_background,
      color:cfg.button_color,
      callback:function(){
        obj.close();
      }
    });
    obj.buttonOK = btnOK;
    obj.goCenter();
  };
    
  var _prepare = function(config){
    var cfg = I.ui.Component.initConfig(config,CFG);
    var obj = I.mobile.Win.create(cfg);
    _create(obj);
    return obj;
  };
  return {
    create:function(cfg){return _prepare(cfg);}
  };
}+'');