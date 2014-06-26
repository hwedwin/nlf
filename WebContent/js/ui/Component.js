/**
 * I.ui.Component
 * <i>组件</i>
 */
I.regist('ui.Component',function(W,D){
  
  var _initConfig = function(config,defaultCfg){
    var cfg = {};
    var tcfg = config;
    if(!tcfg){
      tcfg = {};
    }
    for(var i in defaultCfg){
      cfg[i] = (undefined!=tcfg[i])?tcfg[i]:defaultCfg[i];
    }
    for(var i in tcfg){
      cfg[i] = tcfg[i];
    }
    return cfg;
  };

  return {
    initConfig:function(cfg,defaultCfg){return _initConfig(cfg,defaultCfg);}
  };
}+'');