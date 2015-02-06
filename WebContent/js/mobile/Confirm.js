/**
 * I.mobile.Confirm
 * <i>确定取消提示框</i>
 */
I.regist('mobile.Confirm',function(W,D){
  var CFG = {
    skin:'MobileDefault',
    title:'<i>提示</i>',
    content:'',
    yes_button_label:'确定',
    yes_button_border:'0',
    yes_button_background:'#FFF',
    yes_button_color:'#333',
    no_button_label:'取消',
    no_button_border:'0',
    no_button_background:'#FFF',
    no_button_color:'#333',
    split_color:'#DFDFDF',
    mask_close:false,
    callback:function(){},
    yes:function(){},
    no:function(){}
  };
  var _create = function(obj){
    var cfg = obj.config;
    obj.article.innerHTML = '';
    var html = obj.config.content;
    if(html.indexOf('<')<0){
      html = '<div style="padding:1em;text-align:center">'+html+'</div>';
    }
    obj.article.innerHTML = html;
    obj.footer.innerHTML = '<div class="grid" style="padding:.6em"></div>';
    var buttonBar = I.$(obj.footer,'*')[0];
    var btnNo = I.mobile.Button.create({
      dom:buttonBar,
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
    var div = I.insert('div',buttonBar);
    I.cls(div,'col0');
    div.style.width = '1px';
    div.style.height = '2em';
    div.style.backgroundColor = cfg.split_color;
    var btnYes = I.mobile.Button.create({
      dom:buttonBar,
      skin:cfg.skin,
      label:cfg.yes_button_label,
      border:cfg.yes_button_border,
      background:cfg.yes_button_background,
      color:cfg.yes_button_color,
      callback:function(){
        obj.config.yes.call(obj);
      }
    });
    obj.buttonYes = btnYes;
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