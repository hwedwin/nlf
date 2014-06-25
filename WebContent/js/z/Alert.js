/**
 * I.z.Alert
 * <i>Alert提示框</i>
 */
I.regist('z.Alert',function(W,D){
  var CFG = {
    skin:'Default',
    mask:true,
    width:400,
    height:150,
    shadow_size:6,
    title:'提示',
    title_height:30,
    content:'',
    callback:function(){}
  };
  var _create = function(obj){
    obj.contentPanel.innerHTML = '';
    var o = I.insert('div',obj.contentPanel);
    I.css(o,'position:absolute;margin:0;padding:0;left:0;bottom:0;height:40px;overflow:hidden;width:100%;border-top:1px solid #EEE;');
    obj.bottomPanel = o;
    var btn = I.ui.Button.create({dom:o,skin:obj.config.skin,callback:function(){obj.close();}});
    btn.layer.style.position = 'absolute';
    var r = I.region(btn.layer);
    var space = Math.floor((40-r.height)/2);
    btn.layer.style.right = space+'px';
    btn.layer.style.top = space+'px';
    
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