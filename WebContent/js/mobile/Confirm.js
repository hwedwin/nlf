/**
 * I.mobile.Confirm
 * <i>确定取消提示框</i>
 */
I.regist('mobile.Confirm',function(W,D){
  var CFG = {
    skin:'MobileDefault',
    title:'提示',
    content:'',
    yes_button_label:'确定',
    yes_button_border:'1px solid #0074D9',
    yes_button_background:'#0074D9',
    yes_button_color:'#FFF',
    no_button_label:'取消',
    no_button_border:'1px solid #DDD',
    no_button_background:'#E9E9E9',
    no_button_color:'#333',
    mask_close:false,
    callback:function(){},
    yes:function(){},
    no:function(){}
  };
  var _create = function(obj){
    obj.contentPanel.innerHTML = '';
    var html = obj.config.content;
    if(html.indexOf('<')<0){
      html = '<table width="100%" height="100%"><tbody><tr><td colspan="2" width="100%" height="100%" align="center" valign="middle">'+html+'</td></tr><tr><td></td><td></td></tr></tbody></table>';
    }
    var m = I.insert('div',obj.contentPanel);
    m.innerHTML = html;
    obj.contentPanel = m;
    var cfg = obj.config;
    var td = I.$(obj.contentPanel,'tag','td');
    var btnNo = I.mobile.Button.create({
      dom:td[td.length-2],
      skin:cfg.skin,
      label:cfg.no_button_label,
      border:cfg.no_button_border,
      background:cfg.no_button_background,
      color:cfg.no_button_color,
      callback:function(){
        obj.close();
        obj.config.no.call(obj);
      }
    });
    obj.buttonNo = btnNo;
    
    var btnYes = I.mobile.Button.create({
      dom:td[td.length-1],
      skin:cfg.skin,
      label:cfg.yes_button_label,
      border:cfg.yes_button_border,
      background:cfg.yes_button_background,
      color:cfg.yes_button_color,
      callback:function(){
        obj.close();
        obj.config.yes.call(obj);
      }
    });
    obj.buttonYes = btnYes;
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