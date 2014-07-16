/**
 * I.z.Alert
 * <i>Alert提示框</i>
 */
I.regist('z.Alert',function(W,D){
  var CFG = {
    skin:'Default',
    width:400,
    height:150,
    title:'提示',
    title_height:30,
    content:'',
    button_label:'确定',
    button_border:'1px solid #DDD',
    button_border_hover:'1px solid #999',
    button_background:'#E9E9E9',
    button_background_hover:'#FBFBFB',
    button_color:'#333',
    button_color_hover:'#333',
    callback:function(){}
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
      label:cfg.button_label,
      border:cfg.button_border,
      border_hover:cfg.button_border_hover,
      background:cfg.button_background,
      background_hover:cfg.button_background_hover,
      color:cfg.button_color,
      color_hover:cfg.button_color_hover,
      callback:function(){
        obj.close();
      }
    });
    btn.dom.style.position = 'absolute';
    var r = I.region(btn.dom);
    var space = Math.floor((40-r.height)/2);
    btn.dom.style.right = space+'px';
    btn.dom.style.top = space+'px';
    obj.buttonOK = btn;
    
    var html = obj.config.content;
    if(html.indexOf('<')<0){
      html = '<table width="100%" height="'+(obj.config.height-40)+'"><tbody><tr><td width="100%" height="100%" align="center" valign="middle">'+html+'</td></tr></tbody></table>';
    }
    var m = I.insert('div',obj.contentPanel);
    m.innerHTML = html;
    obj.contentPanel = m;
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