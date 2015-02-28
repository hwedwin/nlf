I.regist('z.Confirm',function(W,D){
var CFG={
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
 footer_height:60,
 footer_background:'#FFF',
 footer_border_color:'#EEE',
 footer_border_height:1,
 no_button_skin:'Default',
 no_button_background:'#FBFBFB',
 no_button_border:'1px solid #CCC',
 no_button_color:'#333',
 no_button_round:4,
 no_button_background_hover:'#E6E6E6',
 no_button_border_hover:'1px solid #ADADAD',
 no_button_color_hover:'#333',
 no_button_round_hover:4,
 no_button_label:'取消',
 no_button_icon:null,
 yes_button_skin:'Default',
 yes_button_background:'#FBFBFB',
 yes_button_border:'1px solid #CCC',
 yes_button_color:'#333',
 yes_button_round:4,
 yes_button_background_hover:'#E6E6E6',
 yes_button_border_hover:'1px solid #ADADAD',
 yes_button_color_hover:'#333',
 yes_button_round_hover:4,
 yes_button_label:'确定',
 yes_button_icon:null,
 callback:function(){},
 yes:function(){},
 no:function(){}
};
var _create=function(obj){
 var cfg=obj.config;
 var btnYes=I.ui.Button.create({
  dom:obj.footerBar,
  skin:cfg.yes_button_skin,
  label:cfg.yes_button_label,
  border:cfg.yes_button_border,
  border_hover:cfg.yes_button_border_hover,
  background:cfg.yes_button_background,
  background_hover:cfg.yes_button_background_hover,
  color:cfg.yes_button_color,
  color_hover:cfg.yes_button_color_hover,
  icon:cfg.yes_button_icon,
  round:cfg.yes_button_round,
  round_hover:cfg.yes_button_round_hover,
  callback:function(){
   obj.config.yes.call(obj);
  }
 });
 var r=I.region(btnYes.dom);
 var space=Math.floor((cfg.footer_height-r.height)/2);
 I.util.Boost.addStyle(btnYes.dom,'position:absolute;right:'+space+'px;top:'+space+'px;');
 obj.buttonYES=btnYes;
 var btnNo=I.ui.Button.create({
  dom:obj.footerBar,
  skin:cfg.no_button_skin,
  label:cfg.no_button_label,
  border:cfg.no_button_border,
  border_hover:cfg.no_button_border_hover,
  background:cfg.no_button_background,
  background_hover:cfg.no_button_background_hover,
  color:cfg.no_button_color,
  color_hover:cfg.no_button_color_hover,
  icon:cfg.no_button_icon,
  round:cfg.no_button_round,
  round_hover:cfg.no_button_round_hover,
  callback:function(){
   obj.config.no.call(obj);
   obj.close();
  }
 });
 I.util.Boost.addStyle(btnNo.dom,'position:absolute;right:'+(space+space+r.width)+'px;top:'+space+'px;');
 obj.buttonNO=btnNo;
 var html=obj.config.content;
 if(html.indexOf('<')<0){
  html='<table width="100%" height="100%"><tbody><tr><td width="100%" height="100%" align="center" valign="middle">'+html+'</td></tr></tbody></table>';
 }
 obj.contentPanel.innerHTML=html;
 I.listen(obj.closeButton,'click',function(){
  obj.config.no.call(obj);
 });
};
var _prepare=function(config){
 var cfg=I.ui.Component.initConfig(config,CFG);
 var obj=I.z.Win.create(cfg);
 _create(obj);
 return obj;
};
return{
 create:function(cfg){return _prepare(cfg);}
};
}+'');