/**
 * I.ui.ToolTip
 * <i>提示条</i>
 */
I.regist('ui.ToolTip',function(W,D){
  var _cssState = {};
  var CSS = function(){/*
.${skin}{position:absolute;margin:0;padding:0;left:0;top:0;z-index:2147483647;}
.${skin} b{font-weight:normal;font-style:normal;display:none;}
.${skin} b.c{display:block;margin:0;padding:0;border:1px solid ${border_color};background-color:${bgcolor};-webkit-border-radius: 6px;-moz-border-radius: 6px;border-radius: 6px;}
.${skin} b.content{display:block;margin:10px;padding:0;color:${fgcolor};}
.${skin} b.s{display:block;margin:0;padding:0;margin-left:10px;margin-top:-1px;height:10px;overflow:hidden;}
.${skin} b.arrow-s0{display:block;margin:0;padding:0;margin-top:-12px;font-family:SimSun;overflow:hidden;font-size:20px;color:${border_color};}
.${skin} b.arrow-s1{display:block;margin:0;padding:0;margin-top:-24px;font-family:SimSun;overflow:hidden;font-size:20px;color:${bgcolor};}
.${skin} b.n{display:block;margin-left:10px;height:10px;}
.${skin} b.arrow-n0{display:block;margin:0;padding:0;margin-top:-12px;font-family:SimSun;overflow:hidden;font-size:20px;color:${border_color};}
.${skin} b.arrow-n1{display:block;margin:0;padding:0;margin-top:-22px;font-family:SimSun;overflow:hidden;font-size:20px;color:${bgcolor};}
  */}+'';
  var Q = {};
  var CFG = {
    bgcolor:'#FEFEE9',
    fgcolor:'#C40000',
    border_color:'#B1B1B1',
    target:'',
    content:'',
    callback:function(){}
  };
  var _css = function(cssName,cfg){
    var s = CSS;
    s = s.substr('function(){/*'.length+1);
    s = s.substr(0,s.length-'*/}'.length);
    s = s.replace(/\${skin}/g,cssName);
    s = s.replace(/\${bgcolor}/g,cfg.bgcolor);
    s = s.replace(/\${fgcolor}/g,cfg.fgcolor);
    s = s.replace(/\${border_color}/g,cfg.border_color);
    return s;
  };
  var create = function(config){
    var cfg = {};
    var tcfg = config;
    if(!tcfg){
      tcfg = {};
    }
    for(var i in CFG){
      if('content'==i){
        if(tcfg[i]){
          cfg[i] = (undefined!=tcfg[i])?tcfg[i]:CFG[i];
        }else{
          cfg[i] = tcfg;
        }
      }else{
        cfg[i] = (undefined!=tcfg[i])?tcfg[i]:CFG[i];
      }
    }
    
    var obj = {};
    var instance = null;
    var init = function(){
      var cssName = ['i-ui-ToolTip',cfg.bgcolor.replace(/#/g,''),cfg.fgcolor.replace(/#/g,''),cfg.border_color.replace(/#/g,'')].join('-');
      if(!_cssState[cssName]){
        I.style(_css(cssName,cfg));
        _cssState[cssName] = true;
      }
      var l = 0;
      while(Q[''+l]){
        l++;
      }
      var tip = {'layer':null,'idx':l,'timer':null,'func':cfg.callback};
      var pr = I.region(cfg.target);
      var o = I.insert('div');
      I.cls(o,cssName);
      o.innerHTML = '<b class="n"><b class="arrow-n0">&#9670;</b><b class="arrow-n1">&#9670;</b></b><b class="c"><b class="content"></b></b><b class="s"><b class="arrow-s0">&#9670;</b><b class="arrow-s1">&#9670;</b></b>';
      I.$(o,'class','content')[0].innerHTML = cfg.content;
      tip.layer = o;

      var sl = I.region(tip.layer);
      var y = pr.y-sl.height+6;
      tip.layer.style.left = (pr.x)+'px';
      tip.layer.style.top = y+'px';
      var r = I.region();
      if(y<r.y){
        I.$(o,'class','s')[0].style.display = 'none';
        y = pr.y+pr.height+6;
        tip.layer.style.top = y+'px';
      }else{
        I.$(o,'class','n')[0].style.display = 'none';
      }
      I.listen(tip.layer,'click',function(m,e){
        obj.close();
      });
      I.listen(cfg.target,'focus',function(m,e){
        obj.close();
      });
      I.listen(cfg.target,'click',function(m,e){
        obj.close();
      });
        
      Q[''+tip.idx] = 'true';
      instance = tip;
    };
     
    init();
    obj['close'] = function(){
      var inst = this.getInstance();
      try{
        if(inst.layer){
          inst.layer.parentNode.removeChild(inst.layer);
          inst.layer = null;
        }
      }catch(e){}
      if(inst.func){
        inst.func();
      }
      delete Q['I'+inst.idx];
    };
    obj['getInstance'] = function(){return instance;};
    I.listen(W,'resize',function(m,e){
      try{
        obj.close();
      }catch(ex){}
    });
    return obj;
  };
    
  return {
    create:function(cfg){return create(cfg);}
  };
}+'');