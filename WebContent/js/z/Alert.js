/**
 * I.z.Alert
 * <i>Alert提示框</i>
 */
I.regist('z.Alert',function(W,D){
  var CFG = {
   skin:'Default',
   mask:true,
   mask_opacity:10,
   mask_color:'#FFF',
   width:400,
   height:150,
   shadow:'#333 0px 0px 8px',
   round:6,
   title:'提示',
   title_height:30,
   title_background:'#EFEFF0',
   title_color:'#000',
   title_border_color:'#BBB',
   title_border_height:1,
   close_icon:'fa fa-times',
   close_background:'#EFEFF0',
   close_color:'#000',
   close_hover_background:'#EFEFF0',
   close_hover_color:'#000',
   content:'',
   content_background:'#FFF',
   footer_height:40,
   footer_background:'#FFF',
   footer_border_color:'#EEE',
   footer_border_height:1,
   button_skin:'Default',
   button_background:'#FBFBFB',
   button_border:'1px solid #CCC',
   button_color:'#333',
   button_round:4,
   button_background_hover:'#E6E6E6',
   button_border_hover:'1px solid #ADADAD',
   button_color_hover:'#333',
   button_round_hover:4,
   button_label:'确定',
   button_icon:null,
   callback:function(){}
  };
  var _create = function(obj){
    var cfg = obj.config;
    var btn = I.ui.Button.create({
      dom:obj.footerBar,
      skin:cfg.button_skin,
      label:cfg.button_label,
      border:cfg.button_border,
      border_hover:cfg.button_border_hover,
      background:cfg.button_background,
      background_hover:cfg.button_background_hover,
      color:cfg.button_color,
      color_hover:cfg.button_color_hover,
      icon:cfg.button_icon,
      round:cfg.button_round,
      round_hover:cfg.button_round_hover,
      callback:function(){
        obj.close();
      }
    });
    var r = I.region(btn.dom);
    var space = Math.floor((cfg.footer_height-r.height)/2);
    I.util.Boost.addStyle(btn.dom,'position:absolute;right:'+space+'px;top:'+space+'px;');
    obj.buttonOK = btn;

    var html = cfg.content;
    if(html.indexOf('<')<0){
      html = '<table width="100%" height="100%"><tbody><tr><td width="100%" height="100%" align="center" valign="middle">'+html+'</td></tr></tbody></table>';
    }
    obj.contentPanel.innerHTML = html;
  };

  var _prepare = function(config){
    var cfg = I.ui.Component.initConfig(config,CFG);
    var obj = I.z.Win.create(cfg);
    _create(obj);
    return obj;
  };
  return {
    create:function(cfg){return _prepare(cfg);}
  };
}+'');