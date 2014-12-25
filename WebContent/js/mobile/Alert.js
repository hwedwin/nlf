/**
 * I.mobile.Alert
 * <i>Alert提示框</i>
 */
I.regist('mobile.Alert',function(W,D){
  var CFG = {
    skin:'MobileDefault',
    title:'提示',
    content:'',
    button_label:'确定',
    button_border:'1px solid #DDD',
    button_background:'#E9E9E9',
    button_color:'#333',
    mask_close:false,
    callback:function(){}
  };
  var _create = function(obj){
    obj.contentPanel.innerHTML = '';
    var html = obj.config.content;
    if(html.indexOf('<')<0){
      html = '<table style="margin-bottom:0.5em;" width="100%" height="100%"><tbody><tr><td width="100%" height="100%" align="center" valign="middle">'+html+'</td></tr></tbody></table>';
    }
    var m = I.insert('div',obj.contentPanel);
    m.innerHTML = html;
    obj.contentPanel = m;
    var cfg = obj.config;
    var btnOK = I.mobile.Button.create({
      dom:obj.contentPanel,
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
    obj.suit();
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