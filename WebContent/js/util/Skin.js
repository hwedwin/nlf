/**
 * I.util.Skin
 * <i>皮肤管理器</i>
 */
I.regist('util.Skin',function(W,D){
  var _cache = {};
  var _css = function(css,skinName){
    var s = css;
    s = s.substr('function(){/*'.length+1);
    s = s.substr(0,s.length-'*/}'.length);
    s = s.replace(/\${skin}/g,skinName);
    return s;
  };
  var _init = function(skinName){
    if(_cache[skinName]){
      return;
    }
    var skin = I.skin[skinName];
    I.style(_css(skin.getCss(),skinName));
    _cache[skinName] = true;
  };
  return {
    init:function(skinName){_init(skinName);}
  };
}+'');