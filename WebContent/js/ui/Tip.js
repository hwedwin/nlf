I.regist('ui.Tip',function(W,D){
  var _cssState = {};
  var TIME = {
    '0':8000,
    '1':3000,
    '2':1000
  };
  var CSS = function(){/*
.${skin}{position:absolute;margin:0;padding:0;left:0;top:0;}
.${skin} b{display:block;margin:0;padding:0;font-size:0;text-indent:0;margin-left:5px;margin-right:5px;height:1px;background-color:${bgcolor}}'
.${skin} i{display:block;margin:0;padding:0;font-size:0;text-indent:0;margin-left:3px;margin-right:3px;height:1px;border:0;border-left:2px solid ${bgcolor};border-right:2px solid ${bgcolor};background-color:${bgcolor}}
.${skin} u{display:block;margin:0;padding:0;font-size:0;text-indent:0;margin-left:2px;margin-right:2px;height:1px;border:0;border-left:1px solid ${bgcolor};border-right:1px solid ${bgcolor};background-color:${bgcolor}}
.${skin} q{display:block;margin:0;padding:0;font-size:0;text-indent:0;margin-left:1px;margin-right:1px;height:2px;border:0;border-left:1px solid ${bgcolor};border-right:1px solid ${bgcolor};background-color:${bgcolor}}
.${skin} p{display:block;margin:0;padding:0;font-size:1em;text-indent:0;text-align:center;height:2em;line-height:2em;overflow:hidden;border:0;border-left:1px solid ${bgcolor};border-right:1px solid ${bgcolor};background-color:${bgcolor};color:${fgcolor};padding-left:1em;padding-right:1em}
  */}+'';
  var CFG = {
    bgcolor:'#000',
    fgcolor:'#FFF',
    msg:'',
    type:1,
    callback:function(){}
  };
  var _css = function(cssName,cfg){
    var s = CSS;
    s = s.substr('function(){/*'.length+1);
    s = s.substr(0,s.length-'*/}'.length);
    s = s.replace(/\${skin}/g,cssName);
    s = s.replace(/\${bgcolor}/g,cfg.bgcolor);
    s = s.replace(/\${fgcolor}/g,cfg.fgcolor);
    return s;
  };
  var Q = {};
  var _create = function(config){
    var cfg = {};
    var tcfg = config;
    if(!tcfg){
      tcfg = {};
    }
    for(var i in CFG){
      if('msg'==i){
        if(tcfg[i]){
          cfg[i] = (undefined!=tcfg[i])?tcfg[i]:CFG[i];
        }else{
          cfg[i] = tcfg;
        }
      }else{
        cfg[i] = (undefined!=tcfg[i])?tcfg[i]:CFG[i];
      }
    }
    
    var obj = null;
    var init = function(){
      var cssName = ['i-ui-Tip',cfg.bgcolor.replace(/#/g,''),cfg.fgcolor.replace(/#/g,'')].join('-');
      if(!_cssState[cssName]){     
        I.style(_css(cssName,cfg));
        _cssState[cssName] = true;
      }
      var l = 0;
      while(Q[l+'']){
        l++;
      }
      var tip = {layer:null,timer:null,idx:l,config:cfg,cssName:cssName};
      var o = D.createElement('div');
      I.cls(o,cssName);
      o.innerHTML = '<b></b><i></i><u></u><q></q><p></p><q></q><u></u><i></i><b></b>';
      I.$(o,'tag','p')[0].innerHTML = cfg.msg;
      D.body.appendChild(o);
      var nr = I.region(o);
      var pr = I.region();
      o.style.left = Math.max(Math.floor(pr.x+pr.width/2-nr.width/2),0)+'px';
      o.style.top = Math.floor(pr.y+10)+'px';
      tip.layer = o;
      var op = 0;
      I.opacity(o,op);
      tip.timer = W.setInterval(function(){
        if(op<100){
          op+=2;
          I.opacity(o,op);
        }else{
          I.opacity(o,100);
          W.clearInterval(tip.timer);
          tip.timer = W.setTimeout(function(){
            tip.layer.parentNode.removeChild(tip.layer);
              delete Q[''+tip.idx];
          },TIME[cfg.type]);
        }
        cfg.callback.apply(tip,cfg);
      },20);
      Q[tip.idx] = 'true';
      obj = tip;
    };
     
    init();
    return obj;
  };
    
  return {
    create:function(msg){return _create(msg);}
  };
}+'');