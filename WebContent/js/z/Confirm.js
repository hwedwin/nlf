/**
 * I.z.Confirm
 * <i>确定取消提示框</i>
 */
I.regist('z.Confirm',function(W,D){
  var CFG = {
    skin:'Default',
    width:400,
    height:150,
    title:'提示',
    title_height:30,
    content:'',
    yes_button_label:'确定',
    yes_button_border:'1px solid #0074D9',
    yes_button_border_hover:'1px solid #0074D9',
    yes_button_background:'#0074D9',
    yes_button_background_hover:'#0068C3',
    yes_button_color:'#FFF',
    yes_button_color_hover:'#FFF',
    no_button_label:'取消',
    no_button_border:'1px solid #DDD',
    no_button_border_hover:'1px solid #999',
    no_button_background:'#E9E9E9',
    no_button_background_hover:'#FBFBFB',
    no_button_color:'#333',
    no_button_color_hover:'#333',
    callback:function(){},
    yes:function(){},
    no:function(){}
  };
  var _create = function(obj){
    obj.contentPanel.innerHTML = '';
    var o = I.insert('div',obj.contentPanel);
    I.css(o,'position:absolute;margin:0;padding:0;left:0;bottom:0;height:40px;overflow:hidden;width:100%;border-top:1px solid #EEE;');
    obj.bottomPanel = o;
    var cfg = obj.config;
    var btn = I.ui.Button.create({
      dom:o,
      skin:cfg.skin,
      label:cfg.yes_button_label,
      border:cfg.yes_button_border,
      border_hover:cfg.yes_button_border_hover,
      background:cfg.yes_button_background,
      background_hover:cfg.yes_button_background_hover,
      color:cfg.yes_button_color,
      color_hover:cfg.yes_button_color_hover,
      callback:function(){
        obj.close();
        obj.config.yes.call(obj);
      }
    });
    btn.dom.style.position = 'absolute';
    var r = I.region(btn.dom);
    var space = Math.floor((40-r.height)/2);
    btn.dom.style.right = space+'px';
    btn.dom.style.top = space+'px';
    obj.buttonYES = btn;
    
    var btn1 = I.ui.Button.create({
      dom:o,
      skin:cfg.skin,
      label:cfg.no_button_label,
      border:cfg.no_button_border,
      border_hover:cfg.no_button_border_hover,
      background:cfg.no_button_background,
      background_hover:cfg.no_button_background_hover,
      color:cfg.no_button_color,
      color_hover:cfg.no_button_color_hover,
      callback:function(){
        obj.close();
        obj.config.no.call(obj);
      }
    });
    btn1.dom.style.position = 'absolute';
    var r1 = I.region(btn1.dom);
    var space1 = Math.floor((40-r1.height)/2);
    btn1.dom.style.right = (space+space1+r.width)+'px';
    btn1.dom.style.top = space1+'px';
    obj.buttonNO = btn1;
    
    var html = obj.config.content;
    if(html.indexOf('<')<0){
      html = '<table width="100%" height="'+(obj.config.height-40)+'"><tbody><tr><td width="100%" height="100%" align="center" valign="middle">'+html+'</td></tr></tbody></table>';
    }
    var m = I.insert('div',obj.contentPanel);
    m.innerHTML = html;
    obj.contentPanel = m;
    I.listen(obj.closeButton,'click',function(m,e){
      obj.config.no.call(obj);
    });
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