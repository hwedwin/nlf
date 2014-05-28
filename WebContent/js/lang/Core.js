I.regist({
  'name':'lang.Core',
  'need':null,
  'fn':function(W,D){
    var T = ['BREAK','CONTINUE','RETURN'];
    var EACH = {'[object Array]':1,'[object NodeList]':1,'[object HTMLCollection]':1,'[object Arguments]':1};
    var C = [
      function(o,s){o.style.cssText = s;},
      function(o,s){o.setAttribute('style',s);}
    ];
    var S = [
      function(o,s){o.className = s;},
      function(o,s){o.setAttribute('class',s);}
    ];
    var STYLE = [
      function(s){
        var o = D.createStyleSheet();
        o.cssText = s;
      },
      function(s){
        var o = D.createElement("style");
        o.type = "text/css";
        o.innerHTML = s; 
        D.getElementsByTagName("head")[0].appendChild(o);
      }
    ];
    var G = {
      'ID':function(o,s){return o.getElementById(s);},
      'NAME':function(o,s){return o.getElementsByName(s);},
      'TAG':function(o,s){return o.getElementsByTagName(s);},
      'CHILD':function(o){
        var c = [];
        each(o.childNodes,function(m,i){if(1 == m.nodeType) c.push(m);});
        return c;
      },
      'CLASS':function(){
        var a = arguments;
        var c = [];
        for(var i=0;i<a[0].childNodes.length;i++){
          m = a[0].childNodes[i];
          if(m.getAttribute){
            if(a[1] == m.getAttribute('class') || a[1] == m.getAttribute('className')) c.push(m);
            each(a.callee(m,a[1]),function(o,j){
              c.push(o);
            });
          }
        }
        return c;
      }
    };
    var A = {
      'IN':function(a,b){b.appendChild(a);},
      'BEFORE':function(a,b){b.parentNode.insertBefore(a,b);},
      'AFTER':function(a,b){b.parentNode.lastChild == b?this['IN'](a,b.parentNode):this['BEFORE'](a,b.nextSibling);}
    };
  var N = function(d,o,s){
    each(d,function(f,i){
      try{
        f(o,s);
        return T[2];
      }catch(e){}
    });
  };
  var $ = function(o){
    switch(o.length){
      case 1: return typeof o[0] == 'string'?('*'==o[0]?G['CHILD'](D.body):G['ID'](D,o[0])):o[0];
      case 2: return '*'==o[1]?G['CHILD']($([o[0]])):G[o[0].toUpperCase()](D,o[1]);
      case 3: return G[o[1].toUpperCase()]($([o[0]]),o[2]);
    }
    return D;
  };
  var insert = function(o){
    if(o.length<1) return null;
    var m = typeof o[0] == 'string'?D.createElement(o[0]):o[0];
    switch(o.length){
      case 1: A['IN'](m,D.body);break;
      case 2: A['IN'](m,o[1]);break;
      case 3: A[o[1].toUpperCase()](m,o[2]);break;
    }
    return m;
  };
  function region(o){
    var x = 0,y = 0,w = 0,h = 0;
    switch(o.length){
      case 1:
        var r = region([]);
        var m = o[0];
        w = m.offsetWidth;
        h = m.offsetHeight;
        if(m.getBoundingClientRect){
          x = m.getBoundingClientRect().left + r.x - D.documentElement.clientLeft;
          y = m.getBoundingClientRect().top + r.y - D.documentElement.clientTop;
        }else for(;m;x+=m.offsetLeft,y+=m.offsetTop,m=m.offsetParent);
        break;
      default:
        x = Math.max(D.documentElement.scrollLeft,D.body.scrollLeft);
        y = Math.max(D.documentElement.scrollTop,D.body.scrollTop);
        w = D.documentElement.clientWidth;
        h = D.documentElement.clientHeight;
    }
    return {'x':x,'y':y,'width':w,'height':h};
  }
  var listen = function(o,s,f){
    o = I.$(o);
    try{o.attachEvent('on'+s,function(e){f(o,W.event || e);});}catch(e){
      try{o.addEventListener(s,function(e){f(o,W.event || e);},false);}catch(ex){}
    }
  };
  var each = function(o,f){
    var tp = Object.prototype.toString.apply(o);
    if((EACH[tp])||(o.length&&(!o.alert)&&('[object String]'!=tp))){
      a:for(var i=0;i<o.length;i++){
        switch(f(o[i],i)){
          case T[0]:break a;
          case T[1]:continue a;
          case T[2]:return;
        }
      }
    }else{
      f(o,0);
    }
  };
  var opacity = function(o,n){
    try{
      o.style.filter='alpha(opacity='+n+')';
    }catch(e){}
    try{
      o.style.opacity = n/100;
    }catch(e){}
  };
   
    I['BREAK'] = T[0];
    I['CONTINUE'] = T[1];
    I['RETURN'] = T[2];
    I['$'] = function(){return $(arguments);};
    I['region'] = function(){return region(arguments);};
    I['css'] = function(o,s){
      each(o,function(m,i){
        N(C,m,s);
      });
      return I;
    };
    I['cls'] = function(o,s){
      each(o,function(m,i){
        N(S,m,s);
      });
      return I;
    };
    I['trim'] = function(s){return s.replace(/(^\s*)|(\s*$)/g,'');};
    I['insert'] = function(){return insert(arguments);};
    I['listen'] = function(o,s,f){
      each(o,function(m,i){listen(m,s,f);});
      return f;
    };
    I['each'] = function(o,f){each(o,f);return I;};
    I['opacity'] = function(o,n){opacity(o,n);return I;};
    I['style'] = function(s){
      N(STYLE,s);
      return I;
    };
    return {
      'version':'1.0.1'
    };
  }
});